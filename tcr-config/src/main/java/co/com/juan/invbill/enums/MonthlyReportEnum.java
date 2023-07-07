package co.com.juan.invbill.enums;

/**
 * @author Juan Felipe
 */
public enum MonthlyReportEnum {

    SALES("Reporte Mensual Ventas"), RETURNS("Reporte Mensual Devoluciones"), PURCHASES("Reporte Mensual Compras");

    private final String report;

    MonthlyReportEnum(String report) {
        this.report = report;
    }

    public String getReport() {
        return report;
    }

}
