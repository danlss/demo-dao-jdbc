package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");

			st.setInt(1, id);

			// traz os resultados em forma de linhas de tabela
			rs = st.executeQuery();

			// caso contenha dados [retornou uma linha do executequery]
			if (rs.next()) {

				Department dep = instantiateDepartment(rs);

				Seller obj = instatiateSeller(rs, dep);
				return obj;
			}
			// nao existe nenhum vendedor com esse id
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "ORDER BY Name");


			// traz os resultados em forma de linhas de tabela
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();

			// caso contenha dados [retornou uma linha do executequery]
			while (rs.next()) {
				// testar se dep existe dentro do map, caso nao exista map retorna null
				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					//caso id dep nao tenha sido instanciado ainda
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
					
				}
				Seller obj = instatiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
			// nao existe nenhum vendedor com esse id
			//return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");

			st.setInt(1, department.getId());

			// traz os resultados em forma de linhas de tabela
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();

			// caso contenha dados [retornou uma linha do executequery]
			while (rs.next()) {

				// testar se dep existe dentro do map, caso nao exista map retorna null
				Department dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					//caso id dep nao tenha sido instanciado ainda
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
					
				}
				Seller obj = instatiateSeller(rs, dep);
				list.add(obj);
				
			}
			// nao existe nenhum vendedor com esse id
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

}
