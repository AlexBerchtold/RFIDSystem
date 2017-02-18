package py.edu.facitec.rfidsystem.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Factory {
	private static SessionFactory sessionFactory;
	
	public static void setUp(){
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
			.configure()
			.build();
		
		try {
			sessionFactory = new MetadataSources(registry)
				.buildMetadata()
				.buildSessionFactory();
		} catch (Exception e) {
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
		}
	}
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			setUp();
		}
		return sessionFactory;
	}
}
