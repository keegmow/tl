package com.keegmow.tasklist;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;


public class DAO {

	private static SessionFactory factory;
	private static void setupFactory() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) { ; }

		Configuration configuration = new Configuration();

		configuration.configure("hibernate.cfg.xml");
		configuration.addResource("task.hbm.xml");
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
				applySettings(configuration.getProperties()).build();
		factory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	//Retrieves all tasks from DB
	@SuppressWarnings("unchecked")
	public static List<Task> getTasks(){
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		
		String hql = "FROM Task ORDER BY active DESC";
		List<Task> tasks = hibernateSession.createQuery(hql).list();
		hibernateSession.getTransaction().commit();
		hibernateSession.close();
		
		return tasks;
	}
	
	//adds task 
	public static int addTask(Task task) {
		if (factory == null)
			setupFactory();
		
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		int i = (Integer)hibernateSession.save(task);
		hibernateSession.getTransaction().commit();
		hibernateSession.close();
		return i;
	}
	
	//Deletes selected task by id#
	public static void deleteTask(int id) {
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		
		
		try {
			Task task = (Task) hibernateSession.load(Task.class, id);
			hibernateSession.getTransaction().commit();
			hibernateSession.getTransaction().begin();
			hibernateSession.delete(task);
			hibernateSession.getTransaction().commit();
		} catch (EntityNotFoundException e) {
			System.out.println(e);
		}
		hibernateSession.close();
	}
	
	//Toggle task from active to complete or vice versa
	public static void toggleTask(int id) {
		
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		
		try {
			Task task = (Task) hibernateSession.load(Task.class, id);
			hibernateSession.getTransaction().commit();
			task.toggleActive();
			hibernateSession.getTransaction().begin();
			hibernateSession.update(task);
			hibernateSession.getTransaction().commit();
		} catch (EntityNotFoundException e) {
			System.out.println(e);
		}
		hibernateSession.close();
	}
	
	
	//Deletes all completed tasks
	public static void deleteComplete() {
		
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		String hql = "DELETE FROM Task WHERE active = false";
		Query query = hibernateSession.createQuery(hql);
		query.executeUpdate();
		hibernateSession.close();
		
	}
	
	
}
