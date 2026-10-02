package kz.iitu.springlab.service;

import kz.iitu.springlab.audit.Audited;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class CatalogService {

    // --- Задание 4.5: вариант исправления через self-reference. Раскомментируйте ПОСЛЕ эксперимента ---
    // @org.springframework.beans.factory.annotation.Autowired
    // @org.springframework.context.annotation.Lazy
    // private CatalogService self;

    public String findById(long id) {
        sleep(50);
        return "Item no. " + id;
    }

    @Audited(action = "CATALOG_LIST", logArguments = true)
    public List<String> findAll(int limit) {
        sleep(300); // намеренно «медленный»
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "Item no. " + i)
                .toList();
    }

    @Audited(action = "CATALOG_REMOVE")
    public String remove(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid identifier: " + id);
        }
        return "Removed item no. " + id;
    }

    // Задание 4.3: self-invocation (вызов через this мимо прокси)
    public String removeTwice(long id) {
        String first  = remove(id);       // ПОСЛЕ ИСПРАВЛЕНИЯ: self.remove(id)
        String second = remove(id + 1);   // ПОСЛЕ ИСПРАВЛЕНИЯ: self.remove(id + 1)
        return first + "; " + second;
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
