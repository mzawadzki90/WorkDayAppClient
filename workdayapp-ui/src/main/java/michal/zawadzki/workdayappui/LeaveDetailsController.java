/*
 created on 19.01.2020
 by Michał Zawadzki
*/
package michal.zawadzki.workdayappui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LeaveDetailsController {

    private final ApplicationContext applicationContext;

    private final ScreenInitializer screenInitializer;

    private Stage stage;

    public LeaveDetailsController(ApplicationContext applicationContext,
                                  ScreenInitializer screenInitializer) {
        this.applicationContext = applicationContext;
        this.screenInitializer  = screenInitializer;
    }

    @FXML
    public void initialize() {
        stage = screenInitializer.getStage();
    }

    public void onRejectClick(ActionEvent actionEvent) {
        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(stage, "list"));
    }

}