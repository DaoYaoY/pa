package cn.fyg.pa.domain.person;

import java.util.List;



public interface PersonRepository {

	Person save(Person person);
	
	Person find(Long id);
	
	Person getDeptMange(String department);

	List<Person> getPersonByManage(ManageEnum... mangeEnum);

	Person findByName(String personname);
}
