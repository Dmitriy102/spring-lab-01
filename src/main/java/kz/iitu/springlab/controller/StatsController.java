package kz.iitu.springlab.controller;

import kz.iitu.springlab.aspect.CallCounterAspect;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/lab4")
public class StatsController {

    private final CallCounterAspect callCounterAspect;

    // Внедряем аспект через конструктор, чтобы взять из него карту со статистикой
    public StatsController(CallCounterAspect callCounterAspect) {
        this.callCounterAspect = callCounterAspect;
    }

    // Эндпоинт для вывода статистики
    @GetMapping("/stats")
    public Map<String, Integer> getStats() {
        return callCounterAspect.getStatistics();
    }
}