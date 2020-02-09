package michal.zawadzki.workdayappclient;

import michal.zawadzki.workdayappclient.api.leave.LeaveRequestsDto;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkdayappClientTest {

    @Test
    void shouldReturnListOfLeaveRequests() {
        final WorkdayappClient workdayappClient = new WorkdayappClient(new RestTemplate());
        final LeaveRequestsDto leaveRequestsDto = workdayappClient.listByWorkerId(1);

        assertTrue(leaveRequestsDto.getLeaveRequests().size() > 0);
    }
}