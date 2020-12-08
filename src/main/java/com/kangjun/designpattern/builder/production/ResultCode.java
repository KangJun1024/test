package com.kangjun.designpattern.builder.production;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用结果状态码
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    OK(0, "成功"),
    UNAUTHORIZED(40100, "未认证"),
    IPNOTALLOWED(40101, "请求的IP不被允许"),
    CLIENT_MSG_ERROR(40102, "clientId或clientSecret错误"),
    FORBIDDEN(40300, "登录失败"),


    BAD_REQUEST_UPDATEPSW_ERROR(40314, "更新密码失败"),
    BAD_REQUEST_NOSUCHUSER_ERROR(40315, "没有此用户ID"),
    BAD_REQUEST_OLDPSWWRONG_ERROR(40316, "旧密码不一致"),
    FORBIDDEN_PASSWORD_NUM_ERROR(40317, "登录失败-密码输错次数达到5次"),

    /**
     * 用户中心验证使用
     */
    FORBIDDEN_USER_DISABLE(40301, "登录失败-用户禁止登录"),
    FORBIDDEN_USER_NOT_EXIT(40311, "登录失败-用户不存在"),
    FORBIDDEN_PASSWORD_ERROR(40312, "登录失败-用户密码错误"),
    BAD_REQUEST_TELEPHONE_ERROR(40313, "手机号参数错误"),
    FORBIDDEN_ERROR_TOKEN(40318, "验证失败，请重新登录"),
    FORBIDDEN_UN_ACCESS(40319,"无访问权限，请联系管理员"),
    FORBIDDEN_TICK_OUT(40320,"您的帐号已在其它地方登录，您已被迫下线，如果不是您本人操作，请及时修改密码"),
    FORBIDDEN_OVER_TIME(40321,"登录已超时，请重新登录"),
    FORBIDDEN_USER_TYPE_NULL(40322,"登录失败-用户类型不能为空"),
    FORBIDDEN_USER_PASSWORD_NULL(40323,"登录失败-用户密码不能为空"),
    CLIENT_PARAMS_ERROR(40324,"非法客户端参数"),
    CLIENT_INIT_ERROR(40325,"ClientDetailsService未实例化"),
    CLIENT_USER_NOT_EXIT(40326,"客户端用户不存在"),
    CLIENT_USER_PASSWORD_ERROR(40327,"客户端秘钥错误"),
    UN_TOOK_TENANT_KEY_ERROR(40328,"无效租户"),

    RC_REGISTER_FAILED(40330,"融云用户注册异常"),



    // 短信验证码登录
    FORBIDDEN_PHONE_ERROR(40321, "登录失败-手机号不存在"),


    BAD_REQUEST(40000, "请求参数错误"),

    OPERA_ERROR(40004, "执行操作失败"),
    PARAMS_ERROR(40005, "参数异常"),
    METHOD_ERROR(40006, "请求方式不允许"),


    NOT_FOUND(40400, "请求数据不存在"),
    METHOD_NOT_ALLOWED(40500, "请求的接口不在权限之内"),
    SERVER_ERROR(50000, "服务器错误");

    private Integer code;

    private String msg;

}
