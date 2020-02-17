package michal.zawadzki.workdayappui.control;/*
    Created on 17/02/2020 by uex1421
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import michal.zawadzki.workdayappclient.WorkdayappClient;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestStatus;
import michal.zawadzki.workdayappui.ScreenInitializer;
import michal.zawadzki.workdayappui.WorkdayappUi;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ChangeStatusController {

    private final ApplicationContext applicationContext;

    private final ScreenInitializer screenInitializer;

    private final WorkdayappClient workdayappClient;

    private LeaveRequestDto selectedLeaveRequest;

    @FXML
    public ComboBox<LeaveRequestStatus> statusComboBox;

    public ChangeStatusController(ApplicationContext applicationContext,
                                  ScreenInitializer screenInitializer,
                                  WorkdayappClient workdayappClient) {
        this.applicationContext = applicationContext;
        this.screenInitializer  = screenInitializer;
        this.workdayappClient   = workdayappClient;
    }

    @FXML
    public void initialize() {
        selectedLeaveRequest = (LeaveRequestDto) screenInitializer.getExtra();
        statusComboBox.getItems().addAll(LeaveRequestStatus.values());
        statusComboBox.getSelectionModel().select(selectedLeaveRequest.getStatus());
    }


    @FXML
    public void onRejectClick(ActionEvent actionEvent) {
        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(screenInitializer.getStage(), "workerList"));
    }

    @FXML
    public void onApproveClick(ActionEvent actionEvent) {
        workdayappClient.updateLeaveRequestStatus(selectedLeaveRequest.getWorkerId(), selectedLeaveRequest.getLeaveId(),
                                                  statusComboBox.getValue());
        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(screenInitializer.getStage(), "workerList"));
    }
}
