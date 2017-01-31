package com.alexfrndz.pojo.response.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorResponse {\n");
        sb.append("  errorCode: ").append(errorCode).append("\n");
        sb.append("  errorMessage: ").append(errorMessage).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
