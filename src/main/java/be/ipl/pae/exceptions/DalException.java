
package be.ipl.pae.exceptions;

public class DalException extends RuntimeException {

  private String message;

  public DalException(String message) {
    super();
    this.message = message;
  }

  public DalException() {
    super();
  }

  public String getMessage() {
    return message;
  }
}
