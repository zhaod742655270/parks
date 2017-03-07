package com.hbyd.parks.common.log;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Operation {
    String type() default "默认操作";
}
