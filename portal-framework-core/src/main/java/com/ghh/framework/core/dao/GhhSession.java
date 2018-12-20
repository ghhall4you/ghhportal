package com.ghh.framework.core.dao;

import java.io.Serializable;

import org.hibernate.LobHelper;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/*****************************************************************
 *
 * 自定义Session
 *
 * @author ghh
 * @date 2018年12月19日下午10:27:33
 * @since v1.0.1
 ****************************************************************/
@Repository("ghhSession")
public final class GhhSession {

	@Autowired
	private SessionFactory sessionFactory;

	private GhhSession() {
	}

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	Session openNewSession() {
		return this.sessionFactory.openSession();
	}

	public Query createQuery(String queryString) {
		return getSession().createQuery(queryString);
	}

	public SQLQuery createSQLQuery(String queryString) {
		return getSession().createSQLQuery(queryString);
	}

	public void delete(Object object) {
		getSession().delete(object);
	}

	public void flush() {
		getSession().flush();
	}

	public Object get(Class<?> clazz, Serializable id) {
		return getSession().get(clazz, id);
	}

	public Object load(Class<?> theClass, Serializable id) {
		return getSession().load(theClass, id);
	}

	public void load(Object object, Serializable id) {
		getSession().load(object, id);
	}

	public void refresh(Object object) {
		getSession().refresh(object);
	}

	public void evict(Object object) {
		getSession().evict(object);
		;
	}

	public Serializable save(Object object) {
		return getSession().save(object);
	}

	public void saveOrUpdate(Object object) {
		getSession().saveOrUpdate(object);
	}

	public void update(Object object) {
		getSession().update(object);
	}

	public Object merge(Object object) {
		return getSession().merge(object);
	}

	public LobHelper getLobHelper() {
		return getSession().getLobHelper();
	}

}
