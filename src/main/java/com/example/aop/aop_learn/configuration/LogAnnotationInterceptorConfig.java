package com.example.aop.aop_learn.configuration;

import com.example.aop.aop_learn.log.LogAnnotation;
import com.example.aop.aop_learn.log.LogAnnotationAdvisor;
import com.example.aop.aop_learn.log.LogAnnotationInterceptor;
import com.example.aop.aop_learn.log.LogAnnotationPointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dewey
 * @date 2019-11-05 11:12
 */
@Configuration
public class LogAnnotationInterceptorConfig {
    @Bean
    public LogAnnotationAdvisor LogAdvisor(){
        LogAnnotationAdvisor advisor = new LogAnnotationAdvisor();

        AnnotationMatchingPointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(LogAnnotation.class);

        advisor.setPointcut(new LogAnnotationPointcut());
        advisor.setPointcut(pointcut);
        advisor.setAdvice(new LogAnnotationInterceptor());
        return advisor;
    }

}
