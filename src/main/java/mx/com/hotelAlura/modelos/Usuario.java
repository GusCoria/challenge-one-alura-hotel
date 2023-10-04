package mx.com.hotelAlura.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {
	@Id
	private Integer id;
	private String nombre;
	private String password;
	
	public Usuario() {
		
	}
	public int getId() {
		return id;
	}
//	public void setId(int id) {
//		this.id = id;
//	}
//	public String getNombre() {
//		return nombre;
//	}
//	public void setNombre(String nombre) {
//		this.nombre = nombre;
//	}
	public String getPassword() {
		return password;
	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
}