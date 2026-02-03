package lk.temcobank.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Interceptor
public class LoggingInterceptor implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(LoggingInterceptor.class.getName());

    @AroundInvoke
    public Object logMethodEntry(InvocationContext context) throws Exception {
        String methodName = context.getMethod().getName();
        String className = context.getTarget().getClass().getSimpleName();
        
        LOGGER.log(Level.FINE, "Entering {0}.{1}", new Object[]{className, methodName});
        
        long startTime = System.currentTimeMillis();
        try {
            return context.proceed();
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            LOGGER.log(Level.FINE, "Exiting {0}.{1} - Duration: {2}ms", 
                new Object[]{className, methodName, duration});
        }
    }
}
