package com.microservice.NotificationService.exceptions;

public class EmptyMessageImageException extends Exception{
    public EmptyMessageImageException(String message){
        super(message);
    }
}
