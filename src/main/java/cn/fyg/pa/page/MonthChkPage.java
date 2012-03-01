package cn.fyg.pa.page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pa.model.MonthChk;
import cn.fyg.pa.model.MonthChkItem;
import cn.fyg.pa.model.WorkType;
import cn.fyg.pa.tool.CheckTool;

public class MonthChkPage {

	private Long year;

	private Long month;

	private List<String> monthChkItems_id;

	private List<String> monthChkItems_sn;

	private List<String> monthChkItems_task;
	
	private List<String> monthChkItems_worktype;
	
	private List<String> monthChkItems_workhour;
	
	public List<String> getMonthChkItems_workhour() {
		return monthChkItems_workhour;
	}

	public void setMonthChkItems_workhour(List<String> monthChkItems_workhour) {
		this.monthChkItems_workhour = monthChkItems_workhour;
	}

	public List<String> getMonthChkItems_worktype() {
		return monthChkItems_worktype;
	}

	public void setMonthChkItems_worktype(List<String> monthChkItems_worktype) {
		this.monthChkItems_worktype = monthChkItems_worktype;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public Long getMonth() {
		return month;
	}

	public void setMonth(Long month) {
		this.month = month;
	}

	public List<String> getMonthChkItems_id() {
		return monthChkItems_id;
	}

	public void setMonthChkItems_id(List<String> monthChkItems_id) {
		this.monthChkItems_id = monthChkItems_id;
	}

	public List<String> getMonthChkItems_sn() {
		return monthChkItems_sn;
	}

	public void setMonthChkItems_sn(List<String> monthChkItems_sn) {
		this.monthChkItems_sn = monthChkItems_sn;
	}

	public List<String> getMonthChkItems_task() {
		return monthChkItems_task;
	}

	public void setMonthChkItems_task(List<String> monthChkItems_task) {
		this.monthChkItems_task = monthChkItems_task;
	}

	public void updateMonthChk(MonthChk monthChk) {
		monthChk.setYear(year);
		monthChk.setMonth(month);
		
		Map<Long,MonthChkItem> map=new HashMap<Long,MonthChkItem>();
		for(MonthChkItem item:monthChk.getMonthChkItems()){
			map.put(item.getId(), item);
		}
		
		List<MonthChkItem> newItems=new ArrayList<MonthChkItem>();
		
		if(!checkFilterItem()){
			monthChk.setMonthChkItems(newItems);
			return;
		}
		
		filterAllItem();
		
		for(int i=0,len=monthChkItems_sn.size();i<len;i++){
			String itemid=monthChkItems_id.get(i);
			Long sn=new Long(monthChkItems_sn.get(i));
			String task=monthChkItems_task.get(i);
			WorkType workType=new WorkType();
			workType.setId(new Long(monthChkItems_worktype.get(i)));
			//TODO 修改此处逻辑位置
			BigDecimal workhour=null;
			String forcheckStr=monthChkItems_workhour.get(i).trim();
			if(CheckTool.checkFloat(forcheckStr)){
				workhour=new BigDecimal(forcheckStr);				
			}

			
			MonthChkItem item = StringUtils.isBlank(itemid) ? new MonthChkItem()
					: map.get(new Long(itemid));
			item.setMonthChk(monthChk);
			item.setSn(sn);
			item.setTask(task);
			item.setWorkType(workType);
			item.setWorkhour(workhour);
			newItems.add(item);
		}
		
		monthChk.setMonthChkItems(newItems);
	}

	private boolean checkFilterItem() {
		if(monthChkItems_id==null) return false;
		if(monthChkItems_sn==null) return false;
		if(monthChkItems_task==null) return false;
		if(monthChkItems_worktype==null) return false;
		if(monthChkItems_workhour==null) return false;
		return true;
	}

	private void filterAllItem() {
		filterEmptyList(monthChkItems_id);
		filterEmptyList(monthChkItems_sn);
		filterEmptyList(monthChkItems_task);
		filterEmptyList(monthChkItems_worktype);
		filterEmptyList(monthChkItems_workhour);
	}

	/**
	 * 过滤参数中出现null 或者为空等情况
	 */
	private void filterEmptyList(List<String> list) {
		String empty="";
		if(list.isEmpty()){
			list.add(empty);
		}
	}

}
