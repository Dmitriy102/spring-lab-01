package kz.iitu.springlab.aspect;

import kz.iitu.springlab.audit.Audited;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;

@Aspect
@Component
@Order(1)   // наименьшее значение = высший приоритет: первым на входе, последним на выходе
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    // имя параметра "audited" связывается с @annotation(audited)
    @Around("@annotation(audited)")
    public Object audit(ProceedingJoinPoint pjp, Audited audited) throws Throwable {
        String action = audited.action();
        log.info("[AUDIT] start {}{}", action,
                audited.logArguments() ? " args=" + Arrays.toString(pjp.getArgs()) : "");
        try {
            Object result = pjp.proceed();
            log.info("[AUDIT] {} success at {}", action, Instant.now());
            return result;
        } catch (Throwable ex) {
            log.error("[AUDIT] {} failure at {}: {}", action, Instant.now(), ex.getMessage());
            throw ex;   // исключение нельзя глотать
        }
    }
}
