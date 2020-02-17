/*
    Created on 16/02/2020 by uex1421
 */
package michal.zawadzki.workdayappui.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import michal.zawadzki.workdayappclient.WorkdayappClient;
import michal.zawadzki.workdayappclient.api.worker.login.CredentialDto;
import michal.zawadzki.workdayappclient.api.worker.login.WorkerLoginDto;
import michal.zawadzki.workdayappui.ApplicationUser;
import michal.zawadzki.workdayappui.ScreenInitializer;
import michal.zawadzki.workdayappui.WorkdayappUi;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static michal.zawadzki.workdayappui.util.AlertUtil.showWarningAlert;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class LoginController {

    @FXML
    public TextField loginInput;

    @FXML
    public TextField passwordInput;

    @FXML
    public Button logInButton;

    private final WorkdayappClient workdayappClient;

    private final ApplicationContext applicationContext;

    private final ScreenInitializer screenInitializer;

    private final ApplicationUser applicationUser;

    private Stage stage;

    public LoginController(WorkdayappClient workdayappClient,
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

    }

    @FXML
    public void onLogInButtonClick(ActionEvent actionEvent) {
        final String login = loginInput.getText();
        boolean validLogin = validateLogin(login);
        if (!validLogin) {
            return;
        }

        final String password = passwordInput.getText();
        boolean validPassword = validatePassword(password);
        if (!validPassword) {
            return;
        }

        final WorkerLoginDto workerLoginDto;
        try {
            workerLoginDto = workdayappClient.login(CredentialDto.builder().login(login).password(password).build());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (stage == null) {
            stage = screenInitializer.getStage();
        }

        applicationUser.setApplicationUser(workerLoginDto);
        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(stage, "list"));
    }

    private boolean validateLogin(String login) {
        if (isEmpty(login)) {
            showWarningAlert("Należy podać login.");
            return false;
        }

        return true;
    }

    private boolean validatePassword(String password) {
        if (isEmpty(password)) {
            showWarningAlert("Należy podać hasło.");
            return false;
        }

        return true;
    }

}
