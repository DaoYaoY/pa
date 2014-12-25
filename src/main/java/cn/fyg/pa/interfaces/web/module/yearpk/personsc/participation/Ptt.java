package cn.fyg.pa.interfaces.web.module.yearpk.personsc.participation;

import java.math.BigDecimal;

import cn.fyg.pa.interfaces.web.shared.tool.Constant;

public class Ptt {
	
	/**
	 * 需要达到的参与度
	 */
	private static final BigDecimal reqPercent=new BigDecimal("0.5");
	
	/**
	 * 最多需要填写的参与个数, 如果根据参与度得出的结果大于这个数，
	 * 那么就取该数
	 */
	private static final int reqNumber=20;
	
	private int winOrLose;
	
	private int draw;
	
	private int total;
	
	private int needCheck;
	
	private int totalCheck;

	public Ptt(int winOrLose, int draw) {
		super();
		this.winOrLose = winOrLose;
		this.draw = draw;
		this.total=this.winOrLose+this.draw;
		initNeedCheck();
	}
	
	private void initNeedCheck() {
		this.totalCheck = reqPercent.multiply(new BigDecimal(this.draw+this.winOrLose))
		.setScale(0, Constant.ROUND_MODEL).intValue();
		if(this.totalCheck>reqNumber){
			this.totalCheck=reqNumber;
		}
		int tempNeedCheck=this.totalCheck-this.winOrLose;
		this.needCheck=tempNeedCheck>0?tempNeedCheck:0;
	}
	
	public int getTotalCheck() {
		return totalCheck;
	}

	public void setTotalCheck(int totalCheck) {
		this.totalCheck = totalCheck;
	}

	public int getTotal() {
		return total;
	}

	public int getWinOrLose() {
		return winOrLose;
	}

	public void setWinOrLose(int winOrLose) {
		this.winOrLose = winOrLose;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}
	
	public String getReqPercent(){
		return reqPercent.multiply(new BigDecimal("100")).setScale(0, Constant.ROUND_MODEL).toString();
	}
	
	public int getNeedCheck(){
		return this.needCheck;
	}
	
	public boolean isPass(){
		return this.needCheck==0;
	}

}
