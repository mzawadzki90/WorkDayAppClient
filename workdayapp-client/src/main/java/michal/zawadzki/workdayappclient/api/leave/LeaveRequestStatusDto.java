/*
    Created on 17/02/2020 by uex1421
 */
package michal.zawadzki.workdayappclient.api.leave;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestStatusDto {

    private LeaveRequestStatus status;

    @JsonProperty("rejectionReason")
    private String rejectionReason;

}
