package com.durovich.restaurant_admin.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.durovich.restaurant_admin.dal.CrudDao;
import com.durovich.restaurant_admin.entity.CurrencyExchange;
import com.durovich.restaurant_admin.entity.Order;
import com.durovich.restaurant_admin.entity.Product;

@Service
public class OrdersService {
	@Autowired
	@Qualifier("crudDao")
	private CrudDao cruddao;

	public List<Order> getAllOrders() {

		return cruddao.list(Order.class);

	}

	public void updateStatus(Order o) {
		cruddao.merge(o);
	}
}
