package co.com.juan.invbill.exceptions;

/**
 * @author Juan Felipe
 */
public class EntityException extends RuntimeException {

    public static final String ENTITY_WITH_SAME_KEY = "There's another entity with same key";

    public EntityException() {
    }

    public EntityException(String exception) {
        super(exception);
    }

    public static class EmptyFieldException extends EntityException {

        public EmptyFieldException(String info) {
            super(String.format("The value for field: %s cannot be empty or null", info));
        }
    }

    public static class NotValidFormatException extends EntityException {

        public NotValidFormatException(String info) {
            super(String.format("The data type or the length for field: %s is not valid", info));
        }
    }

    public static class SavingException extends EntityException {

        public SavingException(String info) {
            super(String.format("The entity: %s has constraints in some fields, please check the data before trying again to save it", info));
        }
    }

    public static class UpdatingException extends EntityException {
        private static final long serialVersionUID = 1L;

        public UpdatingException(String info) {
            super(String.format("The entity: %s has constraints in some fields, please check the data before trying again to update it", info));
        }
    }

    public static class GettingException extends EntityException {

        public GettingException(String info) {
            super(String.format("An exception has occurred getting %s", info));
        }
    }

    public static class FindingException extends EntityException {

        public FindingException(String info) {
            super(String.format("An exception has occurred finding %s", info));
        }
    }

}
