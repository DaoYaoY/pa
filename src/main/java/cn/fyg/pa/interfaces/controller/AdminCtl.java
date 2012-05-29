package cn.fyg.pa.interfaces.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.fyg.pa.interfaces.bean.UrlNameBean;
import cn.fyg.pa.interfaces.login.LoginCtl;



@Controller
@RequestMapping("/admin")
public class AdminCtl {
	private static final Logger logger = LoggerFactory.getLogger(LoginCtl.class);
	
	@RequestMapping(value="/all")
	public String list(Map<String,Object> map) {
		logger.info("show admin all");
		String title="管理员页面";
		List<UrlNameBean> list=new ArrayList<UrlNameBean>();
		list.add(new UrlNameBean("用户管理","/pa/admin/person"));
		list.add(new UrlNameBean("考核报表","/pa/admin/rpt/point/asc"));
		list.add(new UrlNameBean("1.KPI指标类别","/pa/admin/idrtype"));
		list.add(new UrlNameBean("2.KPI类别权重","/pa/admin/idrtypeweight/edit/2010"));
		list.add(new UrlNameBean("3.公司KPI分解","/pa/admin/idrcompany/edit/2010"));
		list.add(new UrlNameBean("4.公司KPI指标分配部门","/pa/admin/deptindicator/2010"));
		list.add(new UrlNameBean("5.公司KPI指标部门分解(办公室)","/pa/admin/deptkpi/2010/department/1"));
		map.put("title", title);
		map.put("urls", list);
		
		return "route/all";
	}
}
