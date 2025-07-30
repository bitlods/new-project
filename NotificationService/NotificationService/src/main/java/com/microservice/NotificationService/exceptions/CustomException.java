package com.microservice.NotificationService.exceptions;

public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
