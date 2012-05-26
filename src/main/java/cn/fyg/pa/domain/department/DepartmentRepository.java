package cn.fyg.pa.domain.department;

import java.util.List;

import cn.fyg.pa.domain.person.Person;

public interface DepartmentRepository {

	Department find(Long id);

	Department findDepartmentByName(String department);

	List<Department> findAllDepartmentsOrderById();

	List<Department> findDepartmentsByGmanage(Person gmanage);
}
