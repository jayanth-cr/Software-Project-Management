package forms;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;

import dao.TaskDB;
import dao.UserDB;
import models.*;

public class MemberManagement {

	ComponentFrame frame;
	// UI Components
	JLabel lbl_addMembersTitle;
	// adding members
	JComboBox<String> cmb_newMemberList;
	JButton btn_addNewMember, btn_resetNewMember, btn_removeNewMember, btn_confirm;
	List<User> newMembers;
	LinkedList<Integer> selectedNewMembers;
	JScrollPane scr_newMemberList;
	DefaultListModel<String> newMemberData;
	JList<String> lst_newMemberList;
	List<User> selectedEmployees;
	List<User> oldMembers;
	List<User> temp1;
	JLabel lbl_memberAdded;

	// back button
	JButton btn_back;

	MemberManagement(User user, Project project) {
		frame = new ComponentFrame("Add New Members");
		// back Button
		btn_back = new JButton("Back");
		btn_back.setFont(frame.myFont);
		btn_back.setBounds(1220, 80, 90, 30);

		// old and new members
		newMembers = new UserDB().getAll();
		oldMembers = project.getUsers();

		// adding new members title
		lbl_addMembersTitle = new JLabel("Add New Members");
		lbl_addMembersTitle.setBounds(750, 100, 700, 50);
		lbl_addMembersTitle.setFont(new Font("MyFont", Font.BOLD, 28));

		// new members
		cmb_newMemberList = new JComboBox<String>();
		cmb_newMemberList.setFont(frame.myFont);
		cmb_newMemberList.setBounds(600, 200, 300, 27);
		cmb_newMemberList.setBackground(Color.white);
		cmb_newMemberList.addItem("---");
		// inserting new members
		User temp;
		temp1 = new LinkedList<User>();
		Iterator<User> it = newMembers.iterator();
		Iterator<User> it1;
		boolean gate = true;
		while (it.hasNext()) {
			temp = it.next();
			gate = true;
			it1 = oldMembers.iterator();
			while (it1.hasNext()) {
				if (temp.getUserId() == it1.next().getUserId()) {
					gate = false;
					break;
				}
			}
			if (gate) {
				temp1.add(temp);

			}
		}
		temp1 = new LinkedList<>(new HashSet<>(temp1));
		Iterator<User> it2 = temp1.iterator();
		while (it2.hasNext()) {
			temp = it2.next();
			cmb_newMemberList.addItem(temp.getUserId() + " - " + temp.getName());
		}
		// adding members
		btn_addNewMember = new JButton("Add");
		btn_addNewMember.setFont(frame.myFont);
		btn_addNewMember.setBounds(950, 200, 120, 25);
		selectedNewMembers = new LinkedList<>();
		newMemberData = new DefaultListModel<>();
		lst_newMemberList = new JList<String>(newMemberData);
		lst_newMemberList.setBackground(Color.white);
		lst_newMemberList.setFont(frame.myFont);
		scr_newMemberList = new JScrollPane(lst_newMemberList);
		scr_newMemberList.setBounds(600, 250, 300, 170);
		// removing members
		btn_removeNewMember = new JButton("Remove");
		btn_removeNewMember.setFont(frame.myFont);
		btn_removeNewMember.setBounds(950, 250, 120, 25);
		// reset members
		btn_resetNewMember = new JButton("Reset");
		btn_resetNewMember.setFont(frame.myFont);
		btn_resetNewMember.setBounds(1090, 200, 120, 25);
		// confirm
		btn_confirm = new JButton("Confirm");
		btn_confirm.setFont(frame.myFont);
		btn_confirm.setBounds(750, 450, 200, 25);
		lbl_memberAdded = new JLabel();
		lbl_memberAdded.setFont(frame.myFont);
		lbl_memberAdded.setBounds(980, 450, 200, 25);
		lbl_memberAdded.setVisible(false);

		// adding components

		// back button
		frame.add(btn_back);

		// adding members
		frame.add(lbl_addMembersTitle);
		frame.add(cmb_newMemberList);
		frame.add(btn_addNewMember);
		frame.add(btn_removeNewMember);
		frame.add(btn_resetNewMember);
		frame.add(scr_newMemberList);
		frame.add(btn_confirm);
		frame.add(lbl_memberAdded);

		// frame info
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.white);
		pnl.setBounds(555, 55, 780, 465);
		pnl.setBorder(new LineBorder(Color.white, 5, true));
		frame.add(pnl);
		frame.add(frame.background);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		// events

		// back button
		// back
		btn_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LeaderDashboard(new UserDB().validate(user.getEmail(), user.getPassword()));
				frame.dispose();
			}
		});

		// new members

		// adding new members
		btn_addNewMember.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				add(selectedNewMembers, newMemberData, cmb_newMemberList);

			}

		});
		// removing new members
		btn_removeNewMember.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				remove(selectedNewMembers, newMemberData, cmb_newMemberList, lst_newMemberList);

			}
		});
		// resetting new members
		btn_resetNewMember.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				reset(selectedNewMembers, newMemberData, cmb_newMemberList);

			}
		});

		// confirm adding
		btn_confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (selectedNewMembers.size() == 0) {
					lbl_memberAdded.setText("Select a Member !!!");
					lbl_memberAdded.setForeground(Color.red);
					lbl_memberAdded.setVisible(false);
					lbl_memberAdded.setVisible(true);

				} else {
					confirmAdd(selectedNewMembers, newMemberData, lst_newMemberList, project);
					lbl_memberAdded.setText("Added Successfully !!!");
					lbl_memberAdded.setForeground(Color.green);
					lbl_memberAdded.setVisible(false);
					lbl_memberAdded.setVisible(true);

				}
				selectedNewMembers = new LinkedList<Integer>();

			}
		});

		// events
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	public void add(List<Integer> list, DefaultListModel<String> model, JComboBox<String> dropdown) {
		lbl_memberAdded.setVisible(false);
		if (!(dropdown.getSelectedItem().equals("---"))) {
			list.add(Integer.parseInt(((String) dropdown.getSelectedItem()).substring(0, 4)));
		} else {
			return;
		}
		model.addElement((String) dropdown.getSelectedItem());
		dropdown.removeItem((String) dropdown.getSelectedItem());
		dropdown.setSelectedIndex(0);
	}

	public void remove(List<Integer> list, DefaultListModel<String> model, JComboBox<String> dropdown,
			JList<String> lst_list) {
		lbl_memberAdded.setVisible(false);
		int selectedIndex = lst_list.getSelectedIndex();
		if (selectedIndex == -1) {

		} else {
			dropdown.addItem(model.get(selectedIndex));
			list.remove((Object) Integer.parseInt((model.get(selectedIndex)).substring(0, 4)));
			model.remove(lst_list.getSelectedIndex());
		}
	}

	public void reset(List<Integer> list, DefaultListModel<String> model, JComboBox<String> dropdown) {
		lbl_memberAdded.setVisible(false);
		list = new LinkedList<>();
		while (model.size() != 0) {
			dropdown.addItem(model.remove(0));
		}
	}

	public void confirmAdd(List<Integer> list, DefaultListModel<String> model, JList<String> lst_list,
			Project project) {
		model.removeAllElements();
		lst_list.removeAll();
		int i = 0;
		while (i < list.size()) {
			new TaskDB().addEmployee(list.get(i), project.getProjectId());
			i++;
		}
	}

}
