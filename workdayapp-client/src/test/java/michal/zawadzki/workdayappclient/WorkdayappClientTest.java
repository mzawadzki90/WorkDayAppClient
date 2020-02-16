package michal.zawadzki.workdayappclient;

import michal.zawadzki.workdayappclient.api.DictionariesDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestsDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveType;
import michal.zawadzki.workdayappclient.api.worker.Role;
import michal.zawadzki.workdayappclient.api.worker.login.CredentialDto;
import michal.zawadzki.workdayappclient.api.worker.login.WorkerLoginDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkdayappClientTest {

    private static WorkdayappClient workdayappClient;

    @BeforeAll
    static void beforeAll() {
        workdayappClient = new WorkdayappClient(new RestTemplate());
    }

    @Test
    void shouldReturnListOfLeaveRequests() {
        final LeaveRequestsDto leaveRequestsDto = workdayappClient.listLeaveRequestsByWorkerId(1);

        assertTrue(leaveRequestsDto.getLeaveRequests().size() > 0);
    }

    @Test
    void shouldReturnListOfWorkerDictionaries() {
        final DictionariesDto dictionariesDto = workdayappClient.listWorkerDictionariesWithoutId(1);

        assertTrue(dictionariesDto.getDictionaries().size() > 0);
    }

    @Test
    void shouldReturnWorkerFreeDays() {
        final int freeDays = workdayappClient.getFreeDays(1);

        assertTrue(freeDays > 0);
    }

    @Test
    void shouldCreateLeaveRequest() {
        workdayappClient.createLeaveRequest(1, createTestRequest());
    }

    @Test
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