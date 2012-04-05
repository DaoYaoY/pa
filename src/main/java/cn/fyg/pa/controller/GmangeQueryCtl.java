package cn.fyg.pa.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pa.bean.IdrMonthPlanQueryBean;
import cn.fyg.pa.bean.MonthchkQueryBean;
import cn.fyg.pa.model.Department;
import cn.fyg.pa.model.IdrMonthPlanBill;
import cn.fyg.pa.model.MonthChk;
import cn.fyg.pa.model.enums.IdrMonthPlanEnum;
import cn.fyg.pa.model.enums.MonthChkEnum;
import cn.fyg.pa.service.DepartmentService;
import cn.fyg.pa.service.IdrMonthPlanBillService;
import cn.fyg.pa.service.MonthChkService;
import cn.fyg.pa.tool.DateTool;

@Controller
@RequestMapping("/gmange/{personId}/query")
public class GmangeQueryCtl {
	@Resource
	MonthChkService monthChkService;
	
	@Resource
	IdrMonthPlanBillService idrMonthPlanBillService;
	
	@Resource
	DepartmentService departmentService;

	@RequestMapping(value="/idrmonthplan",method=RequestMethod.GET)
	public String queryIdrMonthPlan(IdrMonthPlanQueryBean queryBean,Map<String,Object> map){
		List<IdrMonthPlanBill> idrMonthPlanBills=idrMonthPlanBillService.getIdrMonthPlanBillByPeriodAndState(queryBean.getYear(), queryBean.getMonth(), IdrMonthPlanEnum.FINISHED);
		DateTool dtl=new DateTool();
		map.put("years", dtl.getAllYears());
		map.put("months", dtl.getAllMonths());
		map.put("queryPage", queryBean);
		map.put("idrMonthPlanBills", idrMonthPlanBills);
		return "gmangequery/idrmonthplan";
	}
	

	@RequestMapping(value="/monthchk",method=RequestMethod.GET)
	public String queryMonthchk(MonthchkQueryBean queryBean,Map<String,Object> map){
		List<Department> departments=departmentService.getAllDepartmentsOrderById();
		List<MonthChk> monthChks=monthChkService.getMonthChkByPeriodAndDepartmentAndState(queryBean.getYear(), queryBean.getMonth(),queryBean.getDepartment(), MonthChkEnum.FINISHED);
		map.put("dateTool", new DateTool());
		map.put("departments",departments);
		map.put("queryBean", queryBean);
		map.put("monthChks", monthChks);
		return "gmangequery/monthchk";
	}

}
