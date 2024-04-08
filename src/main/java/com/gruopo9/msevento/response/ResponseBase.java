package com.gruopo9.msevento.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class ResponseBase implements Serializable {
    private Integer code;
    private String mensaje;
    private Boolean estado;
    private Optional data;

    public static <T> ResponseBase exitoso(String mensaje, T data) {
        return new ResponseBase(HttpStatus.OK.value(), mensaje, true, Optional.ofNullable(data));
    }

    public static ResponseBase error(String mensaje, HttpStatus httpStatus) {
        return new ResponseBase(httpStatus.value(), mensaje, false, Optional.empty());
    }
}
