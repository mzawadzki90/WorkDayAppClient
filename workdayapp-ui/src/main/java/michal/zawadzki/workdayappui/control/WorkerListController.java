/*
    Created on 20/06/2020 by uex1421
 */
package michal.zawadzki.workdayappui.control;

import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import michal.zawadzki.workdayappclient.WorkdayappClient;
import michal.zawadzki.workdayappclient.api.worker.Role;
import michal.zawadzki.workdayappui.ApplicationUser;
import michal.zawadzki.workdayappui.ScreenInitializer;
import michal.zawadzki.workdayappui.ScreenName;
import michal.zawadzki.workdayappui.WorkdayappUi;
import michal.zawadzki.workdayappui.mapper.WorkerPresentationMapper;
import michal.zawadzki.workdayappui.model.WorkerPresentationView;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class WorkerListController {

    private final WorkdayappClient workdayappClient;

    private final ScreenInitializer screenInitializer;

    private final ApplicationEventPublisher applicationContext;

    private final ApplicationUser applicationUser;

    private Stage stage;

    private ContextMenu contextMenu;

    private ObservableList<WorkerPresentationView> items;

    @FXML
    public TableView<WorkerPresentationView> workers;

    @FXML
    public TextField searchInput;

    public WorkerListController(WorkdayappClient workdayappClient, ScreenInitializer screenInitializer,
            ApplicationEventPublisher applicationContext, ApplicationUser applicationUser) {
        this.workdayappClient = workdayappClient;
        this.screenInitializer = screenInitializer;
        this.applicationContext = applicationContext;
        this.applicationUser = applicationUser;
    }

    @FXML
    public void initialize() {
        stage = screenInitializer.getStage();
        createContextMenu();

        workers.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if (t.getButton() == MouseButton.SECONDARY && applicationUser.getApplicationUser().getRole().equals(Role.DIRECTOR)) {
                contextMenu.show(workers, t.getScreenX(), t.getScreenY());
            }
        });

        searchInput.textProperty().addListener(geListenerForSearchInput());

        items = workers.getItems();
        items.addAll(workdayappClient.listWorkersByKeyword(null)
                .stream()
                .map(WorkerPresentationMapper::toPresentationView)
                .collect(Collectors.toList()));
    }

    private void createContextMenu() {
        contextMenu = new ContextMenu();
        final MenuItem workerLeaveRequests = new MenuItem("Wnioski urlopowe pracownika");
        workerLeaveRequests.setOnAction(event -> applicationContext.publishEvent(
                new WorkdayappUi.ScreenEvent(stage, ScreenName.WORKERS_LEAVE_REQUEST_LIST, workers.getSelectionModel().getSelectedItem())));
        contextMenu.getItems().add(workerLeaveRequests);
    }

    private ChangeListener<String> geListenerForSearchInput() {
        return (observable, oldValue, newValue) -> {
            if (oldValue.equals(newValue)) {
                return;
            }

            List<WorkerPresentationView> workerPresentationViews;
            final String trimmed = newValue.trim();
            if (StringUtils.isEmpty(trimmed) || trimmed.length() < 3) {
                workerPresentationViews = workdayappClient.listWorkersByKeyword(null)
                        .stream()
                        .map(WorkerPresentationMapper::toPresentationView)
                        .collect(Collectors.toList());
            } else {
                workerPresentationViews = workdayappClient.listWorkersByKeyword(trimmed)
                        .stream()
                        .map(WorkerPresentationMapper::toPresentationView)
                        .collect(Collectors.toList());
            }

            items.removeIf(worker -> true);
            items.addAll(workerPresentationViews);
        };
    }

}
