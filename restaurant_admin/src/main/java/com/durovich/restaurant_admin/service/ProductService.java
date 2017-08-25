package com.durovich.restaurant_admin.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.durovich.restaurant_admin.dal.CrudDao;
import com.durovich.restaurant_admin.entity.CurrencyExchange;
import com.durovich.restaurant_admin.entity.Order;
import com.durovich.restaurant_admin.entity.Product;

@Service
public class ProductService {
	@Autowired
	@Qualifier("crudDao")
	private CrudDao productDao;

	public void addProduct(Product product) {
		productDao.merge(product);
	}

	public List<Product> getAllProducts() {
		return productDao.list(Product.class);
	}

	public void deleteProduct(Product p) {
		productDao.delete(Product.class, p.getId());
	}
	@Transactional
	public Order addOrder(Order o){
		return productDao.merge(o);
	}
	@Transactional
	public void del(long id){
		 productDao.delete(Order.class, id);
	}

	public void updateProduct(Product product) {
		productDao.merge(product);
		
	}

	public List<Product> getAllEnableProducts() {
		List<Product> list=productDao.list(Product.class);
		list=list.stream().filter(p->p.isEnabled()).collect(Collectors.toList());
		return list;
	}
}
