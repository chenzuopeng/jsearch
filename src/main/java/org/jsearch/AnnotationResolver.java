package org.jsearch;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsearch.annotation.Criterion;
import org.jsearch.annotation.Group;
import org.jsearch.criterion.CriterionDeclaration;
import org.jsearch.criterion.GroupDeclaration;
import org.jsearch.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author dylan.chen Jul 3, 2009
 * 
 */
class AnnotationResolver {

	private static Logger logger = LoggerFactory.getLogger(AnnotationResolver.class);

	/**
	 * 用于查找GroupId的正则表达式对象
	 * */
	private static Pattern groupIdPattern = Pattern.compile(Constants.EXPRESSION_GROUP_ID);

	private static Map<Class<?>, Map<String, GroupDeclaration>> cache = Collections.synchronizedMap(new HashMap<Class<?>, Map<String, GroupDeclaration>>());
	
	public Map<String, GroupDeclaration> resolve(Class<?> clazz) {
		Map<String, GroupDeclaration> groups = cache.get(clazz);
		if (groups == null) {
			logger.debug("could not find GroupDeclaration for class=\"{}\" in cache",clazz);
			groups = resolveGroup(clazz);
			resolveCriterion(clazz, groups);
			cache.put(clazz, groups);
		}else{
			logger.debug("find GroupDeclaration for class=\"{}\" in cache",clazz);
		}
		return groups;
	}

	private Map<String, GroupDeclaration> resolveGroup(Class<?> clazz) {
		Map<String, GroupDeclaration> groups = new HashMap<String, GroupDeclaration>();
		if (clazz.isAnnotationPresent(Group.class)) {
			Group group = clazz.getAnnotation(Group.class);
			String[] groupIds = group.groupId();
			LogicalOperateType[] operates = group.operate();
			for (int i = 0; i < groupIds.length; i++) {
				String groupId = formatGroupId(groupIds[i]);
				if (isValidGroupId(groupId)) {
					LogicalOperateType logicalOperateType = i < operates.length ? operates[i] : LogicalOperateType.AND;
					GroupDeclaration groupDeclaration = new GroupDeclaration();
					groupDeclaration.setBeanClass(clazz);
					groupDeclaration.setGroupId(groupId);
					groupDeclaration.setOperate(logicalOperateType);
					groups.put(groupId, groupDeclaration);
					logger.debug("resolve {} from {}", groupDeclaration, clazz.getName());
				} else {
					logger.warn("groupId=\"{}\" of Group on {} is illegal", groupId, clazz);
				}
			}
		}else{
			GroupDeclaration groupDeclaration = new GroupDeclaration();
			groupDeclaration.setBeanClass(clazz);
			groupDeclaration.setGroupId(Constants.DEFAULT_GROUP_ID);
			groupDeclaration.setOperate(LogicalOperateType.AND);
			groups.put(Constants.DEFAULT_GROUP_ID, groupDeclaration);
			logger.debug("could not find Group Annotation on {},add default group[{}]",clazz.getName(),groupDeclaration);
		}
		return groups;
	}

	private void resolveCriterion(Class<?> clazz, Map<String, GroupDeclaration> groups) {
		Field[] fields = BeanUtils.getFieldsByAnnotation(clazz, Criterion.class);
		for (Field field : fields) {
			Criterion annotationOfField = field.getAnnotation(Criterion.class);
			String groupId = annotationOfField.groupId();
			groupId = formatGroupId(groupId);
			if (isValidGroupId(groupId) && groups.containsKey(groupId)) {
				CriterionDeclaration criterionDeclaration = new CriterionDeclaration();
				criterionDeclaration.setBeanClass(clazz);
				criterionDeclaration.setField(field);
				criterionDeclaration.setColumnName(getColumnName(field, annotationOfField));
				criterionDeclaration.setExpression(annotationOfField.expression());
				criterionDeclaration.setOperate(annotationOfField.operate());
				criterionDeclaration.setGroupId(groupId);
				groups.get(groupId).add(criterionDeclaration);
				logger.debug("resolve {} with groupId=\"{}\" from {}",new Object[]{criterionDeclaration,groupId,clazz.getName()});
			} else {
				logger.warn("groupId=\"{}\" of name=\"{}\" of Field is illegal", groupId, field.getName());
			}
		}
	}

	/**
	 * 格式化组ID.
	 * 
	 * @param groupId
	 *            组ID
	 * @return String
	 **/
	private String formatGroupId(String groupId) {
		return groupId.trim();
	}

	/**
	 * 获取列名.
	 * 
	 * @param field
	 *            属性
	 * @param criterion
	 *            属性的注释
	 * @return String
	 **/
	protected String getColumnName(Field field, Criterion annotationOfField) {
		String columnName = annotationOfField.columnName();
		if (StringUtils.isBlank(columnName)) {
			columnName = field.getName();
		}
		return columnName;
	}

	/**
	 * @Description: 验证groupId是否有效
	 * @param groupId
	 * @return boolean
	 * */
	private boolean isValidGroupId(String groupId) {
		if (StringUtils.isNotBlank(groupId)) {
			// GroupId字符串必须不能包含组标签
			Matcher matcher = groupIdPattern.matcher(groupId);
			if (!matcher.find()) {
				return true;
			}
		}
		return false;
	}

}
