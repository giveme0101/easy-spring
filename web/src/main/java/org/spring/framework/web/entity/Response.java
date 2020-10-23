package org.spring.framework.web.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.spring.framework.web.util.DateUtil;

@Getter
@ToString
@NoArgsConstructor
public class Response {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public Response(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = DateUtil.now();
    }
}
