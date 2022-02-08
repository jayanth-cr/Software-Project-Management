package dao;

import java.util.*;

import models.*;

public interface TaskDAO {
	boolean create(Task task,int projectId, int phaseId, int assignedTo);
	Map<Phase,List<Task>> getFor(int assignedTo, int projectId);
	Map<Phase,List<Task>> getAll(int projectId);
	boolean update(int taskId, String status);
	boolean addEmployee(int empId, int projectId);
}
