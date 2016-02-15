package com.atm.servletCapture;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import com.appstechs.beans.Employee;

public class CreateEmployee {

	static Session session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();
	static Transaction t = session.beginTransaction();

	public static void main(String[] args) {

		int number = 2;

		ReadRows(number);

	}

	private static void ReadRows(int number) {
		String hql = "FROM Employee E WHERE E.P_Id = 2";
		Query query = session.createQuery(hql);
		List results = query.list();
		Iterator its = results.iterator();
		while (its.hasNext()) {
			String name = "Moksha";
			Employee o = (Employee) its.next();
			if (o.getFirstName().equals(name)) {
				System.out.println("row not inserted");
				session.close();
				
			} else {
				createRow();
				System.out.println("row inserted");
			}
		}

	}

	private static void createRow() {
		Employee e = new Employee();
		e.setFirstName("Moksha");
		e.setLastName("Pasunoori");

		session.merge(e);
		t.commit();
		session.close();

		System.out.println("Account Saved!");

	}

}
