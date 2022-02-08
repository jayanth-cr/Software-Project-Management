package forms;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import validation.Validation;
import java.util.*;
import dao.*;
import models.*;

public class Register {
	
	// UI components
	ComponentFrame frame;
	JLabel lbl_name, lbl_email, lbl_designation, lbl_password, lbl_confirmPassword, lbl_loginTitle;
	JLabel lbl_passwordCondition,lbl_passwordCondition1;
	JLabel lbl_nameError, lbl_emailError, lbl_passwordError, lbl_confirmPasswordError, lbl_registerMessage;
	JTextField txt_name, txt_email,  txt_designation;
	JPasswordField txt_password, txt_confirmPassword;
	JButton btn_register;
	ButtonGroup designation;
	JRadioButton rad_leader, rad_member;

	// validation
	List<String> result;
	
	// database
	int userId, userDesignation;
	String name, email, password;
	User user;
	
	Register() {
		frame = new ComponentFrame("Register");

		// title
		lbl_loginTitle = new JLabel("Create New Account");
		lbl_loginTitle.setFont(new Font("MyFont",Font.BOLD, 28));
		lbl_loginTitle.setBounds(810, 160, 300, 40);
		
		// name
		lbl_name = new JLabel("Full Name");
		lbl_name.setBounds(700, 270, 200, 30);
		lbl_name.setFont(frame.myFont);
		txt_name = new JTextField();
		txt_name.setBounds(900, 270, 300, 27);
		txt_name.setFont(frame.myFont);
		lbl_nameError = new JLabel("Invalid Name !");
		lbl_nameError.setVisible(false);
		lbl_nameError.setFont(frame.myFont);
		lbl_nameError.setForeground(Color.RED);
		lbl_nameError.setBounds(1210, 270, 200, 30);

		// email
		lbl_email = new JLabel("Email");
		lbl_email.setBounds(700, 330, 200, 30);
		lbl_email.setFont(frame.myFont);
		txt_email = new JTextField();
		txt_email.setBounds(900, 330, 300, 27);
		txt_email.setFont(frame.myFont);
		lbl_emailError = new JLabel("Invalid Email !");
		lbl_emailError.setVisible(false);
		lbl_emailError.setFont(frame.myFont);
		lbl_emailError.setForeground(Color.RED);
		lbl_emailError.setBounds(1210, 330, 200, 30);

		// designation
		lbl_designation = new JLabel("Designation");
		lbl_designation.setBounds(700, 390, 200, 30);
		lbl_designation.setFont(frame.myFont);
		designation = new ButtonGroup();
		rad_leader = new JRadioButton("Project Leader");
		rad_leader.setFont(frame.myFont);
		rad_leader.setBounds(895, 390, 170, 27);
		rad_leader.setBackground(Color.white);
		rad_member = new JRadioButton("Team Member");
		rad_member.setFont(frame.myFont);
		rad_member.setBounds(1060, 390, 200, 27);
		rad_member.setSelected(true);
		rad_member.setBackground(Color.white);
		designation.add(rad_member);
		designation.add(rad_leader);

		// password
		lbl_password = new JLabel("Password");
		lbl_password.setBounds(700, 450, 200, 30);
		lbl_password.setFont(frame.myFont);
		txt_password = new JPasswordField();
		txt_password.setBounds(900, 450, 300, 27);
		txt_password.setFont(frame.myFont);
		lbl_passwordError = new JLabel("Invalid Password ! ");
		lbl_passwordError.setVisible(false);
		lbl_passwordError.setFont(frame.myFont);
		lbl_passwordError.setForeground(Color.RED);
		lbl_passwordError.setBounds(1210, 450, 200, 30);
		lbl_passwordCondition = new JLabel("Note :    Password should contain a minimum of 8 characters, atleast a");
		lbl_passwordCondition.setFont(frame.myFont);
		lbl_passwordCondition.setBounds(700, 650, 1300, 30);
		lbl_passwordCondition1 = new JLabel("   capital letter, a small letter, a special character, a number .");
		lbl_passwordCondition1.setFont(frame.myFont);
		lbl_passwordCondition1.setBounds(760, 680, 1300, 30);
		
		// confirm password
		lbl_confirmPassword = new JLabel("Confirm Password");
		lbl_confirmPassword.setBounds(700, 510, 200, 30);
		lbl_confirmPassword.setFont(frame.myFont);
		txt_confirmPassword = new JPasswordField();
		txt_confirmPassword.setBounds(900, 510, 300, 27);
		txt_confirmPassword.setFont(frame.myFont);
		lbl_confirmPasswordError = new JLabel("Password Not Matching !");
		lbl_confirmPasswordError.setVisible(false);
		lbl_confirmPasswordError.setFont(frame.myFont);
		lbl_confirmPasswordError.setForeground(Color.RED);
		lbl_confirmPasswordError.setBounds(1210, 510, 250, 30);

		// register
		btn_register = new JButton("Register");
		btn_register.setBounds(900, 580, 150, 30);
		btn_register.setFont(frame.myFont);
		lbl_registerMessage = new JLabel("Registered Successfully !!!");
		lbl_registerMessage.setFont(frame.myFont);
		lbl_registerMessage.setBounds(920, 580, 200, 30);
		lbl_registerMessage.setVisible(false);
		
		// adding the components
		// title
		frame.add(lbl_loginTitle);
		// name
		frame.add(lbl_name);
		frame.add(txt_name);
		frame.add(lbl_nameError);

		// email
		frame.add(lbl_email);
		frame.add(txt_email);
		frame.add(lbl_emailError);

		// designation
		frame.add(lbl_designation);
		frame.add(rad_leader);
		frame.add(rad_member);

		// password
		frame.add(lbl_password);
		frame.add(txt_password);
		frame.add(lbl_passwordError);
		frame.add(lbl_passwordCondition);
		frame.add(lbl_passwordCondition1);

		// confirm password
		frame.add(lbl_confirmPassword);
		frame.add(txt_confirmPassword);
		frame.add(lbl_confirmPasswordError);

		// register button
		frame.add(btn_register);
		frame.add(lbl_registerMessage);
		
		
		// background
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.white);
		pnl.setBounds(510, 110, 950, 670);
		pnl.setBorder(new LineBorder(Color.white, 5, true));
		frame.add(pnl);
		frame.add(frame.background);
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);

		// validation
		btn_register.addMouseListener(new MouseAdapter() {

			@SuppressWarnings("deprecation")
			@Override
			public void mousePressed(MouseEvent e) {

				lbl_nameError.setVisible(false);
				lbl_emailError.setVisible(false);
				lbl_passwordError.setVisible(false);
				lbl_confirmPasswordError.setVisible(false);
				lbl_registerMessage.setVisible(false);
				
				
				userId = 0;
				name = txt_name.getText();
				email = txt_email.getText();
				password = txt_password.getText();
				if( rad_leader.isSelected() ) {
					userDesignation = 1;
				}
				else {
					userDesignation = 0;
				}
				
				result = Validation.validateUser(name, email , password);
				if (!(result.size() == 0)) {
					if (result.contains("Name")) {
						lbl_nameError.setVisible(true);

					}
					if (result.contains("Email")) {
						lbl_emailError.setVisible(true);

					}
					if (result.contains("Password")) {
						lbl_passwordError.setVisible(true);

					}

					return;
				}
				
				if (!(password.equals(txt_confirmPassword.getText()))) {
					lbl_confirmPasswordError.setVisible(true);
					return;
				}
				new UserDB().save( new User(name, email, password, userDesignation) );	
				lbl_registerMessage.setVisible(true);
				new Login();
				frame.dispose();
			}

		});
		// events 
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		} );

	}
	
	
	

}
