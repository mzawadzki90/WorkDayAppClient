/*
    Created on 16/02/2020 by uex1421
 */
package michal.zawadzki.workdayappclient.api.worker.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import michal.zawadzki.workdayappclient.api.worker.Role;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerLoginDto {

    private int id;

    private Role role;

    private String firstName;

    private String lastName;

    private String email;

}
