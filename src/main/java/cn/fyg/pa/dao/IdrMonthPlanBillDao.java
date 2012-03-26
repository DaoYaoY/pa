package cn.fyg.pa.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import cn.fyg.pa.model.Department;
import cn.fyg.pa.model.IdrMonthPlanBill;
import cn.fyg.pa.model.IdrTask;
import cn.fyg.pa.model.enums.IdrMonthPlanEnum;

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

	public IdrMonthPlanBill getMaxMonthIdrMonthPlanBill(Department department) {
		CriteriaBuilder builder=entityManager.getCriteriaBuilder();
		CriteriaQuery<IdrMonthPlanBill> query=builder.createQuery(IdrMonthPlanBill.class);
		Root<IdrMonthPlanBill> root=query.from(IdrMonthPlanBill.class);
		query.select(root);
		query.where(builder.equal(root.get("department"), department));
		query.orderBy(builder.desc(root.get("year")),builder.desc(root.get("month")));
		List<IdrMonthPlanBill> ret=entityManager.createQuery(query).setMaxResults(1).getResultList();
		if(ret.isEmpty()){
			return null;
		}
		return ret.get(0);
	}


	public List<IdrMonthPlanBill> findBillByState(IdrMonthPlanEnum... states) {
		CriteriaBuilder builder=entityManager.getCriteriaBuilder();
		CriteriaQuery<IdrMonthPlanBill> query=builder.createQuery(IdrMonthPlanBill.class);
		Root<IdrMonthPlanBill> root=query.from(IdrMonthPlanBill.class);
		query.select(root);
		if(states.length>0){
			query.where(root.get("state").in((Object[])states));
		}
		query.orderBy(builder.desc(root.get("year")),builder.desc(root.get("month")));
		return entityManager.createQuery(query).getResultList();
	}



	
}
