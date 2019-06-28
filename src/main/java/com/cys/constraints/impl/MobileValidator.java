package com.cys.constraints.impl;

import com.cys.constraints.Mobile;
import com.cys.utils.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * @author chenyushi
 * @date 2019-06-28
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {


	@Override
	public void initialize(Mobile constraintAnnotation) {

	}

	@Override
	public boolean isValid(String mobile, ConstraintValidatorContext constraintValidatorContext) {
		boolean ret = true;
		if(mobile != null){
			ret = StringUtil.isMobile(mobile);
		}
		return ret;
	}
}
