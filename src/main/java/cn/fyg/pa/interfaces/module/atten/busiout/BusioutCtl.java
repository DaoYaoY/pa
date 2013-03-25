package cn.fyg.pa.interfaces.module.atten.busiout;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pa.application.BusioutService;
import cn.fyg.pa.domain.model.atten.busiout.BusiState;
import cn.fyg.pa.domain.model.atten.busiout.Busiout;
import cn.fyg.pa.domain.model.atten.common.AMPM;
import cn.fyg.pa.domain.model.person.Person;
import cn.fyg.pa.domain.model.person.PersonRepository;
import cn.fyg.pa.domain.shared.Result;
import cn.fyg.pa.infrastructure.util.DateTool;
import cn.fyg.pa.interfaces.module.shared.bean.YearAndMonthBean;
import cn.fyg.pa.interfaces.module.shared.session.SessionUtil;
import cn.fyg.pa.interfaces.module.shared.tool.Constant;

@Controller
@RequestMapping("/atten/{personId}/busiout")
public class BusioutCtl {
	
	private interface Page{
		String PATH = "atten/busiout/";
		String LIST = PATH + "list";
		String NEW = PATH + "new";
		String VIEW = PATH + "view";
	}
	
	@Resource
	SessionUtil sessionUtil;
	@Resource
	PersonRepository personRepository;
	@Resource
	BusioutService busioutService;
	
	@ModelAttribute("person")
	public Person initPerson(@PathVariable("personId") Long personId){
		return personRepository.find(personId);
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(YearAndMonthBean queryBean,@ModelAttribute("person")Person person,Map<String,Object> map,HttpSession session){
		List<Busiout> busioutList=busioutService.getBusioutByPersonAndYearAndMonth(person, queryBean.getYear(), queryBean.getMonth());
		map.put("dateTool", new DateTool());
		map.put("queryBean", queryBean);
		map.put("busioutList", busioutList);
		return Page.LIST;
	}

	@RequestMapping(value="new",method=RequestMethod.GET)
	public String toNew(@ModelAttribute("person")Person person,Map<String,Object> map){
		Object obj=sessionUtil.getValueAndRemove("busiout");
		Busiout busiout=(obj==null?busioutService.create(person):(Busiout)obj);
		map.put("busiout", busiout);
		map.put("ampms", AMPM.values());
		map.put("person", person);
		map.put("dayList", new DateTool().theDaysAfterToday());
		return Page.NEW;
	}
	
	@RequestMapping(value="commit",method=RequestMethod.POST)
	public String commit(HttpServletRequest request,@ModelAttribute("person")Person person,Map<String,Object> map,RedirectAttributes redirectAttributes){
		Busiout busiout = busioutService.create(person);
		ServletRequestDataBinder binder = new ServletRequestDataBinder(busiout);
		binder.bind(request);
		
		Result result = busioutService.verify(busiout);
		if(result.notPass()){
			redirectAttributes.addFlashAttribute(Constant.MESSAGE_NAME,result.cause());
			sessionUtil.setValue("busiout", busiout);
			return "redirect:new";
		}
		busiout.setNo(busioutService.getNextNo(person, busiout.getBegDayitem().getYear(), busiout.getBegDayitem().getMonth()));
		busiout.setPerson(person);
		busiout.setBusiState(BusiState.committed);
		busiout.setCommitDate(new Date());
		busioutService.save(busiout);
		return "redirect:list";
	}
	
	@RequestMapping(value="view/{busioutId}",method=RequestMethod.GET)
	public String toView( @PathVariable("busioutId")Long busioutId,YearAndMonthBean queryBean,Map<String,Object> map){
		Busiout busiout=busioutService.find(busioutId);
		map.put("busiout", busiout);
		map.put("queryBean", queryBean);
		return Page.VIEW;
	}
	
}
