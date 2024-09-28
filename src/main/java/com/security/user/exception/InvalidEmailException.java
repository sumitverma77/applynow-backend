package com.security.user.exception;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException(String message){
         super(message);
    }
}
