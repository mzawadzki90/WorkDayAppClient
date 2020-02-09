package michal.zawadzki.workdayappclient.api.leave;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LeaveRequestDto {

    @NotNull
    private LeaveType type;

    private LeaveRequestStatus status;

    @NotNull
    private Date since;

    @NotNull
    private Date till;

    @NotNull
    private int days;

    @JsonProperty("replacement_id")
    private Integer replacementId;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("modifiedAt")
    private Date modifiedAt;

}