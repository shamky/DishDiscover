package com.amogh.dishdiscover.exception;

public class FavoriteServiceException extends Exception {
  public FavoriteServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public FavoriteServiceException(String message) {
    super(message);
  }
}
