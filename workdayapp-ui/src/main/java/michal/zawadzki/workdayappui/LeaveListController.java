/*
 created on 19.01.2020
 by Michał Zawadzki
*/
package michal.zawadzki.workdayappui;

import michal.zawadzki.workdayappclient.WorkdayappClient;
import org.springframework.stereotype.Component;

@Component
public class LeaveListController {

    private final WorkdayappClient workdayappClient;

    public LeaveListController(WorkdayappClient workdayappClient) {
        this.workdayappClient = workdayappClient;
    }


}
