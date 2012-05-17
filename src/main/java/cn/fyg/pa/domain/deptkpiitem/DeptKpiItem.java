package cn.fyg.pa.domain.deptkpiitem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.CascadeType;


import cn.fyg.pa.domain.companykpi.IdrCompany;
import cn.fyg.pa.domain.deptkpi.DeptKpi;

/**
 * 部门kpi分解明细，作为聚合根
 */
@Entity
public class DeptKpiItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//部门kpi分解明细id
	
	@ManyToOne(cascade={CascadeType.PERSIST})
	@JoinColumn(name = "deptkpi_id")
	private DeptKpi deptKpi;//部门kpi分解
	
	private Long sn;//序号
	
	@ManyToOne
	@JoinColumn(name = "idrcompany_id")
	private IdrCompany idrCompany;//公司指标
	
	private String context;//部门指标内容

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeptKpi getDeptKpi() {
		return deptKpi;
	}

	public void setDeptKpi(DeptKpi deptKpi) {
		this.deptKpi = deptKpi;
	}

	public Long getSn() {
		return sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public IdrCompany getIdrCompany() {
		return idrCompany;
	}

	public void setIdrCompany(IdrCompany idrCompany) {
		this.idrCompany = idrCompany;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	
}
