package com.amogh.dishdiscover.exception;

public class RecipeServiceException extends Exception {

  public RecipeServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public RecipeServiceException(String message) {
    super(message);
  }

}