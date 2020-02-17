/*
 created on 15.02.2020
 by Michał Zawadzki
*/
package michal.zawadzki.workdayappui.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import michal.zawadzki.workdayappclient.api.worker.Role;
import michal.zawadzki.workdayappui.ApplicationUser;
import michal.zawadzki.workdayappui.ScreenInitializer;
import michal.zawadzki.workdayappui.WorkdayappUi;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MenuController {

    private final ApplicationContext applicationContext;

    private final ScreenInitializer screenInitializer;

    private final ApplicationUser applicationUser;

    @FXML
    public Menu yourLeavesM;

    public MenuController(ApplicationContext applicationContext,
                          ScreenInitializer screenInitializer,
                          ApplicationUser applicationUser) {
        this.applicationContext = applicationContext;
        this.screenInitializer  = screenInitializer;
        this.applicationUser    = applicationUser;
    }

    @FXML
    public void initialize() {
        if (applicationUser.getApplicationUser().getRole().equals(Role.DIRECTOR)) {
            final MenuItem workersRequests = new MenuItem("Wnioski pracowników");
            yourLeavesM.getItems().add(workersRequests);
            workersRequests.addEventHandler(ActionEvent.ACTION, actionEvent -> applicationContext
                    .publishEvent(new WorkdayappUi.ScreenEvent(screenInitializer.getStage(), "workerList")));
        }

    }

    @FXML
    public void onLogOutClick(MouseEvent actionEvent) {
        applicationUser.setApplicationUser(null);
        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(screenInitializer.getStage(), "login"));
    }


    @FXML
    public void OnNewLeaveRequestClick(ActionEvent actionEvent) {
        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(screenInitializer.getStage(), "details"));
    }

    @FXML
    public void OnLeaveRequestClick(ActionEvent actionEvent) {
        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(screenInitializer.getStage(), "list"));
    }

}
