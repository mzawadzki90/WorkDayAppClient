/*
    Created on 20/06/2020 by uex1421
 */
package michal.zawadzki.workdayappclient.api.worker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkerDto extends SimplifiedWorkerDto {

    @JsonProperty("current_position")
    private OccupationDto currentPosition;

    private SimplifiedWorkerDto supervisor;

    private String room;

    @JsonProperty("stationary_number")
    private String stationaryNumber;

    @JsonProperty("mobile_number")
    private String mobileNumber;

    public WorkerDto() {
    }

    public WorkerDto(int id, Role role, String email, String firstName, String lastName, OccupationDto currentPosition,
            SimplifiedWorkerDto supervisor, String room, String stationaryNumber, String mobileNumber) {
        super(id, role, email, firstName, lastName);
        this.currentPosition = currentPosition;
        this.supervisor = supervisor;
        this.room = room;
        this.stationaryNumber = stationaryNumber;
        this.mobileNumber = mobileNumber;
    }

    public OccupationDto getCurrentPosition() {
        return this.currentPosition;
    }

    public SimplifiedWorkerDto getSupervisor() {
        return this.supervisor;
    }

    public String getRoom() {
        return this.room;
    }

    public String getStationaryNumber() {
        return this.stationaryNumber;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setCurrentPosition(OccupationDto currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setSupervisor(SimplifiedWorkerDto supervisor) {
        this.supervisor = supervisor;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setStationaryNumber(String stationaryNumber) {
        this.stationaryNumber = stationaryNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}
