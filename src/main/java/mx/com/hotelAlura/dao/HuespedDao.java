package mx.com.hotelAlura.dao;
import java.util.List;

import javax.persistence.EntityManager;

import mx.com.hotelAlura.modelos.Huesped;


public class HuespedDao {

	private EntityManager em;

	public HuespedDao(EntityManager em) {
		super();
		this.em = em;
	}
	
	public void guardar(Huesped huesped) {
		this.em.persist(huesped);
	}
	

	public void  update(Huesped huesped) {
		em.merge(huesped);
	}
	
	public Huesped buscarPorId(int id) {
        return em.find(Huesped.class, id);
    }

    public void eliminar(Huesped huesped) {
        em.remove(huesped);
    }
    
    public List<Huesped> getAll(){
    	String cuerry = "SELECT h FROM Huesped h"; 
    	return em.createQuery(cuerry, Huesped.class).getResultList();
    	
    }
    
    public List<Huesped> consultaPorColumnas(String busqueda){
        String jpql = "SELECT H FROM Huesped H WHERE H.nombre LIKE :busqueda " +
                      "OR H.apellido LIKE :busqueda " +
                      "OR H.nacionalidad LIKE :busqueda " +
                      "OR H.id LIKE :busqueda " +
                      "OR H.telefono LIKE :busqueda";
        return em.createQuery(jpql, Huesped.class)
                 .setParameter("nombre", "%" + busqueda + "%").getResultList();
    }

}