package py.edu.facitec.rfidsystem.dao;

import java.util.List;

import javax.persistence.criteria.Join;

import py.edu.facitec.rfidsystem.entidad.PermisoAcceso;

public class PermisoAccesoDao extends GenericDao<PermisoAcceso> {

	public PermisoAccesoDao() {
		super(PermisoAcceso.class);
	}
	
	public List<PermisoAcceso> recuperarPorFiltro(String filtro){
		int filtroId = 0;
		try {
			filtroId = Integer.parseInt(filtro);
		} catch (NumberFormatException e) {
		}
		instanciarCriteria();
		Join<Object, Object> joinPermiso=root.join("funcionario");
		Join<Object, Object> joinAcceso=root.join("oficina");
		criteriaQuery.where(
				builder.or(
						builder.equal(root.<Integer>get("id"), filtroId),
						builder.like(builder.lower(joinPermiso.<String>get("nombre")), "%"+filtro.toLowerCase()+"%"),
						builder.like(builder.lower(joinAcceso.<String>get("descripcion")), "%"+filtro.toLowerCase()+"%")
						)
				);
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}
	
	public List<PermisoAcceso> filtroPorOficina(String desde, String hasta, int order, int filtro) {
		instanciarCriteria();
		Join<Object, Object> joinOficina=root.join("oficina");
		Join<Object, Object> joinFuncionario=root.join("funcionario");
		if (filtro==0) {
			criteriaQuery.where(
					builder.between(joinOficina.<String>get("descripcion"), desde.toLowerCase()+"%", hasta.toLowerCase()+"%"));
		}else{
			criteriaQuery.where(
					builder.between(joinFuncionario.<String>get("nombre"), desde.toLowerCase()+"%", hasta.toLowerCase()+"%"));
		}
		if (order==0) {
			criteriaQuery.orderBy(builder.asc(joinOficina.<Integer>get("descripcion")));
		}else{
			criteriaQuery.orderBy(builder.asc(joinFuncionario.<Integer>get("nombre")));
		}
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}

}
