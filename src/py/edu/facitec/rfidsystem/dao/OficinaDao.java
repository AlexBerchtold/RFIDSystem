package py.edu.facitec.rfidsystem.dao;

import java.util.List;

import py.edu.facitec.rfidsystem.entidad.Bloque;
import py.edu.facitec.rfidsystem.entidad.Oficina;

public class OficinaDao extends GenericDao<Oficina> {

	public OficinaDao() {
		super(Oficina.class);
	}
	
	public List<Oficina> recuperarPorFiltro(String filtro){
		int filtroId = 0;
		try {
			filtroId = Integer.parseInt(filtro);
		} catch (NumberFormatException e) {
			
		}
		instanciarCriteria();
		criteriaQuery.where(
				builder.or(
						builder.like(builder.lower(root.<String>get("descripcion")), "%"+filtro.toLowerCase()+"%"),
						builder.equal(root.<Integer>get("id"), filtroId))
				);
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}
	
	public List<Oficina> recuperarPorActivo(String filtro){
		int filtroId = 0;
		try {
			filtroId = Integer.parseInt(filtro);
		} catch (NumberFormatException e) {
			
		}
		instanciarCriteria();
		criteriaQuery.where(
				builder.or(
						builder.like(builder.lower(root.<String>get("descripcion")), "%"+filtro.toLowerCase()+"%"),
						builder.equal(root.<Integer>get("id"), filtroId)),
				        builder.isTrue(root.<Boolean>get("estado"))
				);
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}
	
	public List<Oficina> recuperarInactivo(String filtro){
		int filtroId = 0;
		try {
			filtroId = Integer.parseInt(filtro);
		} catch (NumberFormatException e) {
			
		}
		instanciarCriteria();
		criteriaQuery.where(
				builder.or(
						builder.like(builder.lower(root.<String>get("descripcion")), "%"+filtro.toLowerCase()+"%"),
						builder.equal(root.<Integer>get("id"), filtroId)),
				        builder.isFalse(root.<Boolean>get("estado"))
				);
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}
	


}
