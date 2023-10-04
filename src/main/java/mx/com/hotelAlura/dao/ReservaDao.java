package mx.com.hotelAlura.dao;


import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import mx.com.hotelAlura.modelos.Reserva;


public class ReservaDao {
	private EntityManager em;

	public ReservaDao(EntityManager em) {
		super();
		this.em = em;
	}
	
	public void guardar(Reserva reserva) {
		this.em.persist(reserva);
	}
	
	public void  update(Reserva reserva) {
		em.merge(reserva);
	}
	

	public Reserva buscarPorId(int id) {
        return em.find(Reserva.class, id);
    }
	public void eliminar(Reserva reserva) {
	    em.remove(reserva);
	}

	
	public List<Reserva> getAll(){
    	String cuerry = "SELECT r FROM Reserva r"; 
    	return em.createQuery(cuerry, Reserva.class).getResultList();
    	
    }
	
	public List<Reserva> consultaPorBusqueda(String entrada) {
		String jpql = "SELECT h FROM Reserva h WHERE " +
		                "(YEAR(h.fecha_Entrada) = :ano OR " +
		                "YEAR(h.fecha_Salida) = :ano OR " +
		                "h.valor = :valor OR " +
		                "h.forma_pago LIKE :formaPago OR " +
		                "CAST(h.id_reserva AS string) = :id)";
		  
		  TypedQuery<Reserva> query = em.createQuery(jpql, Reserva.class);
		  
		  int num = -1;
		  BigDecimal valor = null;
		  String formaPago = null;
		  String idString = null;
		  
		  try {
			  num = Integer.parseInt(entrada);
			  valor = new BigDecimal(entrada);
			  idString = entrada;
		} catch (NumberFormatException e) {
		}
		  query.setParameter("ano", num);
		  query.setParameter("valor", valor);
		  query.setParameter("formaPago", "%" + entrada + "%");
		  query.setParameter("id", idString);
		  
		  return query.getResultList();
	}





	

}