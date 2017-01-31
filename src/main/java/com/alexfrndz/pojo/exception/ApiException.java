package com.alexfrndz.pojo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlRootElement;

@EqualsAndHashCode(callSuper=false)
@Data
@XmlRootElement(name = "error")
public class ApiException extends RuntimeException {


    private String errorCode;

    public ApiException() {
    }

    public ApiException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
}
