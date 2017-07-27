package com.durovich.restaurant_admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class OrderPosition {
	@Id
	@GeneratedValue
	private long id;
	@OneToOne
	@MapsId
	private Product product;

	@Column
	private int count;
	@Column
	private Double cost;
	@Transient
	private Double costPerOne;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	
	public Double getCostPerOne() {
		return costPerOne;
	}

	public void setCostPerOne(Double costPerOne) {
		this.costPerOne = costPerOne;
	}

}
