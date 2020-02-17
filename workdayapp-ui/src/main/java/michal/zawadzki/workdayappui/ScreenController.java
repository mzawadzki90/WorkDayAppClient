package michal.zawadzki.workdayappui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    private Map<String, Resource> screenMap;

    @PostConstruct
    public void init() {
        screenMap = new HashMap<>();
        screenMap.put("login", loginForm);
        screenMap.put("list", leaveList);
        screenMap.put("details", leaveDetails);
        screenMap.put("workerList", workerLeaveList);
    }

    public Resource getScreenByName(String name) {
        return Optional.ofNullable(screenMap.get(name))
                       .orElseThrow(() -> new RuntimeException("Screen with given name not exists."));
    }

}