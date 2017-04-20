package py.edu.facitec.rfidsystem.dao;

import java.util.List;

import org.hibernate.criterion.Order;

import py.edu.facitec.rfidsystem.entidad.Funcionario;

public class FuncionarioDao extends GenericDao<Funcionario> {

	public FuncionarioDao() {
		super(Funcionario.class);
	}
	
	public List<Funcionario> recuperarPorFiltro(String filtro){
		instanciarCriteria();
		criteriaQuery.where(
				builder.or(
						builder.like(builder.lower(root.<String>get("nombre")), "%"+filtro.toLowerCase()+"%"),
						builder.like(builder.lower(root.<String>get("apellido")), "%"+filtro.toLowerCase()+"%"),
						builder.like(builder.lower(root.<String>get("tarjeta")), "%"+filtro.toLowerCase()+"%")
						));
		lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}
	
	public List<Funcionario> filtroListadoNombre(String desde, String hasta, String order){
		instanciarCriteria();
		criteriaQuery.where(
				builder.or(
						builder.between(builder.lower(root.<String>get("nombre")), desde.toLowerCase()+"%", hasta.toLowerCase()+"%")
				));
		criteriaQuery.orderBy(builder.asc(root.<Integer>get(order.toLowerCase())));
		lista=session.createQuery(criteriaQuery).getResultList();
		return lista;
	}
	
	public List<Funcionario> filtroListadoId(Integer d, Integer h, String order){
		instanciarCriteria();
		criteriaQuery.where(
				builder.or(
						builder.between(root.<Integer>get("id"), d, h)
				));
		criteriaQuery.orderBy(builder.asc(root.<Integer>get(order.toLowerCase())));
		lista=session.createQuery(criteriaQuery).getResultList();
		return lista;
	}

}
