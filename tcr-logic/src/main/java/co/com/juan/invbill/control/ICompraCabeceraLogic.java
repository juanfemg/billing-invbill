package co.com.juan.invbill.control;

import java.util.List;

import co.com.juan.invbill.model.CompraCabecera;
import co.com.juan.invbill.model.CompraCabeceraId;

/**
 * @author Juan Felipe
 */
public interface ICompraCabeceraLogic {

    void saveCompraCabecera(CompraCabecera entity);

    void updateCompraCabecera(CompraCabecera entity);

    CompraCabecera getCompraCabecera(CompraCabeceraId id);

    List<CompraCabecera> getDataCompraCabecera();

    List<CompraCabecera> findByCriteria(Object[] variables, Object[] variablesBetween,
                                        Object[] variablesBetweenDates);

    List<CompraCabecera> findByProperty(String propertyName, Object value);

}
