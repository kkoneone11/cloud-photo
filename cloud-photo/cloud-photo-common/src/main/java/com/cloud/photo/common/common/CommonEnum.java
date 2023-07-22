package com.cloud.photo.common.common;

/**
 * 自定义错误枚举类
 * @author linzsh
 */
public enum CommonEnum implements BaseErrorInfo {

    //success
    SUCCESS(200, "成功！"),

    //error
    INTERNAL_SERVER_ERROR(90500, "服务器内部错误!"),

    LOGIN_INFO_IS_NULL(90400, "登录信息为空!"),
    USERNAME_PASSWORD_ERROR(90400, "请检查账密是否正确!"),
    PHONE_IS_NULL(90400, "手机号不能为空!"),
    PHONE_IS_NOT_VALID(90400, "手机号不合法!"),
    FILE_LIST_IS_NULL(90400, "上传的文件列表未空!"),
    TRANS_IS_NULL(90404, "传输列表为空!"),
    LOGIN_FAIL(90400, "登录失败!"),
    USER_INFO_NOT_INPUT(90401, "你是第一次登录, 请补全用户信息!"),
    NO_LOGIN(90402, "您未登录!"),
    USER_IS_NULL(90404, "用户信息不存在!"),
    UNAUTHORIZED_CODE(90402,"登录Token过期，请重新登陆！"),
    NO_PERMISSION_CODE(90406, "没有权限进行此操作！"),
    FILE_NOT_UPLOADED(90407, "文件未上传！"),
    FILE_UPLOADED_ERROR(90408, "文件上传出错！"),
    NO_UPLOAD_FILE(90409, "没有获取到传输列表！"),
    ;

    //数据操作错误定义
    /** 错误码 */
    private final Integer resultCode;

    /** 错误描述 */
    private final String resultMsg;

    CommonEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public Integer getResultCode() {
        return this.resultCode;
    }

    @Override
    public String getResultMsg() {
        return this.resultMsg;
    }
}
