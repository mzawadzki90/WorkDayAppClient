package michal.zawadzki.workdayappui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScreenInitializer implements ApplicationListener<WorkdayappUi.ScreenEvent> {

    private final String applicationTitle;

    private final ApplicationContext applicationContext;

    private final ScreenController screenController;

    private Stage stage;

    public ScreenInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                             ApplicationContext applicationContext,
                             ScreenController screenController) {
        this.applicationTitle   = applicationTitle;
        this.applicationContext = applicationContext;
        this.screenController   = screenController;
    }

    @Override
    public void onApplicationEvent(WorkdayappUi.ScreenEvent event) {
        try {
            final String screenName = event.getScreenName();
            final Resource screen = screenController.getScreenByName(screenName);
            FXMLLoader fxmlLoader = new FXMLLoader(screen.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent = fxmlLoader.load();

            if (stage == null) {
                stage = event.getStage();
            }

            stage.setScene(new Scene(parent));
            stage.setTitle(applicationTitle);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public Stage getStage() {
        return stage;
    }
}
