package co.com.juan.invbill.model;
// Generated 11-feb-2017 9:50:57 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * LoginApp generated by hbm2java
 */
public class LoginApp implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String idLoginApp;
	private Date ultimoLogin;
	private String idSession;

	public LoginApp() {
	}

	public LoginApp(String idLoginApp) {
		this.idLoginApp = idLoginApp;
	}

	public LoginApp(String idLoginApp, Date ultimoLogin, String idSession) {
		this.idLoginApp = idLoginApp;
		this.ultimoLogin = ultimoLogin;
		this.idSession = idSession;
	}

	public String getIdLoginApp() {
		return this.idLoginApp;
	}

	public void setIdLoginApp(String idLoginApp) {
		this.idLoginApp = idLoginApp;
	}

	public Date getUltimoLogin() {
		return this.ultimoLogin;
	}

	public void setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	public String getIdSession() {
		return this.idSession;
	}

	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

}