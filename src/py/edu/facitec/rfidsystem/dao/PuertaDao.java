package py.edu.facitec.rfidsystem.dao;

import java.util.List;

import org.apache.commons.collections.functors.IfClosure;

import py.edu.facitec.rfidsystem.entidad.Puerta;

public class PuertaDao extends GenericDao<Puerta> {

	public PuertaDao() {
		super(Puerta.class);
	}
	
	public List<Puerta> recuperarPorFiltro(String filtro){
		int filtroId = 0;
		try {
			filtroId = Integer.parseInt(filtro);
		} catch (NumberFormatException e) {
			
		}
		instanciarCriteria();
		criteriaQuery.where(
				builder.or(
						builder.like(builder.lower(root.<String>get("descripcion")), "%"+filtro.toLowerCase()+"%"),
						builder.equal(root.<Integer>get("numeroDePuerta"), filtroId),
						builder.equal(root.<Integer>get("id"), filtroId))
				);
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}
	
	public List<Puerta> recuperarActivo(String filtro){
		int filtroId = 0;
		try {
			filtroId = Integer.parseInt(filtro);
		} catch (NumberFormatException e) {
			
		}
		instanciarCriteria();
		criteriaQuery.where(
				builder.or(
						builder.like(builder.lower(root.<String>get("descripcion")), "%"+filtro.toLowerCase()+"%"),
						builder.equal(root.<Integer>get("numeroDePuerta"), filtroId),
						builder.equal(root.<Integer>get("id"), filtroId)),
				        builder.isTrue(root.<Boolean>get("estado"))
				);
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}
	
	public List<Puerta> recuperarInactivo(String filtro){
		int filtroId = 0;
		try {
			filtroId = Integer.parseInt(filtro);
		} catch (NumberFormatException e) {
			
		}
		instanciarCriteria();
		criteriaQuery.where(
				builder.or(
						builder.like(builder.lower(root.<String>get("descripcion")), "%"+filtro.toLowerCase()+"%"),
						builder.equal(root.<Integer>get("numeroDePuerta"), filtroId),
						builder.equal(root.<Integer>get("id"), filtroId)),
				        builder.isFalse(root.<Boolean>get("estado"))
				);
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}
	
	

}
