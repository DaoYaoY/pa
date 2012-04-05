package cn.fyg.pa.bean;

import cn.fyg.pa.tool.DateTool;

public class MonthchkQueryBean {
	
	public static final String DEFAULT_DEPARTMENT="办公室";
	
	private Long year;
	private Long month;
	private String department;
	
	public MonthchkQueryBean() {
		DateTool dateTool=new DateTool();
		this.year = dateTool.getCurrentYear();
		this.month = dateTool.getCurrentMonth();
		this.department = DEFAULT_DEPARTMENT;
	}
	
	
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
	public Long getMonth() {
		return month;
	}
	public void setMonth(Long month) {
		this.month = month;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	

}
