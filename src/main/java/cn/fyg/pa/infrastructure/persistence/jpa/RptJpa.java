package cn.fyg.pa.infrastructure.persistence.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class RptJpa {

	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * 计算部门经理评价中的得分
	 * 算法解释：
	 *    sum(a.val*b.point)/5： val为部门经理评价的等级，每个等级分五档。point为项目的分数（不同项目分值权重不同）
	 *    除以5代表分五个档次
	 *    连接部门显示参与评价的人员
	 * @param year
	 * @return
	 */
	public List<Object[]> getCheckPoint(Long year) {
		String sql="select  x.id,x.name,x.department,y.realpt from fyperson x," +
				"(select a.personid,sum(a.val*b.point)/5 as realpt " +
				"from fychkmange a,fychkitem b where a.itemid=b.id and a.year=:year group by a.personid) y " +
				"where x.id=y.personid order by id asc";
		@SuppressWarnings("unchecked")
		List<Object[]> resultList=entityManager.createNativeQuery(sql).setParameter("year", year).getResultList();
		return resultList;
	}
	
	
	/**
	 * 计算员工评价中的得分
	 *  算法解释：
	 *  	fycheck表中一条记录对应两次评价，所以用union all来做两次查询
	 *  	val保存时为1 0 -1三个值 表示胜平负，计算时则用2 1 0 三个值表示得分
	 *  	总得分的计算方法为  实际得分/实际能得到的最高分*100
	 *  	实际公式为：sum(val)/{count(personid)*2}*100   count(personid)意思是一个人被评比的次数，乘以2代表胜的话可以得2分 
	 * @param year
	 * @return
	 */
	public List<Object[]> getVal(Long year){		
		String sql="select personid,sum(val)/count(personid)/2*100 from (" +
				"select colid as personid, val+1 as val from fycheck where year=:year " +
				"union all " +
				"select rowid as personid,-val+1 as val from fycheck where year=:year " +
				")temp group by personid";
		@SuppressWarnings("unchecked")
		List<Object[]> resultList=entityManager.createNativeQuery(sql).setParameter("year", year).getResultList();
		return resultList;
	}
	
}
