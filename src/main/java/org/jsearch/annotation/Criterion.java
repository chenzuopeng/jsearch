package org.jsearch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsearch.Constants;
import org.jsearch.OperateType;


/**
 *
 * 查询条件.
 *  
 * @author dylan.chen Jul 3, 2009
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Criterion {
	
	/**
	 * 字段或属性名
	 * */
	public String columnName() default "";
	
	/**
	 * 组编号
	 * */
	public String groupId() default Constants.DEFAULT_GROUP_ID;
    
	/**
	 * 操作符
	 * */
	public OperateType operate() default OperateType.EQ;
	
	/**
	 * like查询的表达式
	 * */
	public String expression() default Constants.DEFAULT_EXPRESSION_LIKE;
}
