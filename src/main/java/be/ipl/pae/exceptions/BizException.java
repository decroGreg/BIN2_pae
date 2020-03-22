package be.ipl.pae.exceptions;

public class BizException extends RuntimeException {
  private String message;

  public BizException(String message) {
    super();
    this.message = message;
  }

  public BizException() {
    super();
  }

  public String getMessage() {
    return message;
  }
}
