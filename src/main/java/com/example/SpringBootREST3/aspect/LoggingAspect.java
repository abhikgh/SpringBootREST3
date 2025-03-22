package com.example.SpringBootREST3.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

/*
    @Aspect :: proxy-class
               When we call a method of our proxy, the call is delegated to the implementation class
               or do things before, after, or around the delegation
*/

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //Around : Advice (Advice taken by the Aspect at a particular JoinPoint)
    //@annotation(Logging) :: Pointcut (expression that selects one or more JoinPoints where Advice is executed)
    //aroundGovernedTransaction :: JoinPoint ( method where Aspect gets plugged-in )
    /*
        1. Where :: @annotation(Logging)
        2. How   :: Around
        3. What  :: aroundGovernedTransaction
     */
    @Around("@annotation(Logging)")
    public Object aroundGovernedTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        //Get intercepted method details
        String methodName = methodSignature.getName();
        String className = methodSignature.getDeclaringType().getSimpleName();

        long start = System.currentTimeMillis();
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);
        beforeGoverenedTransaction(methodName, className);
        Object response = joinPoint.proceed(); //executes the joinpoint
        afterGovernedTransaction(methodName, className, start);
        return response;
    }

    private void beforeGoverenedTransaction(String methodName, String className) {
        log.info("Start :: Method {} , transactionId is {}", methodName + ":" +className, MDC.get("transactionId"));
    }

    private void afterGovernedTransaction(String methodName, String className, long start) {
        log.info("End :: Method {} , transactionId is {}, time in millis {}", methodName + ":" +className, MDC.get("transactionId"), System.currentTimeMillis() - start);
    }

   /* private String getRequestUrl(ProceedingJoinPoint joinPoint) {
        String requestUrl = httpServletRequest.getRequestURL().toString();
        Map<String, Object> paramsMap = new HashMap<>();
        String[] parameterNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if(ArrayUtils.isNotEmpty(parameterNames)){
            IntStream.rangeClosed(0,parameterNames.length-1)
                    .forEach(i -> paramsMap.put(parameterNames[i],joinPoint.getArgs()[i]));
        }
        StringBuffer stringBuffer = new StringBuffer(requestUrl+"?");
        paramsMap.entrySet().stream().forEach(entry -> stringBuffer.append(entry.getKey()+"="+entry.getValue()+"&"));
        String url = stringBuffer.toString();
        url = url.substring(0, url.length()-1);
        return url;
    }*/


}
