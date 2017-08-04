package com.durovich.restaurant_admin.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name = "order_table")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column
	private Double totalAmount;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name = "position_id")
	private List<OrderPosition> positions;
	@Column
	private int tableNumber;
	@Column
	private String paymentType;
	

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
	
	public String getBill() {
		String bill="Order №"+id+"\nTable №"+tableNumber+"\n\n";
		for(OrderPosition o:positions)
			bill+=o.getProduct().getName()+"   Count:"+o.getCount()+"   Price:"+o.getCost()+"$\n";
		bill+="\nPayment Type:"+paymentType+"\n\n";
		
		bill+="Thanks you for choosing us!";
		return bill;
	}

}
