package com.example.aop.aop_learn.log;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * @author dewey
 * @date 2019-11-05 11:10
 */
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
public class LogAnnotationAdvisor extends AbstractPointcutAdvisor {

    private Pointcut pointcut;

    private Advice advice;


    public void setPointcut(Pointcut pointcut){
        this.pointcut = pointcut;
    }

    public void setAdvice(Advice advice){
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}
