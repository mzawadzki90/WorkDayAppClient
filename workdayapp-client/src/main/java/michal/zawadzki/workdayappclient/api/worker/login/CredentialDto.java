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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialDto {

    @NotBlank
    @Size(min = 8, max = 16)
    private String login;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

}
