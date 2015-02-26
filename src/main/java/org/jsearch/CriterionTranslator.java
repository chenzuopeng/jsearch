package org.jsearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsearch.criterion.CriterionDeclaration;
import org.jsearch.criterion.GroupDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author dylan.chen Jul 7, 2009
 * 
 */
class CriterionTranslator {
	
	private static Logger logger = LoggerFactory.getLogger(CriterionTranslator.class);
	
	public Map<String, String> translate(Map<String, GroupDeclaration> groupDeclarations, BeanObject beanObject) {
		Map<String, String> result = new HashMap<String, String>();
		Map<String, List<CriterionDeclaration>> groups = beanObject.getGroups();
		Map<String, List<String>> paramsOfColumn = beanObject.getParamsOfColumn();
		Set<String> keys = groups.keySet();
		for (String key : keys) {
			List<CriterionDeclaration> criterionDeclarations = groups.get(key);
			int size = criterionDeclarations.size();
			String[] criterionToStringInGroup = new String[size];
			for (int i = 0; i < size; i++) {
				CriterionDeclaration criterionDeclaration = criterionDeclarations.get(i);
				String column = criterionDeclaration.getColumnName();
				String fieldName=criterionDeclaration.getField().getName();
				OperateType operateType = criterionDeclaration.getOperate();
//				criterionToStringInGroup[i] = operateType.buildString(column, paramsOfColumn.get(column).toArray(),criterionDeclaration.getExpression());
				criterionToStringInGroup[i] = operateType.buildString(column, paramsOfColumn.get(fieldName).toArray(),criterionDeclaration.getExpression());
				logger.debug("translate Criterion [{}] of groupId=\"{}\" Group to String [{}]",new Object[]{criterionDeclaration,key,criterionToStringInGroup[i]});
			}
			LogicalOperateType logicalOperateType = groupDeclarations.get(key).getOperate();
			String groupToString = logicalOperateType.buildString(criterionToStringInGroup);
			result.put(key, groupToString);
			logger.debug("translate groupId=\"{}\" Group to String [{}]",key,groupToString);
		}
		return result;
	}
	
}