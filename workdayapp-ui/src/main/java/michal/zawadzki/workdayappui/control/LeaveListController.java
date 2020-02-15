/*
 created on 19.01.2020
 by Michał Zawadzki
*/
package michal.zawadzki.workdayappui.control;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import michal.zawadzki.workdayappclient.WorkdayappClient;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestDto;
import michal.zawadzki.workdayappui.ScreenInitializer;
import michal.zawadzki.workdayappui.WorkdayappUi;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LeaveListController {

    private final static List<String> DATE_COLUMN_IDS =
            Arrays.asList("leaveRequestSince", "leaveRequestTill", "leaveRequestCreatedAt",
                          "leaveRequestModifiedAt");

    private final WorkdayappClient workdayappClient;

    private final ApplicationContext applicationContext;

    private final ScreenInitializer screenInitializer;

    private Stage stage;

    @FXML
    public TextField availableDaysTF;

    @FXML
    public TableView<LeaveRequestDto> leaveRequests;

    public LeaveListController(WorkdayappClient workdayappClient,
                               ApplicationContext applicationContext,
                               ScreenInitializer screenInitializer) {
        this.workdayappClient   = workdayappClient;
        this.applicationContext = applicationContext;
        this.screenInitializer  = screenInitializer;
    }

    @FXML
    public void initialize() {
        final int freeDays = workdayappClient.getFreeDays(1);
        availableDaysTF.setText(String.valueOf(freeDays));

        leaveRequests.getColumns().stream().filter(column -> DATE_COLUMN_IDS.contains(column.getId()))
                     .map(column -> (TableColumn<LeaveRequestDto, Date>) column)
                     .forEach(dateColumn -> dateColumn.setCellFactory(dateCellFactory()));


        final ObservableList<LeaveRequestDto> items = leaveRequests.getItems();
        items.addAll(workdayappClient.listLeaveRequestsByWorkerId(1).getLeaveRequests());
    }

    @FXML
    public void onAddNewClick(ActionEvent actionEvent) {
        if (stage == null) {
            stage = screenInitializer.getStage();
        }

        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(stage, "details"));
    }

    private Callback<TableColumn<LeaveRequestDto, Date>, TableCell<LeaveRequestDto, Date>> dateCellFactory() {
        return column -> new TableCell<>() {
            private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

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