package forms;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import models.*;

public class MemberDashboard {

	// UI components
	ComponentFrame frame;
	JLabel lbl_title, lbl_welcome;
	List<Project> projectList;
	JList<String> lst_projectList;
	DefaultListModel<String> projects;
	JScrollPane scr_projectList;
	Iterator<Project> it;
	// project info
	JLabel lbl_projectInfo, lbl_projectTitle, lbl_projectDescription, lbl_projectLeader, lbl_projectMembers;
	JLabel lbl_projectTitleValue, lbl_projectDescriptionValue, lbl_projectLeaderValue, lbl_memberTemp;
	JButton btn_viewFullProject, btn_profile;
	JPanel pnl_projectMembersValue;
	JScrollPane scr_members;
	List<User> members;
	Iterator<User> it1;

	// profile
	JLabel lbl_profile, lbl_name, lbl_id, lbl_email, lbl_password;
	JLabel lbl_nameValue, lbl_idValue, lbl_emailValue, lbl_passwordValue;

	// logout
	JButton btn_logout;

	MemberDashboard(User user) {
		frame = new ComponentFrame("Dashboard");

		// welcome
		lbl_welcome = new JLabel("Welcome " + user.getName());
		lbl_welcome.setFont(new Font("MyFont", Font.BOLD, 40));
		lbl_welcome.setBounds(750, 90, 700, 60);

		// title
		lbl_title = new JLabel("Projects");
		lbl_title.setFont(new Font("MyFont", Font.BOLD, 30));
		lbl_title.setBounds(230, 100, 300, 40);

		// profile button
		btn_profile = new JButton("Profile");
		btn_profile.setFont(frame.myFont);
		btn_profile.setBounds(1530, 80, 120, 30);

		// logout
		btn_logout = new JButton("Logout");
		btn_logout.setForeground(Color.red);
		btn_logout.setFont(frame.myFont);
		btn_logout.setBounds(1680, 80, 120, 30);

		// profile
		lbl_profile = new JLabel("Profile");
		lbl_profile.setFont(new Font("MyFont", Font.BOLD, 25));
		lbl_profile.setBounds(870, 200, 200, 30);

		lbl_name = new JLabel("Name");
		lbl_name.setFont(new Font("MyFont", Font.BOLD, 20));
		lbl_name.setBounds(730, 290, 200, 30);
		lbl_nameValue = new JLabel(user.getName());
		lbl_nameValue.setFont(frame.myFont);
		lbl_nameValue.setBounds(950, 290, 200, 30);

		lbl_id = new JLabel("User ID");
		lbl_id.setFont(new Font("MyFont", Font.BOLD, 20));
		lbl_id.setBounds(730, 380, 200, 30);
		lbl_idValue = new JLabel(Integer.toString(user.getUserId()));
		lbl_idValue.setFont(frame.myFont);
		lbl_idValue.setBounds(950, 382, 200, 30);

		lbl_email = new JLabel("Email");
		lbl_email.setFont(new Font("MyFont", Font.BOLD, 20));
		lbl_email.setBounds(730, 470, 200, 30);
		lbl_emailValue = new JLabel(user.getEmail());
		lbl_emailValue.setFont(frame.myFont);
		lbl_emailValue.setBounds(950, 470, 200, 30);

		lbl_password = new JLabel("Password");
		lbl_password.setFont(new Font("MyFont", Font.BOLD, 20));
		lbl_password.setBounds(730, 560, 200, 30);
		lbl_passwordValue = new JLabel("Click to change password");
		lbl_passwordValue.setForeground(Color.blue);
		lbl_passwordValue.setFont(frame.myFont);
		lbl_passwordValue.setBounds(950, 560, 400, 30);

		// project list
		projectList = new LinkedList<Project>(new LinkedHashSet<Project>(user.getProjects()));
		projects = new DefaultListModel<>();
		it = projectList.iterator();
		Project temp;
		while (it.hasNext()) {
			temp = it.next();
			projects.addElement("  " + Integer.toString(temp.getProjectId()) + " - " + temp.getTitle());
		}
		lst_projectList = new JList<>(projects);
		lst_projectList.setFont(frame.myFont);
		lst_projectList.setFixedCellHeight(60);
		scr_projectList = new JScrollPane(lst_projectList);
		scr_projectList.setBounds(100, 190, 400, 600);
		scr_projectList.setBorder(null);

		// project info
		lbl_projectInfo = new JLabel("Project Info");
		lbl_projectInfo.setFont(new Font("MyFont", Font.BOLD, 25));
		lbl_projectInfo.setBounds(870, 200, 200, 30);

		lbl_projectTitle = new JLabel("Project Title  ");
		lbl_projectTitle.setFont(new Font("MyFont", Font.BOLD, 20));
		lbl_projectTitle.setBounds(730, 290, 200, 30);
		lbl_projectTitleValue = new JLabel();
		lbl_projectTitleValue.setFont(frame.myFont);
		lbl_projectTitleValue.setBounds(950, 290, 400, 30);

		lbl_projectDescription = new JLabel("Project Description");
		lbl_projectDescription.setFont(new Font("MyFont", Font.BOLD, 20));
		lbl_projectDescription.setBounds(730, 380, 200, 30);
		lbl_projectDescriptionValue = new JLabel();
		lbl_projectDescriptionValue.setVerticalAlignment(1);
		lbl_projectDescriptionValue.setVerticalTextPosition(1);
		lbl_projectDescriptionValue.setFont(frame.myFont);
		lbl_projectDescriptionValue.setBounds(950, 382, 850, 120);

		lbl_projectLeader = new JLabel("Project Leader");
		lbl_projectLeader.setFont(new Font("MyFont", Font.BOLD, 20));
		lbl_projectLeader.setBounds(730, 510, 200, 30);
		lbl_projectLeaderValue = new JLabel();
		lbl_projectLeaderValue.setFont(frame.myFont);
		lbl_projectLeaderValue.setBounds(950, 510, 400, 30);

		lbl_projectMembers = new JLabel("Team Members");
		lbl_projectMembers.setFont(new Font("MyFont", Font.BOLD, 20));
		lbl_projectMembers.setBounds(730, 600, 200, 30);

		pnl_projectMembersValue = new JPanel();
		pnl_projectMembersValue.setSize(400, 280);
		pnl_projectMembersValue.setLayout(new BoxLayout(pnl_projectMembersValue, BoxLayout.Y_AXIS));
		pnl_projectMembersValue.setBackground(Color.white);
		scr_members = new JScrollPane(pnl_projectMembersValue);
		scr_members.setBounds(950, 605, 400, 280);
		scr_members.setBorder(null);

		btn_viewFullProject = new JButton("View Project Details");
		btn_viewFullProject.setFont(frame.myFont);
		btn_viewFullProject.setBounds(930, 900, 230, 30);
		btn_viewFullProject.setVisible(false);

		change(true, false);
		// adding components
		frame.add(lbl_welcome);
		frame.add(lbl_title);
		frame.add(scr_projectList);

		// profile
		frame.add(lbl_profile);
		frame.add(lbl_name);
		frame.add(lbl_nameValue);
		frame.add(lbl_id);
		frame.add(lbl_idValue);
		frame.add(lbl_email);
		frame.add(lbl_emailValue);
		frame.add(lbl_password);
		frame.add(lbl_passwordValue);
		frame.add(btn_logout);

		// project info
		frame.add(lbl_projectInfo);
		frame.add(lbl_projectTitle);
		frame.add(lbl_projectTitleValue);
		frame.add(lbl_projectDescription);
		frame.add(lbl_projectDescriptionValue);
		frame.add(lbl_projectLeader);
		frame.add(lbl_projectLeaderValue);
		frame.add(lbl_projectMembers);
		frame.add(scr_members);
		frame.add(btn_viewFullProject);
		frame.add(btn_profile);

		// frame info
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.white);
		pnl.setBounds(80, 70, 440, 750);
		pnl.setBorder(new LineBorder(Color.white, 5, true));
		JPanel pnl1 = new JPanel();
		pnl1.setBackground(Color.white);
		pnl1.setBounds(600, 60, 1250, 900);
		pnl1.setBorder(new LineBorder(Color.white, 5, true));
		frame.add(pnl);
		frame.add(pnl1);
		frame.add(frame.background);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		// events

		// change password
		lbl_passwordValue.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				new ChangePassword(frame, frame.myFont, user);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_passwordValue.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lbl_passwordValue.setForeground(Color.blue);
			}
		});
		// profile
		btn_profile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					lst_projectList.clearSelection();
				} catch (Exception e1) {

				}

				change(true, false);
			}
		});
		// logout
		btn_logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JDialog confirm = new JDialog(frame, "Are you Sure ? ");
				confirm.setVisible(true);
				confirm.setFont(new Font("MyFont", Font.BOLD, 25));
				confirm.setBounds(800, 400, 400, 200);
				confirm.setLayout(null);

				JLabel lbl_exit = new JLabel("Do you want to Logout ? ");
				lbl_exit.setBounds(50, 30, 250, 30);
				lbl_exit.setFont(frame.myFont);
				JButton btn_yes = new JButton("Yes");
				btn_yes.setFont(frame.myFont);
				btn_yes.setBounds(100, 90, 70, 30);
				JButton btn_no = new JButton("No");
				btn_no.setFont(frame.myFont);
				btn_no.setBounds(185, 90, 60, 30);

				btn_yes.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						new Login();
						frame.dispose();
						confirm.dispose();
					}
				});
				btn_no.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						confirm.dispose();
					}
				});

				confirm.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						confirm.dispose();
					}
				});
				confirm.add(lbl_exit);
				confirm.add(btn_no);
				confirm.add(btn_yes);

			}
		});

		// project info
		lst_projectList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					change(false, true);
					int selectedIndex = lst_projectList.getSelectedIndex();
					lbl_projectTitleValue.setText(projectList.get(selectedIndex).getTitle());
					lbl_projectDescriptionValue
							.setText("<html>" + projectList.get(selectedIndex).getDescription() + "</html>");
					List<User> employeeList = new LinkedList<User>(
							new HashSet<User>(projectList.get(selectedIndex).getEmployees()));
					List<User> userList = new LinkedList<User>(
							new HashSet<User>(projectList.get(selectedIndex).getUsers()));
					Iterator<User> it1 = userList.iterator();
					User leader = null;
					while (it1.hasNext()) {
						leader = it1.next();
						if (!employeeList.contains(leader)) {
							break;
						}
					}
					lbl_projectLeaderValue.setText(leader.getUserId() + " - " + leader.getName());
					members = new LinkedList<User>(new HashSet<User>(projectList.get(selectedIndex).getEmployees()));
					User temp;
					pnl_projectMembersValue.removeAll();
					pnl_projectMembersValue.revalidate();
					pnl_projectMembersValue.repaint();
					it1 = members.iterator();
					while (it1.hasNext()) {
						temp = it1.next();
						lbl_memberTemp = new JLabel(temp.getUserId() + " - " + temp.getName());
						lbl_memberTemp.setFont(frame.myFont);
						pnl_projectMembersValue.add(lbl_memberTemp);
					}
					btn_viewFullProject.setVisible(true);

				}
			}
		});
		// view project details
		btn_viewFullProject.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MemberProjectInfo(user, projectList.get(lst_projectList.getSelectedIndex()));
				frame.dispose();
			}
		});

		// main window
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	public static ArrayList<String> update() {
		ArrayList<String> projects = new ArrayList<String>();
		return (projects);
	}

	public void change(boolean one, boolean two) {
		lbl_profile.setVisible(one);
		lbl_name.setVisible(one);
		lbl_nameValue.setVisible(one);
		lbl_id.setVisible(one);
		lbl_idValue.setVisible(one);
		lbl_email.setVisible(one);
		lbl_emailValue.setVisible(one);
		lbl_password.setVisible(one);
		lbl_passwordValue.setVisible(one);

		lbl_projectInfo.setVisible(two);
		lbl_projectTitle.setVisible(two);
		lbl_projectTitleValue.setVisible(two);
		lbl_projectDescription.setVisible(two);
		lbl_projectDescriptionValue.setVisible(two);
		lbl_projectLeader.setVisible(two);
		lbl_projectLeaderValue.setVisible(two);
		lbl_projectMembers.setVisible(two);
		pnl_projectMembersValue.setVisible(two);
		scr_members.setVisible(two);
		btn_viewFullProject.setVisible(two);
	}

}
