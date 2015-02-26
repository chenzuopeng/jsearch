package org.jsearch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jsearch.LogicalOperateType;

/**
 * 
 * @author dylan.chen Jul 8, 2009
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Group {

	/**
	 * 组编号
	 * */
	public String[] groupId();
	
	/**
	 * 操作符
	 * */
	public LogicalOperateType[] operate();
}
