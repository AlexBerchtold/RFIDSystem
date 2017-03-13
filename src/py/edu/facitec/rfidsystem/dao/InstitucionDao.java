package py.edu.facitec.rfidsystem.dao;

import java.util.List;

import py.edu.facitec.rfidsystem.entidad.Institucion;

public class InstitucionDao extends GenericDao<Institucion> {

	public InstitucionDao() {
		super(Institucion.class);
	}
	
	public List<Institucion> recuperarPorFiltro(String filtro){
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

}
