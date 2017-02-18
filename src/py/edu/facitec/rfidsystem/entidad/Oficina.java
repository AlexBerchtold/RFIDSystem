package py.edu.facitec.rfidsystem.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="Oficina")
public class Oficina {
	@Id
	@Column(name="Id", unique=true)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="Bloqueid", referencedColumnName="id")
	private Bloque bloque;
	
	@Column(name="Oficina", nullable=false )
	private String descripcionOficina;
	
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

	public String getDescripcionOficina() {
		return descripcionOficina;
	}

	public void setDescripcionOficina(String descripcionOficina) {
		this.descripcionOficina = descripcionOficina;
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
