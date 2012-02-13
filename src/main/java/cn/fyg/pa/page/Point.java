package cn.fyg.pa.page;

import java.math.BigDecimal;

import cn.fyg.pa.tool.Constant;

public class Point {
	
	//人员id
	private Long personId;
	//人员名称
	private String personName;
	//人员部门
	private String personDept;
	//分级考核得分
	private BigDecimal scheck=null; 
	//部门考核平均值
	private BigDecimal mdep=null;
	//总体考核平均值
	private BigDecimal mall=null;
	//部门考核幅度
	private BigDecimal damp=null;
	//考核幅度平均值
	private BigDecimal mamp=null;
	//分级考核总分
	private BigDecimal stotal=new BigDecimal("150");
	//分级均衡得分值
	private BigDecimal s=null;
	
	
	//分级考核幅度
	private BigDecimal alpha=null;
	//最高得分
	private BigDecimal maxs=null;
	//最低得分
	private BigDecimal mins=null;
	//分级均衡得分幅度
	private BigDecimal samp=null;
	//纵向考核最后得分
	private BigDecimal upsilon=null;
	
	
	//横向考核得分
	private BigDecimal val=null;
	//最终得分
	private BigDecimal result=null;
	
	/**
	 * 根据提供的值，计算s 公式如下
	 * [(Scheck-Mdep)*Mamp+Damp*Mall]/(Damp*Stotal)
	 */
	public void calculatS(){
		if(damp.compareTo(Constant.ZERO)==0) return;
		
		BigDecimal up=scheck.subtract(mdep).multiply(mamp).add(damp.multiply(mall));
		BigDecimal down=damp.multiply(stotal);
		s=up.divide(down,Constant.SCALE,Constant.ROUND_MODEL);
	}
	
	/**
	 * 计算upsilon
	 * 分级平均考核幅度 alpha=Mamp/Stotal;
	 * upsilon=[(S-Smin)/Samp*alpha-alpha/2]*100=[((S-Smin)*2-Samp)*Alpha/(Samp*2)]*100
	 */
	public void calculatUpsilon(){
		alpha=mamp.divide(stotal, Constant.SCALE, Constant.ROUND_MODEL);
		BigDecimal up=s.subtract(mins).multiply(new BigDecimal("2")).subtract(samp).multiply(alpha);
		BigDecimal down=samp.multiply(new BigDecimal("2"));
		upsilon=up.multiply(Constant.HUNDRED).divide(down,Constant.SCALE,Constant.ROUND_MODEL);
	}
	
	/**
	 * 计算合计值
	 * Result=upsilon+val
	 */
	public void calculatResult(){
		result=upsilon.add(val);
	}
	
	public Point personId(Long personId) {
		this.personId = personId;
		return this;
	}
	
	public Point personName(String personName) {
		this.personName = personName;
		return this;
	}
	
	public Point personDept(String personDept) {
		this.personDept = personDept;
		return this;
	}
	
	public Point scheck(BigDecimal scheck) {
		this.scheck = scheck;
		return this;
	}
	
	public Point mdep(BigDecimal mdep) {
		this.mdep = mdep;
		return this;
	}
	
	public Point mall(BigDecimal mall) {
		this.mall = mall;
		return this;
	}
	
	public Point damp(BigDecimal damp) {
		this.damp = damp;
		return this;
	}
	
	public Point s(BigDecimal s) {
		this.s = s;
		return this;
	}
	
	public Point mamp(BigDecimal mamp) {
		this.mamp = mamp;
		return this;
	}
	
	public Point maxs(BigDecimal maxs) {
		this.maxs = maxs;
		return this;
	}
	
	public Point upsilon(BigDecimal upsilon) {
		this.upsilon = upsilon;
		return this;
	}
	
	public Point alpha(BigDecimal alpha) {
		this.alpha = alpha;
		return this;
	}
	
	public Point samp(BigDecimal samp) {
		this.samp = samp;
		return this;
	}
	
	public Point stotal(BigDecimal stotal) {
		this.stotal = stotal;
		return this;
	}
	
	public Point mins(BigDecimal mins) {
		this.mins = mins;
		return this;
	}
	
	public Point val(BigDecimal val) {
		this.val = val;
		return this;
	}

	public Point result(BigDecimal result) {
		this.result = result;
		return this;
	}

	
	
	public Long getPersonId() {
		return personId;
	}
	
	public String getPersonName() {
		return personName;
	}
	
	public String getPersonDept() {
		return personDept;
	}

	public BigDecimal getScheck() {
		return scheck;
	}

	public BigDecimal getMdep() {
		return mdep;
	}

	public BigDecimal getMall() {
		return mall;
	}

	public BigDecimal getDamp() {
		return damp;
	}
	
	public BigDecimal getMamp() {
		return mamp;
	}
	
	public BigDecimal getStotal() {
		return stotal;
	}
	public BigDecimal getS() {
		return s;
	}

	public BigDecimal getAlpha() {
		return alpha;
	}
	public BigDecimal getMaxs() {
		return maxs;
	}
	
	public BigDecimal getMins() {
		return mins;
	}
	
	public BigDecimal getSamp() {
		return samp;
	}
	public BigDecimal getUpsilon() {
		return upsilon;
	}

	public BigDecimal getVal() {
		return val;
	}

	public BigDecimal getResult() {
		return result;
	}

	
	
	
	
}
