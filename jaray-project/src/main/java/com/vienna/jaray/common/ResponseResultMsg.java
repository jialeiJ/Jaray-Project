package com.vienna.jaray.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回json格式数据的包装类
 * @author Administrator
 *
 */
public class ResponseResultMsg {
	
	public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 100;
	
	//状态码：成功-200  失败-100
	private Integer code;
	//提示信息
	private String msg;
	
	//返回给浏览器的数据
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public static ResponseResultMsg success() {
		ResponseResultMsg resultMsg = new ResponseResultMsg();
		resultMsg.setCode(SUCCESS_CODE);
		resultMsg.setMsg("处理成功！");
		return resultMsg;
	}
	
	public static ResponseResultMsg fail() {
		ResponseResultMsg resultMsg = new ResponseResultMsg();
		resultMsg.setCode(FAIL_CODE);
		resultMsg.setMsg("处理失败！");
		return resultMsg;
	}
	
	public ResponseResultMsg add(String key, Object value) {
		this.getMap().put(key, value);
		return this;
	}

	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public ResponseResultMsg setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	public Map<String, Object> getMap() {
		return map;
	}

	public ResponseResultMsg setMap(Map<String, Object> map) {
		this.map = map;
		return this;
	}

}
