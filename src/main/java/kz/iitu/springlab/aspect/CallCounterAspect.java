package kz.iitu.springlab.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class CallCounterAspect {

    // Хранилище: имя метода -> количество вызовов (ConcurrentHashMap по условию)
    private final Map<String, Integer> counterMap = new ConcurrentHashMap<>();

    // Срабатывает перед публичными методами сервисного слоя
    @Before("kz.iitu.springlab.aspect.Pointcuts.serviceOperation()")
    public void countCall(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        
        // Увеличиваем счетчик для метода
        counterMap.merge(methodName, 1, Integer::sum);
        
        // Выводим специальный тег [COUNT], который просил преподаватель
        System.out.println("[COUNT] Method " + methodName + " called. Total count: " + counterMap.get(methodName));
    }

    // Метод, чтобы контроллер мог забрать накопленную статистику
    public Map<String, Integer> getStatistics() {
        return counterMap;
    }
}