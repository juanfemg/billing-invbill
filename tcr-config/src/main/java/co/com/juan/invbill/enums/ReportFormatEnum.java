package co.com.juan.invbill.enums;

/**
 * @author Juan Felipe
 */
public enum ReportFormatEnum {

    PDF("pdf", "application/pdf"), EXCEL("xls", "application/vnd.ms-excel");

    private final String format;
    private final String mime;

    ReportFormatEnum(String format, String mime) {
        this.format = format;
        this.mime = mime;
    }

    public String getFormat() {
        return format;
    }

    public String getMime() {
        return mime;
    }

}
