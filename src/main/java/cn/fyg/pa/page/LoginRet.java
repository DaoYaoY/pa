package cn.fyg.pa.page;

public class LoginRet {
	
	private String personid;
	private String chkstr;
	private String mange;
	private boolean isPass;
	
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid = personid;
	}
	public boolean isPass() {
		return isPass;
	}
	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
	public String getChkstr() {
		return chkstr;
	}
	public void setChkstr(String chkstr) {
		this.chkstr = chkstr;
	}
	public String getMange() {
		return mange;
	}
	public void setMange(String mange) {
		this.mange = mange;
	}

}
