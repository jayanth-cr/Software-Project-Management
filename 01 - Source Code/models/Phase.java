package models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="phases")
public class Phase {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="phaseid")
	private int phaseId;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade= {CascadeType.ALL})
	@JoinColumn(name = "projectid")
	private Project project;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy="phase")
	List<Task> tasks;
	
	public Phase(){}
	
	public Phase(String name){
		this.name = name;
	}

	public int getPhaseId() {
		return phaseId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addTask(Task task) {
		if(tasks==null) {
			tasks = new ArrayList<>();
		}
		tasks.add(task);
		task.setPhase(this);
	}

	@Override
	public String toString() {
		return "Phase [" + "name=" + name + "]";
	}
	
	
}
