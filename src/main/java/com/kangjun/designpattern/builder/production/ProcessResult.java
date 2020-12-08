package com.kangjun.designpattern.builder.production;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回单个实体处理结果
 */
@Data
public class ProcessResult {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 信息
     */
    private String message;
    /**
     * 返回体信息
     */
    private Object data;

    public ProcessResult(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
        this.data = data;
    }

    public ProcessResult() {
    }

    public ProcessResult(ResultCode resultCode) {

        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
        this.data = null;
    }


}
