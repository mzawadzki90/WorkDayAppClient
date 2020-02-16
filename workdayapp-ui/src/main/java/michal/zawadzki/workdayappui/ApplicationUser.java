/*
    Created on 16/02/2020 by uex1421
 */
package michal.zawadzki.workdayappui;

import michal.zawadzki.workdayappclient.api.worker.login.WorkerLoginDto;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUser {

    private WorkerLoginDto applicationUser;

    public ApplicationUser() {
    }

    public WorkerLoginDto getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(WorkerLoginDto applicationUser) {
        if (this.applicationUser != null) {
            return;
        }

        this.applicationUser = applicationUser;
    }

}
