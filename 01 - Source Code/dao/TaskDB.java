package dao;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import models.Phase;
import models.Project;
import models.Task;
import models.User;

public class TaskDB implements TaskDAO {

	private SessionFactory factory;
	
	public TaskDB() {
		factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Project.class)
				.addAnnotatedClass(Phase.class)
				.addAnnotatedClass(Task.class)
				.buildSessionFactory();
	}
	
	@Override
	public boolean create(Task task, int projectId, int phaseId, int assignedTo) {
		boolean check = true;
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			User user = session.get(User.class, assignedTo);
			user.addTask(task);
			Project project = session.get(Project.class, projectId);
			project.addTask(task);
			Phase phase = session.get(Phase.class, phaseId);
			phase.addTask(task);
			session.save(task);
			session.getTransaction().commit();
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

	@Override
	public Map<Phase, List<Task>> getFor(int assignedTo, int projectId) {
		Session session = factory.openSession();
		Map<Phase, List<Task>> map = new LinkedHashMap<>();
		User user = session.get(User.class,assignedTo);
		List<Task> tasks = user.getTasks();
		for(int i=0;i<tasks.size();i++) {
			Task temp = tasks.get(i);
			if(temp.getProject().getProjectId()==projectId) {
				if(map.containsKey(temp.getPhase())) {
					map.get(temp.getPhase()).add(temp);
				}
				else {
					List<Task> list = new ArrayList<>();
					list.add(temp);
					map.put(temp.getPhase(), list);
				}
			}
		}
		session.close();
		return map;
	}

	@Override
	public Map<Phase, List<Task>> getAll(int projectId) {
		Session session = factory.openSession();
		Map<Phase, List<Task>> map = new LinkedHashMap<>();
		Project project = session.get(Project.class,projectId);
		List<Task> tasks = project.getTasks();
		for(int i=0;i<tasks.size();i++) {
			Task temp = tasks.get(i);
			if(map.containsKey(temp.getPhase())) {
				map.get(temp.getPhase()).add(temp);
			}
			else {
				List<Task> list = new ArrayList<>();
				list.add(temp);
				map.put(temp.getPhase(), list);
			}
		}
		session.close();
		return map;
	}
	
	public boolean update(int taskId, String status) {
		boolean check = true;
		Session session = factory.openSession();
		try {
			session.beginTransaction();
			Task task = session.get(Task.class,taskId);
			task.setStatus(status);
			session.saveOrUpdate(task);
			session.getTransaction().commit();
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

	@Override
	public boolean addEmployee(int empId, int projectId) {
		Session session = factory.openSession();
		boolean check = true;
		try {
			session.beginTransaction();
			User user = session.get(User.class, empId);
			Project project = session.get(Project.class, projectId);
			user.addProject(project);
			session.saveOrUpdate(user);
			session.getTransaction().commit();
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
