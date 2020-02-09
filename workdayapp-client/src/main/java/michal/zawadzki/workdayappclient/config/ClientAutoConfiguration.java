package michal.zawadzki.workdayappclient.config;

import michal.zawadzki.workdayappclient.WorkdayappClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientAutoConfiguration {

    @Bean
    public WorkdayappClient workdayappClient(RestTemplate restTemplate) {
        return new WorkdayappClient(restTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
