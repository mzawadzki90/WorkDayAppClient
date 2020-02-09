package michal.zawadzki.workdayappclient;

import michal.zawadzki.workdayappclient.api.leave.LeaveRequestsDto;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.util.Collections;

public class WorkdayappClient {

    private final RestTemplate restTemplate;

    public WorkdayappClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LeaveRequestsDto listByWorkerId(int workerId) {
        final URI uri = new DefaultUriBuilderFactory().builder().scheme("http").host("localhost").port(8080)
                                                      .path("api/leave/requests/worker/{workerId}")
                                                      .build(
                                                              Collections.singletonMap("workerId",
                                                                                       workerId));
        return restTemplate.getForObject(uri, LeaveRequestsDto.class);
    }

}
