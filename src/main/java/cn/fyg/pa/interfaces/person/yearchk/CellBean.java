package cn.fyg.pa.interfaces.person.yearchk;

import cn.fyg.pa.domain.person.Person;
import cn.fyg.pa.domain.yearchk.Fycheck;

public class CellBean {
	
	private Person colPerson;
	private Person rowPerson;
	private Fycheck fycheck;
	public Person getColPerson() {
		return colPerson;
	}
	public void setColPerson(Person colPerson) {
		this.colPerson = colPerson;
	}
	public Person getRowPerson() {
		return rowPerson;
	}
	public void setRowPerson(Person rowPerson) {
		this.rowPerson = rowPerson;
	}
	public Fycheck getFycheck() {
		return fycheck;
	}
	public void setFycheck(Fycheck fycheck) {
		this.fycheck = fycheck;
	}
	
}
