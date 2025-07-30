package com.microservice.UserService.exceptions;

public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
