/*
    Created on 20/06/2020 by uex1421
 */
package michal.zawadzki.workdayappui.control;

import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import michal.zawadzki.workdayappclient.WorkdayappClient;
import michal.zawadzki.workdayappui.ScreenInitializer;
import michal.zawadzki.workdayappui.mapper.WorkerPresentationMapper;
import michal.zawadzki.workdayappui.model.WorkerPresentationView;
import org.springframework.stereotype.Component;

@Component
public class WorkerListController {

    private final WorkdayappClient workdayappClient;

    private final ScreenInitializer screenInitializer;

    private Stage stage;

    @FXML
    public TableView<WorkerPresentationView> workers;

    public WorkerListController(WorkdayappClient workdayappClient, ScreenInitializer screenInitializer) {
        this.workdayappClient = workdayappClient;
        this.screenInitializer = screenInitializer;
    }

    @FXML
    public void initialize() {
        stage = screenInitializer.getStage();

        final ObservableList<WorkerPresentationView> items = workers.getItems();
        items.addAll(workdayappClient.listWorkersByKeyword(null)
                .stream()
                .map(WorkerPresentationMapper::toPresentationView)
                .collect(Collectors.toList()));
    }

}
