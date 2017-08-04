package com.durovich.restaurant_admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.durovich.restaurant_admin.dal.LoginDao;

@Service
public class LoginService {
	@Autowired
	private LoginDao logindao;

	public boolean login(String password, String username) {
		return logindao.login(username, password);

	}
}
