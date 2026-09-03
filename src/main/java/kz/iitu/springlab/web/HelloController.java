package kz.iitu.springlab.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting("Hello, " + name + "!", owner, LocalDateTime.now());
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(owner,
                        System.getProperty("java.version"),
                        Runtime.getRuntime().availableProcessors());
    }

    // --- Индивидуальное задание (Вариант 1) ---
    @GetMapping("/sum")
    public CalcResult calculate(@RequestParam(defaultValue = "0") int a, 
                                @RequestParam(defaultValue = "0") int b) {
        return new CalcResult(a + b, a - b, a * b);
    }

    public record Greeting(String message, String owner, LocalDateTime timestamp) { }
    
    public record Info(String owner, String javaVersion, int cpuCores) { }
    
    // Класс-ответ для индивидуального задания (Вариант 1)
    public record CalcResult(int sum, int difference, int product) { }
}