package com.gruopo9.msevento.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EventoUtils {
    private EventoUtils(){

    }
    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus){
        return new ResponseEntity<String>("Mensaje: "+ message, httpStatus);
    }


}
