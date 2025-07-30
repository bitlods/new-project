package com.microservice.StoryService.exceptions;

public class EmptyMessageImageException extends Exception{
    public EmptyMessageImageException(String message){
        super(message);
    }
}
