package org.jsearch;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*
* 此类用于解析模板.
*  
* @author dylan.chen Jul 7, 2009
* 
*/
class TemplateParser {

	private static Logger logger = LoggerFactory.getLogger(TemplateParser.class);

	protected static IExpressionCleaner whereClauseCleaner = new WhereClauseCleaner();

	public String parse(String template, Map<String, String> groups) {
		String result = template;
		result = cleanEmptyGroup(result, groups);
		result = whereClauseCleaner.clean(result);

		Set<String> keys = groups.keySet();
		for (String key : keys) {
			result = result.replaceAll(getGroupExpression(key), groups.get(key));
		}
		logger.debug("parse [{}] from template=\"{}\"",result,template);
		return result;
	}

	/**
	 * 清除无值的分组标签
	 * */
	protected String cleanEmptyGroup(String exp, Map<String, String> groups) {
		Pattern pattern = Pattern.compile(Constants.EXPRESSION_GROUP_ID);
		Matcher matcher = pattern.matcher(exp);
		Set<String> keySet = groups.keySet();
		String groupId = null;
		while (matcher.find()) {
			groupId = matcher.group(1);
			if (!keySet.contains(groupId)) {
				exp = exp.replaceAll(this.getGroupExpression(groupId), Constants.BLANK);
				if (logger.isDebugEnabled()) {
					logger.debug("clean empty Group with groupId:{}", this.getGroupLabel(groupId));
				}
			}
		}
		return exp;
	}

	/**
	 * 表达式上的组标识
	 */
	protected String getGroupLabel(String groupId) {
		return Constants.GROUP_ID_LEFT + groupId + Constants.GROUP_ID_RIGHT;
	}

	/**
	 * 用于替换组的表达式
	 */
	protected String getGroupExpression(String groupLabel) {
		return Pattern.quote(getGroupLabel(groupLabel));

	}
}