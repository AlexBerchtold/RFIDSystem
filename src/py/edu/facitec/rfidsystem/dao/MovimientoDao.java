package py.edu.facitec.rfidsystem.dao;

import java.util.List;

import javax.persistence.criteria.Join;

import py.edu.facitec.rfidsystem.entidad.Movimiento;

public class MovimientoDao extends GenericDao<Movimiento> {

	public MovimientoDao() {
		super(Movimiento.class);
	}
	
	public List<Movimiento> recuperarPorFiltro(String filtro){
		int filtroId = 0;
		try {
			filtroId = Integer.parseInt(filtro);
		} catch (NumberFormatException e) {
			
		}
		instanciarCriteria();
		Join<Object, Object> joinPermiso=root.join("permisoAcceso");
		Join<Object, Object> joinFuncionario=joinPermiso.join("funcionario");
		criteriaQuery.where(
				builder.or(
						builder.equal(root.<Integer>get("id"), filtroId),
						builder.like(builder.lower(joinFuncionario.<String>get("nombre")),"%"+filtro.toLowerCase()+"%"))
				);
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}

}
