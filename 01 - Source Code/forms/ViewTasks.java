package forms;

import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import models.*;

@SuppressWarnings("serial")
public class ViewTasks extends JPanel {

	List<Task> taskList;
	Phase temp;
	Task task;
	Iterator<Task> it;
	boolean gate = false;

	// back button
	JButton btn_back;

	// UI Components
	JLabel lbl_taskIdTitle, lbl_taskTitleTitle, lbl_assignedToTitle, lbl_statusTitle, lbl_priorityTitle, lbl_costTitle,
			lbl_dueDateTitle;
	JLabel lbl_noTasks;
	JLabel lbl_taskId, lbl_taskTitle, lbl_assignedTo, lbl_status, lbl_priority, lbl_cost, lbl_dueDate;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	ViewTasks(Map<Phase, List<Task>> map, Phase phase,JButton btn_back) {

		for (Map.Entry m : map.entrySet()) {
			temp = (Phase) m.getKey();
			if (temp.getPhaseId() == phase.getPhaseId()) {
				taskList = (List<Task>) m.getValue();
				gate = true;
				break;
			}

		}
		int y = 140;
		if (gate) {
			lbl_taskIdTitle = new JLabel("Task ID");
			lbl_taskIdTitle.setFont(new Font("MyFont", Font.BOLD, 20));
			lbl_taskIdTitle.setBounds(230, 50, 100, 50);
			lbl_taskIdTitle.setHorizontalAlignment(JLabel.CENTER);

			lbl_taskTitleTitle = new JLabel("Task");
			lbl_taskTitleTitle.setFont(new Font("MyFont", Font.BOLD, 20));
			lbl_taskTitleTitle.setBounds(330, 50, 200, 50);
			lbl_taskTitleTitle.setHorizontalAlignment(JLabel.CENTER);

			lbl_assignedToTitle = new JLabel("Assigned to");
			lbl_assignedToTitle.setFont(new Font("MyFont", Font.BOLD, 20));
			lbl_assignedToTitle.setBounds(530, 50, 300, 50);
			lbl_assignedToTitle.setHorizontalAlignment(JLabel.CENTER);

			lbl_statusTitle = new JLabel("Status");
			lbl_statusTitle.setFont(new Font("MyFont", Font.BOLD, 20));
			lbl_statusTitle.setBounds(830, 50, 200, 50);
			lbl_statusTitle.setHorizontalAlignment(JLabel.CENTER);

			lbl_priorityTitle = new JLabel("Priority");
			lbl_priorityTitle.setFont(new Font("MyFont", Font.BOLD, 20));
			lbl_priorityTitle.setBounds(1030, 50, 200, 50);
			lbl_priorityTitle.setHorizontalAlignment(JLabel.CENTER);

			lbl_costTitle = new JLabel("Cost");
			lbl_costTitle.setFont(new Font("MyFont", Font.BOLD, 20));
			lbl_costTitle.setBounds(1230, 50, 150, 50);
			lbl_costTitle.setHorizontalAlignment(JLabel.CENTER);

			lbl_dueDateTitle = new JLabel("Due Date");
			lbl_dueDateTitle.setFont(new Font("MyFont", Font.BOLD, 20));
			lbl_dueDateTitle.setBounds(1380, 50, 120, 50);
			lbl_dueDateTitle.setHorizontalAlignment(JLabel.CENTER);

			add(lbl_taskIdTitle);
			add(lbl_taskTitleTitle);
			add(lbl_assignedToTitle);
			add(lbl_statusTitle);
			add(lbl_priorityTitle);
			add(lbl_costTitle);
			add(lbl_dueDateTitle);

		} else {
			lbl_noTasks = new JLabel("--- No Tasks Assigned Yet ---");
			lbl_noTasks.setFont(new Font("MyFont", Font.BOLD, 28));
			lbl_noTasks.setBounds(700, 400, 550, 50);
			lbl_noTasks.setHorizontalAlignment(JLabel.CENTER);

			add(lbl_noTasks);
		}

		if (gate) {
			it = taskList.iterator();
			while (it.hasNext()) {
				task = it.next();
				lbl_taskId = new JLabel();
				lbl_taskId.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				lbl_taskId.setBounds(230, y, 100, 50);
				lbl_taskId.setText(Integer.toString(task.getTaskId()));
				lbl_taskId.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				lbl_taskId.setHorizontalAlignment(JLabel.CENTER);

				lbl_taskTitle = new JLabel();
				lbl_taskTitle.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				lbl_taskTitle.setBounds(330, y, 200, 50);
				lbl_taskTitle.setText(task.getTitle());
				lbl_taskTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				lbl_taskTitle.setHorizontalAlignment(JLabel.CENTER);

				lbl_assignedTo = new JLabel();
				lbl_assignedTo.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				lbl_assignedTo.setBounds(530, y, 300, 50);
				lbl_assignedTo.setText(task.getUser().getUserId() + " - " + task.getUser().getName());
				lbl_assignedTo.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				lbl_assignedTo.setHorizontalAlignment(JLabel.CENTER);

				lbl_status = new JLabel();
				lbl_status.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				lbl_status.setBounds(830, y, 200, 50);
				lbl_status.setText(task.getStatus());
				lbl_status.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				lbl_status.setHorizontalAlignment(JLabel.CENTER);

				lbl_priority = new JLabel();
				lbl_priority.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				lbl_priority.setBounds(1030, y, 200, 50);
				lbl_priority.setText(task.getPriority());
				lbl_priority.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				lbl_priority.setHorizontalAlignment(JLabel.CENTER);

				lbl_cost = new JLabel();
				lbl_cost.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				lbl_cost.setBounds(1230, y, 150, 50);
				lbl_cost.setText(Integer.toString(task.getCost()));
				lbl_cost.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				lbl_cost.setHorizontalAlignment(JLabel.CENTER);

				lbl_dueDate = new JLabel();
				lbl_dueDate.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				lbl_dueDate.setBounds(1380, y, 120, 50);
				String[] date = task.getDueDate().toString().split("-");
				lbl_dueDate.setText(date[2]+"-"+date[1]+"-"+date[0]);
				lbl_dueDate.setBorder(BorderFactory.createLineBorder(Color.black, 1));
				lbl_dueDate.setHorizontalAlignment(JLabel.CENTER);

				add(lbl_taskId);
				add(lbl_taskTitle);
				add(lbl_assignedTo);
				add(lbl_status);
				add(lbl_priority);
				add(lbl_cost);
				add(lbl_dueDate);
				y += 70;
			}

		}
		add(btn_back);

		JPanel pnl = new JPanel();
		pnl.setBackground(Color.white);
		pnl.setBounds(200, 20, 1700, 850);
		pnl.setBorder(new LineBorder(Color.white, 5, true));
		add(pnl);
		JLabel background;
		ImageIcon img = new  ImageIcon("E:\\Bootcamp\\Project Assignments\\03 - Bootathon - 01\\SoftwareProjectManagementSystem\\04 Images\\jeb.png");
		background = new JLabel("",img,JLabel.CENTER);
		background.setBounds(0, 0, 2000, 1010);
		add(background);
		setBackground(Color.white);
		setLayout(null);
		

	}

}
