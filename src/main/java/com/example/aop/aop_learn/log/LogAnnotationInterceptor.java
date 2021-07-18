package com.example.aop.aop_learn.log;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author dewey
 * @date 2019-11-05 11:11
 */
public class LogAnnotationInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("before method invoke "+System.currentTimeMillis());
        Object[] args = methodInvocation.getArguments();
        Object proceed = methodInvocation.proceed();
        LogAnnotation annotation = methodInvocation.getMethod().getAnnotation(LogAnnotation.class);
        System.out.println(annotation.value()+"=====");
        if(args!=null) {
            for (int i = 0; i < args.length; i++) {
                System.out.println(args[i].toString());
            }
        }
        System.out.println("after method invoke "+System.currentTimeMillis());
        return proceed;
    }
}
