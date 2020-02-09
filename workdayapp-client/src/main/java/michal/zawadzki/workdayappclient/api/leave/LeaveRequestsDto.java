package michal.zawadzki.workdayappclient.api.leave;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import michal.zawadzki.workdayappclient.api.MetaDto;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LeaveRequestsDto {

    private MetaDto meta;

    @JsonProperty("leave_requests")
    private List<LeaveRequestDto> leaveRequests;

}
