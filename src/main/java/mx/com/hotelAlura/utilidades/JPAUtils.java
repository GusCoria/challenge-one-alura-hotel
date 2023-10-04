package mx.com.hotelAlura.utilidades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {
	private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("hotelAlura");
	
	public static EntityManager getEntityManager() {
		return FACTORY.createEntityManager();
		
	}
}