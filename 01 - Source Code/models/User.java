package models;


import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="userid")
	private int userId;

	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="designation")
	private int designation;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(name="user_project", joinColumns=@JoinColumn(name="userid"),inverseJoinColumns=@JoinColumn(name="projectid"))
	List<Project> projects;
	
	@OneToMany(mappedBy="user",cascade = {CascadeType.ALL})
	List<Task> tasks;
	
	public User() {}
	
	public User(String name, String email, String password, int designation) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.designation = designation;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDesignation() {
		return designation;
	}
	public void setDesignation(int designation) {
		this.designation = designation;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public void addProject(Project project) {
		if(projects==null) {
			projects = new ArrayList<>();
		}
		projects.add(project);
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void addTask(Task task) {
		if(tasks==null) {
			tasks = new ArrayList<>();
		}
		tasks.add(task);
		task.setUser(this);
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", designation=" + designation + "]";
	}
	
}
