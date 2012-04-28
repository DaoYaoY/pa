package cn.fyg.pa.interfaces.page;

import cn.fyg.pa.domain.person.Person;
import cn.fyg.pa.domain.yearchk.Fycheck;

public class Cell {
	
	
	/**
	 * empty-没有值
	 * person-人员
	 * data-数据
	 */
	private String type;
	private Person fyperson;
	private Person scperson;
	private Fycheck dataCell;
	
	public Person getScperson() {
		return scperson;
	}
	public void setScperson(Person scperson) {
		this.scperson = scperson;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Person getFyperson() {
		return fyperson;
	}
	public void setFyperson(Person fyperson) {
		this.fyperson = fyperson;
	}
	public Fycheck getDataCell() {
		return dataCell;
	}
	public void setDataCell(Fycheck dataCell) {
		this.dataCell = dataCell;
	}
	

}
