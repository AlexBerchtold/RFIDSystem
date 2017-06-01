package py.edu.facitec.rfidsystem.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="Oficina")
public class Oficina {
	@Id
	@GenericGenerator(name="ofi_generator",strategy="increment")
	@GeneratedValue(generator="ofi_generator")
	@Column(name="Id", unique=true)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="Bloqueid", referencedColumnName="id")
	private Bloque bloque;
	
	@Column(name="Descripcion", nullable=false )
	private String descripcion;
	
	@Column(name="Estado", nullable=false)
	private Boolean estado;
	
	@OneToMany(mappedBy="oficina")
	private List<Puerta> puertas;
	
	@OneToMany(mappedBy="oficina")
	private List<PermisoAcceso> permisoAccesos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Bloque getBloque() {
		return bloque;
	}

	public void setBloque(Bloque bloque) {
		this.bloque = bloque;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public List<Puerta> getPuertas() {
		return puertas;
	}

	public void setPuertas(List<Puerta> puertas) {
		this.puertas = puertas;
	}

	public List<PermisoAcceso> getPermisoAccesos() {
		return permisoAccesos;
	}

	public void setPermisoAccesos(List<PermisoAcceso> permisoAccesos) {
		this.permisoAccesos = permisoAccesos;
	}
	
	

}
