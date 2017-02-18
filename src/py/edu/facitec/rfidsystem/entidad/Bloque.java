package py.edu.facitec.rfidsystem.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="Bloque")
public class Bloque {
	@Id
	@Column(name="Id", unique=true)
	private Integer id;
	
	@Column(name="Nombre", length=255, nullable=false)
	private String nombre;
	
	@ManyToOne
	@JoinColumn(name="Intitucionid", referencedColumnName="id")
	private Institucion institucion;
	
	@OneToMany(mappedBy="bloque")
	private List<Oficina> oficinas;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<Oficina> getOficinas() {
		return oficinas;
	}

	public void setOficinas(List<Oficina> oficinas) {
		this.oficinas = oficinas;
	}
	
	

}
