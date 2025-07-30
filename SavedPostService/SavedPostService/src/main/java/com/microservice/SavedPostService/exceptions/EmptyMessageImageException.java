package com.microservice.SavedPostService.exceptions;

public class EmptyMessageImageException extends Exception{
    public EmptyMessageImageException(String message){
        super(message);
    }
}
