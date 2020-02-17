/*
 created on 19.01.2020
 by Michał Zawadzki
*/
package michal.zawadzki.workdayappui.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import michal.zawadzki.workdayappclient.WorkdayappClient;
import michal.zawadzki.workdayappclient.api.DictionariesDto;
import michal.zawadzki.workdayappclient.api.DictionaryDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveRequestDto;
import michal.zawadzki.workdayappclient.api.leave.LeaveType;
import michal.zawadzki.workdayappclient.api.worker.login.WorkerLoginDto;
import michal.zawadzki.workdayappui.ApplicationUser;
import michal.zawadzki.workdayappui.ScreenInitializer;
import michal.zawadzki.workdayappui.WorkdayappUi;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.getInstance;
import static michal.zawadzki.workdayappui.util.AlertUtil.showErrorAlert;

@Component
public class LeaveDetailsController {

    private final ApplicationContext applicationContext;

    private final ScreenInitializer screenInitializer;

    private final WorkdayappClient workdayappClient;

    private final ApplicationUser applicationUser;

    private WorkerLoginDto workerLoginDto;

    private Stage stage;

    @FXML
    public DatePicker tillDatePicker;

    @FXML
    public DatePicker sinceDatePicker;

    @FXML
    public TextField freeDaysInput;

    @FXML
    public TextArea notesTextArea;

    @FXML
    public TextField labourDaysInput;

    @FXML
    public ComboBox<LeaveType> leaveTypeComboBox;

    @FXML
    public ComboBox<DictionaryDto> replacementComboBox;

    public LeaveDetailsController(ApplicationContext applicationContext,
                                  ScreenInitializer screenInitializer,
                                  WorkdayappClient workdayappClient,
                                  ApplicationUser applicationUser) {
        this.applicationContext = applicationContext;
        this.screenInitializer  = screenInitializer;
        this.workdayappClient   = workdayappClient;
        this.applicationUser    = applicationUser;
    }

    @FXML
    public void initialize() {
        workerLoginDto = applicationUser.getApplicationUser();
        stage          = screenInitializer.getStage();

        final int freeDays = workdayappClient.getFreeDays(workerLoginDto.getId());
        freeDaysInput.setText(String.valueOf(freeDays));

        leaveTypeComboBox.getItems().addAll(LeaveType.values());
        leaveTypeComboBox.getSelectionModel().selectFirst();

        labourDaysInput.setText("0");

        final DictionariesDto dictionariesDto =
                workdayappClient.listWorkerDictionariesWithoutId(workerLoginDto.getId());
        replacementComboBox.getItems().addAll(Stream.concat(Stream.of(new DictionaryDto()),
                                                            dictionariesDto.getDictionaries().stream()).collect(
                Collectors.toList()));
        replacementComboBox.setConverter(getDictionaryConverter());
        replacementComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void onSendClick(ActionEvent actionEvent) {
        final Optional<LeaveRequestDto> leaveRequestDto = createLeaveRequestDto();

        if (leaveRequestDto.isEmpty()) {
            return;
        }

        workdayappClient.createLeaveRequest(workerLoginDto.getId(), leaveRequestDto.get());
        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(stage, "list"));

    }

    @FXML
    public void onRejectClick(ActionEvent actionEvent) {
        applicationContext.publishEvent(new WorkdayappUi.ScreenEvent(stage, "list"));
    }

    @FXML
    public void onClearClick(ActionEvent actionEvent) {
        tillDatePicker.setValue(null);
        sinceDatePicker.setValue(null);
        notesTextArea.setText(null);
        labourDaysInput.setText("0");
        leaveTypeComboBox.getSelectionModel().selectFirst();
        replacementComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void onSinceDateChanged(ActionEvent actionEvent) {
        setLabourDays();
    }

    @FXML
    public void onTillDateChanged(ActionEvent actionEvent) {
        setLabourDays();
    }

    private StringConverter<DictionaryDto> getDictionaryConverter() {
        return new StringConverter<>() {
            @Override public String toString(DictionaryDto dictionaryDto) {
                return Optional.ofNullable(dictionaryDto).map(DictionaryDto::getName).orElse("-");
            }

            @Override public DictionaryDto fromString(String s) {
                return null;
            }
        };
    }

    private void setLabourDays() {
        final LocalDate since = sinceDatePicker.getValue();
        final LocalDate till = tillDatePicker.getValue();
        boolean datesFilled = datesNotNull(since, till);
        if (!datesFilled) {
            labourDaysInput.setText("0");
            return;
        }

        labourDaysInput.setText(String.valueOf(getWorkingDaysBetweenTwoDates(since, till)));
    }

    private boolean datesNotNull(LocalDate date, LocalDate date2) {
        return date != null && date2 != null;
    }

    //code from: https://stackoverflow.com/questions/4600034/calculate-number-of-weekdays-between-two-dates-in-java
    private static int getWorkingDaysBetweenTwoDates(LocalDate localStartDate, LocalDate localEndDate) {
        final Date startDate = Date.valueOf(localStartDate);
        Calendar startCal = getInstance();
        startCal.setTime(startDate);

        final Date endDate = Date.valueOf(localEndDate);
        Calendar endCal = getInstance();
        endCal.setTime(endDate);

        int workDays = 0;

        //Return 1 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 1;
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }

        do {
            //excluding start date
            startCal.add(DAY_OF_MONTH, 1);
            if (startCal.get(DAY_OF_WEEK) != SATURDAY && startCal.get(DAY_OF_WEEK) != SUNDAY) {
                ++workDays;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date

        return workDays + 1;
    }

    private Optional<LeaveRequestDto> createLeaveRequestDto() {
        final LeaveRequestDto leaveRequestDto = new LeaveRequestDto();

        final LeaveType leaveType = leaveTypeComboBox.getValue();
        leaveRequestDto.setType(leaveType);

        final LocalDate since = sinceDatePicker.getValue();
        final boolean validSince = validateDate(since);
        if (!validSince) {
            return Optional.empty();
        }
        leaveRequestDto.setSince(Date.valueOf(since));

        final LocalDate till = tillDatePicker.getValue();
        final boolean validTill = validateDate(till);
        if (!validTill) {
            return Optional.empty();
        }
        leaveRequestDto.setTill(Date.valueOf(till));

        final boolean validateDates = validateDates(since, till);
        if (!validateDates) {
            return Optional.empty();
        }

        final int labourDays = Integer.parseInt(labourDaysInput.getText());
        final boolean validLabourDays = validateLabourDays(labourDays);
        if (!validLabourDays) {
            return Optional.empty();
        }
        leaveRequestDto.setDays(labourDays);

        final int replacementId = replacementComboBox.getValue().getId();
        leaveRequestDto.setReplacementId(replacementId == 0 ? null : replacementId);

        leaveRequestDto.setNote(notesTextArea.getText());

        return Optional.of(leaveRequestDto);
    }

    private boolean validateLabourDays(Integer labourDays) {
        final int freeDays = workdayappClient.getFreeDays(workerLoginDto.getId());
        if (freeDays < labourDays) {
            showErrorAlert("Przekroczono dopuszczalny limit wolnych dni.");
            return false;
        }

        return true;
    }

    private boolean validateDate(LocalDate date) {
        if (date == null) {
            showErrorAlert("Należy wprowadzić datę.");
            return false;
        }

        return true;
    }

    private boolean validateDates(LocalDate since, LocalDate till) {
        if (since != null && till != null && since.compareTo(till) > 0) {
            showErrorAlert("Data 'od' nie moźe być większa od daty 'do'.");
            return false;
        }

        return true;
    }

}