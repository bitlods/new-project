package com.microservice.MessageService.exceptions;

public class EmptyMessageImageException extends Exception{
    public EmptyMessageImageException(String message){
        super(message);
    }
}
