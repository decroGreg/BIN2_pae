package be.ipl.pae.exceptions;

public class IhmException extends Exception {
  public IhmException() {
    super("erreur fatal");
  }

  public IhmException(String message) {
    super(message);
  }
}
