package org.jsearch;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * 操作符.
 * 
 * @author dylan.chen Jul 3, 2009
 * 
 */
public enum OperateType {

	/**
	 * 等于
	 */
	EQ("%s = :%s", Amount.ONE, OperateTypeToStringBuilder.GENERAL_TO_STRING_BUILDER),

	/**
	 * 不等于
	 */
	NE("%s <> :%s", Amount.ONE, OperateTypeToStringBuilder.GENERAL_TO_STRING_BUILDER),

	/**
	 * 大于
	 */
	GT("%s > :%s", Amount.ONE, OperateTypeToStringBuilder.GENERAL_TO_STRING_BUILDER),
	/**
	 * //大等于
	 */
	GE("%s >= :%s", Amount.ONE, OperateTypeToStringBuilder.GENERAL_TO_STRING_BUILDER),
	/**
	 * 小于
	 */
	LT("%s < :%s", Amount.ONE, OperateTypeToStringBuilder.GENERAL_TO_STRING_BUILDER),
	/**
	 * 小等于
	 */
	LE("%s <= :%s", Amount.ONE, OperateTypeToStringBuilder.GENERAL_TO_STRING_BUILDER),
	/**
	 * in
	 */
	IN("%s in (%s)", Amount.MULTI, OperateTypeToStringBuilder.IN_TO_STRING_BUILDER),
	/**
	 * not in
	 */
	NOT_IN("%s not in (%s)", Amount.MULTI, OperateTypeToStringBuilder.IN_TO_STRING_BUILDER),
	/**
	 * between
	 */
	BETWEEN("%s between :%s and :%s", Amount.TWO, OperateTypeToStringBuilder.BETWEEN_TO_STRING_BUILDER),
	/**
	 * 
	 */
	NOT_BETWEEN("%s not between :%s and :%s", Amount.TWO, OperateTypeToStringBuilder.BETWEEN_TO_STRING_BUILDER),
	/**
	 * 
	 */
	LIKE("%s like :%s", Amount.ONE, OperateTypeToStringBuilder.GENERAL_TO_STRING_BUILDER),
	/**
	 * 
	 */
	NOT_LIKE("%s not like :%s", Amount.ONE, OperateTypeToStringBuilder.GENERAL_TO_STRING_BUILDER);

	private interface OperateTypeToStringBuilder {

		public final static OperateTypeToStringBuilder GENERAL_TO_STRING_BUILDER = new OperateTypeToStringBuilder() {
			public String toString(OperateType operateType, String leftOperand, Object[] rightOperands, String expression) {
				return String.format(operateType.template, leftOperand, rightOperands[0]);
			}
		};

		public final static OperateTypeToStringBuilder BETWEEN_TO_STRING_BUILDER = new OperateTypeToStringBuilder() {
			public String toString(OperateType operateType, String leftOperand, Object[] rightOperands, String expression) {
				return String.format(operateType.template, leftOperand, rightOperands[0], rightOperands[1]);
			}
		};

		public final static OperateTypeToStringBuilder IN_TO_STRING_BUILDER = new OperateTypeToStringBuilder() {
			public String toString(OperateType operateType, String leftOperand, Object[] rightOperands, String expression) {
				return String.format(operateType.template, leftOperand, ":"+StringUtils.join(rightOperands, ",:"));
			}
		};

		public String toString(OperateType operateType, String leftOperand, Object[] rightOperands, String expression);

	}

	/**
	 * 操作数个数枚举
	 */
	public enum Amount {
		/**
		 * 无
		 */
		ZERO,
		/**
		 * 一个
		 */
		ONE,
		/**
		 * 二个
		 */
		TWO,
		/**
		 * 多个,一个以上
		 * */
		MULTI;

		public static Amount valueOf(int ordinal) {
			for (Amount amount : values()) {
				if (amount.ordinal() == ordinal) {
					return amount;
				}
			}
			return MULTI;
		}
	}

	/**
	 * 操作符的字符串形式
	 */
	private String template;

	/**
	 * 右操作数个数
	 */
	private Amount amountOfRightOperand;

	private OperateTypeToStringBuilder toStringBuilder;

	private OperateType(String template, Amount amountOfRightOperand, OperateTypeToStringBuilder toStringBuilder) {
		this.template = template;
		this.amountOfRightOperand = amountOfRightOperand;
		this.toStringBuilder = toStringBuilder;
	}

	public boolean validateRightOperand(Object rightOperands) {
		if (Amount.TWO.compareTo(this.amountOfRightOperand) == 0) {
			Amount amount = Amount.valueOf(ArrayUtils.getLength(rightOperands));
			return rightOperands.getClass().isArray() && amount == this.amountOfRightOperand;
		}
		return true;
	}

	public String buildString(String leftOperand, Object[] rightOperands, String expression) {
		return this.toStringBuilder.toString(this, leftOperand, rightOperands, expression);
	}

	public Amount getAmountOfRightOperand() {
		return this.amountOfRightOperand;
	}

}
