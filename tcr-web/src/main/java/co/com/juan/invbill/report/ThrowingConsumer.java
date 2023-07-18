package co.com.juan.invbill.report;

/**
 * @author Juan Felipe
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {

    void accept(T t) throws E;

}
