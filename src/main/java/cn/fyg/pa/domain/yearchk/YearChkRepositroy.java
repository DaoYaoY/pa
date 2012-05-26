package cn.fyg.pa.domain.yearchk;

import java.util.List;

import cn.fyg.pa.domain.person.Person;
import cn.fyg.pa.interfaces.person.yearchk.PersonChkBean;

public interface YearChkRepositroy {


	List<PersonChkBean> getPersonYearChkResult(Long year, Person person);
	
	List<Fycheck> getPersonYearChkAboutPerson(Long year,Person aboutPerson,Person chkPerson);
	
	void saveFychecks(List<Fycheck> fychecks);

	List<Fycheck> getPersonYearChkByChkperson(Person person);

}
