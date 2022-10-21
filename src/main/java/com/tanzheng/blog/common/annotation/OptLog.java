package com.tanzheng.blog.common.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author shino
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    /**
     * @return 操作类型
     */
    String optType() default "";

}
