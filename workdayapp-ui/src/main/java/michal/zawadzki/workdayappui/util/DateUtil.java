/*
    Created on 20/06/2020 by uex1421
 */
package michal.zawadzki.workdayappui.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestDto;

public class DateUtil {

    private static final String DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss";

    private DateUtil() {

    }

    public static Callback<TableColumn<LeaveRequestDto, Date>, TableCell<LeaveRequestDto, Date>> dateCellFactory() {
        return column -> new TableCell<LeaveRequestDto, Date>() {

            private final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

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
