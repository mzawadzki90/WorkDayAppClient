package michal.zawadzki.workdayappui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class WorkdayappUi extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override public void init() throws Exception {
        applicationContext = new SpringApplicationBuilder(WorkdayappUiApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) {
        applicationContext.publishEvent(new ScreenEvent(primaryStage, ScreenName.LOGIN));
    }

    @Override public void stop() throws Exception {
        applicationContext.stop();
        Platform.exit();
    }

    public static class ScreenEvent extends ApplicationEvent {

        private final ScreenName screenName;

        private Object extra;

        public ScreenEvent(Stage primaryStage, ScreenName screenName) {
            super(primaryStage);
            this.screenName = screenName;
        }

        public ScreenEvent(Stage primaryStage, ScreenName screenName, Object extra) {
            this(primaryStage, screenName);
            this.extra = extra;
        }

        public Stage getStage() {
            return ((Stage) getSource());
        }

        public ScreenName getScreenName() {
            return screenName;
        }

        public Object getExtra() {
            return extra;
        }
    }

}
