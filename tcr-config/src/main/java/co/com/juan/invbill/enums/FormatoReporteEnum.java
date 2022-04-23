package co.com.juan.invbill.enums;

/**
 * @author Juan Felipe
 * 
 */
public enum FormatoReporteEnum {

	PDF("pdf", "application/pdf"), EXCEL("xls", "application/vnd.ms-excel");

	private final String formato;
	private final String mime;

	private FormatoReporteEnum(String formato, String mime) {
		this.formato = formato;
		this.mime = mime;
	}

	/**
	 * @return the formato
	 * 
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * @return the mime
	 * 
	 */
	public String getMime() {
		return mime;
	}

}
