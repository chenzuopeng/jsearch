package org.jsearch;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 逻辑操作符类型.
 * 
 * @author dylan.chen Jul 5, 2009
 * 
 */
public enum LogicalOperateType {

	AND(" and "),

	OR(" or ");

	private String operator;

	private LogicalOperateType(String operator) {
		this.operator = operator;
	}

	public String buildString(String[] operands) {
		return "("+StringUtils.join(operands, this.operator)+")";
	}
}
