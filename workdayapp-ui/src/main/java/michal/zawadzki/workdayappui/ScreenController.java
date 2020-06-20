package michal.zawadzki.workdayappui;

import static michal.zawadzki.workdayappui.ScreenName.CHANGE_LEAVE_REQUEST_STATUS;
import static michal.zawadzki.workdayappui.ScreenName.LEAVE_REQUEST_DETAILS;
import static michal.zawadzki.workdayappui.ScreenName.LEAVE_REQUEST_LIST;
import static michal.zawadzki.workdayappui.ScreenName.LOGIN;
import static michal.zawadzki.workdayappui.ScreenName.WORKERS_LEAVE_REQUEST_LIST;
import static michal.zawadzki.workdayappui.ScreenName.WORKER_LIST;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ScreenController {

    @Value("classpath:/login.fxml")
    private Resource loginForm;

    @Value("classpath:/leave-list.fxml")
    private Resource leaveList;

    @Value("classpath:/worker-leave-list.fxml")
    private Resource workerLeaveList;

    @Value("classpath:/leave-details.fxml")
    private Resource leaveDetails;

    @Value("classpath:/change-status.fxml")
    private Resource changeStatus;

    @Value("classpath:/worker-list.fxml")
    private Resource workerList;

    private Map<ScreenName, Resource> screenMap;

    @PostConstruct
    public void init() {
        screenMap = new HashMap<>();
        screenMap.put(LOGIN, loginForm);
        screenMap.put(LEAVE_REQUEST_LIST, leaveList);
        screenMap.put(LEAVE_REQUEST_DETAILS, leaveDetails);
        screenMap.put(WORKERS_LEAVE_REQUEST_LIST, workerLeaveList);
        screenMap.put(CHANGE_LEAVE_REQUEST_STATUS, changeStatus);
        screenMap.put(WORKER_LIST, workerList);
    }

    public Resource getScreenByName(ScreenName name) {
        return Optional.ofNullable(screenMap.get(name)).orElseThrow(() -> new RuntimeException("Screen with given name not exists."));
    }

}