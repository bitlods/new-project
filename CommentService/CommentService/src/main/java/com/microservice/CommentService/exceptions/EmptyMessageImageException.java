package com.microservice.CommentService.exceptions;

public class EmptyMessageImageException extends Exception{
    public EmptyMessageImageException(String message){
        super(message);
    }
}
