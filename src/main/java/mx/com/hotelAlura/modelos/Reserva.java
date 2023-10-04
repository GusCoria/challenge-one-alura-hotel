package mx.com.hotelAlura.modelos;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="reserva")
public class Reserva {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_reserva;
	private Date fecha_Entrada;
	private Date fecha_Salida;
	@Column(columnDefinition="DECIMAL(8,4)")
    private java.math.BigDecimal valor = new java.math.BigDecimal(0);
	private String forma_pago;

	public Reserva() {
	}
	
	public int getId() {
		return id_reserva;
	}
	
//	public void setId(int id_reserva) {
//		this.id_reserva = id_reserva;
//	}
	public Date getFechaEntrada() {
		return fecha_Entrada;
	}
	public void setFechaEntrada(Date fecha_Entrada) {
		this.fecha_Entrada = fecha_Entrada;
	}
	public Date getFechaSalida() {
		return fecha_Salida;
	}
	public void setFechaSalida(Date fecha_Salida) {
		this.fecha_Salida = fecha_Salida;
	}
	public java.math.BigDecimal getValor() {
		return valor;
	}
	public void setValor(java.math.BigDecimal valor) {
		this.valor = valor;
	}
	public String getFormaPago() {
		return forma_pago;
	}
	public void setFormaPago(String forma_pago) {
		this.forma_pago = forma_pago;
	}
}