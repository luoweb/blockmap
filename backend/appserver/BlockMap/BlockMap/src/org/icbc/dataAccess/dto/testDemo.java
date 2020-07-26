package org.icbc.dataAccess.dto;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

public class testDemo {
	@Test
	public void testExample() {
		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UserDto userdto = new UserDto();
		userdto.setName("mary");
		userdto.setPassword("123456");
		userdto.setAdress("beijing");
		session.save(userdto);
		tx.commit();
		session.close();
		sessionFactory.close();
	}
}
