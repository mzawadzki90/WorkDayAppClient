/*
    Created on 16/02/2020 by uex1421
 */
package michal.zawadzki.workdayappui.util;

import javafx.scene.control.Alert;

public class AlertUtil {

    private AlertUtil() {

    }

    public static void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błędne dane");
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }

    public static void showWarningAlert(String warningMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Błędne dane");
        alert.setContentText(warningMessage);

        alert.showAndWait();
    }

}
