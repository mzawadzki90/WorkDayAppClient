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

    private String note;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("modifiedAt")
    private Date modifiedAt;

    @JsonProperty("leave_id")
    private Integer leaveId;

    @JsonProperty("worker_id")
    private Integer workerId;

    @JsonProperty("worker_first_name")
    private String workerFirstName;

    @JsonProperty("worker_last_name")
    private String workerLastName;

    @JsonProperty("worker_email")
    private String workerEmail;

}