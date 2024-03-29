/*
    Created on 17/02/2020 by uex1421
 */
package michal.zawadzki.workdayappui.control;

import java.util.EnumSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import michal.zawadzki.workdayappclient.WorkdayappClient;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestStatus;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestStatusDto;
import michal.zawadzki.workdayappui.ScreenInitializer;
import michal.zawadzki.workdayappui.ScreenName;
import michal.zawadzki.workdayappui.WorkdayappUi;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ChangeStatusController {

    private final ApplicationContext applicationContext;

    private final ScreenInitializer screenInitializer;

    private final WorkdayappClient workdayappClient;

    private LeaveRequestDto selectedLeaveRequest;

    @FXML
    public ComboBox<LeaveRequestStatus> statusComboBox;

    @FXML
    public TextArea rejectionReasonTA;

    public ChangeStatusController(ApplicationContext applicationContext, ScreenInitializer screenInitializer,
            WorkdayappClient workdayappClient) {
        this.applicationContext = applicationContext;
        this.screenInitializer = screenInitializer;
        this.workdayappClient = workdayappClient;
    }

    @FXML
    public void initialize() {
        selectedLeaveRequest = (LeaveRequestDto) screenInitializer.getExtra();
        statusComboBox.getItems().addAll(EnumSet.of(LeaveRequestStatus.ACCEPTED, LeaveRequestStatus.REJECTED));
        statusComboBox.getSelectionModel().select(selectedLeaveRequest.getStatus());
    }

    @FXML
    public void onRejectClick(ActionEvent actionEvent) {
        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(screenInitializer.getStage(), ScreenName.WORKERS_LEAVE_REQUEST_LIST));
    }

    @FXML
    public void onApproveClick(ActionEvent actionEvent) {
        workdayappClient.updateLeaveRequestStatus(selectedLeaveRequest.getWorkerId(), selectedLeaveRequest.getLeaveId(),
                getRequestStatusDto());
        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(screenInitializer.getStage(), ScreenName.WORKERS_LEAVE_REQUEST_LIST));
    }

    private LeaveRequestStatusDto getRequestStatusDto() {
        final LeaveRequestStatus status = statusComboBox.getValue();
        final String text = rejectionReasonTA.getText();
        final String reason = !StringUtils.isEmpty(text) ? text : null;
        return new LeaveRequestStatusDto(status, reason);
    }

}
