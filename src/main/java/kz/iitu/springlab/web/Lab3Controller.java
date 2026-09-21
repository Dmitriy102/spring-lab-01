package kz.iitu.springlab.web;

import kz.iitu.springlab.config.*;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/lab3")
public class Lab3Controller {

    private final AppProperties props;
    private final EnvironmentBanner banner;
    private final Environment environment;

    public Lab3Controller(AppProperties props, EnvironmentBanner banner, Environment environment) {
        this.props = props;
        this.banner = banner;
        this.environment = environment;
    }

    @GetMapping("/config")
    public Map<String, Object> config() {
        Map<String, Object> configMap = new LinkedHashMap<>();
        
        configMap.put("owner", props.owner());
        configMap.put("group", props.group());
        configMap.put("mailFrom", props.mail().from());
        configMap.put("mailRetryCount", props.mail().retryCount());
        configMap.put("mailTimeout", props.mail().timeout().toString());
        configMap.put("mailEnabled", props.mail().enabled());
        configMap.put("uiTheme", props.ui().theme());
        configMap.put("uiItemsPerPage", props.ui().itemsPerPage());
        configMap.put("serverPort", environment.getProperty("server.port"));
        configMap.put("activeProfiles", Arrays.asList(environment.getActiveProfiles()));
        configMap.put("banner", banner.describe());

        return configMap;
    }
}