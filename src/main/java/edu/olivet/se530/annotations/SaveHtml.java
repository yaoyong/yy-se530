package edu.olivet.se530.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 保存网页注解，对所声明的方法可以保存网页到本地文件
 * <a href="mailto:nathanaelibport@gmail.com">Nathanael Yang</a> Jan 21, 2015 8:22:36 AM
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD) 
public @interface SaveHtml {

	boolean save() default true;
}
