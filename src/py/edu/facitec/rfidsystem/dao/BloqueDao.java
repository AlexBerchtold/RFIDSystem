package py.edu.facitec.rfidsystem.dao;

import java.util.List;

import py.edu.facitec.rfidsystem.entidad.Bloque;

public class BloqueDao extends GenericDao<Bloque> {

	public BloqueDao() {
		super(Bloque.class);
	}
	
	public List<Bloque> recuperarPorFiltro(String filtro){
		int filtroId = 0;
		try {
			filtroId = Integer.parseInt(filtro);
		} catch (NumberFormatException e) {
			
		}
		instanciarCriteria();
		criteriaQuery.where(
				builder.or(
						builder.like(builder.lower(root.<String>get("nombre")), "%"+filtro.toLowerCase()+"%"),
						builder.equal(root.<Integer>get("id"), filtroId))
				);
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}


}
