package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
			
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("==== TEST1: seller findById ====");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		System.out.println();
		
		System.out.println("==== TEST2: department findByDepartmentId ====");
		Department dep = new Department (2, null);
		List<Seller> list = sellerDao.findByDepartment(dep);
		for(Seller obj : list) {
			System.out.println(obj);
		}
		System.out.println();
		
		System.out.println("==== TEST3: seller findAll ====");
		list = sellerDao.findAll();
		for(Seller obj : list) {
			System.out.println(obj);
		}
		System.out.println();
		
		System.out.println("==== TEST4: seller insert ====");
		Seller newseller = new Seller (null, "Greg", "greg@gmail.com", new Date(), 4000.0, dep);
		sellerDao.insert(newseller);
		System.out.println("Inserted! New id = " + newseller.getId());
		System.out.println();
		
		System.out.println("==== TEST5: seller UPDATE ====");
		seller = sellerDao.findById(1);
		seller.setName("Martha Waine");
		sellerDao.update(seller);
		System.out.println("Update Complete. ");
		System.out.println();
		
		System.out.println("==== TEST6: seller DELETE ====");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("DELETE complete!");
		sc.close();
	}

}
