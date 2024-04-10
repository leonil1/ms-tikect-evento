package com.gruopo9.msevento.aggregates.response;


import com.gruopo9.msevento.aggregates.constants.Constants;
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
        return new ResponseBase(Constants.CODE_SUCCESS, mensaje, true, Optional.ofNullable(data));
    }

    public static ResponseBase errorViolationException(String mensaje) {
        return new ResponseBase(Constants.CODE_ERROR, mensaje, false, Optional.empty());
    }
    public static ResponseBase errorNotFound(String mensaje) {
        return new ResponseBase(Constants.CODE_NOT_FOUND, mensaje, false, Optional.empty());
    }
    public static ResponseBase errorInternalSErverError(String mensaje) {
        return new ResponseBase(Constants.INTERNAL_SERVER_ERROR, mensaje, false, Optional.empty());
    }


}
