package be.ipl.pae.exceptions;

public class IHMException extends Exception {
  public IHMException() {
    super("erreur fatal");
  }

  public IHMException(String message) {
    super(message);
  }
}
