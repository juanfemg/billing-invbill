package co.com.juan.invbill.enums;

/**
 * @author Juan Felipe
 */
public enum StatusEnum {

    A("ACTIVO"), I("INACTIVO");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
