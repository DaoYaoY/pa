package cn.fyg.pa.interfaces.module.admin.yeartypeweight;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pa.application.IdrYearTypeWeightService;
import cn.fyg.pa.domain.model.indicatortype.IdrTypeRepository;
import cn.fyg.pa.domain.model.yeartypeweight.IdrYearTypeWeight;
import cn.fyg.pa.domain.shared.Result;
import cn.fyg.pa.infrastructure.message.imp.SessionMPR;

@Controller
@RequestMapping("/admin/idrtypeweight")
public class IdrTypeWeightCtl {
	
	private static final Logger logger=LoggerFactory.getLogger(IdrTypeWeightCtl.class);
	
	@Resource
	IdrTypeRepository idrTypeRepository;
	
	@Resource
	IdrYearTypeWeightService idrYearTypeWeightService;
	
	
	@RequestMapping(value="/edit/{year}",method=RequestMethod.GET)
	public String toEdit(@PathVariable("year") Long year,Map<String,Object> map,HttpSession session){
		logger.info("toEdit");
		IdrYearTypeWeight idrYearTypeWeight=idrYearTypeWeightService.getByYear(year);
		map.put("idrTypes", idrTypeRepository.findAll());
		map.put("idrYearTypeWeight", idrYearTypeWeight);
		map.put("message", new SessionMPR(session).getMessage());
		return "typeweight/edit";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(IdrYearTypeWeight idrYearTypeWeightForm,Map<String,Object> map,HttpSession session){
		logger.info("save");
		idrYearTypeWeightForm=idrYearTypeWeightService.save(idrYearTypeWeightForm);
		new SessionMPR(session).setMessage("保存成功！");
		return "redirect:edit/"+idrYearTypeWeightForm.getYear();
	}
	
	@RequestMapping(value="/commit",method=RequestMethod.POST)
	public String commit(IdrYearTypeWeight idrYearTypeWeightForm,Map<String,Object> map,HttpSession session){
		logger.info("commit");
		IdrYearTypeWeight idrYearTypeWeight=idrYearTypeWeightService.save(idrYearTypeWeightForm);
		Result result=idrYearTypeWeight.isTypeWeightRight();
		if(result.pass()){
			new SessionMPR(session).setMessage("提交通过!");
		}else{
			new SessionMPR(session).setMessage("提交未通过,"+result.cause()+"!");
		}
		return "redirect:edit/"+idrYearTypeWeight.getYear();
	}

}
