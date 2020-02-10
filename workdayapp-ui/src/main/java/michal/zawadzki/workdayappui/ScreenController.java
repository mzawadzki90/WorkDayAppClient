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

    @Value("classpath:/leave-list.fxml")
    private Resource leaveList;

    @Value("classpath:/leave-details.fxml")
    private Resource leaveDetails;

    private Map<String, Resource> screenMap;

    @PostConstruct
    public void init() {
        screenMap = new HashMap<>();
        screenMap.put("list", leaveList);
        screenMap.put("details", leaveDetails);
    }

    public Resource getScreenByName(String name) {
        return Optional.ofNullable(screenMap.get(name))
                       .orElseThrow(() -> new RuntimeException("Screen with given name not exists."));
    }

}