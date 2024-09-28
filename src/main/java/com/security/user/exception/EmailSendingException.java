package com.security.user.exception;

public class EmailSendingException extends RuntimeException{
    public EmailSendingException(String message , Throwable couse) {
         super(message, couse);
    }
}
