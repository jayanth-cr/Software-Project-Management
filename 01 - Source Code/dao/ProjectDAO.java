package dao;

import java.util.*;

import models.*;

public interface ProjectDAO {
	boolean create(Project project, List<Phase>phases, List<Integer> users);
	Project getById(int projectId);
}
