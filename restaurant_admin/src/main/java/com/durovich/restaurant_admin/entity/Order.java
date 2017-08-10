package com.durovich.restaurant_admin.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity(name = "order_table")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private Double totalAmount;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "position_id")
	private List<OrderPosition> positions;
	@Column
	private int tableNumber;
	@Column
	private String paymentType;
	@Column
	private Date date=new Date();
	@Column
	private String status="Pending";

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public int getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public String getBill() {
		String bill = "Order :" + id + "\nTable :" + tableNumber + "\n\n";
		for (OrderPosition o : positions)
			bill += o.getProduct().getName() + "   Count:" + o.getCount() + "   Price:" + o.getCost() + "$\n";
		bill += "\nPayment Type:" + paymentType + "\n\n";

		bill += "Thanks you for choosing us!";
		return bill;
	}

}
