package org.ametiste.sns.reports;

import org.ametiste.ifaces.api.error.CommonApiError;
import org.ametiste.shared.test.IntegrationTestUtil;
import org.ametiste.sns.reports.interfaces.api.NoReportsError;
import org.ametiste.sns.reports.interfaces.api.ReportNotFoundError;
import org.ametiste.sns.reports.interfaces.facade.dto.ReportDTO;
import org.ametiste.sns.reports.model.Report;
import org.ametiste.sns.reports.model.ReportRepository;
import org.apache.commons.lang.SerializationUtils;
import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { 
		"classpath:spring/report-service-servlet.xml", "classpath:spring/app-config.xml" }
)
@ActiveProfiles(profiles = { 
		"org.ametiste.environment:development:dev-embedded" }
)
public class SyncReportServiceIntegrationTest {

	static {
		System.setProperty("org.ametiste.reports.distributer.service.url", "http://localhost:9000/");
	}

	@Autowired
	private WebApplicationContext wac;

    @Autowired
    @Qualifier("reportRepository")
    private ReportRepository transientReportRepository;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}

	@BeforeClass
	public static void setupClass() {
		HashMap<String, Object> report = new HashMap<String, Object>();
		report.put("test_field", "value");
		report.put("another_field", "another_value");
	}

	/**
	 * 
	 * Test case where new report successfully created with default persistence strategy.
     *
     * Note: persistence does not set through report parameters, PERSISTED report must be created by deafult.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateNewReportWithPersistedPersistenceStrategy() throws Exception {

		UUID reportId = UUID.randomUUID();

		Date date = new Date();

		String sender = "INTEGRATION_TEST";
		String type = "INT_TEST_REPORT";

		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("test_field", "created value");
		content.put("another_field", "another_value created");

		this.mockMvc.perform(put("/reports/" + reportId.toString())
			.contentType(MediaType.APPLICATION_JSON)
			.content(createReportJSON(reportId, Report.PERSISTED_REPORT, date, sender, type, content))
			.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(header().string("Ame-Request-Id", CoreMatchers.notNullValue()));
	}


    /**
     *
     * Test case where new report successfully created with transient persistence strategy.
     *
     * Report must be created and placed to transient reports repository.
     *
     * @throws Exception
     */
    @Test
    public void testCreateNewReportWithTransientPersistenceStrategy() throws Exception {

        UUID reportId = UUID.randomUUID();

        Date date = new Date();

        String sender = "INTEGRATION_TEST";
        String type = "INT_TEST_REPORT";

        HashMap<String, Object> content = new HashMap<String, Object>();
        content.put("test_field", "created value");
        content.put("another_field", "another_value created");

        this.mockMvc.perform(put("/reports/" + reportId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(createReportJSON(reportId, date, sender, type, content))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Ame-Request-Id", CoreMatchers.notNullValue()));

        final Report reportById = transientReportRepository.getReportById(reportId);

        assertNotNull(reportById);

    }

    /**
     *
     * Test case where new report successfully created with transient persistence strategy.
     *
     * And then successfully retrieved by id using http-api.
     *
     * @throws Exception
     */
    /*
    @Test
    public void testCreateAndGetNewReportWithTransientPersistenceStrategy() throws Exception {

        UUID reportId = UUID.randomUUID();

        Date date = new Date();

        String sender = "INTEGRATION_TEST";
        String type = "INT_TEST_REPORT";

        HashMap<String, Object> content = new HashMap<String, Object>();
        content.put("test_field", "created value");
        content.put("another_field", "another_value created");

        this.mockMvc.perform(put("/reports/" + reportId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(createReportJSON(reportId, date, sender, type, content))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Ame-Request-Id", CoreMatchers.notNullValue()));

        final Report reportById = transientReportRepository.getReportById(reportId);

        assertNotNull(reportById);

        this.mockMvc.perform(get("/reports/{reportId}", reportId.toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonField("id", reportId.toString()))
                        //	        TODO: check this out, how to check dates?
                        //	        .andExpect(jsonField("date", ""))
                .andExpect(jsonField("type", type))
                .andExpect(jsonField("sender", sender))
                .andExpect(jsonField("content.test_field", "created value"))
                .andExpect(jsonField("content.another_field", "another_value created"))
                .andExpect(header().string("Ame-Request-Id", CoreMatchers.notNullValue()));

    } */

	/**
	 * 
	 * Test case where new report successfully created.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdatePersistedReport() throws Exception {

		UUID reportId = UUID.fromString("00000000-b199-4f24-b89f-f117362f52a1");

		Date date = new Date();

		String sender = "INTEGRATION_TEST";
		String type = "INT_TEST_REPORT";

		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("test_field", "new report value");
		content.put("another_field", "yep too");

		this.mockMvc.perform(put("/reports/" + reportId.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(createReportJSON(reportId, Report.PERSISTED_REPORT, date, sender, type, content))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(header().string("Ame-Request-Id", CoreMatchers.notNullValue()));

	}

	/**
	 * 
	 * Test case where new report can't be created cos internal error.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCantCreateNewDefaultReport() throws Exception {

		UUID reportId = UUID.randomUUID();

		Date date = new Date();

		String sender = "INTEGRATION_TEST";
		String type = "NOT_A_SIMPLE_REPORT";

		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("test_field", "value");
		content.put("another_field", "another_value");

		this.mockMvc.perform(put("/reports/" + reportId).contentType(MediaType.APPLICATION_JSON)
			.content(createReportJSON(reportId, date, sender, type, content))
			.accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(errorCode(CommonApiError.INTERNAL_ERROR.getCode()))
			.andExpect(jsonPath("$.request_id").exists())
			.andExpect(header().string("Ame-Request-Id", CoreMatchers.notNullValue()));
	}

	/**
	 * 
	 * Test case where new report can't be created cos internal error.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReportDoesNotExists() throws Exception {

		String reportId = "00000000-0000-4f24-b89f-f117362f52a3";

		this.mockMvc.perform(get("/reports/{reportId}", reportId).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(errorCode(ReportNotFoundError.CODE))
			.andExpect(errorField(ReportNotFoundError.REPORT_ID_FIELD, reportId))
			.andExpect(jsonPath("$.request_id").exists())
			.andExpect(header().string("Ame-Request-Id", CoreMatchers.notNullValue()));
	}

	/*
	 * That test case checks content negotiation resolving rules:
	 * 
	 * path extension must be resolved first, than Accept header.
	 * 
	 * @throws Exception
	 */
    /*
	@Test
	public void testContentNegotiationResolvingForPersistedReports() throws Exception {

		this.mockMvc.perform(get("/reports/{reportId}.html", "00000000-0000-4f24-b89f-f117362f52a3")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotAcceptable())
			.andExpect(header().string("Ame-Request-Id", CoreMatchers.notNullValue()));

		this.mockMvc.perform(get("/reports/{reportId}.json", "00000000-0000-4f24-b89f-f117362f52a3")
			.accept(MediaType.TEXT_HTML))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(header().string("Ame-Request-Id", CoreMatchers.notNullValue()));

		this.mockMvc.perform(get("/reports/{reportId}", "00000000-0000-4f24-b89f-f117362f52a3")
				.accept(MediaType.ALL))
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(header().string("Ame-Request-Id", CoreMatchers.notNullValue()));
		
	} */

    /*
	@Test
	public void testGetPersistedReport() throws Exception {

		String reportId = "00000000-b199-4f24-b89f-f117362f52a1";

		this.mockMvc.perform(get("/reports/{reportId}", reportId).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonField("id", reportId))
			//	        TODO: check this out, how to check dates?
			//	        .andExpect(jsonField("date", ""))
			.andExpect(jsonField("type", "INT_TEST_REPORT")).andExpect(jsonField("sender", "INTEGRATION_TEST"))
			.andExpect(jsonField("content.test_field", "new report value"))
			.andExpect(jsonField("content.another_field", "yep too"))
			.andExpect(header().string("Ame-Request-Id", CoreMatchers.notNullValue()));
	} */

	private ResultMatcher jsonField(String fieldName, Object value) {
		return jsonPath("$." + fieldName).value(value);
	}

	private ResultMatcher errorCode(String errorCode) {
		return jsonPath("$.code").value(errorCode);
	}

	private ResultMatcher errorField(String fieldName, Object value) {
		return jsonPath("$." + fieldName).value(value);
	}

	private byte[] createReportJSON(UUID reportId, Date date, String sender, String type, HashMap<String, Object> content) {
		return createReportJSON(reportId, null, date, sender, type, content);
	}

    private byte[] createReportJSON(UUID reportId, String persistenceStrategy, Date date, String sender, String type, HashMap<String, Object> content) {

        ReportDTO reportDTO = new ReportDTO();

        reportDTO.setId(reportId);
        reportDTO.setDate(date);
        reportDTO.setSender(sender);
        reportDTO.setType(type);
        reportDTO.setContent(content);

        if (persistenceStrategy != null) {
            reportDTO.setPersistenceStrategy(persistenceStrategy);
        }

        return IntegrationTestUtil.convertObjectToJSONBytes(reportDTO);
    }

}
