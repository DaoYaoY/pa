package cn.fyg.pa.application.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pa.application.DeptKpiService;
import cn.fyg.pa.domain.model.department.Department;
import cn.fyg.pa.domain.model.deptindicator.DeptIndicator;
import cn.fyg.pa.domain.model.deptindicator.DeptIndicatorRepository;
import cn.fyg.pa.domain.model.deptkpi.DeptKpi;
import cn.fyg.pa.domain.model.deptkpi.DeptKpiFactory;
import cn.fyg.pa.domain.model.deptkpi.DeptKpiRepository;
import cn.fyg.pa.domain.model.deptkpiitem.DeptKpiItemRepository;
import cn.fyg.pa.domain.shared.Result;

@Service
public class DeptKpiServiceImpl implements DeptKpiService {
	
	@Resource
	DeptKpiRepository deptKpiRepository;
	
	@Resource
	DeptKpiItemRepository deptKpiItemRepository;
	
	@Resource
	DeptIndicatorRepository deptIndicatorRepository;

	@Override
	public DeptKpi getDeptKpiByYearAndDepartment(Long year, Department department) {
		DeptKpi deptKpi = deptKpiRepository.findByYearAndDepartment(year, department);
		if(deptKpi==null){
			return DeptKpiFactory.createDeptKpiByYearAndDepartment(year, department);
		}
		return deptKpi;
	}

	@Override
	public Result commitDeptKpi(DeptKpi deptKpi) {
		DeptIndicator deptIndicator = deptIndicatorRepository.findByYearAndDepartment(deptKpi.getYear(), deptKpi.getDepartment());
		return deptKpi.verifySelf(deptIndicator);
	}

	@Override
	@Transactional
	public DeptKpi save(DeptKpi deptKpi) {
		return deptKpiRepository.save(deptKpi);
	}

}
