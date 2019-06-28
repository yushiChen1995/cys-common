package com.cys.constraints;

import com.cys.constraints.impl.MobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;



/**
 * @author chenyushi
 * @date 2019-06-28
 *  Target：设置指定注解的作用域
 *         ElementType.TYPE            类上
 *         ElementType.FIELD           属性上
 *         ElementType.CONSTRUCTOR     构造方法上
 *         ElementType.METHOD          方法上
 *  Retention:设置注解的生命周期
 *         SOURCE      编译时期
 *         CLASS       编译后运行前
 *         RUNTIME     运行时都有效      [常用的生命周期，它要结合反射使用]
 *
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy= MobileValidator.class)
public @interface Mobile {
	String message() default "mobile不是有效的手机号码格式";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
