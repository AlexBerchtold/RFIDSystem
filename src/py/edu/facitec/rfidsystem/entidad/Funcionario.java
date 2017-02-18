package py.edu.facitec.rfidsystem.entidad;


import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="Funcionario")
public class Funcionario {
	
	@Id
	@Column(name="Id", unique=true)
	private Integer id;
	
	@Column(name="NoDocumento", unique=true, nullable=false)
	private Integer noDocumento;
	
	@Column(name="Nombre", nullable=false, length=255)
	private String nombre;
	
	@Column(name="Apellido", nullable=false, length=255)
	private String apellido;
	
	@Column(name="FechaNacimiento", nullable=false)
	private Date fechaNacimiento;
	
	@Column(name="Sexo", nullable=false, length=1)
	private String sexo;
	
	@Column(name="FechaIncorporacion", nullable=false)
	private Date fechaIncorporacion;
	
	@Column(name="Tarjeta", nullable=false, unique=true, length=255)
	private String tarjeta;
	
	@OneToMany(mappedBy="funcionario")
	private List<PermisoAcceso> permisoAccesos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNoDocumento() {
		return noDocumento;
	}

	public void setNoDocumento(Integer noDocumento) {
		this.noDocumento = noDocumento;
	}

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
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getFechaIncorporacion() {
		return fechaIncorporacion;
	}

	public void setFechaIncorporacion(Date fechaIncorporacion) {
		this.fechaIncorporacion = fechaIncorporacion;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}

	public List<PermisoAcceso> getPermisoAccesos() {
		return permisoAccesos;
	}

	public void setPermisoAccesos(List<PermisoAcceso> permisoAccesos) {
		this.permisoAccesos = permisoAccesos;
	}
	
	

}
