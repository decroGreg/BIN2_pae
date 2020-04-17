package be.ipl.pae.exceptions;

public class FatalException extends RuntimeException {

  public FatalException() {
    super("Erreur fatal");
  }

  public FatalException(String message) {
    super(message);
  }
}
