package cn.fyg.pa.interfaces.module.person.yearchk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pa.application.PersonSummaryService;
import cn.fyg.pa.application.YearCheckService;
import cn.fyg.pa.application.YearConfigService;
import cn.fyg.pa.application.YearchkStateService;
import cn.fyg.pa.domain.model.monthchk.MonthChk;
import cn.fyg.pa.domain.model.monthchk.MonthChkEnum;
import cn.fyg.pa.domain.model.monthchk.MonthChkItem;
import cn.fyg.pa.domain.model.monthchk.MonthChkRepository;
import cn.fyg.pa.domain.model.person.Person;
import cn.fyg.pa.domain.model.person.PersonRepository;
import cn.fyg.pa.domain.model.summary.PersonSummary;
import cn.fyg.pa.domain.model.yearchk.EnableYearNotExist;
import cn.fyg.pa.domain.model.yearchk.Fycheck;
import cn.fyg.pa.domain.model.yearchk.FycheckFactory;
import cn.fyg.pa.domain.model.yearchk.YearChkRepositroy;
import cn.fyg.pa.interfaces.module.shared.message.impl.SessionMPR;


/**
 * 重构年度考核类
 */
@Controller
@RequestMapping("/person/{personId}/yearchk")
public class PersonYearChkCtl {
	
	@Resource
	PersonRepository personRepository;
	@Resource
	YearConfigService yearConfigService;
	@Resource 
	YearChkRepositroy yearChkRepositroy;
	@Resource
	YearCheckService yearCheckService;
	
	@ModelAttribute("person")
	public Person initPerson(@PathVariable("personId") Long personId){
		return personRepository.find(personId);
	}
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String list(@ModelAttribute("person")Person person,Map<String,Object> map,HttpSession session){
		try {
			Long year=yearConfigService.getEnableYear();
			List<PersonChkBean> yearChkBeans = yearChkRepositroy.getPersonYearChkResult(year, person);
			PageBeanBuilder builder=new PageBeanBuilder(yearChkBeans);
			int needChkPersons=personRepository.countStaffByType(person.getType())-2;//2代表去掉两个人，一个是自己，另外一个被考评的人
			boolean commit = yearchkStateService.isCommit(year, person.getId());
			PageBean pageBean = builder.builder(year, needChkPersons,commit);
			map.put("person", person);
			map.put("pageBean", pageBean);
		} catch (EnableYearNotExist e) {
			map.put("message","当前时间无法进行年终员工考核");
			return "yearchk/personchk/nottime";
		}
		map.put("message",new SessionMPR(session).getMessage());
		return "yearchk/personchk/list";
	}
							
	@RequestMapping(value="/personchk/{colId}",method=RequestMethod.GET)
	public String toPersonchk(@PathVariable("colId") Long colPersonId,@ModelAttribute("person")Person chkPerson,Map<String,Object> map,HttpSession session){
		
		Long year=0L;
		try {
			year=yearConfigService.getEnableYear();
		}catch (EnableYearNotExist e) {
			map.put("message","当前时间无法进行年终员工考核");
			return "yearchk/personchk/nottime";
		}
		
		Person colPerson=personRepository.find(colPersonId);
		List<Person> sameTypePerson=personRepository.getStaffByType(chkPerson.getType());
		sameTypePerson=removePerson(sameTypePerson,colPerson,chkPerson);
		List<CellBean> cellBeans=createDefaultList(year,chkPerson,colPerson,sameTypePerson);
		List<Fycheck> hasChecks=yearChkRepositroy.getPersonYearChkAboutPerson(year, colPerson, chkPerson);
		List<Fycheck> hasChecksForColPerson=changeChecksForColPerson(colPersonId,hasChecks);
		Map<String,Fycheck> hasChecksValues=changeChecksToMap(hasChecksForColPerson);
		cellBeans=setValueTocellBeans(cellBeans,hasChecksValues);
		PersonPageBeanBuilder builder=new PersonPageBeanBuilder(cellBeans);
		map.put("person", chkPerson);
		map.put("pageBean", builder.build(year, colPerson));
		map.put("message",new SessionMPR(session).getMessage());
		return "yearchk/personchk/personchk";
	}

	private List<Person> removePerson(List<Person> sameTypePerson,Person... persons) {
		for (Person comparePerson : persons) {
			Iterator<Person> iterator = sameTypePerson.iterator();
			while (iterator.hasNext()) {
				Person person = iterator.next();
				if (person.getId().equals(comparePerson.getId())) {
					sameTypePerson.remove(person);
					break;
				}
			}
		}
		
		return sameTypePerson;
	}

	private List<CellBean> setValueTocellBeans(List<CellBean> cellBeans,Map<String, Fycheck> hasChecksValues) {
		for (CellBean cellBean : cellBeans) {
			String key=cellBean.getColPerson().getId()+":"+cellBean.getRowPerson().getId();
			Fycheck fycheck=hasChecksValues.get(key);
			if(fycheck!=null){
				cellBean.setFycheck(fycheck);
			}
		}
		return cellBeans;
	}

	private Map<String, Fycheck> changeChecksToMap(
			List<Fycheck> hasChecksForColPerson) {
		Map<String, Fycheck> returnMap=new HashMap<String, Fycheck>();
		for (Fycheck fycheck : hasChecksForColPerson) {
			returnMap.put(fycheck.getColId()+":"+fycheck.getRowId(), fycheck);
		}
		return returnMap;
	}

	private List<Fycheck> changeChecksForColPerson(Long colPersonId,List<Fycheck> hasChecks) {
		for (Fycheck fycheck : hasChecks) {
			if(!fycheck.getColId().equals(colPersonId)){
				swapColIdAndRowId(fycheck);
			}
		}
		return hasChecks;
	}

	private void swapColIdAndRowId(Fycheck fycheck) {
		Long tempRowId=fycheck.getRowId();
		fycheck.setRowId(fycheck.getColId());
		fycheck.setColId(tempRowId);
		fycheck.setVal(0L-fycheck.getVal());
	}

	private List<CellBean> createDefaultList(Long year,Person chkPerson,Person colPerson,List<Person> rowPersons) {
		List<CellBean> retList=new ArrayList<CellBean>();
		for(Person rowPerson:rowPersons){
			if(!colPerson.getId().equals(rowPerson.getId())){				
				CellBean cellBean=new CellBean();
				cellBean.setColPerson(colPerson);
				cellBean.setRowPerson(rowPerson);
				Fycheck fycheck=FycheckFactory.createFycheck(year, colPerson.getId(), rowPerson.getId(), chkPerson.getId());
				cellBean.setFycheck(fycheck);
				retList.add(cellBean);
			}
		}
		
		return retList;
	}
	
	@RequestMapping(value="/personchk/{colId}/save",method=RequestMethod.POST)
	public String save(@PathVariable("colId") Long colPersonId,Long token,@ModelAttribute("person")Person chkPerson,PersonPageReceiveBean page,Map<String,Object> map,HttpSession session){
		
		List<Fycheck> fychecks=page.getFychecks();
		fychecks=changeChecksForColSave(fychecks);
		yearCheckService.saveFychecks(fychecks);
		new SessionMPR(session).setMessage("保存成功！");
		return "redirect:../../";
	}
	

	private List<Fycheck> changeChecksForColSave(List<Fycheck> fychecks) {
		for (Fycheck fycheck : fychecks) {
			if(fycheck.getColId().intValue()<fycheck.getRowId().intValue()){
				swapColIdAndRowId(fycheck);
			}
		}
		return fychecks;
	}

	@RequestMapping(value="/saveAllChecks",method=RequestMethod.POST)
	public String saveAllChecks(@RequestParam("year") Long year,@ModelAttribute("person")Person person,Map<String,Object> map,HttpSession session){
		List<Person> sameTypePerson=personRepository.getStaffByType(person.getType());
		sameTypePerson=removePerson(sameTypePerson, person);
		List<Fycheck> fychecks=createFychecksByDefault(year,sameTypePerson,person);
		List<Fycheck> savedFychecks=yearChkRepositroy.getPersonYearChkByChkperson(year,person);
		Map<String,Fycheck> hasCheckedValues=changeChecksToMap(savedFychecks);
		fychecks=setValuesToFychecks(fychecks,hasCheckedValues);
		yearCheckService.saveFychecks(fychecks);
		new SessionMPR(session).setMessage("全部评价已完成！");
		return "redirect:../yearchk";
	}

	private List<Fycheck> createFychecksByDefault(Long year,List<Person> sameTypePerson,Person chkPerson) {
		ArrayList<Fycheck> returnList = new ArrayList<Fycheck>();
		for (Person rowPerson : sameTypePerson) {
			for (Person colPerson : sameTypePerson) {
				if(colPerson.getId().intValue()>rowPerson.getId().intValue()){
					Fycheck fycheck=FycheckFactory.createFycheck(year,colPerson.getId(), rowPerson.getId(), chkPerson.getId());
					returnList.add(fycheck);
				}
			}
		}
		return returnList;
	}

	private List<Fycheck> setValuesToFychecks(List<Fycheck> fychecks,Map<String, Fycheck> hasCheckedValues) {
		for (int i = 0,len=fychecks.size(); i < len; i++) {
			Fycheck fycheck = fychecks.get(i);
			String key=fycheck.getColId()+":"+fycheck.getRowId();
			Fycheck hasCheckedValue=hasCheckedValues.get(key);
			if(hasCheckedValue!=null){
				fychecks.set(i, hasCheckedValue);
			}
		}
		return fychecks;
	}
	
	@Resource
	MonthChkRepository monthChkRepository;
	@Resource
	PersonSummaryService personSummaryService;
	@Resource
	YearchkStateService yearchkStateService;
	
	@RequestMapping(value="/personchk/{colId}/comparework/{rowId}",method=RequestMethod.GET)
	public String toCompareWork(@PathVariable("colId") Long colPersonId,@PathVariable("rowId") Long rowPersonId,Map<String,Object> map,HttpSession session){
		Long year=0L;
		try {
			year=yearConfigService.getEnableYear();
		}catch (EnableYearNotExist e) {
			map.put("message","当前时间无法进行年终员工考核");
		}
		
		map.put("year",year);
		
		Person colPerson=personRepository.find(colPersonId);
		List<MonthChk> colMonthChk = monthChkRepository.getMonthChkByPersonAndState(year, colPerson, MonthChkEnum.FINISHED);
		List<MonthChkItem> colItems=fetchMonthChkItems(colMonthChk);
		map.put("colItems", colItems);
		map.put("colPerson", colPerson);
		
		Person rowPerson = personRepository.find(rowPersonId);
		List<MonthChk> rowMonthChk = monthChkRepository.getMonthChkByPersonAndState(year, rowPerson, MonthChkEnum.FINISHED);
		List<MonthChkItem> rowItems=fetchMonthChkItems(rowMonthChk);
		map.put("rowItems", rowItems);
		map.put("rowPerson", rowPerson);
		
		return "yearchk/personchk/comparework";
	}

	private List<MonthChkItem> fetchMonthChkItems(List<MonthChk> colMonthChk) {
		ArrayList<MonthChkItem> monthChkItems = new ArrayList<MonthChkItem>();
		for(MonthChk monthChk:colMonthChk){
			monthChkItems.addAll(monthChk.getMonthChkItems());
		}
		return monthChkItems;
	}
	
	@RequestMapping(value="/personchk/{colId}/comparesummary/{rowId}",method=RequestMethod.GET)
	public String toCompareSummary(@PathVariable("colId") Long colPersonId,@PathVariable("rowId") Long rowPersonId,Map<String,Object> map,HttpSession session){
		Long year=0L;
		try {
			year=yearConfigService.getEnableYear();
		}catch (EnableYearNotExist e) {
			map.put("message","当前时间无法进行年终员工考核");
		}
		
		map.put("year",year);
		
		Person colPerson=personRepository.find(colPersonId);
		PersonSummary colSummary = personSummaryService.find(year, colPersonId);
		map.put("colSummary", colSummary);
		map.put("colPerson", colPerson);
		
		Person rowPerson = personRepository.find(rowPersonId);
		PersonSummary rowSummary = personSummaryService.find(year, rowPersonId);
		map.put("rowSummary", rowSummary);
		map.put("rowPerson", rowPerson);
		
		return "yearchk/personchk/comparesummary";
	}
	
	
	@RequestMapping(value="/commitAllChecks",method=RequestMethod.POST)
	public String commitAllChecks(@RequestParam("year") Long year,@PathVariable("personId") Long personId,Map<String,Object> map,HttpSession session){
		yearchkStateService.commitYearchk(year, personId);
		new SessionMPR(session).setMessage("全部评价已提交！");
		return "redirect:../yearchk";
	}
}
