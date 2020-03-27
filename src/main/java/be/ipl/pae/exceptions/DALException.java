package be.ipl.pae.exceptions;

public class DALException extends RuntimeException {

  private String message;

  public DALException(String message) {
    super();
    this.message = message;
  }

  public DALException() {
    super();
  }

  public String getMessage() {
    return message;
  }
}
