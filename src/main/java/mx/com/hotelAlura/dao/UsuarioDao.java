package mx.com.hotelAlura.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import mx.com.hotelAlura.modelos.Usuario;

public class UsuarioDao {

	private EntityManager em;

	public UsuarioDao(EntityManager em) {
		super();
		this.em = em;
	}
	
//	public void guardar(Usuario usuario) {
//		this.em.persist(usuario);
//	}
//	
//	public void  update(Usuario usuario) {
//		em.merge(usuario);
//	}
//	
//
//	public Usuario buscarPorId(int id) {
//        return em.find(Usuario.class, id);
//    }
//	public void eliminar(Usuario usuario) {
//	    em.remove(usuario);
//	}

	public boolean checkCredentials(String usua, String password) {
	    String jpql = "SELECT usu FROM Usuario usu WHERE usu.nombre = :usua";
	    TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class)
	            .setParameter("usua", usua);

	    List<Usuario> listaUsuarios = query.getResultList();

	    if (!listaUsuarios.isEmpty()) {
	        Usuario user = listaUsuarios.get(0);
	        String contra = user.getPassword();
	        return contra.equals(password);
	    }
	    return false;
	}

}