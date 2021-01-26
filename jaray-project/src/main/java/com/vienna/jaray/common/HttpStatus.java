package com.vienna.jaray.common;

/**
 * @author Jaray
 * @date 2020年09月13日 00:09
 * @description: 请求响应状态常量
 */
public enum HttpStatus {
    /**
     * 验证码错误
     */
    CAPTCHA_ERROR(999, "验证码错误"),
    /**
     * 验证码错误
     */
    CAPTCHA_INVALID(998, "验证码失效"),
    /**
     * 用户名或密码错误
     */
    NAME_OR_PASSWORD_ERROR(401, "用户名或密码错误"),
    /**
     * 信息状态码：继续
     */
    SC_CONTINUE(100, "初始的请求已经接受，请客户端继续发送剩余部分"),
    /**
     * 信息状态码：切换协议
     */
    SC_SWITCHING_PROTOCOLS(101, "请求这要求服务器切换协议，服务器已确定切换"),
    /**
     * 信息状态码：继续处理
     */
    SC_PROCESSING(102, "处理将被继续执行"),
    /**
     * 成功状态码：成功
     */
    SC_OK(200, "服务器已成功处理了请求"),
    /**
     * 成功状态码：已创建
     */
    SC_CREATED(201, "请求成功并且服务器创建了新的资源"),
    /**
     * 成功状态码：已接受
     */
    SC_ACCEPTED(202, "服务器已接受请求，但尚未处理"),
    /**
     * 成功状态码：非授权信息
     */
    SC_NON_AUTHORITATIVE_INFORMATION(203, "服务器已成功处理请求，但返回的信息可能来自另一个来源"),
    /**
     * 成功状态码：无内容
     */
    SC_NO_CONTENT(204, "服务器成功处理了请求，但没有返回任何内容"),
    /**
     * 成功状态码：重置内容
     */
    SC_RESET_CONTENT(205, "服务器处理成功，用户终端应重置文档视图"),
    /**
     * 成功状态码：部分内容
     */
    SC_PARTIAL_CONTENT(206, "服务器成功处理了部分GET请求"),
    /**
     * 成功状态码：多种状态
     */
    SC_MULTI_STATUS(207, "消息体将是一个XML消息，并且可能依照之前子请求数量的不同，包含一系列独立的响应代码"),
    /**
     * 重定向状态码：多种操作
     */
    SC_MULTIPLE_CHOICES(300, "针对请求，服务器可执行多种操作"),
    /**
     * 重定向状态码：永久移动
     */
    SC_MOVED_PERMANENTLY(301, "请求的页面已永久跳转到新的url"),
    /**
     * 重定向状态码：临时移动
     */
    SC_MOVED_TEMPORARILY(302, "服务器目前从不同位置的网页响应请求，但请求仍继续使用原有位置来进行以后的请求"),
    /**
     * 重定向状态码：查看其他位置
     */
    SC_SEE_OTHER(303, "请求者应当对不同的位置使用单独的GET请求来检索响应时，服务器返回此代码"),
    /**
     * 重定向状态码：未修改
     */
    SC_NOT_MODIFIED(304, "自从上次请求后，请求的网页未修改过"),
    /**
     * 重定向状态码：使用代理
     */
    SC_USE_PROXY(305, "请求者只能使用代理访问请求的网页"),
    /**
     * 重定向状态码：临时重定向
     */
    SC_TEMPORARY_REDIRECT(307, "服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求"),
    /**
     * 客户端错误状态码：错误请求
     */
    SC_BAD_REQUEST(400, "服务器不理解请求的语法"),
    /**
     * 客户端错误状态码：未授权
     */
    SC_UNAUTHORIZED(401, "请求要求用户的身份验证"),
    /**
     * 客户端错误状态码：预留状态码
     */
    SC_PAYMENT_REQUIRED(402, "预留状态码"),
    /**
     * 客户端错误状态码：禁止
     */
    SC_FORBIDDEN(403, "服务器拒绝请求"),
    /**
     * 客户端错误状态码：未找到
     */
    SC_NOT_FOUND(404, "服务器找不到请求的页面"),
    /**
     * 客户端错误状态码：方法禁用
     */
    SC_METHOD_NOT_ALLOWED(405, "禁用请求中指定的方法"),
    /**
     * 客户端错误状态码：不接受
     */
    SC_NOT_ACCEPTABLE(406, "无法使用请求的内容特性响应请求的页面"),
    /**
     * 客户端错误状态码：需要代理授权
     */
    SC_PROXY_AUTHENTICATION_REQUIRED(407, "请求需要代理的身份认证"),
    /**
     * 客户端错误状态码：请求超时
     */
    SC_REQUEST_TIMEOUT(408, "服务器等候请求时发生超时"),
    /**
     * 客户端错误状态码：冲突
     */
    SC_CONFLICT(409, "服务器在完成请求时发生冲突"),
    /**
     * 客户端错误状态码：已删除
     */
    SC_GONE(410, "客户端请求的资源已经不存在"),
    /**
     * 客户端错误状态码：需要有效长度
     */
    SC_LENGTH_REQUIRED(411, "服务器不接受不含有效长度表头字段的请求"),
    /**
     * 客户端错误状态码：未满足前提条件
     */
    SC_PRECONDITION_FAILED(412, "服务器未满足请求者在请求中设置的其中一个前提条件"),
    /**
     * 客户端错误状态码：请求实体过大
     */
    SC_REQUEST_TOO_LONG(413, "由于请求实体过大，服务器无法处理，因此拒绝请求"),
    /**
     * 客户端错误状态码：请求url过长
     */
    SC_REQUEST_URI_TOO_LONG(414, "请求的url过长，服务器无法处理"),
    /**
     * 客户端错误状态码：不支持格式
     */
    SC_UNSUPPORTED_MEDIA_TYPE(415, "服务器无法处理请求中附带媒体格式"),
    /**
     * 客户端错误状态码：范围无效
     */
    SC_REQUESTED_RANGE_NOT_SATISFIABLE(416, "客户端请求的范围无效"),
    /**
     * 客户端错误状态码：未满足期望
     */
    SC_EXPECTATION_FAILED(417, "服务器无法满足请求表头字段要求"),
    /**
     * 客户端错误状态码：认证超时
     */
    SC_INSUFFICIENT_SPACE_ON_RESOURCE(419, "认证超时"),
    /**
     * 服务端错误状态码：服务器错误
     */
    SC_INTERNAL_SERVER_FAIL(500, "服务器内部错误，无法完成请求"),
    /**
     * 服务端错误状态码：尚未实施
     */
    SC_NOT_IMPLEMENTED(501,"服务器不具备完成请求的功能"),
    /**
     * 服务端错误状态码：错误网关
     */
    SC_BAD_GATEWAY(502,"服务器作为网关或代理出现错误"),
    /**
     * 服务端错误状态码：服务不可用
     */
    SC_SERVICE_UNAVAILABLE(503,"服务器目前无法使用"),
    /**
     * 服务端错误状态码：网关超时
     */
    SC_GATEWAY_TIMEOUT(504,"网关或代理服务器，未及时获取请求"),
    /**
     * 服务端错误状态码：不支持版本
     */
    SC_HTTP_VERSION_NOT_SUPPORTED(505,"服务器不支持请求中使用的HTTP协议版本");

    /**
     * 状态码
     */
    private int statusCode;
    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     *  私有化构造方法
     * @param statusCode 状态码
     * @param statusDesc 状态描述
     */
    private HttpStatus(int statusCode, String statusDesc) {
        this.statusCode = statusCode;
        this.statusDesc = statusDesc;
    }

    /**
     * 获取状态描述方法
     * @param statusCode 状态码
     * @return 状态描述
     */
    public static String getStatusDesc(int statusCode) {
        for (HttpStatus httpStatus : HttpStatus.values()) {
            if (httpStatus.getStatusCode() == statusCode) {
                return httpStatus.statusDesc;
            }
        }
        return null;
    }

    /**
     * 获取状态描述方法
     * @return 状态描述
     */
    public String getStatusDesc() {
        return statusDesc;
    }
    /**
     * 设置状态描述方法
     * @param statusDesc 状态描述
     */
    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
    /**
     * 获取状态码方法
     * @return 状态码
     */
    public int getStatusCode() {
        return statusCode;
    }
    /**
     * 设置状态码方法
     * @param statusCode 状态码
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
