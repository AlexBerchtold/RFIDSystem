package py.edu.facitec.rfidsystem.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="Institucion")
public class Institucion {
	@Id
	@Column(name="Id")
	private Integer id;
	
	@Column(name="Descripcion", length=255, nullable=false)
	private String descripcion;
	
	@OneToMany(mappedBy="institucion")
	private List<Bloque> bloques;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Bloque> getBloques() {
		return bloques;
	}

	public void setBloques(List<Bloque> bloques) {
		this.bloques = bloques;
	}
	
	

}
