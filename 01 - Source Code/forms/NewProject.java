package forms;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.*;
import validation.*;
import models.*;
import dao.*;

public class NewProject {

	// UI components
	ComponentFrame frame;
	JLabel lbl_title, lbl_projectTitle, lbl_description, lbl_phases, lbl_members, lbl_projectTitleError,
			lbl_descriptionError, lbl_phasesError, lbl_phasesEmptyError;
	JTextField txt_projectTitle, txt_phases;
	JTextArea txa_description;
	JButton btn_createProject;
	JScrollPane scr_description;

	// phases
	JList<String> lst_phaseList;
	LinkedList<String> phases;
	JButton btn_addPhase, btn_resetPhase, btn_removePhase;
	DefaultListModel<String> phaseData;
	JScrollPane scr_phaseList;

	// members
	JComboBox<String> cmb_memberList;
	JButton btn_addMember, btn_resetMember, btn_removeMember;
	List<User> allUsers;
	List<User> selectedEmployees;
	LinkedList<Integer> selectedMembers;
	JScrollPane scr_memberList;
	DefaultListModel<String> memberData;
	JList<String> lst_memberList;

	// database
	Project newProject;

	// back button
	JButton btn_back;

	NewProject(User user) {
		frame = new ComponentFrame("New Project");

		// title
		lbl_title = new JLabel("New Project");
		lbl_title.setBounds(900, 30, 300, 40);
		lbl_title.setFont(new Font("MyFont", Font.BOLD, 28));

		// back Button
		btn_back = new JButton("Back");
		btn_back.setFont(frame.myFont);
		btn_back.setBounds(1430, 35, 90, 30);

		// project title
		lbl_projectTitle = new JLabel("Title");
		lbl_projectTitle.setBounds(670, 100, 200, 30);
		lbl_projectTitle.setFont(new Font("MyFont", Font.BOLD, 20));
		txt_projectTitle = new JTextField();
		txt_projectTitle.setFont(frame.myFont);
		txt_projectTitle.setBounds(850, 100, 400, 27);
		lbl_projectTitleError = new JLabel("Invalid Title");
		lbl_projectTitleError.setFont(frame.myFont);
		lbl_projectTitleError.setBounds(1270, 100, 200, 30);
		lbl_projectTitleError.setForeground(Color.red);
		lbl_projectTitleError.setVisible(false);

		// description
		lbl_description = new JLabel("Description");
		lbl_description.setBounds(670, 150, 200, 30);
		lbl_description.setFont(new Font("MyFont", Font.BOLD, 20));
		txa_description = new JTextArea();
		txa_description.setFont(frame.myFont);
		txa_description.setLineWrap(true);
		txa_description.setVisible(true);
		scr_description = new JScrollPane(txa_description);
		scr_description.setFont(frame.myFont);
		scr_description.setBounds(850, 150, 500, 300);
		lbl_descriptionError = new JLabel("Invalid Description");
		lbl_descriptionError.setFont(frame.myFont);
		lbl_descriptionError.setBounds(1370, 150, 400, 30);
		lbl_descriptionError.setForeground(Color.red);
		lbl_descriptionError.setVisible(false);

		// phases
		lbl_phases = new JLabel("Phases");
		lbl_phases.setFont(new Font("MyFont", Font.BOLD, 20));
		lbl_phases.setBounds(670, 470, 200, 30);
		txt_phases = new JTextField();
		txt_phases.setFont(frame.myFont);
		txt_phases.setBounds(850, 470, 200, 27);
		lbl_phasesError = new JLabel("Invalid Phase");
		lbl_phasesError.setFont(frame.myFont);
		lbl_phasesError.setBounds(1275, 470, 200, 30);
		lbl_phasesError.setForeground(Color.red);
		lbl_phasesError.setVisible(false);
		lbl_phasesEmptyError = new JLabel("Phases cannot be empty");
		lbl_phasesEmptyError.setFont(frame.myFont);
		lbl_phasesEmptyError.setBounds(1070, 580, 300, 25);
		lbl_phasesEmptyError.setForeground(Color.red);
		lbl_phasesEmptyError.setVisible(false);

		// add phases
		btn_addPhase = new JButton("Add");
		btn_addPhase.setFont(frame.myFont);
		btn_addPhase.setBounds(1070, 470, 85, 25);
		phases = new LinkedList<>();
		phaseData = new DefaultListModel<>();
		lst_phaseList = new JList<String>(phaseData);
		lst_phaseList.setBackground(Color.white);
		lst_phaseList.setFont(frame.myFont);
		scr_phaseList = new JScrollPane(lst_phaseList);
		scr_phaseList.setBounds(850, 520, 200, 170);
		// remove phases
		btn_removePhase = new JButton("Remove");
		btn_removePhase.setFont(frame.myFont);
		btn_removePhase.setBounds(1070, 520, 110, 25);
		// reset phases
		btn_resetPhase = new JButton("Reset");
		btn_resetPhase.setFont(frame.myFont);
		btn_resetPhase.setBounds(1160, 470, 90, 25);

		// members
		lbl_members = new JLabel("Team members");
		lbl_members.setFont(new Font("MyFont", Font.BOLD, 20));
		lbl_members.setBounds(670, 700, 200, 30);
		cmb_memberList = new JComboBox<String>();
		cmb_memberList.setFont(frame.myFont);
		cmb_memberList.setBounds(850, 700, 350, 27);
		cmb_memberList.setBackground(Color.white);
		cmb_memberList.addItem("---");

		// inserting employees into combo box
		allUsers = new UserDB().getAll();
		User temp;
		Iterator<User> it = allUsers.iterator();
		while (it.hasNext()) {
			temp = it.next();
			cmb_memberList.addItem(temp.getUserId() + " - " + temp.getName());
		}

		// adding members
		btn_addMember = new JButton("Add");
		btn_addMember.setFont(frame.myFont);
		btn_addMember.setBounds(1220, 700, 85, 25);
		selectedMembers = new LinkedList<>();
		selectedEmployees = new LinkedList<>();
		memberData = new DefaultListModel<>();
		lst_memberList = new JList<String>(memberData);
		lst_memberList.setBackground(Color.white);
		lst_memberList.setFont(frame.myFont);
		scr_memberList = new JScrollPane(lst_memberList);
		scr_memberList.setBounds(850, 750, 350, 170);

		// removing members
		btn_removeMember = new JButton("Remove");
		btn_removeMember.setFont(frame.myFont);
		btn_removeMember.setBounds(1220, 750, 110, 25);

		// reset members
		btn_resetMember = new JButton("Reset");
		btn_resetMember.setFont(frame.myFont);
		btn_resetMember.setBounds(1310, 700, 90, 25);

		// create project button
		btn_createProject = new JButton("Create Project !!!");
		btn_createProject.setFont(frame.myFont);
		btn_createProject.setBounds(940, 950, 200, 25);

		// adding components

		// back button
		frame.add(btn_back);

		// title
		frame.add(lbl_title);

		// project title
		frame.add(lbl_projectTitle);
		frame.add(txt_projectTitle);
		frame.add(lbl_projectTitleError);

		// description
		frame.add(scr_description);
		frame.add(lbl_description);
		frame.add(lbl_descriptionError);

		// phases
		frame.add(lbl_phases);
		frame.add(txt_phases);
		frame.add(btn_addPhase);
		frame.add(scr_phaseList);
		frame.add(btn_resetPhase);
		frame.add(btn_removePhase);
		frame.add(lbl_phasesError);
		frame.add(lbl_phasesEmptyError);

		// members
		frame.add(lbl_members);
		frame.add(cmb_memberList);
		frame.add(btn_addMember);
		frame.add(btn_removeMember);
		frame.add(btn_resetMember);
		frame.add(scr_memberList);

		// create project button
		frame.add(btn_createProject);

		// frame info
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.white);
		pnl.setBounds(555, 10, 1000, 980);
		pnl.setBorder(new LineBorder(Color.white, 5, true));
		frame.add(pnl);
		frame.add(frame.background);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		// event

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
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// create project
		btn_createProject.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				List<String> result;
				Iterator<String> it;
				String project;
				String desc;
				boolean gate;
				gate = true;

				project = txt_projectTitle.getText().trim();
				desc = txa_description.getText().trim();

				lbl_projectTitleError.setVisible(false);
				lbl_descriptionError.setVisible(false);
				lbl_phasesError.setVisible(false);
				lbl_phasesEmptyError.setVisible(false);

				result = Validation.validateProject(project, desc);
				if (result.size() != 0) {
					gate = false;
					it = result.iterator();
					while (it.hasNext()) {
						String temp = it.next();
						if (temp.equals("Title")) {
							lbl_projectTitleError.setVisible(true);
						}
						if (temp.equals("Description")) {
							lbl_descriptionError.setText("Invalid Description");
							lbl_descriptionError.setVisible(true);
						}
					}
				}
				if (desc.length() > 500) {
					gate = false;
					lbl_descriptionError.setText("Description can have only 500 charcters");
					lbl_descriptionError.setVisible(true);
				}
				if (phases.size() == 0) {
					gate = false;
					lbl_phasesEmptyError.setVisible(true);
				}
				if (gate) {
					List<Phase> list = new LinkedList<Phase>();
					it = phases.iterator();
					while (it.hasNext()) {
						list.add(new Phase(it.next()));
					}

					selectedMembers.add(user.getUserId());

					newProject = new Project(project, desc);
					new ProjectDB().create(newProject, list, selectedMembers);
					new LeaderDashboard(new UserDB().validate(user.getEmail(), user.getPassword()));
					frame.dispose();
				}
			}
		});

		// adding phases
		btn_addPhase.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				lbl_phasesError.setVisible(false);
				String temp;
				temp = txt_phases.getText().trim();
				temp = temp.toUpperCase();
				if (Validation.isName(temp)) {
					if (!phases.contains(temp)) {
						phases.add(temp);
					} else {
						lbl_phasesError.setText("Phase already exists");
						lbl_phasesError.setVisible(true);
						return;
					}
				} else {
					lbl_phasesError.setText("Invalid Phase Name");
					lbl_phasesError.setVisible(true);
					return;
				}
				phaseData.addElement(temp);
				txt_phases.setText("");
				lbl_phasesEmptyError.setVisible(false);

			}

		});
		// removing phases
		btn_removePhase.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selectedIndex;
				selectedIndex = lst_phaseList.getSelectedIndex();
				if (selectedIndex == -1) {

				} else {
					phases.remove(phaseData.get(selectedIndex));
					phaseData.remove(lst_phaseList.getSelectedIndex());
				}

			}
		});

		// resetting phases
		btn_resetPhase.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				phases = new LinkedList<>();
				phaseData.clear();
			}
		});
		// adding members
		btn_addMember.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!(cmb_memberList.getSelectedItem().equals("---"))) {
					selectedMembers.add(Integer.parseInt(((String) cmb_memberList.getSelectedItem()).substring(0, 4)));
				} else {
					return;
				}
				memberData.addElement((String) cmb_memberList.getSelectedItem());
				cmb_memberList.removeItem((String) cmb_memberList.getSelectedItem());
				cmb_memberList.setSelectedIndex(0);

			}

		});
		// removing members
		btn_removeMember.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selectedIndex = lst_memberList.getSelectedIndex();
				if (selectedIndex == -1) {

				} else {
					cmb_memberList.addItem(memberData.get(selectedIndex));
					selectedMembers.remove((Object) Integer.parseInt((memberData.get(selectedIndex)).substring(0, 4)));
					memberData.remove(lst_memberList.getSelectedIndex());
				}

			}
		});
		// resetting members
		btn_resetMember.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				selectedMembers = new LinkedList<>();
				while (memberData.size() != 0) {
					cmb_memberList.addItem(memberData.remove(0));
				}
			}
		});
	}

}
