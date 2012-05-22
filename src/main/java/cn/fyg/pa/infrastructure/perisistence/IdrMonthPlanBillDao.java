package cn.fyg.pa.infrastructure.perisistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import cn.fyg.pa.domain.department.Department;
import cn.fyg.pa.domain.deptmonthplan.IdrMonthPlanBill;
import cn.fyg.pa.domain.deptmonthplan.IdrMonthPlanEnum;
import cn.fyg.pa.domain.deptmonthplan.IdrTask;

@Repository
public class IdrMonthPlanBillDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public IdrMonthPlanBill find(Long id){
		return entityManager.find(IdrMonthPlanBill.class, id);
	}
	

	public IdrMonthPlanBill save(IdrMonthPlanBill idrMonthPlanBill) {
		for(IdrTask idrTask:idrMonthPlanBill.getIdrTasks()){
			idrTask.setIdrTaskBill(idrMonthPlanBill);
			if(idrTask.getContext()!=null){
				idrTask.setContext(idrTask.getContext().trim());
			}
			if(idrTask.getSummary()!=null){
				idrTask.setSummary(idrTask.getSummary().trim());
			}
		}
		if(idrMonthPlanBill.getId()==null){
			return create(idrMonthPlanBill);
		}
		return update(idrMonthPlanBill);
	}
	
	private IdrMonthPlanBill create(IdrMonthPlanBill idrMonthPlanBill) {
		entityManager.persist(idrMonthPlanBill);
		return idrMonthPlanBill;
	}

	private IdrMonthPlanBill update(IdrMonthPlanBill idrMonthPlanBill) {
		IdrMonthPlanBill oldIdrMonthPlanBill= entityManager.find(IdrMonthPlanBill.class,idrMonthPlanBill.getId());
		Set<Long> idrTaskIds=new HashSet<Long>();
		for (IdrTask idrTask : idrMonthPlanBill.getIdrTasks()) {
			idrTaskIds.add(idrTask.getId());
		}
		for(IdrTask idrTask :oldIdrMonthPlanBill.getIdrTasks()){
			if(!idrTaskIds.contains(idrTask.getId())){
				entityManager.remove(idrTask);
			}
		}
		return entityManager.merge(idrMonthPlanBill);
	}

	public IdrMonthPlanBill findMaxMonthIdrMonthPlanBill(Department department) {
		CriteriaBuilder builder=entityManager.getCriteriaBuilder();
		CriteriaQuery<IdrMonthPlanBill> query=builder.createQuery(IdrMonthPlanBill.class);
		Root<IdrMonthPlanBill> root=query.from(IdrMonthPlanBill.class);
		query.select(root);
		query.where(builder.equal(root.get("department"), department));
		query.orderBy(builder.desc(root.get("year")),builder.desc(root.get("month")));
		List<IdrMonthPlanBill> retList=entityManager.createQuery(query).setMaxResults(1).getResultList();
		return retList.isEmpty()?null:retList.get(0);
	}

	public List<IdrMonthPlanBill> findByPeriodAndDepartmentAndState(Long year,Long month,Department[] department,IdrMonthPlanEnum... states){
		CriteriaBuilder builder=entityManager.getCriteriaBuilder();
		CriteriaQuery<IdrMonthPlanBill> query=builder.createQuery(IdrMonthPlanBill.class);
		Root<IdrMonthPlanBill> root=query.from(IdrMonthPlanBill.class);
		query.select(root);
		List<Predicate> criteria=new ArrayList<Predicate>();
		if(year!=null){
			criteria.add(builder.equal(root.get("year"), year));
		}
		if(month!=null){
			criteria.add(builder.equal(root.get("month"), month));
		}
		if(department!=null&&department.length>0){
			criteria.add(root.get("department").in((Object[])department));
		}
		if(states!=null&&states.length>0){
			criteria.add(root.get("state").in((Object[])states));
		}
		
		if(criteria.size()==1){
			query.where(criteria.get(0));
		}else{
			query.where(builder.and(criteria.toArray(new Predicate[0])));
		}
		query.orderBy(builder.desc(root.get("year")),
				builder.desc(root.get("month")),
				builder.asc(root.get("department")));
		return entityManager.createQuery(query).getResultList();
		
	}

}
