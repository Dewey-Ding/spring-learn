package com.example.learn.log;

import java.lang.annotation.*;

/**
 * @author dewey
 * @date 2019-11-02 19:36
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface LogAnnotation {
    String value();
}
