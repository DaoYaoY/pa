package cn.fyg.pa.infrastructure.perisistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import cn.fyg.pa.domain.person.ManageEnum;
import cn.fyg.pa.domain.person.Person;
import cn.fyg.pa.domain.yearchk.Fycheck;
import cn.fyg.pa.domain.yearchk.YearChkRepositroy;
import cn.fyg.pa.interfaces.yearchk.PersonChkBean;

@Repository
public class YearChkRepositroyJpa implements YearChkRepositroy{
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 获得人员考核结果，用sql查询 XXX 能否修改？
	 * @return
	 */
	@Override
	public List<PersonChkBean> getPersonYearChkResult(Long year,Person person){
		String sql = "select person.id,person.name,person.department,ifnull(result.win,0) as win,ifnull(result.lose,0) as lost,ifnull(result.draw,0) as draw" +
				" from fyperson as person" +
				" left join (" +
				"   select personid,sum(win) as win,sum(draw) as draw,sum(lose) as lose from(" +
				"     select colid as personid,if(val=1,1,0) as win,if(val=0,1,0) as draw,if(val=-1,1,0) as lose  from fycheck where chkid=:personid and year=:year " +
				"     union all" +
				"     select rowid as personid,if(-val=1,1,0)as win,if(val=0,1,0) as draw,if(-val=-1,1,0) as lose from fycheck where chkid=:personid and year=:year " +
				") as temp group by personid ) as result on person.id=result.personid" +
				" left join department on person.department=department.name" +
				" where person.id!=:personid and person.manage=:manage and person.type=:type" +
				" order by department.id,person.id asc";
		Query query = entityManager.createNativeQuery(sql)
				.setParameter("personid", person.getId())
				.setParameter("manage", ManageEnum.N.toString())
				.setParameter("type", person.getType().toString())
				.setParameter("year", year);
		@SuppressWarnings("unchecked")
		List<Object[]> tempList=query.getResultList();
		List<PersonChkBean> retList=new ArrayList<PersonChkBean>();
		for(Object[] obj:tempList){
			PersonChkBean yearChkBean=new PersonChkBean();
			yearChkBean.setId(((Integer)obj[0]).intValue());
			yearChkBean.setName((String)obj[1]);
			yearChkBean.setDepartment((String)obj[2]);
			yearChkBean.setWin(((BigDecimal)obj[3]).intValue());
			yearChkBean.setLose(((BigDecimal)obj[4]).intValue());
			yearChkBean.setDraw(((BigDecimal)obj[5]).intValue());
			retList.add(yearChkBean);
		}
		return retList;
	}

	@Override
	public List<Fycheck> getPersonYearChkAboutPerson(Long year,Person aboutPerson, Person chkPerson) {
		// TODO Auto-generated method stub 实现person查询
		return null;
	}
}
