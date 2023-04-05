package com.example.gatewayservice.logger;

import com.example.loggerapi.utils.LoggerUtils2;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LoggerServiceAspect {
    @Autowired
    private ObjectMapper mapper;
    @Pointcut("within(com.example.gatewayservice.service..*) "
            +"|| within(com.example.gatewayservice.mapper..*)"
            +"|| within(com.example.gatewayservice.consumer..*)")
    public void aroundService() {

    }
    @Around("aroundService()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Map<String, Object> parameters = getParameters(joinPoint);
            LoggerUtils2.info(joinPoint.getSignature().getDeclaringType(), joinPoint.getSignature().getName(), "request", mapper.writeValueAsString(parameters));
            final Object result = joinPoint.proceed();
            LoggerUtils2.info(joinPoint.getSignature().getDeclaringType(), joinPoint.getSignature().getName(), "response", mapper.writeValueAsString(result));
            return result;
        } catch (Exception e) {
            LoggerUtils2.info(joinPoint.getSignature().getDeclaringType(), joinPoint.getSignature().getName(), "exception", e);
            return e;
        }
    }
    private Map<String, Object> getParameters(JoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();

        HashMap<String, Object> map = new HashMap<>();

        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], joinPoint.getArgs()[i]);
        }
        return map;
    }
}
