package com.cloud.photo.users.common;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 业务响应体
 *
 * @author linzsh
 */
@Data
public class ResultBody {
    /**
     * 响应业务代码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private Object data;

    /**
     * 请求ID
     */
    private String requestId;

    public ResultBody() {
    }

    public ResultBody(BaseErrorInfo errorInfo) {
        this.code = errorInfo.getResultCode();
        this.message = errorInfo.getResultMsg();
    }

    /**
     * 返回默认成功
     *
     * @return ResultBody
     */
    public static ResultBody success() {
        return success(null, CommonEnum.SUCCESS);
    }

    /**
     * 成功
     *
     * @param data 数据响应体
     * @return ResultBody
     */
    public static ResultBody success(Object data,String requestId) {
        ResultBody rb = new ResultBody();
        rb.setCode(CommonEnum.SUCCESS.getResultCode());
        rb.setMessage(CommonEnum.SUCCESS.getResultMsg());
        rb.setData(data);
        rb.setRequestId(requestId);
        return rb;
    }

    /**
     * 成功
     *
     * @param data 数据响应体
     * @return ResultBody
     */
    public static ResultBody success(Object data) {
        ResultBody rb = new ResultBody();
        rb.setCode(CommonEnum.SUCCESS.getResultCode());
        rb.setMessage(CommonEnum.SUCCESS.getResultMsg());
        rb.setData(data);
        return rb;
    }

    /**
     * 成功
     *
     * @param data 数据体
     * @param errorInfo 一个业务状态枚举
     * @return ResultBody
     */
    public static ResultBody success(Object data, BaseErrorInfo errorInfo) {
        ResultBody rb = new ResultBody();
        if (errorInfo == null) {
            rb.setCode(CommonEnum.SUCCESS.getResultCode());
            rb.setMessage(CommonEnum.SUCCESS.getResultMsg());
        } else {
            rb.setCode(errorInfo.getResultCode());
            rb.setMessage(errorInfo.getResultMsg());
        }
        rb.setData(data);
        return rb;
    }

    /**
     * 失败
     * @param errorInfo 状态信息
     * @return ResultBody
     */
    public static ResultBody error(BaseErrorInfo errorInfo) {
        ResultBody rb = new ResultBody();
        rb.setCode(errorInfo.getResultCode());
        rb.setMessage(errorInfo.getResultMsg());
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     * @param code 状态码
     * @param message 状态描述
     * @return ResultBody
     */
    public static ResultBody error(Integer code, String message,String requestId) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setData(null);
        rb.setRequestId(requestId);
        return rb;
    }


    /**
     * 失败
     * @param message 状态描述
     * @return ResultBody
     */
    public static ResultBody error(String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(CommonEnum.INTERNAL_SERVER_ERROR.getResultCode());
        rb.setMessage(message);
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     * @param code 状态码
     * @param message 状态描述
     * @return ResultBody
     */
    public static ResultBody error(Integer code, String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setData(null);
        return rb;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
