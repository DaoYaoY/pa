package cn.fyg.pa.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import cn.fyg.pa.domain.model.enums.MonthChkEnum;
import cn.fyg.pa.domain.person.Person;

@Entity
public class MonthChk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

	private Long year;
	
	private Long month;

	@Enumerated(EnumType.STRING)
	private MonthChkEnum state;

	@OneToMany(mappedBy = "monthChk",
			fetch = FetchType.EAGER, 
			cascade = {CascadeType.ALL},
			targetEntity = MonthChkItem.class)
	@OrderBy("sn ASC")
	private List<MonthChkItem> monthChkItems=new ArrayList<MonthChkItem>();
	
	//TODO 重构
	/**
	 * 减去另外一个明细集合
	 * @param anotherMonthChkItems
	 * @return 剩余的集合
	 */
	public void subtractMonthChkItemsById(List<MonthChkItem> anotherMonthChkItems){
		List<Long> anotherItemIds=new ArrayList<Long>();
		for(MonthChkItem item:anotherMonthChkItems){
			if (item.getId() != null) {
				anotherItemIds.add(item.getId());
			}
		}
		Iterator<MonthChkItem> iterator=monthChkItems.iterator();
		while(iterator.hasNext()){
			MonthChkItem item=iterator.next();
			if(anotherItemIds.contains(item.getId())){
				iterator.remove();
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
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

	public MonthChkEnum getState() {
		return state;
	}

	public void setState(MonthChkEnum state) {
		this.state = state;
	}

	public List<MonthChkItem> getMonthChkItems() {
		return monthChkItems;
	}

	public void setMonthChkItems(List<MonthChkItem> monthChkItems) {
		this.monthChkItems = monthChkItems;
	}
	
	public void next() throws StateChangeException{
		this.state.setMonthChk(this);
		this.state.next();
	}
	
	public void back() throws StateChangeException{
		this.state.setMonthChk(this);
		this.state.back();
	}

}
