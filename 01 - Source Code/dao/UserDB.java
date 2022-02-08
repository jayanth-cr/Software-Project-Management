package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import models.Phase;
import models.Project;
import models.Task;
import models.User;

public class UserDB implements UserDAO {
	private SessionFactory factory;
	public UserDB() {
		factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Project.class)
				.addAnnotatedClass(Phase.class)
				.addAnnotatedClass(Task.class)
				.buildSessionFactory();
	}
	
	@Override
	public boolean save(User user) {
		Session session = factory.openSession();
		boolean check = true;
		try {
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		}
		catch(Exception e) {
			System.out.println(e.toString());
			check = false;
		}
		finally {
			session.close();
		}
		System.out.println("Saved : "+check);
		return check;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User validate(String email, String password) {
		Session session = factory.openSession();
		session.beginTransaction();
		String hql = "from User u where u.email= ?1 and u.password= ?2";
		Query<User> query = session.createQuery(hql);
		query.setParameter(1, email);
		query.setParameter(2, password);
		User user = query.uniqueResult();
		session.getTransaction().commit();
		session.close();
		if(user==null) {
			return null;
		}
		else if(user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll(){
		Session session = factory.openSession();
		String hql = "from User u where u.designation=0";
		session.beginTransaction();
		Query<User> query = session.createQuery(hql);
		List<User> users = query.list();
		session.getTransaction().commit();
		session.close();
		return users;
	}
	
	@Override
	public User get(int id) {
		Session session = factory.openSession();
		User user = session.get(User.class,id);
		session.close();
		return user;
	}

	@Override
	public boolean changePassword(int userId, String password, String newPassword) {
		Session session = factory.openSession();
		boolean check = true;
		try {
			User user = session.get(User.class, userId);
			session.beginTransaction();
			if(user.getPassword().equals(password)) {
				user.setPassword(newPassword);
				session.save(user);
				session.getTransaction().commit();
			}
			else {
				check = false;
			}
		}
		catch(Exception e) {
			check = false;
			System.out.println(e.toString());
		}
		finally {
			session.close();
		}
		return check;
	}

}
