package co.com.juan.invbill.exceptions;

/**
 * @author Juan Felipe
 */
public class EntityException extends RuntimeException {

    public static final String ENTITY_WITH_SAME_KEY = "There's another entity with same key";

    public EntityException(String exception) {
        super(exception);
    }

    public static class EmptyFieldException extends EntityException {

        public EmptyFieldException(String entity, String field) {
            super(String.format("An exception has occurred saving the entity '%s'. The value for field '%s' cannot be empty or null", entity, field));
        }
    }

    public static class NotValidFormatException extends EntityException {

        public NotValidFormatException(String entity, String field) {
            super(String.format("An exception has occurred saving the entity '%s'. The data type or the length for field '%s' is not valid", entity, field));
        }
    }

    public static class SavingException extends EntityException {

        public SavingException(String entity) {
            super(String.format("The entity '%s' has constraints in some fields, please check the data before trying again to save it", entity));
        }
    }

    public static class UpdatingException extends EntityException {
        private static final long serialVersionUID = 1L;

        public UpdatingException(String entity) {
            super(String.format("The entity '%s' has constraints in some fields, please check the data before trying again to update it", entity));
        }
    }

    public static class GettingException extends EntityException {

        public GettingException(String entity) {
            super(String.format("An exception has occurred getting the entity '%s'", entity));
        }
    }

    public static class FindingException extends EntityException {

        public FindingException(String entity) {
            super(String.format("An exception has occurred finding the entity '%s'", entity));
        }
    }

}
