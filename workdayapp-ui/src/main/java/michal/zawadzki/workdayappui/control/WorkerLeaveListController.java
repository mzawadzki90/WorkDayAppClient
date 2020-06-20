/*
    Created on 17/02/2020 by uex1421
 */
package michal.zawadzki.workdayappui.control;

import static michal.zawadzki.workdayappui.util.DateUtil.dateCellFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import michal.zawadzki.workdayappclient.WorkdayappClient;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestDto;
import michal.zawadzki.workdayappui.ScreenInitializer;
import michal.zawadzki.workdayappui.ScreenName;
import michal.zawadzki.workdayappui.WorkdayappUi;
import michal.zawadzki.workdayappui.model.WorkerPresentationView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class WorkerLeaveListController {

    private static final List<String> DATE_COLUMN_IDS = Arrays.asList("leaveRequestSince", "leaveRequestTill", "leaveRequestCreatedAt",
            "leaveRequestModifiedAt");

    private final WorkdayappClient workdayappClient;

    private final ApplicationContext applicationContext;

    private final ScreenInitializer screenInitializer;

    private Stage stage;

    private WorkerPresentationView workerPresentationView;

    @FXML
    public TableView<LeaveRequestDto> leaveRequests;

    public WorkerLeaveListController(WorkdayappClient workdayappClient, ApplicationContext applicationContext,
            ScreenInitializer screenInitializer) {
        this.workdayappClient = workdayappClient;
        this.applicationContext = applicationContext;
        this.screenInitializer = screenInitializer;
    }

    @FXML
    public void initialize() {
        stage = screenInitializer.getStage();

        final Object extra = screenInitializer.getExtra();
        if (extra instanceof WorkerPresentationView) {
            workerPresentationView = (WorkerPresentationView) extra;
        }

        leaveRequests.getColumns()
                .stream()
                .filter(column -> DATE_COLUMN_IDS.contains(column.getId()))
                .map(column -> (TableColumn<LeaveRequestDto, Date>) column)
                .forEach(dateColumn -> dateColumn.setCellFactory(dateCellFactory()));

        leaveRequests.setRowFactory(rowFactory -> {
            TableRow<LeaveRequestDto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    applicationContext.publishEvent(
                            new WorkdayappUi.ScreenEvent(stage, ScreenName.CHANGE_LEAVE_REQUEST_STATUS, row.getItem()));
                }
            });
            return row;
        });

        final ObservableList<LeaveRequestDto> items = leaveRequests.getItems();

        final List<LeaveRequestDto> leaveRequests;
        if (workerPresentationView == null) {
            leaveRequests = workdayappClient.listWorkerLeaveRequests().getLeaveRequests();
        } else {
            leaveRequests = workdayappClient.listLeaveRequestsByWorkerId(workerPresentationView.getId()).getLeaveRequests();
        }

        items.addAll(leaveRequests);
    }

}
