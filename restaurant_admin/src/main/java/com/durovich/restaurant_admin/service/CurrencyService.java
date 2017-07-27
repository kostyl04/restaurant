package com.durovich.restaurant_admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.durovich.restaurant_admin.dal.CrudDao;
import com.durovich.restaurant_admin.entity.CurrencyExchange;
import com.durovich.restaurant_admin.entity.Product;

@Service
public class CurrencyService {

	@Autowired
	@Qualifier("crudDao")
	private CrudDao currencyDao;

	public void addCurrency(CurrencyExchange curr) {
		currencyDao.merge(curr);
	}

	public List<CurrencyExchange> getAllCurrencies() {
		return currencyDao.list(CurrencyExchange.class);
	}

	public void deleteCurrency(CurrencyExchange curr) {
		currencyDao.delete(CurrencyExchange.class, curr.getId());
	}
}
