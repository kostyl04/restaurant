package com.durovich.restaurant_admin.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name = "order_table")
public class Order {
	@Id
	@GeneratedValue
	private long id;
	@Column
	private Double totalAmount;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private List<OrderPosition> positions;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<OrderPosition> getPositions() {
		return positions;
	}

	public void setPositions(List<OrderPosition> positions) {
		this.positions = positions;
	}

}
