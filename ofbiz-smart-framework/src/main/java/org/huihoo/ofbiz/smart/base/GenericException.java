package org.huihoo.ofbiz.smart.base;

public class GenericException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public GenericException() {
    super();
  }

  public GenericException(String message) {
    super(message);
  }

  public GenericException(String message, Throwable cause) {
    super(message, cause);
  }

  public GenericException(Throwable cause) {
    super(cause);
  }


  @Override
  public String getMessage() {
    Throwable nested = getCause();
    if (nested != null) {
      if (super.getMessage() == null) {
        return nested.getMessage();
      } else {
        return super.getMessage() + " (" + nested.getMessage() + ")";
      }
    } else {
      return super.getMessage();
    }
  }
}
