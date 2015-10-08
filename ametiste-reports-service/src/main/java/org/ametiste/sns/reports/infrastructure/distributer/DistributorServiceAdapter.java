package org.ametiste.sns.reports.infrastructure.distributer;

import org.ametiste.sns.reports.application.ReportService;
import org.ametiste.sns.reports.interfaces.facade.internal.assembler.ReportDTOAssembler;
import org.ametiste.sns.reports.model.Report;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

/**
 * @since 0.1.0
 */
public class DistributorServiceAdapter implements ReportService {

    private final RestTemplate restTemplate = new RestTemplate(
        Arrays.asList(new MappingJackson2HttpMessageConverter())
    );

    private final String suroURI;

    private final ReportService reportService;

    // TODO: this bean exist in the context
    private final ReportDTOAssembler reportDTOAssembler = new ReportDTOAssembler();

    public DistributorServiceAdapter(String suroURI, ReportService reportService) {
        this.suroURI = suroURI;
        this.reportService = reportService;
    }

    @Override
    public void storeReport(Report report) {

        try {
            sendToSuro(report);
        } catch (RuntimeException e) {
            // NOTE: just catch em all, we don't want affect local report service yet.
        }

        reportService.storeReport(report);
    }

    private void sendToSuro(Report report) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        restTemplate.postForObject(suroURI, new HttpEntity(report, headers), Map.class);
    }

}
