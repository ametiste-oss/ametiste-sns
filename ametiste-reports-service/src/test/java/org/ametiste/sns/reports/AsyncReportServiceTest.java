package org.ametiste.sns.reports;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = { "org.ametiste.environment.multithread",
		"org.ametiste.environment:test:async",
		"org.ametiste.environment:development:dev-embedded" })
public class AsyncReportServiceTest extends SyncReportServiceIntegrationTest {
	
	@Override
	@Ignore
	@Test
	public void testCantCreateNewDefaultReport() throws Exception {
		super.testCantCreateNewDefaultReport();
	}
	
}
