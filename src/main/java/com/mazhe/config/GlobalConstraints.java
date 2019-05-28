package com.mazhe.config;

public  class GlobalConstraints {
	private GlobalConstraints() {
	};
	
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_REPORT = "ROLE_REPORT";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	public static final String SESSION_LOGIN_USER = "loginUser";
	
	public static final String MESSAGE_LEVEL_SUCCESS = "success";
	public static final String MESSAGE_LEVEL_INFO = "info";
	public static final String MESSAGE_LEVEL_WARNING = "warning";
	public static final String MESSAGE_LEVEL_ERROR = "error";
	
	// for audit log
	public static final String FUNCTION_USER_LOGIN = "登陆";
	public static final String FUNCTION_USER_CHANGEPWD = "修改密码";
	public static final String STATUS_USER_LOGIN_SUCCESS = "登陆成功";
	public static final String STATUS_USER_LOGIN_FAILURE = "登陆失败";
	
	public static final String CHANGE_PWD_URL = "/changepwd";

	//长险类型
	public static final String SYSTYPE_1 = "L";
	//短险类型
	public static final String SYSTYPE_2 = "S";
	//团险
	public static final String SYSTYPE_3 = "G";
	
	//时分秒
	public static final String START_HMS = " 00:00:00";
	public static final String END_HMS = " 23:59:59";
	
	// call status
	public static final String POL_TRANS_STATUS_OK = "0";
	
	//定结单状态
	public static final String SETTLE_STATUS_1 = "1";//定结单生成未到账
	public static final String SETTLE_STATUS_2 = "2";//定结单取消
	public static final String SETTLE_STATUS_3 = "3";//定结确认到账
	public static final String SETTLE_STATUS_4 = "4";//定结确认中间态(失败)
	
	
}
