package forms;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import dao.*;
import javax.swing.*;
import models.*;

public class LeaderProjectInfo {

	// UI Components
	JFrame frame;
	JPanel pnl_tasks;
	JTabbedPane tab_container;
	JPanel temp;
	Phase phase;

	// phases
	List<Phase> phaseList;
	Set<Phase> tempList;
	Map<Phase, List<Task>> map;

	// back button
	JButton btn_back;
	List<JButton> backButtons;
	
	LeaderProjectInfo(User user, Project project) {
		frame = new JFrame("Project");
		// tab pane
		tab_container = new JTabbedPane();
		tab_container.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));


		// phases
		
		map = new TaskDB().getAll(project.getProjectId());
		tempList = project.getPhases();
		phaseList = new LinkedList<Phase>(tempList);
		Collections.sort(phaseList, new Comparator<Phase>() {

			@Override
			public int compare(Phase phase1, Phase phase2) {
				if (phase1.getPhaseId() > phase2.getPhaseId()) {
					return (1);
				}
				if (phase1.getPhaseId() == phase2.getPhaseId()) {
					return 0;
				}
				return (-1);
			}
		});
		backButtons = new LinkedList<JButton>();
		Iterator<Phase> it = phaseList.iterator();
		while (it.hasNext()) {
			phase = it.next();
			btn_back = new JButton("Back");
			btn_back.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
			btn_back.setBounds(1800, 35, 90, 30);
			btn_back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new LeaderDashboard(new UserDB().validate(user.getEmail(),user.getPassword()));
					frame.dispose();	
				}
			});
			temp = new ViewTasks(map, phase,btn_back);
			backButtons.add(btn_back);
			temp.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
			tab_container.add(phase.getPhaseId() + " - " + phase.getName(), temp);

		}

		// adding components
		frame.add(tab_container);
		
		// frame info
		frame.getContentPane().setBackground(Color.white);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		
		// window
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);}
		});
	}

}
