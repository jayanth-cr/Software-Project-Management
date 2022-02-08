package forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.LineBorder;

import org.jdatepicker.impl.*;
import validation.*;
import dao.TaskDB;
import dao.UserDB;
import models.*;

public class TaskManagement {
	ComponentFrame frame;
	// UI Components
	JLabel lbl_taskTitle, lbl_taskPriority, lbl_taskCost, lbl_taskDueDate, lbl_phases, lbl_assignedMember, lbl_title,
			lbl_taskAssignedInfo;
	JTextField txt_taskTitle, txt_taskCost;
	JComboBox<String> cmb_taskPriority, cmb_phases, cmb_members;
	JButton btn_assign;
	// errors
	JLabel lbl_taskTitleError, lbl_costError, lbl_dueDateError, lbl_assignedMemberError;

	// phases
	List<Phase> phaseList;
	Set<Phase> tempList;
	Iterator<Phase> it;
	// assign to
	List<User> employeeList;
	Iterator<User> it1;
	User temp;
	// due date
	JDatePickerImpl dueDatePicker;
	SqlDateModel model;
	Properties properties;
	JDatePanelImpl dueDatePanel;
	String answer = "";
	JFormattedTextField f1;
	// DB
	String title;
	String cost;
	Date dueDate;

	// back button
	JButton btn_back;

	@SuppressWarnings("serial")
	TaskManagement(User user, Project project) {
		frame = new ComponentFrame("Assign Tasks");

		// title
		lbl_title = new JLabel("Assign Tasks");
		lbl_title.setFont(new Font("MyFont", Font.BOLD, 28));
		lbl_title.setBounds(830, 90, 700, 50);

		// back Button
		btn_back = new JButton("Back");
		btn_back.setFont(frame.myFont);
		btn_back.setBounds(1420, 80, 90, 30);

		// phases
		lbl_phases = new JLabel("Phase");
		lbl_phases.setFont(frame.myFont);
		lbl_phases.setBounds(700, 200, 200, 30);
		cmb_phases = new JComboBox<>();
		cmb_phases.setBackground(Color.white);
		cmb_phases.setFont(frame.myFont);
		cmb_phases.setBounds(850, 200, 300, 27);
		tempList = project.getPhases();
		phaseList = new LinkedList<Phase>(tempList);
		it = phaseList.iterator();
		while (it.hasNext()) {
			cmb_phases.addItem(it.next().getName());
		}

		// assigned member
		lbl_assignedMember = new JLabel("Assign to ");
		lbl_assignedMember.setFont(frame.myFont);
		lbl_assignedMember.setBounds(700, 260, 200, 30);
		cmb_members = new JComboBox<>();
		cmb_members.setFont(frame.myFont);
		cmb_members.setBounds(850, 260, 300, 27);
		cmb_members.setBackground(Color.white);
		employeeList = new LinkedList<User>(new LinkedHashSet<User>(project.getEmployees()));
		it1 = employeeList.iterator();
		while (it1.hasNext()) {
			temp = it1.next();
			cmb_members.addItem(temp.getUserId() + " - " + temp.getName());
		}
		lbl_assignedMemberError = new JLabel("This field is empty");
		lbl_assignedMemberError.setFont(frame.myFont);
		lbl_assignedMemberError.setBounds(1170, 260, 400, 27);
		lbl_assignedMemberError.setForeground(Color.red);
		lbl_assignedMemberError.setVisible(false);

		// task title
		lbl_taskTitle = new JLabel("Task");
		lbl_taskTitle.setFont(frame.myFont);
		lbl_taskTitle.setBounds(700, 320, 200, 30);
		txt_taskTitle = new JTextField();
		txt_taskTitle.setFont(frame.myFont);
		txt_taskTitle.setBounds(850, 320, 300, 30);
		lbl_taskTitleError = new JLabel("Invalid Task");
		lbl_taskTitleError.setFont(frame.myFont);
		lbl_taskTitleError.setBounds(1170, 320, 400, 27);
		lbl_taskTitleError.setForeground(Color.red);
		lbl_taskTitleError.setVisible(false);

		// task priority
		lbl_taskPriority = new JLabel("Priority");
		lbl_taskPriority.setFont(frame.myFont);
		lbl_taskPriority.setBounds(700, 380, 200, 30);
		cmb_taskPriority = new JComboBox<>();
		cmb_taskPriority.setFont(frame.myFont);
		cmb_taskPriority.setBounds(850, 380, 300, 30);
		cmb_taskPriority.setBackground(Color.white);
		cmb_taskPriority.addItem("Very High");
		cmb_taskPriority.addItem("High");
		cmb_taskPriority.addItem("Medium");
		cmb_taskPriority.addItem("Low");
		cmb_taskPriority.addItem("Very Low");

		// cost
		lbl_taskCost = new JLabel("Cost");
		lbl_taskCost.setFont(frame.myFont);
		lbl_taskCost.setBounds(700, 440, 200, 30);
		txt_taskCost = new JTextField();
		txt_taskCost.setFont(frame.myFont);
		txt_taskCost.setBounds(850, 440, 300, 30);
		lbl_costError = new JLabel("Invalid Cost ( Valid :  1 - 9,99,99,999 )");
		lbl_costError.setFont(frame.myFont);
		lbl_costError.setBounds(1170, 440, 400, 27);
		lbl_costError.setForeground(Color.red);
		lbl_costError.setVisible(false);

		// due date
		lbl_taskDueDate = new JLabel("Due Date");
		lbl_taskDueDate.setFont(frame.myFont);
		lbl_taskDueDate.setBounds(700, 500, 200, 30);
		model = new SqlDateModel();
		properties = new Properties();
		properties.put("text.day", "Day");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year");
		dueDatePanel = new JDatePanelImpl(model, properties);
		dueDatePicker = new JDatePickerImpl(dueDatePanel, new AbstractFormatter() {

			@Override
			public String valueToString(Object value) throws ParseException {
				if (value != null) {
					Calendar cal = (Calendar) value;
					SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
					String date = f.format(cal.getTime());
					f = new SimpleDateFormat("yyyy-MM-dd");
					answer = f.format(cal.getTime());
					return (date);
				}
				return ("");
			}

			@Override
			public Object stringToValue(String text) throws ParseException {

				return ("");
			}
		});
		f1 = dueDatePicker.getJFormattedTextField();
		f1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		f1.setBackground(Color.white);
		f1.setFont(frame.myFont);
		f1.setEditable(false);
		f1.setPreferredSize(new Dimension(250, 30));
		dueDatePicker.setBounds(850, 500, 250, 30);
		lbl_dueDateError = new JLabel("Invalid Due Date");
		lbl_dueDateError.setFont(frame.myFont);
		lbl_dueDateError.setBounds(1120, 500, 300, 27);
		lbl_dueDateError.setForeground(Color.red);
		lbl_dueDateError.setVisible(false);

		// assign
		btn_assign = new JButton("Assign");
		btn_assign.setFont(frame.myFont);
		btn_assign.setBounds(950, 600, 100, 30);
		lbl_taskAssignedInfo = new JLabel("Task Assigned Successfully !!!");
		lbl_taskAssignedInfo.setFont(frame.myFont);
		lbl_taskAssignedInfo.setForeground(Color.green);
		lbl_taskAssignedInfo.setBounds(1070, 600, 300, 30);
		lbl_taskAssignedInfo.setVisible(false);

		// adding components

		// title
		frame.add(lbl_title);

		// back button
		frame.add(btn_back);

		// phases
		frame.add(lbl_phases);
		frame.add(cmb_phases);

		// assigned member
		frame.add(lbl_assignedMember);
		frame.add(cmb_members);
		frame.add(lbl_assignedMemberError);

		// task title
		frame.add(lbl_taskTitle);
		frame.add(txt_taskTitle);
		frame.add(lbl_taskTitleError);

		// priority
		frame.add(lbl_taskPriority);
		frame.add(cmb_taskPriority);

		// cost
		frame.add(lbl_taskCost);
		frame.add(txt_taskCost);
		frame.add(lbl_costError);

		// due date
		frame.add(lbl_taskDueDate);
		frame.add(dueDatePicker);
		frame.add(lbl_dueDateError);

		// assign button
		frame.add(btn_assign);
		frame.add(lbl_taskAssignedInfo);

		// frame info
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.white);
		pnl.setBounds(570, 50, 960, 650);
		pnl.setBorder(new LineBorder(Color.white, 5, true));
		frame.add(pnl);
		frame.add(frame.background);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		// events

		// back
		btn_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LeaderDashboard(new UserDB().validate(user.getEmail(), user.getPassword()));
				frame.dispose();
			}
		});

		// window
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		btn_assign.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> error = new LinkedList<String>();
				boolean gate;
				gate = false;
				lbl_taskAssignedInfo.setVisible(false);
				lbl_costError.setVisible(false);
				lbl_dueDateError.setVisible(false);
				lbl_taskTitleError.setVisible(false);
				lbl_assignedMemberError.setVisible(false);
				if (answer.equals("")) {
					long millis = System.currentTimeMillis();
					dueDate = new Date(millis);
				} else {
					dueDate = Date.valueOf(answer);
				}
				title = txt_taskTitle.getText().trim();
				cost = txt_taskCost.getText().trim();
				if (employeeList.size() == 0) {
					gate = false;
					lbl_assignedMemberError.setVisible(true);
				}
				error = Validation.validateTask(title, cost, dueDate);
				if (error.size() != 0) {
					if (error.contains("Title")) {
						lbl_taskTitleError.setText("Invalid Task");
						lbl_taskTitleError.setVisible(true);
					}
					if (error.contains("Cost")) {
						lbl_costError.setVisible(true);
					}
					if (error.contains("Date")) {
						lbl_dueDateError.setVisible(true);
					}
					return;
				}
				gate = false;

				if (title.length() > 50) {
					gate = true;
					lbl_taskTitleError.setText("Max length is 50 characters");
					lbl_taskTitleError.setVisible(true);
				}
				if (Integer.parseInt(cost) > 99999999) {
					gate = true;
					lbl_costError.setVisible(true);
				}
				if (gate) {
					return;
				}

				Task task = new Task(title, (String) cmb_taskPriority.getSelectedItem(), "pending",
						Integer.parseInt(cost), dueDate);
				new TaskDB().create(task, project.getProjectId(),
						phaseList.get(cmb_phases.getSelectedIndex()).getPhaseId(),
						Integer.parseInt(((String) cmb_members.getSelectedItem()).substring(0, 4)));
				txt_taskTitle.setText("");
				txt_taskCost.setText("");
				lbl_taskAssignedInfo.setVisible(false);
				lbl_taskAssignedInfo.setVisible(true);

			}
		});
	}

}
