package com.durovich.restaurant_admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class CurrencyExchange {
	@Id
	@GeneratedValue
	private long id;
	@Column
	private String name;
	@Column
	private Double rate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	@Override
	public String toString() {
		return  name ;
	}
	
	
}