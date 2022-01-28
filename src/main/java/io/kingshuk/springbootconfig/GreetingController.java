package io.kingshuk.springbootconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/greeting")
@RefreshScope
public class GreetingController {

    @Value("${my.greeting:FO}")
    private String greeting;

    @Value("static greeting")
    private String greetingStatic;

    @Value("${my.list.greeting:null}")
    private List<String> greetingList;

    @Value("#{${my.map.greeting:null}}")
    private Map<String, String> greetingMap;

    @Autowired
    private AppConfigProperties appConfigProperties;

    @Autowired
    private Environment environment;

    @GetMapping
    public String greeting() {
        return greeting;
    }

    @GetMapping("/static")
    public String greetingStatic() {
        return greetingStatic;
    }

    @GetMapping("/list")
    public String greetingList() {
        return Arrays.toString(greetingList.toArray());
    }

    @GetMapping("/map")
    public String greetingMap() {
        return greetingMap.keySet().stream()
                .map(key -> key + "=" + greetingMap.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    @GetMapping("/config")
    public String greetingConfig() {
        return appConfigProperties.getName() + " || " + appConfigProperties.getDescription() + " || "
                + appConfigProperties.getGreeting().getList() + " || " + appConfigProperties.getGreeting().getMessages();
    }

    @GetMapping("/env-details")
    public String getEnvDetails() {
        return environment.toString();
    }
}
