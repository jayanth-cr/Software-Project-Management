package models;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="projects")
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="projectid")
	private int projectId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="project", cascade= {CascadeType.ALL})
	Set<Phase> phases = new LinkedHashSet<>();
	
	@ManyToMany(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(name="user_project", joinColumns=@JoinColumn(name="projectid"),inverseJoinColumns=@JoinColumn(name="userid"))
	List<User> users = new ArrayList<>();
	
	@OneToMany(mappedBy="project",cascade = {CascadeType.ALL})
	List<Task> tasks;

	public Project() {}

	public Project(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<Phase> getPhases() {
		return phases;
	}

	public void setPhases(Set<Phase> phases) {
		this.phases = phases;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Task> getTasks() {
		return tasks;
	}
	
	public List<User> getEmployees(){
		List<User> list = new ArrayList<>();
		for(int i=0;i<users.size();i++) {
			if(users.get(i).getDesignation()==0) {
				list.add(users.get(i));
			}
		}
		return list;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void addPhase(Phase phase) {
		phases.add(phase);
		phase.setProject(this);
	}
	
	public void addTask(Task task) {
		if(tasks==null) {
			tasks = new ArrayList<>();
		}
		tasks.add(task);
		task.setProject(this);
	} 

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", title=" + title + ", description="
				+ description + "]";
	}
	
	
	
}
