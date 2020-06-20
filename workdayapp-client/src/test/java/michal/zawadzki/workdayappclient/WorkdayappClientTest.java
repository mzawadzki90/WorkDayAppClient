package michal.zawadzki.workdayappclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import michal.zawadzki.workdayappclient.api.DictionariesDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestStatus;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestsDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveType;
import michal.zawadzki.workdayappclient.api.worker.Role;
import michal.zawadzki.workdayappclient.api.worker.login.CredentialDto;
import michal.zawadzki.workdayappclient.api.worker.login.WorkerLoginDto;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class WorkdayappClientTest {

    private static WorkdayappClient workdayappClient;

    @BeforeAll
    static void beforeAll() {
        workdayappClient = new WorkdayappClient(new RestTemplate());
    }

    @Test
    @Ignore
    void shouldListWorkerLeaveRequests() {
        final LeaveRequestsDto leaveRequestsDto = workdayappClient.listWorkerLeaveRequests();

        assertTrue(leaveRequestsDto.getLeaveRequests().size() > 0);
    }

    @Test
    @Ignore
    void shouldReturnListOfLeaveRequests() {
        final LeaveRequestsDto leaveRequestsDto = workdayappClient.listLeaveRequestsByWorkerId(1);

        assertTrue(leaveRequestsDto.getLeaveRequests().size() > 0);
    }

    @Test
    @Ignore
    void shouldReturnListOfWorkerDictionaries() {
        final DictionariesDto dictionariesDto = workdayappClient.listWorkerDictionariesWithoutId(1);

        assertTrue(dictionariesDto.getDictionaries().size() > 0);
    }

    @Test
    @Ignore
    void shouldReturnWorkerFreeDays() {
        final int freeDays = workdayappClient.getFreeDays(1);

        assertTrue(freeDays > 0);
    }

    @Test
    @Ignore
    void shouldCreateLeaveRequest() {
        workdayappClient.createLeaveRequest(1, createTestRequest());
    }

    @Test
    @Ignore
    void shouldUpdateLeaveRequestStatus() {
        workdayappClient.updateLeaveRequestStatus(1, 1, LeaveRequestStatus.REJECTED);
    }

    @Test
    @Ignore
    void shouldLoginSuccessfully() {
        final WorkerLoginDto workerLoginDto =
                workdayappClient.login(CredentialDto.builder().login("jan.kowalski").password("test1234").build());


        assertEquals(Role.REGULAR_EMPLOYEE, workerLoginDto.getRole());
        assertEquals("Jan", workerLoginDto.getFirstName());
    }

    private LeaveRequestDto createTestRequest() {
        final LeaveRequestDto leaveRequestDto = new LeaveRequestDto();
        leaveRequestDto.setType(LeaveType.VACATION);
        leaveRequestDto.setSince(Date.valueOf(LocalDate.of(2020, 3, 4)));
        leaveRequestDto.setTill(Date.valueOf(LocalDate.of(2020, 3, 5)));
        return leaveRequestDto;
    }

}