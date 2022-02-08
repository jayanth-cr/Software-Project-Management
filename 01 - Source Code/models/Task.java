package models;

import java.sql.Date;
import javax.persistence.*;

@Entity
@Table(name="task")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="taskid")
	private int taskId;
	
	@Column(name="title")
	private String title;
	
	@ManyToOne(cascade= {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="projectid")
	private Project project;
	
	@Column(name="priority")
	private String priority;
	
	@Column(name="cost")
	private int cost;
	
	@Column(name="status")
	private String status;
	
	@Column(name="duedate")
	private Date dueDate;
	
	@ManyToOne(cascade= {CascadeType.ALL})
	@JoinColumn(name="phaseid")
	private Phase phase;
	
	@ManyToOne(cascade= {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="assigned_to")
	private User user;
	
	public Task() {}
	
	public Task(String title, String priority,String status, int cost, Date dueDate) {
		this.title = title;
		this.priority = priority;
		this.status = status;
		this.cost = cost;
		this.dueDate = dueDate;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", title=" + title + ", priority=" + priority + ", cost=" + cost + ", status="
				+ status + ", dueDate=" + dueDate + "]";
	}
	
	
	
	
}
