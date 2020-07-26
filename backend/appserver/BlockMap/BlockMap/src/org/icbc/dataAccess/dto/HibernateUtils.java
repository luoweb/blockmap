package org.icbc.dataAccess.dto;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	static Configuration cfg = null;
	static SessionFactory sessionFactory = null;
	static {
		cfg = new Configuration().configure();
		sessionFactory = cfg.buildSessionFactory();
		
	}
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
