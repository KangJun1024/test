package com.kangjun.designpattern.builder.production;

import com.alibaba.fastjson.JSONObject;

/**
 * 返回响应结果支持
 */
public class ResultSupport {
    public static ProcessResult ok() {
        return ok(new JSONObject());
    }

    public static ProcessResult ok(Object data) {
        return ResultSupport.of(ResultCode.OK.getCode(), ResultCode.OK.getMsg(), data);
    }

    public static ProcessResult success(String message, Object data) {
        return ResultSupport.of(ResultCode.OK.getCode(), message, data);
    }

    public static ProcessResult success(String message) {
        return success(message, new JSONObject());
    }

    public static ProcessResult saveSuccess() {
        return saveSuccess(new JSONObject());
    }

    public static ProcessResult saveSuccess(Object data) {
        return success("新增成功", data);
    }

    public static ProcessResult delSuccess() {
        return delSuccess(new JSONObject());
    }

    public static ProcessResult delSuccess(Object data) {
        return success("删除成功", data);
    }

    public static ProcessResult updateSuccess() {
        return updateSuccess(new JSONObject());
    }

    public static ProcessResult updateSuccess(Object data) {
        return success("修改成功", data);
    }


    public static ProcessResult fail() {
        return fail(ResultCode.OPERA_ERROR.getMsg());
    }

    public static ProcessResult fail(String message) {
        return ResultSupport.of(ResultCode.PARAMS_ERROR.getCode(), message, new JSONObject());
    }

    public static ProcessResult fail(Integer code, String message) {
        return ResultSupport.of(code, message, new JSONObject());
    }

    public static ProcessResult paramError(String message) {
        return ResultSupport.of(ResultCode.OPERA_ERROR.getCode(), message, new JSONObject());
    }

    public static ProcessResult methodError() {
        return ResultSupport.of(ResultCode.METHOD_ERROR.getCode(), ResultCode.METHOD_ERROR.getMsg(), new JSONObject());
    }

    public static ProcessResult of(Integer code, String message, Object data) {
        return new Builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    public static class Builder {
        public ProcessResult result;

        public Builder() {
            this.result = new ProcessResult();
        }

        public Builder code(Integer code) {
            this.result.setCode(code);
            return this;
        }

        public Builder message(String message) {
            this.result.setMessage(message);
            return this;
        }

        public Builder data(Object data) {
            this.result.setData(data);
            return this;
        }

        public ProcessResult build() {
            return this.result;
        }
    }
}
