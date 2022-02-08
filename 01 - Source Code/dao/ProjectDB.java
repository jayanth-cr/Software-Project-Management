package dao;

import java.util.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import models.*;

public class ProjectDB implements ProjectDAO {
	
	private SessionFactory factory;
	
	public ProjectDB() {
		factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Project.class)
				.addAnnotatedClass(Phase.class)
				.addAnnotatedClass(Task.class)
				.buildSessionFactory();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean create(Project project, List<Phase> phases, List<Integer> userIds) {
		boolean check = true;
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			Query<User> query = session.createQuery("from User u where userid in :ids");
			query.setParameter("ids", userIds);
			List<User> users = query.list();
			for(int i=0;i<users.size();i++) {
				users.get(i).addProject(project);
			}
			session.save(project);
			for(int i=0;i<phases.size();i++) {
				project.addPhase(phases.get(i));
				session.save(phases.get(i));
			}
			session.getTransaction().commit();
		}
		catch(Exception e) {
			check = false;
			System.out.println(e);
		}
		finally {
			session.close();
		}
		return check;
	}

	@Override
	public Project getById(int projectId) {
		Session session = factory.openSession();
		Project project = session.get(Project.class, projectId);
		session.close();
		return project;
	}
	
	
	

}
