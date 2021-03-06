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

@Entity(name="Puerta")
public class Puerta {
	@Id
	@GenericGenerator(name="pue_generator",strategy="increment")
	@GeneratedValue(generator="pue_generator")
	@Column(name="Id", unique=true)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="Oficinaid", referencedColumnName="id")
	private Oficina oficina;
	
	@Column(name="Descripcion", nullable=false, length=255)
	private String descripcion;
	
	@Column(name="NumeroDePuerta", nullable=false, unique=true)
	private Integer numeroDePuerta;
	
	@Column(name="Estado", nullable=false)
	private boolean estado;
	
	@OneToMany(mappedBy="puerta")
	private List<PermisoAcceso> permisoAccesos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getNumeroDePuerta() {
		return numeroDePuerta;
	}

	public void setNumeroDePuerta(Integer numeroDePuerta) {
		this.numeroDePuerta = numeroDePuerta;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public List<PermisoAcceso> getPermisoAccesos() {
		return permisoAccesos;
	}

	public void setPermisoAccesos(List<PermisoAcceso> permisoAccesos) {
		this.permisoAccesos = permisoAccesos;
	}
	
	

}
