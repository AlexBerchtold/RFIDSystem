package py.edu.facitec.rfidsystem.dao;

import java.util.List;

import javax.persistence.criteria.Join;

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
	
	public List<Puerta> buscadorPuertaPorOficina(Integer id) {
		instanciarCriteria();
		Join<Object, Object> joinPuerta1=root.join("oficina");
		criteriaQuery.where(builder.or(builder.equal(joinPuerta1.<Integer>get("id"), id)));
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}
	
	public List<Puerta> filtroPorOficina(String desde, String hasta, String order) {
		Integer d =0, h =0;
		try {
			d= Integer.parseInt(desde);
			h= Integer.parseInt(hasta);
		} catch (NumberFormatException e) {
		}
		instanciarCriteria();
		Join<Object, Object> joinPuerta1=root.join("oficina");
		criteriaQuery.where(
				builder.or(
						builder.between(joinPuerta1.<String>get("descripcion"), desde.toLowerCase()+"%", hasta.toLowerCase()+"zzzzz%"),
						builder.between(root.<Integer>get("numeroDePuerta"), d, h)));
		if (order=="descripcion") {
			criteriaQuery.orderBy(builder.asc(joinPuerta1.<Integer>get(order)));
		}else{
			criteriaQuery.orderBy(builder.asc(root.<Integer>get(order)));
		}
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}
	
	

}
