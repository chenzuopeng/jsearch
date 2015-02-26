package org.jsearch;

/**
 * 
 * 
 * @author dylan.chen Jul 7, 2009
 * 
 */
public final class Constants {

	private Constants(){
	}
	
	/**
	 * null的字符值
	 * */
	public final static String NULL="null";
	
	/**
	 * 空格符
	 * */
	public final static String BLANK=" ";
	
	/**
	 * like值的占位符
	 * */
	public final static String PLACEHOLDER_LIKE="${P}";
	
	/**
	 * like查询的表达式的值
	 * */
	public final static String DEFAULT_EXPRESSION_LIKE="'"+PLACEHOLDER_LIKE+"%'";
	
	/**
	 * 组标记的表达式
	 * */
	public final static String EXPRESSION_GROUP_ID="\\$\\{(.*?)\\}";
	
	/**
	 * 组标记的左边界
	 * */
	public final static String GROUP_ID_LEFT="${";
	
	/**
	 * 组标记的右边界
	 * */
	public final static String GROUP_ID_RIGHT="}";
	
	/**
	 * 默认的组ID
	 * */
	public final static String DEFAULT_GROUP_ID="0";
	
}
