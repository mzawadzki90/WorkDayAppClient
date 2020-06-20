/*
    Created on 20/06/2020 by uex1421
 */
package michal.zawadzki.workdayappui.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WorkerPresentationView {

    private final int id;

    private final String role;

    private final String email;

    private final String firstName;

    private final String lastName;

    private final String position;

    private final String team;

    private final String department;

    private final String supervisor;

}
