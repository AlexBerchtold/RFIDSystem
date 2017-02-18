package py.edu.facitec.rfidsystem.entidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="PermisoAcceso")
public class PermisoAcceso {
	@Id
	@Column(name="Id", unique=true)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="Oficinaid", referencedColumnName="id")
	private Oficina oficina;
	
	@ManyToOne
	@JoinColumn(name="Funcinarioid", referencedColumnName="id")
	private Funcionario funcionario;
	
	@ManyToOne
	@JoinColumn(name="Puertaid", referencedColumnName="id")
	private Puerta puerta;
	
	@OneToMany(mappedBy="permisoAcceso")
	private List<Movimiento> movimientos;

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

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Puerta getPuerta() {
		return puerta;
	}

	public void setPuerta(Puerta puerta) {
		this.puerta = puerta;
	}

	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	
	

}
