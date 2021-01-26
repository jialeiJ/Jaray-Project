package com.vienna.jaray.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 通用的返回json格式数据包装类
 */
public class ResponseResult implements Serializable {
	private static final long serialVersionUID = -1358179221615132114L;
	public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 100;

	/**
	 * 状态码：成功-200  失败-100
	 */
	private Integer code;
	/**
	 * 提示信息
	 */
	private String msg;

	/**
	 * 返回给浏览器的数据
	 */
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public static ResponseResult success() {
		ResponseResult resultMsg = new ResponseResult();
		resultMsg.setCode(SUCCESS_CODE);
		resultMsg.setMsg("处理成功！");
		return resultMsg;
	}
	
	public static ResponseResult fail() {
		ResponseResult resultMsg = new ResponseResult();
		resultMsg.setCode(FAIL_CODE);
		resultMsg.setMsg("处理失败！");
		return resultMsg;
	}
	
	public ResponseResult add(String key, Object value) {
		this.getMap().put(key, value);
		return this;
	}

	
	public Integer getCode() {
		return code;
	}
	public ResponseResult setCode(Integer code) {
		this.code = code;
		return this;
	}
	public String getMsg() {
		return msg;
	}
	public ResponseResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	public Map<String, Object> getMap() {
		return map;
	}

	public ResponseResult setMap(Map<String, Object> map) {
		this.map = map;
		return this;
	}

}
