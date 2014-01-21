package cn.fyg.pa.interfaces.module.monthlog.cf;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pa.application.SummarySnapshotService;
import cn.fyg.pa.domain.model.summarysnapshot.SnapshotEnum;
import cn.fyg.pa.domain.model.summarysnapshot.SummarySnapshot;
import cn.fyg.pa.domain.model.summarysnapshot.SummarySnapshotRepository;
import cn.fyg.pa.domain.shared.state.StateChangeException;
import cn.fyg.pa.interfaces.module.monthlog.SummarySnapshotFacade;
import cn.fyg.pa.interfaces.module.monthlog.dto.PageItemBean;
import cn.fyg.pa.interfaces.module.shared.bean.YearBean;
import cn.fyg.pa.interfaces.module.shared.message.MessagePasser;
import cn.fyg.pa.interfaces.module.shared.session.SessionUtil;
import cn.fyg.pa.interfaces.module.shared.tool.Constant;
import cn.fyg.pa.interfaces.module.shared.tool.DateTool;

@Controller
@RequestMapping("/monthlog/cf")
public class ConfirmSummarySnapshotCtl {
	
	private static final Logger logger=LoggerFactory.getLogger(ConfirmSummarySnapshotCtl.class);
	
	private static final String QUERY_BEAN_LIST = "manage.summarysnapshot.confirmsummarysnapshotctl.list";
	
	private static final String PATH = "mange/summarysnapshot/";
	private interface Page {
		String LIST = PATH + "list";
		String VIEW = PATH + "view";
	}
	
	@Resource
	SessionUtil sessionUtil;
	@Resource
	SummarySnapshotService summarySnapshotService;
	@Resource
	SummarySnapshotRepository summarySnapshotRepository;
	@Resource
	SummarySnapshotFacade summarySnapshotFacade;
	@Resource
	MessagePasser messagePasser;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String  toList(Map<String,Object> map){
		YearBean queryBean=getYearBeanFromSession();
		List<SummarySnapshot> summarySnapshots = summarySnapshotRepository.findByYear(queryBean.getYear());
		map.put("summarySnapshots", summarySnapshots);
		map.put("dateTool", new DateTool());
		map.put("queryBean", queryBean);
		map.put(Constant.MESSAGE_NAME, messagePasser.getMessage());
		return Page.LIST;
	}
	
	private YearBean getYearBeanFromSession(){
		YearBean yearBean=sessionUtil.getValue(QUERY_BEAN_LIST);
		if(yearBean==null){
			yearBean=new YearBean();
			sessionUtil.setValue(QUERY_BEAN_LIST, yearBean);
		}
		return yearBean;
	}
	
	@RequestMapping(value="",method=RequestMethod.POST)
	public String setYearBean(YearBean yearBean){
		sessionUtil.setValue(QUERY_BEAN_LIST, yearBean);
		return "redirect:/monthlog/cf";
	}
	
	@RequestMapping(value="/{summarySnapshotId}",method=RequestMethod.GET)
	public String toView(@PathVariable("summarySnapshotId") Long summarySnapshotId,Map<String,Object> map){
		SummarySnapshot summarySnapshot = summarySnapshotRepository.find(summarySnapshotId);
		map.put("summarySnapshot", summarySnapshot);
		PageItemBean pageItemBean = summarySnapshotFacade.buildPageItemBean(summarySnapshot.getSnapshotItems());
		map.put("pageItemBean", pageItemBean);
		boolean isSummarySnapshotCanConfirm=summarySnapshot.getState().equals(SnapshotEnum.RECEIVED);
		map.put("isSummarySnapshotCanConfirm", isSummarySnapshotCanConfirm);
		return Page.VIEW;
	}
	
	@RequestMapping(value="/{summarySnapshotId}/confirm",method=RequestMethod.POST)
	public String confirm(@PathVariable("summarySnapshotId") Long summarySnapshotId){
		String message="确认成功";
		try {
			summarySnapshotService.next(summarySnapshotId);
		} catch (StateChangeException e) {
			logger.error("",e);
			message="确认失败";
		}
		messagePasser.setMessage(message);
		return "redirect:/monthlog/cf";
	}
}
