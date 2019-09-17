package model.dao;

import java.util.List;

import db.DB;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;

public class DaoFactory {
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	/*/public static DepartmentDao crateDepartmentDao() {
		return new DepartmentDao() 
	}*/
}
