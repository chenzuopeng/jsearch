package org.jsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 此类用于清除无用的where,and,or字符.
 *  
 * @author dylan.chen Jul 7, 2009
 * 
 */
public class WhereClauseCleaner implements IExpressionCleaner{
	
	private static final Logger logger = LoggerFactory.getLogger(WhereClauseCleaner.class);

	protected final static Pattern AND_OR_PATTERN=Pattern.compile("(\\s)(OR\\s+(AND))(\\s)|(\\s)((AND)\\s+OR)(\\s)");
	
	protected final static Pattern AND_OR_N_PATTERN=Pattern.compile("(\\s)(AND|OR)(\\s+)(AND|OR|WHERE|GROUP|ORDER|,|\\)|FROM)(\\s)");
	
	protected final static Pattern AND_OR_P_PATTERN=Pattern.compile("(\\s)(AND|OR|\\(|WHERE)(\\s+)(AND|OR)(\\s)");
	
	protected final static Pattern WHERE_PATTERN=Pattern.compile("(\\s)(WHERE)(\\s)+(WHERE|GROUP|ORDER|,|\\)|FROM)(\\s)");
	/**
	 * 清除"[and]多个或一个空格[or]"和"[or]多个或一个空格[and]"子串中的"and"
	 *    例："and or"-->" or","or and"-->"or "
	 * */
	protected final static IExpressionCleaner AND_OR=new IExpressionCleaner(){
			public String clean(String input) {
				logger.debug("[AND_OR] Input:"+input);
				String tmp=input;	
				Matcher matcher=AND_OR_PATTERN.matcher(tmp.toUpperCase());
				while(matcher.find()){
					if(matcher.group(2)!=null){
						if (logger.isDebugEnabled()) {
							logger.debug("[AND_OR]:"+"start:"+matcher.start()+",end:"+matcher.end()+",group:"+matcher.group()+"|replace:"+matcher.group(3));
						}
						tmp=tmp.substring(0,matcher.start(3))+tmp.substring(matcher.end(3));
					}else if(matcher.group(6)!=null){
						if (logger.isDebugEnabled()) {
							logger.debug("[AND_OR]:"+"start:"+matcher.start()+",end:"+matcher.end()+",group:"+matcher.group()+"|replace:"+matcher.group(7));
						}
						tmp=tmp.substring(0, matcher.start(7))+tmp.substring(matcher.end(7));
					}
					matcher=AND_OR_PATTERN.matcher(tmp.toUpperCase());
					logger.debug("[AND_OR]:"+tmp);
				}
				return tmp;
			}
	};

	/**
	 * 清除"[and或or]多个或一个空格[and或or或where或group或order或,或)或from]"子串中的空格前面的"and或or"
	 *    例："and or"-->" or","or and"-->" and","and where"-->"where"
	 * */
	protected final static IExpressionCleaner AND_OR_N=new IExpressionCleaner(){
			
			public String clean(String input) {
				logger.debug("[AND_OR_N] Input:"+input);
				String tmp=input;
				Matcher matcher=AND_OR_N_PATTERN.matcher(tmp.toUpperCase());
				while(matcher.find()){
					if (logger.isDebugEnabled()) {
					   logger.debug("[AND_OR_N]:" + "start:" + matcher.start()
							  + ",end:" + matcher.end() + "|group:"
							  + matcher.group() + "|replace:" + matcher.group(2));
				    }
					tmp=tmp.substring(0,matcher.start(2))+tmp.substring(matcher.end(2));
					matcher=AND_OR_N_PATTERN.matcher(tmp.toUpperCase());
					logger.debug("[AND_OR_N]:"+tmp);
				}
				return tmp;
			}
	};
	
	/**
	 * 清除"[and或or或(或where]多个或一个空格[and或or]"子串中的空格后面的"and或or"
	 *    例："and or"-->"and ","where and"-->"where ","( and"-->""( ;
	 * */
	protected final static IExpressionCleaner AND_OR_P=new IExpressionCleaner(){

			public String clean(String input) {
				logger.debug("[AND_OR_P] Input:"+input);
				String tmp=input;
				Matcher matcher=AND_OR_P_PATTERN.matcher(tmp.toUpperCase());
				while(matcher.find()){
					if (logger.isDebugEnabled()) {
						  logger.debug("[AND_OR_P]:"+"start:"+matcher.start()+ ",end:" + matcher.end() +"|group:"+matcher.group()+"|replace:"+matcher.group(4));
					}
					tmp=tmp.substring(0,matcher.start(4))+tmp.substring(matcher.end(4));
					matcher=AND_OR_P_PATTERN.matcher(tmp.toUpperCase());
					logger.debug("[AND_OR_P]:"+tmp);
				}
				return tmp;
			}
	};
	
	/**
	 * 清除空的where子句中的的where
	 *    例："where and"-->" and ","where group"-->" group"
	 * */
	protected final static IExpressionCleaner WHERE=new IExpressionCleaner(){
			
			public String clean(String input) {
				logger.debug("[WHERE] Input:"+input);
				String tmp=input;
				Matcher matcher=WHERE_PATTERN.matcher(tmp.toUpperCase());
				while(matcher.find()){
					if (logger.isDebugEnabled()) {
					   logger.debug("[WHERE]:"+"start=:"+matcher.start()+"|group:"+matcher.group()+"|replace:"+matcher.group(1));
					}
					tmp=tmp.substring(0, matcher.start(2))+tmp.substring(matcher.end(2));
					matcher=WHERE_PATTERN.matcher(tmp.toUpperCase());
					logger.debug("[WHERE]:"+tmp);
				}
				return tmp;
			}
	};
	
	/**
	 * AND_OR必须要放在最前,WHERE放在最后
	 * */
	protected final static IExpressionCleaner[] cleaners={AND_OR,AND_OR_N,AND_OR_P,WHERE};
			
	public String clean(String input) {
		logger.debug("Input:"+input);
		String tmp=" ( "+input+" ) ";//构造处理最后一个非法符号的处理串
		for(IExpressionCleaner cleaner:cleaners){
			tmp=cleaner.clean(tmp);
		}
		//去除前面添加的部分
		tmp=tmp.substring(3,tmp.length()-3);
		logger.debug("Ounput:"+tmp);
		return tmp;
	}
}
