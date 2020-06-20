package michal.zawadzki.workdayappclient;

import static java.util.Collections.singletonMap;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import michal.zawadzki.workdayappclient.api.DictionariesDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestStatusDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestsDto;
import michal.zawadzki.workdayappclient.api.worker.WorkerDto;
import michal.zawadzki.workdayappclient.api.worker.login.CredentialDto;
import michal.zawadzki.workdayappclient.api.worker.login.WorkerLoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

public class WorkdayappClient {

    private final RestTemplate restTemplate;

    public WorkdayappClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static void main(String[] args) {
        System.out.println("Created for development purposes.");
    }

    public LeaveRequestsDto listWorkerLeaveRequests() {
        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder.path("api/leave/requests").build();

        return restTemplate.getForObject(uri, LeaveRequestsDto.class);
    }

    public LeaveRequestsDto listLeaveRequestsByWorkerId(int workerId) {
        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder.path("api/leave/requests/worker/{workerId}").build(singletonMap("workerId", workerId));
        return restTemplate.getForObject(uri, LeaveRequestsDto.class);
    }

    public void createLeaveRequest(int workerId, LeaveRequestDto leaveRequestDto) {
        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder.path("api/leave/requests/worker/{workerId}").build(singletonMap("workerId", workerId));

        restTemplate.postForObject(uri, leaveRequestDto, Void.class);
    }

    public void updateLeaveRequestStatus(int workerId, int leaveId, LeaveRequestStatusDto leaveRequestStatusDto) {
        final Map<String, Integer> pathParams = new HashMap<>();
        pathParams.put("workerId", workerId);
        pathParams.put("leaveId", leaveId);

        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder.path("api/leave/requests/worker/{workerId}/leave/{leaveId}").build(pathParams);

        restTemplate.put(uri, leaveRequestStatusDto);
    }

    public WorkerLoginDto login(@NotNull @Valid CredentialDto credentialDto) {
        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder.path("api/workers/login").build();

        return restTemplate.postForObject(uri, credentialDto, WorkerLoginDto.class);
    }

    public List<WorkerDto> listWorkersByKeyword(String keyword) {
        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder.path("api/workers").queryParam("keyword", keyword).build();

        final ResponseEntity<WorkerDto[]> entity = restTemplate.getForEntity(uri, WorkerDto[].class);
        final WorkerDto[] body = entity.getBody();

        if (body == null) {
            return Collections.emptyList();
        }

        return Arrays.stream(body).collect(Collectors.toList());
    }

    public DictionariesDto listWorkerDictionariesWithoutId(int workerId) {
        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder.path("api/workers/dictionaries/without/{workerId}").build(singletonMap("workerId", workerId));
        return restTemplate.getForObject(uri, DictionariesDto.class);
    }

    public int getFreeDays(int workerId) {
        final UriBuilder uriBuilder = getUriBuilder();
        final URI uri = uriBuilder.path("api/workers/{workerId}/free/days").build(singletonMap("workerId", workerId));
        return restTemplate.getForObject(uri, Integer.class);
    }

    private UriBuilder getUriBuilder() {
        return new DefaultUriBuilderFactory().builder().scheme("http").host("localhost").port(8080);
    }

}
