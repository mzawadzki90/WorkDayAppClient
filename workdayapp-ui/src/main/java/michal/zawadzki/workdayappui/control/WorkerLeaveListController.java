/*
    Created on 17/02/2020 by uex1421
 */
package michal.zawadzki.workdayappui.control;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import michal.zawadzki.workdayappclient.WorkdayappClient;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestDto;
import michal.zawadzki.workdayappui.ApplicationUser;
import michal.zawadzki.workdayappui.ScreenInitializer;
import michal.zawadzki.workdayappui.WorkdayappUi;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class WorkerLeaveListController {

    private final static List<String> DATE_COLUMN_IDS =
            Arrays.asList("leaveRequestSince", "leaveRequestTill", "leaveRequestCreatedAt",
                          "leaveRequestModifiedAt");

    private final WorkdayappClient workdayappClient;

    private final ApplicationContext applicationContext;

    private final ScreenInitializer screenInitializer;

    private Stage stage;

    @FXML
    public TableView<LeaveRequestDto> leaveRequests;

    @FXML
    public Label appUserInfo;

    public WorkerLeaveListController(WorkdayappClient workdayappClient,
                                     ApplicationContext applicationContext,
                                     ScreenInitializer screenInitializer,
                                     ApplicationUser applicationUser) {
        this.workdayappClient   = workdayappClient;
        this.applicationContext = applicationContext;
        this.screenInitializer  = screenInitializer;
    }

    @FXML
    public void initialize() {
        stage = screenInitializer.getStage();

        leaveRequests.getColumns().stream().filter(column -> DATE_COLUMN_IDS.contains(column.getId()))
                     .map(column -> (TableColumn<LeaveRequestDto, Date>) column)
                     .forEach(dateColumn -> dateColumn.setCellFactory(dateCellFactory()));

        leaveRequests.setRowFactory(rowFactory -> {
            TableRow<LeaveRequestDto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(stage, "changeStatus", row.getItem()));
                }
            });
            return row;
        });

        final ObservableList<LeaveRequestDto> items = leaveRequests.getItems();
        items.addAll(workdayappClient.listWorkerLeaveRequests().getLeaveRequests());
    }

    private Callback<TableColumn<LeaveRequestDto, Date>, TableCell<LeaveRequestDto, Date>> dateCellFactory() {
        return column -> new TableCell<>() {
            private final SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(format.format(item));
                }
            }
        };
    }

}
