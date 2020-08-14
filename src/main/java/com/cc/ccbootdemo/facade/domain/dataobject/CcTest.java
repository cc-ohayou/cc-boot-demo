package com.cc.ccbootdemo.facade.domain.dataobject;

import java.io.Serializable;

/**
 * cc_test
 * @author 
 */
public class CcTest implements Serializable {
    private Long id;

    private String description;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}