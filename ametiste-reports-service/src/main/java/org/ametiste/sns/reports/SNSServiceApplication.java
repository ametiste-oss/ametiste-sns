package org.ametiste.sns.reports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 *
 * @since
 */
@SpringBootApplication
public class SNSServiceApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SNSServiceApplication.class, args);
    }


}
