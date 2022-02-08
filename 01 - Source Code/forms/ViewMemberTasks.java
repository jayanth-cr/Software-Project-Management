package forms;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import dao.TaskDB;
import models.*;

@SuppressWarnings("serial")
public class ViewMemberTasks extends JPanel {

	List<Task> taskList;
	Phase temp;
	Task task;
	Iterator<Task> it;
	boolean gate = false;

	// UI Components
	JLabel lbl_taskIdTitle, lbl_taskTitleTitle, lbl_statusTitle, lbl_priorityTitle, lbl_costTitle, lbl_dueDateTitle;
	JLabel lbl_noTasks, lbl_btnUpdateStatus;
	JLabel lbl_taskId, lbl_taskTitle, lbl_priority, lbl_cost, lbl_dueDate;
	JButton btn_update;
	boolean flag;

	JComboBox<String> cmb_status;
	List<JComboBox<String>> comboList;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	ViewMemberTasks(Map<Phase, List<Task>> map, Phase phase, JButton btn_back) {

		for (Map.Entry m : map.entrySet()) {
			temp = (Phase) m.getKey();
			if (temp.getPhaseId() == phase.getPhaseId()) {
				taskList = (List<Task>) m.getValue();
				gate = true;
				break;
			}

		}
		flag = false;
		btn_update = new JButton("Update");
		btn_update.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
		btn_update.setBounds(850, 800, 130, 45);
		lbl_btnUpdateStatus = new JLabel("Updated Successfully !!!");
		lbl_btnUpdateStatus.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
		lbl_btnUpdateStatus.setBounds(1000, 805, 250, 35);
		lbl_btnUpdateStatus.setForeground(Color.green);
		lbl_btnUpdateStatus.setVisible(false);

		int y = 140;
		if (gate) {
			btn_update.setVisible(true);

			lbl_taskIdTitle = new JLabel("Task ID");
			lbl_taskIdTitle.setFont(new Font("MyFont", Font.BOLD, 20));
			lbl_taskIdTitle.setBounds(430, 50, 200, 50);
			lbl_taskIdTitle.setHorizontalAlignment(JLabel.CENTER);

			lbl_taskTitleTitle = new JLabel("Task");
			lbl_taskTitleTitle.setFont(new Font("MyFont", Font.BOLD, 20));
			lbl_taskTitleTitle.setBounds(630, 50, 200, 50);
			lbl_taskTitleTitle.setHorizontalAlignment(JLabel.CENTER);

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
			lbl_dueDateTitle.setBounds(1380, 50, 200, 50);
			lbl_dueDateTitle.setHorizontalAlignment(JLabel.CENTER);

			add(lbl_taskIdTitle);
			add(lbl_taskTitleTitle);
			add(lbl_statusTitle);
			add(lbl_priorityTitle);
			add(lbl_costTitle);
			add(lbl_dueDateTitle);

		} else {
			btn_update.setVisible(false);

			lbl_noTasks = new JLabel("--- No Tasks Assigned Yet ---");
			lbl_noTasks.setFont(new Font("MyFont", Font.BOLD, 28));
			lbl_noTasks.setBounds(700, 400, 550, 50);
			lbl_noTasks.setHorizontalAlignment(JLabel.CENTER);

			add(lbl_noTasks);
		}

		comboList = new LinkedList<JComboBox<String>>();
		if (gate) {
			it = taskList.iterator();
			while (it.hasNext()) {
				task = it.next();
				lbl_taskId = new JLabel();
				lbl_taskId.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				lbl_taskId.setBounds(430, y, 200, 50);
				lbl_taskId.setText(Integer.toString(task.getTaskId()));
				lbl_taskId.setHorizontalAlignment(JLabel.CENTER);
				lbl_taskId.setBorder(BorderFactory.createLineBorder(Color.black, 1));

				lbl_taskTitle = new JLabel();
				lbl_taskTitle.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				lbl_taskTitle.setBounds(630, y, 200, 50);
				lbl_taskTitle.setText(task.getTitle());
				lbl_taskTitle.setHorizontalAlignment(JLabel.CENTER);
				lbl_taskTitle.setBorder(BorderFactory.createLineBorder(Color.black, 1));

				cmb_status = new JComboBox<String>();
				((JLabel) cmb_status.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
				cmb_status.setBackground(Color.white);
				cmb_status.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				cmb_status.setBounds(830, y, 200, 50);
				cmb_status.addItem("Pending");
				cmb_status.addItem("In Progress");
				cmb_status.addItem("Completed");
				cmb_status.setSelectedItem(task.getStatus());
				comboList.add(cmb_status);

				lbl_priority = new JLabel();
				lbl_priority.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				lbl_priority.setBounds(1030, y, 200, 50);
				lbl_priority.setText(task.getPriority());
				lbl_priority.setHorizontalAlignment(JLabel.CENTER);
				lbl_priority.setBorder(BorderFactory.createLineBorder(Color.black, 1));

				lbl_cost = new JLabel();
				lbl_cost.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				lbl_cost.setBounds(1230, y, 150, 50);
				lbl_cost.setText(Integer.toString(task.getCost()));
				lbl_cost.setHorizontalAlignment(JLabel.CENTER);
				lbl_cost.setBorder(BorderFactory.createLineBorder(Color.black, 1));

				lbl_dueDate = new JLabel();
				lbl_dueDate.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
				lbl_dueDate.setBounds(1380, y, 200, 50);
				String[] date = task.getDueDate().toString().split("-");
				lbl_dueDate.setText(date[2]+"-"+date[1]+"-"+date[0]);
				lbl_dueDate.setHorizontalAlignment(JLabel.CENTER);
				lbl_dueDate.setBorder(BorderFactory.createLineBorder(Color.black, 1));

				add(lbl_taskId);
				add(lbl_taskTitle);
				add(cmb_status);
				add(lbl_priority);
				add(lbl_cost);
				add(lbl_dueDate);
				y += 70;
			}

		}
		
		// combo box state 
		Iterator<JComboBox<String>> it = comboList.iterator();
		while (it.hasNext()) {
			it.next().addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					flag = true;
				}
			});
		}

		// update button
		btn_update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (flag) {
					for (int i = 0; i < comboList.size(); i++) {
						new TaskDB().update(taskList.get(i).getTaskId(), comboList.get(i).getSelectedItem().toString());
					}
					lbl_btnUpdateStatus.setVisible(true);
					flag = false;
				}
			}
		});
		btn_update.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lbl_btnUpdateStatus.setVisible(false);
			}
		});
		add(lbl_btnUpdateStatus);
		add(btn_update);
		add(btn_back);
		
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.white);
		pnl.setBounds(400, 20, 1500, 850);
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
