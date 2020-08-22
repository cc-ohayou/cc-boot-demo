/*
 * BasePageCondition.java
 * Copyright 2020 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.qunhe.toilet.core;

import lombok.Data;

/**
 * @Author bupo
 * @DATE 2020/8/12 17:28
 * @Description
 */
@Data
public class BasePageCondition {
    private Integer page;
    private Integer size;
}
