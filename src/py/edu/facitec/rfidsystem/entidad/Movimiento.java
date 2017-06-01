package py.edu.facitec.rfidsystem.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="Movimiento")
public class Movimiento {
	@Id
	@GenericGenerator(name="mov_generator",strategy="increment")
	@GeneratedValue(generator="mov_generator")
	@Column(name="Id", unique=true)
	private Integer id;
	
	@Column(name="Hora", nullable=false)
	private Date hora;
	
	@ManyToOne
	@JoinColumn(name="PermisoAccesoid", referencedColumnName="id")
	private PermisoAcceso permisoAcceso;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public PermisoAcceso getPermisoAcceso() {
		return permisoAcceso;
	}

	public void setPermisoAcceso(PermisoAcceso permisoAcceso) {
		this.permisoAcceso = permisoAcceso;
	}
	
	

}
