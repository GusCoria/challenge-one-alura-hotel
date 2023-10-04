package mx.com.hotelAlura.modelos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="huesped")
public class Huesped {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_huesped;
	private String nombre;
	private String apellido;
	private Date fecha_nacimiento;
	private String nacionalidad;
	private String telefono;
	private int id_reserva;
	
	public Huesped() {
	}
	
	public int getIdReserva() {
		return this.id_reserva;
	}

	public void setIdReserva(int idReserva) {
		this.id_reserva = idReserva;
	}
	
	public int getId() {
		return this.id_huesped;
	}
//	public void setId(int id_huesped) {
//		this.id_huesped = id_huesped;
//	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Date getFechaNacimiento() {
		return this.fecha_nacimiento;
	}
	public void setFechaNacimiento(Date fecha_Nacimiento) {
		this.fecha_nacimiento = fecha_Nacimiento;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}