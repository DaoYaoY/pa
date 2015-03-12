package cn.fyg.pa.interfaces.web.module.report.analysismonthchk;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.fyg.pa.domain.model.monthchk.MonthChk;
import cn.fyg.pa.domain.model.monthchk.MonthChkRepository;
import cn.fyg.pa.domain.model.person.ManageEnum;
import cn.fyg.pa.domain.model.person.Person;
import cn.fyg.pa.domain.model.person.PersonRepository;
import cn.fyg.pa.domain.model.person.StateEnum;
import cn.fyg.pa.interfaces.web.module.report.analysismonthchk.dto.AnalysisMonthChkBean;

@Component
public class AnalysisMonthChkFacade {
	
	@Resource
	MonthChkRepository monthChkReposity;
	@Resource
	PersonRepository personRepository;
	
	public AnalysisMonthChkBean analyseMonthChk(Long year,Long month){
		List<Person> persons=personRepository.findPersonByManageAndStateOrderByDepartment(ManageEnum.N,StateEnum.invalid,StateEnum.valid);
		List<MonthChk> monthChks=monthChkReposity.findMonthChkByPeriod(year, month);
		AnalysisMonthChkBuiler builder=new AnalysisMonthChkBuiler(persons, monthChks);
		return builder.build(year,month);
	}

}
