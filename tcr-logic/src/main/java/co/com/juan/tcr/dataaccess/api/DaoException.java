package co.com.juan.tcr.dataaccess.api;

/**
 * @author Juan Felipe
 *
 */
public class DaoException extends Exception {

	private static final long serialVersionUID = -5563346597440437628L;

	public DaoException() {
		super();
	}

	public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
