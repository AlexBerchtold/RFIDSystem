package py.edu.facitec.rfidsystem.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import py.edu.facitec.rfidsystem.util.Factory;

public class GenericDao <T>{
	protected Session session;
	private Class<T> entity;
	protected CriteriaBuilder builder;
	protected CriteriaQuery<T> criteriaQuery;
	protected Root<T> root;
	protected List<T> lista;
	
	public GenericDao(Class<T> entity) {
		this.entity = entity;
		session = Factory.getSessionFactory().openSession();
	}
	
	public void insertarOModificar(T entity){
		if(entity == null){
			System.out.println("entidad nula");
		}
		session.beginTransaction();
		session.saveOrUpdate(entity);
	}
	
	public void eliminar(T entity){
		session.beginTransaction();
		session.delete(entity);
	}
	
	public T recuperarPorId(int id){
		T result = session.get(entity, id);
		cerrar();
		return result;
	}
	
	public List<T> recuperarTodo(){
		String hql = "FROM "+entity.getName()+" ORDER BY id";
		Query query = session.createQuery(hql);
		lista = query.getResultList();
		//criteriaQuery.orderBy(builder.asc(root.get("id")));
		//lista = session.createQuery(criteriaQuery).getResultList();
		cerrar();
		return lista;
	}
	
	public void ejecutar() throws Exception{
		session.flush();
		session.getTransaction().commit();
		cerrar();
	}
	
	public void cancelar(){
		session.getTransaction().rollback();
		session.clear();
		cerrar();
	}
	
	public void cerrar(){
		session.close();
	}
	
	protected void instanciarCriteria() {
		//instanciar los objetos necesarios para utiilizar criteria
		builder = session.getCriteriaBuilder();
		criteriaQuery = builder.createQuery(entity);
		root = criteriaQuery.from( entity );
		criteriaQuery.select( root );
	}
	
}
