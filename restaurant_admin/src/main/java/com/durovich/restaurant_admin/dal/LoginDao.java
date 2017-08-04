package com.durovich.restaurant_admin.dal;

import java.io.Serializable;
import java.util.List;

public interface LoginDao extends CrudDao {
	boolean login(String username,String password);
}
