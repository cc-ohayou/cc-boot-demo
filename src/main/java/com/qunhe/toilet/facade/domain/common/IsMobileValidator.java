package com.qunhe.toilet.facade.domain.common;

import com.qunhe.toilet.facade.domain.common.annotation.IsMobile;
import com.qunhe.toilet.facade.domain.common.util.RegUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/5/29 21:13.
 */
public class  IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    private boolean required=false;



    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required=constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       if(required){
           return RegUtil.isPhoneValid(value);
       }else{
           if(StringUtils.isEmpty(value)){
               return true;
           }
       }

        return false;
    }
}
