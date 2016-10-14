package com.mo9.web.utils.log4j;

import org.apache.log4j.Logger;

import com.mo9.utils.log4j.Log4jUtil;
/**
 * 
 * @author beiteng
 * log4j测试
 */
public class Log4jTest {
	private static Logger logger = Logger.getLogger(Log4jTest.class);  

    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        Log4jUtil.diffLevelLog(); 
    }  
}
