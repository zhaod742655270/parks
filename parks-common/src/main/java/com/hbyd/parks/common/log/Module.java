package com.hbyd.parks.common.log;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Module {
    String description() default "默认模块";
}
