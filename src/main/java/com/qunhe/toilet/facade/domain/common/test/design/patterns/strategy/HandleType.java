package com.qunhe.toilet.facade.domain.common.test.design.patterns.strategy;

import java.lang.annotation.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/6/11 11:40.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandleType {

    String value();
}
