package forms;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;

import models.User;
import dao.UserDB;
import validation.Validation;

public class Login {
	// UI components
	ComponentFrame frame;
	JLabel lbl_credentialError, lbl_loginTitle, lbl_email, lbl_password, lbl_createNewAccount, lbl_createNewAccount1,
			lbl_emailError, lbl_passwordError;
	JTextField txt_email;
	JPasswordField txt_password;
	JButton btn_login;

	// validation
	List<String> result;

	// database
	User user;

	Login() {
		frame = new ComponentFrame("Login");
		

		
		// login title
		lbl_loginTitle = new JLabel("Welcome !!!");
		lbl_loginTitle.setBounds(820, 250, 300, 50);
		lbl_loginTitle.setFont(new Font("MyFont", Font.BOLD, 28));

		// email
		lbl_email = new JLabel("Email");
		lbl_email.setBounds(700, 350, 200, 30);
		lbl_email.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
		txt_email = new JTextField();
		txt_email.setBounds(850, 350, 300, 27);
		txt_email.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
		lbl_emailError = new JLabel("Invalid Email !");
		lbl_emailError.setVisible(false);
		lbl_emailError.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
		lbl_emailError.setForeground(Color.RED);
		lbl_emailError.setBounds(1160, 350, 200, 30);

		// password
		lbl_password = new JLabel("Password");
		lbl_password.setBounds(700, 400, 200, 30);
		lbl_password.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
		txt_password = new JPasswordField();
		txt_password.setBounds(850, 400, 300, 27);
		txt_password.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
		lbl_passwordError = new JLabel("Invalid Password !");
		lbl_passwordError.setVisible(false);
		lbl_passwordError.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
		lbl_passwordError.setForeground(Color.RED);
		lbl_passwordError.setBounds(1160, 400, 200, 30);

		// login button
		btn_login = new JButton("Sign In");
		btn_login.setBounds(850, 460, 100, 30);
		btn_login.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));

		// credential error
		lbl_credentialError = new JLabel("Invalid Login Credentials");
		lbl_credentialError.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
		lbl_credentialError.setForeground(Color.red);
		lbl_credentialError.setBounds(970, 460, 300, 30);
		lbl_credentialError.setVisible(false);

		// create new account
		lbl_createNewAccount = new JLabel("Don't have an account ? ");
		lbl_createNewAccount1 = new JLabel("Create New Account");
		lbl_createNewAccount.setBounds(700, 520, 300, 30);
		lbl_createNewAccount.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
		lbl_createNewAccount1.setBounds(930, 520, 200, 30);
		lbl_createNewAccount1.setFont(new Font("MyFont", Font.ROMAN_BASELINE, 20));
		lbl_createNewAccount1.setForeground(Color.BLUE);

		// adding components
		frame.add(lbl_loginTitle);
		frame.add(lbl_email);
		frame.add(lbl_password);
		frame.add(txt_email);
		frame.add(txt_password);
		frame.add(btn_login);
		frame.add(lbl_credentialError);
		frame.add(lbl_createNewAccount1);
		frame.add(lbl_createNewAccount);
		frame.add(lbl_emailError);
		frame.add(lbl_passwordError);

		
		// frame info
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.white);
		pnl.setBounds(600, 200, 750, 450);
		pnl.setBorder(new LineBorder(Color.white, 5, true));
		frame.add(pnl);
		frame.add(frame.background);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setLayout(null);

		// validation
		lbl_createNewAccount1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl_createNewAccount1.setForeground(Color.BLACK);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lbl_createNewAccount1.setForeground(Color.blue);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				new Register();
				frame.dispose();
			}

		});

		btn_login.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mousePressed(MouseEvent e) {
				lbl_emailError.setVisible(false);
				lbl_passwordError.setVisible(false);
				lbl_credentialError.setVisible(false);
				result = Validation.validateUser(txt_email.getText(), txt_password.getText());
				if (result.size() != 0) {
					if (result.contains("Email")) {
						lbl_emailError.setVisible(true);
					}
					if (result.contains("Password")) {
						lbl_passwordError.setVisible(true);
					}
					return;
				}
				user = new UserDB().validate(txt_email.getText(), txt_password.getText());
				if (user == null) {
					lbl_credentialError.setVisible(true);
				} else {
					if (user.getDesignation() == 1) {
						new LeaderDashboard(user);
					} else {
						new MemberDashboard(user);
					}
					frame.dispose();
				}
			}
		});

	}

	public static void main(String args[]) {
		new Login();
	}
}
