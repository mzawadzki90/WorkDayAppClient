package michal.zawadzki.workdayappclient;

import michal.zawadzki.workdayappclient.api.DictionariesDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestsDto;
import michal.zawadzki.workdayappclient.api.worker.login.CredentialDto;
import michal.zawadzki.workdayappclient.api.worker.login.WorkerLoginDto;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static java.util.Collections.singletonMap;

public class WorkdayappClient {

    private final RestTemplate restTemplate;

    public WorkdayappClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static void main(String[] args) {
        System.out.println("Created for development purposes.");
    }

    public LeaveRequestsDto listLeaveRequestsByWorkerId(int workerId) {
        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder
                .path("api/leave/requests/worker/{workerId}")
                .build(
                        singletonMap("workerId",
                                     workerId));
        return restTemplate.getForObject(uri, LeaveRequestsDto.class);
    }

    public void createLeaveRequest(int workerId, LeaveRequestDto leaveRequestDto) {
        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder
                .path("api/leave/requests/worker/{workerId}")
                .build(
                        singletonMap("workerId",
                                     workerId));

        restTemplate.postForObject(uri, leaveRequestDto, Void.class);
    }

    public WorkerLoginDto login(@NotNull @Valid CredentialDto credentialDto) {
        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder
                .path("api/workers/login")
                .build();

        return restTemplate.postForObject(uri, credentialDto, WorkerLoginDto.class);
    }

    public DictionariesDto listWorkerDictionariesWithoutId(int workerId) {
        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder
                .path("api/workers/dictionaries/without/{workerId}")
                .build(
                        singletonMap("workerId",
                                     workerId));
        return restTemplate.getForObject(uri, DictionariesDto.class);
    }

    public int getFreeDays(int workerId) {
        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder
                .path("api/workers/{workerId}/free/days")
                .build(
                        singletonMap("workerId",
                                     workerId));
        return restTemplate.getForObject(uri, Integer.class);
    }

    private UriBuilder getUriBuilder() {
        return new DefaultUriBuilderFactory().builder().scheme("http").host("localhost").port(8080);
    }

}
