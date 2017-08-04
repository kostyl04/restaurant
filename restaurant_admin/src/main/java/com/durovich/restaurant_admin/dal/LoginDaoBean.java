package com.durovich.restaurant_admin.dal;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class LoginDaoBean extends AbstractDaoBean implements LoginDao {

	@Override
	public boolean login(String username, String password) {
		Query query = currentSession().createQuery("from User u where u.username=:username and u.password=:password");
		query.setParameter("username", username);
		query.setParameter("password", password);
		if (query.getResultList().isEmpty())
			return false;
		return true;
	}

}
