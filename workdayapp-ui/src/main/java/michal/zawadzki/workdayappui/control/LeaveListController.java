/*
 created on 19.01.2020
 by Michał Zawadzki
*/
package michal.zawadzki.workdayappui.control;

import static michal.zawadzki.workdayappui.util.DateUtil.dateCellFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import michal.zawadzki.workdayappclient.WorkdayappClient;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestDto;
import michal.zawadzki.workdayappclient.api.worker.login.WorkerLoginDto;
import michal.zawadzki.workdayappui.ApplicationUser;
import michal.zawadzki.workdayappui.ScreenInitializer;
import michal.zawadzki.workdayappui.ScreenName;
import michal.zawadzki.workdayappui.WorkdayappUi;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LeaveListController {

    private final static List<String> DATE_COLUMN_IDS =
            Arrays.asList("leaveRequestSince", "leaveRequestTill", "leaveRequestCreatedAt",
                          "leaveRequestModifiedAt");

    private final WorkdayappClient workdayappClient;

    private final ApplicationContext applicationContext;

    private final ScreenInitializer screenInitializer;

    private final ApplicationUser applicationUser;

    private WorkerLoginDto workerLoginDto;

    @FXML
    public TextField availableDaysTF;

    @FXML
    public TableView<LeaveRequestDto> leaveRequests;

    @FXML
    public Label appUserInfo;

    public LeaveListController(WorkdayappClient workdayappClient,
                               ApplicationContext applicationContext,
                               ScreenInitializer screenInitializer,
                               ApplicationUser applicationUser) {
        this.workdayappClient   = workdayappClient;
        this.applicationContext = applicationContext;
        this.screenInitializer  = screenInitializer;
        this.applicationUser    = applicationUser;
    }

    @FXML
    public void initialize() {
        workerLoginDto = applicationUser.getApplicationUser();
        setUserInfo();
        final int freeDays = workdayappClient.getFreeDays(workerLoginDto.getId());
        availableDaysTF.setText(String.valueOf(freeDays));

        leaveRequests.getColumns().stream().filter(column -> DATE_COLUMN_IDS.contains(column.getId()))
                     .map(column -> (TableColumn<LeaveRequestDto, Date>) column)
                     .forEach(dateColumn -> dateColumn.setCellFactory(dateCellFactory()));


        final ObservableList<LeaveRequestDto> items = leaveRequests.getItems();
        items.addAll(workdayappClient.listLeaveRequestsByWorkerId(workerLoginDto.getId()).getLeaveRequests());
    }

    @FXML
    public void onAddNewClick(ActionEvent actionEvent) {
        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(screenInitializer.getStage(), ScreenName.LEAVE_REQUEST_DETAILS));
    }

    private void setUserInfo() {
        appUserInfo.setText(
                String.format("Witaj %s %s (%s)", workerLoginDto.getFirstName(), workerLoginDto.getLastName(),
                              workerLoginDto.getEmail()));
    }

}
