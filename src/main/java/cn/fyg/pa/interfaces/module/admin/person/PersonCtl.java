package cn.fyg.pa.interfaces.module.admin.person;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.fyg.pa.application.PersonService;
import cn.fyg.pa.domain.model.person.ManageEnum;
import cn.fyg.pa.domain.model.person.Person;
import cn.fyg.pa.domain.model.person.PersonRepository;
import cn.fyg.pa.domain.model.person.TypeEnum;
import cn.fyg.pa.interfaces.module.shared.tool.EnumUtil;
import cn.fyg.pa.interfaces.module.shared.tool.Tool;

//XXX   用户模块待重构，独立用户模块？
@Controller
@RequestMapping("/admin/person")
public class PersonCtl {

	@Resource
	PersonRepository personRepository;
	@Resource
	PersonService personService;
	
	@RequestMapping(value="")
	public ModelAndView index() {
		
		ModelAndView mav = new ModelAndView();
		List<Person> persons = personRepository.getAllFyperson();
		mav.addObject("persons",persons);
		mav.setViewName("person/list");
		return mav;
	}
	
	@RequestMapping(value="/new")
	public ModelAndView _new(){
		Person person=new Person();
		ModelAndView mav=new ModelAndView();
		mav.addObject("typeEnum",EnumUtil.enumToMap(TypeEnum.values()));
		mav.addObject("manageEnum",EnumUtil.enumToMap(ManageEnum.values()));
		mav.addObject("person",person);
		mav.setViewName("person/new");
		return mav;
	}
	
    /** 保存新增 */  
    @RequestMapping(value="",method=RequestMethod.POST)  
    public ModelAndView create(Person person,BindingResult result) throws Exception {
    	if(result.hasErrors()){
			ModelAndView mav=new ModelAndView();
			mav.addObject("typeEnum",EnumUtil.enumToMap(TypeEnum.values()));
			mav.addObject("manageEnum",EnumUtil.enumToMap(ManageEnum.values()));
			mav.addObject("person", person);
			mav.setViewName("person/new");
			return mav;
		}
    	personService.save(person);  
        return new ModelAndView("redirect:person");  
    }  
      
	/**显示编辑*/
	@RequestMapping(value="/{personId}")
	public ModelAndView edit(@PathVariable("personId")Long personId){
		Person person=personRepository.find(personId);
		ModelAndView mav=new ModelAndView();
		mav.addObject("typeEnum",EnumUtil.enumToMap(TypeEnum.values()));
		mav.addObject("manageEnum",EnumUtil.enumToMap(ManageEnum.values()));
		mav.addObject("person", person);
		mav.setViewName("person/edit");
		return mav;
	}
	
	/**保存编辑*/
	@RequestMapping(value="/{personId}",method=RequestMethod.PUT)
	public ModelAndView update(@PathVariable("personId")Long personId,Person person,BindingResult result){
		if(result.hasErrors()){
			person.setId(personId);
			ModelAndView mav=new ModelAndView();
			mav.addObject("typeEnum",EnumUtil.enumToMap(TypeEnum.values()));
			mav.addObject("manageEnum",EnumUtil.enumToMap(ManageEnum.values()));
			mav.addObject("person", person);
			mav.setViewName("person/edit");
			return mav;
		}
		person.setId(personId);
		personService.save(person);
		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:../person");
		return mav;
	}
	
	@RequestMapping(value="/{personId}",method=RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable("personId")Long personId){
		personService.remove(personId);
		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:person");
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/password")
	public ModelAndView initPassword(@RequestParam(value="passlen",required=false) Long passlen,@RequestParam(value="type",required=false) String type) {
		
		List<Person> people = personRepository.getAllFyperson();
		List<Person> peopleHasPassword=new ArrayList<Person>();
		for (Person fyperson : people) {
			if(type.equals("reset")){
				fyperson.setChkstr(Tool.getPassword(passlen));
				peopleHasPassword.add(fyperson);
			}else if(type.equals("init")){
				if(fyperson.getChkstr()==null||fyperson.getChkstr().trim().equals("")){
					fyperson.setChkstr(Tool.getPassword(passlen));
					peopleHasPassword.add(fyperson);
				}
			}

		}
		personService.saveAll(peopleHasPassword);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("persons",people);
		mav.addObject("msg","操作成功");
		mav.setViewName("redirect:");
		return mav;
		
	}
	

}
