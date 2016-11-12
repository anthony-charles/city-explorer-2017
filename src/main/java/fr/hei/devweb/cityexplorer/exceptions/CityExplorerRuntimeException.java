package fr.hei.devweb.cityexplorer.exceptions;

public class CityExplorerRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 9025730660535871559L;

	public CityExplorerRuntimeException() {
		super();
	}

	public CityExplorerRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CityExplorerRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CityExplorerRuntimeException(String message) {
		super(message);
	}

	public CityExplorerRuntimeException(Throwable cause) {
		super(cause);
	}
	
	

}
