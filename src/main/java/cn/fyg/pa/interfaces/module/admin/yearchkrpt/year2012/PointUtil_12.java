package cn.fyg.pa.interfaces.module.admin.yearchkrpt.year2012;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.fyg.pa.interfaces.module.shared.tool.Constant;

public class PointUtil_12 {
	
	private List<Point_12> rptList;
	private boolean hasCalculate=false;
	
	public PointUtil_12(List<Object[]> checkPoint,List<Object[]> val){
		rptList=new ArrayList<Point_12>(checkPoint.size());
		for (int i = 0; i < checkPoint.size(); i++) {
			Object[] arr=checkPoint.get(i);
			Point_12 point=new Point_12().personId(((Integer)arr[0]).longValue())
					.personName(arr[1].toString())
					.personDept(arr[2].toString())
					.scheck((BigDecimal)arr[3]);
			rptList.add(point);
		}
		Map<Long,BigDecimal> temp=new HashMap<Long,BigDecimal>();
		for (int i = 0; i < val.size(); i++) {
			Object[] arr=val.get(i);
			temp.put(((Integer)arr[0]).longValue(), (BigDecimal)arr[1]);
		}
		for(Point_12 point:rptList){
			BigDecimal val2 = temp.get(point.getPersonId());
			point.val(val2==null?new BigDecimal("0"):val2);
		}
	}
	
	public void calculate(){
		calculateMamp();
		calculateSmaxAndSmainAndSamp();
		calculatU();
		calculatResult();
		hasCalculate=true;
	}

	private void calculatResult() {
		for(Point_12 point:rptList){
			point.calculatResult();
		}
	}

	private void calculatU() {
		for(Point_12 point:rptList){
			point.calculatUpsilon();
		}
	}

	private void calculateSmaxAndSmainAndSamp() {
		BigDecimal Smax=rptList.get(0).getScheck();
		BigDecimal Smin=rptList.get(0).getScheck();
		BigDecimal Samp=Constant.ZERO;
		for(Point_12 point : rptList){
			BigDecimal s = point.getScheck();
			Smax = s.compareTo(Smax) > 0 ? s : Smax;
			Smin = s.compareTo(Smin) < 0 ? s : Smin;
		}
		Samp = Smax.subtract(Smin);
		for(Point_12 point:rptList){
			point.samp(Samp).maxs(Smax).mins(Smin);
		}
	}


	//考核幅度平均值
	private void calculateMamp() {
		Map<String, BigDecimal> depAmp = calculateEveryDepAmp();
		filterZero(depAmp);
		BigDecimal total=new BigDecimal("0");
		int count=0;
		for(Map.Entry<String, BigDecimal> entry:depAmp.entrySet()){
			total=total.add(entry.getValue());
			count++;
		}
		BigDecimal mamp=total.divide(new BigDecimal(count),Constant.SCALE,Constant.ROUND_MODEL);
		for(Point_12 point:rptList){
			point.mamp(mamp);
		}
	}

	//过滤是0的键，不参与计算
	private void filterZero(Map<String, BigDecimal> depAmp) {
		Iterator<String> it=depAmp.keySet().iterator();
		while(it.hasNext()){
			String key=it.next();
			if(depAmp.get(key).compareTo(Constant.ZERO)==0){
				it.remove();
			}
		}
	}

	
	//计算所有部门的考核幅度
	private Map<String, BigDecimal> calculateEveryDepAmp() {
		Map<String,BigDecimal> depMax=new HashMap<String,BigDecimal>();
		Map<String,BigDecimal> depMin=new HashMap<String,BigDecimal>();
		for(Point_12 point:rptList){
			String personDept=point.getPersonDept();
			BigDecimal scheck = point.getScheck();
			updateDepMax(depMax, personDept, scheck);
			updateDepMin(depMin, personDept, scheck);
		}
		for(String key:depMax.keySet()){
			depMax.put(key, depMax.get(key).subtract(depMin.get(key)));
		}
		return depMax;
	}

	//获得部门最小值
	private void updateDepMin(Map<String, BigDecimal> depMin,
			String personDept, BigDecimal scheck) {
		if(depMin.containsKey(personDept)){
			if(scheck.compareTo(depMin.get(personDept))<0){
				depMin.put(personDept, scheck);
				return;
			}
			return;
		}
		depMin.put(personDept, scheck);
	}
	
	//获得部门最大值
	private void updateDepMax(Map<String, BigDecimal> depMax,
			String personDept, BigDecimal scheck) {
		if(depMax.containsKey(personDept)){
			if(scheck.compareTo(depMax.get(personDept))>0){
				depMax.put(personDept, scheck);
				return;
			}
			return;
		}
		depMax.put(personDept, scheck);
	}


	
	public List<Point_12> getResult() throws Exception{
		if(!hasCalculate) throw new Exception("point dont  calculate!");
		return rptList;
	}
	
	public void orderByPoint(String order) throws Exception{
		if(!hasCalculate) throw new Exception("point dont  calculate!");
		Collections.sort(rptList, new PointDescComparator());
		int i=1;
		for (Point_12 point : rptList) {
			point.ranking(i++);
		}
		if(order.equals("desc")){
			Collections.reverse(rptList);
		}
	}
	
	private class PointDescComparator implements Comparator<Point_12> {
		@Override
		public int compare(Point_12 p1, Point_12 p2) {
			return p2.getResult().compareTo(p1.getResult());
		}
	}

}
