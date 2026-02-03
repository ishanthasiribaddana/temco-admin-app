package lk.temcobank.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Interceptor
public class AuditInterceptor implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(AuditInterceptor.class.getName());

    @AroundInvoke
    public Object auditMethod(InvocationContext context) throws Exception {
        String methodName = context.getMethod().getName();
        String className = context.getTarget().getClass().getSimpleName();
        
        LOGGER.log(Level.INFO, "Audit: {0}.{1} invoked", new Object[]{className, methodName});
        
        return context.proceed();
    }
}
