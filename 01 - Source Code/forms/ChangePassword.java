package forms;

import validation.*;
import models.User;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;

import dao.UserDB;

@SuppressWarnings("serial")
public class ChangePassword extends JDialog {

	ChangePassword(JFrame frame, Font myFont, User user) {
		JLabel lbl_old, lbl_new, lbl_confirm, lbl_error, lbl_condition, lbl_condition1;
		JPasswordField txt_old, txt_new, txt_confirm;
		JButton btn_change;
		JDialog change = new JDialog(frame, "Change Password");

		change.getContentPane().setBackground(Color.white);
		change.setVisible(true);
		change.setFont(new Font("MyFont", Font.BOLD, 25));
		change.setBounds(600, 250, 700, 600);
		change.setLayout(null);

		lbl_old = new JLabel("Old Password");
		lbl_old.setFont(myFont);
		lbl_old.setBounds(50, 80, 250, 25);

		lbl_new = new JLabel("New Password");
		lbl_new.setFont(myFont);
		lbl_new.setBounds(50, 150, 250, 25);

		lbl_confirm = new JLabel("Re-enter New Password");
		lbl_confirm.setFont(myFont);
		lbl_confirm.setBounds(50, 220, 250, 25);

		txt_old = new JPasswordField();
		txt_old.setFont(myFont);
		txt_old.setBounds(300, 80, 250, 25);

		txt_new = new JPasswordField();
		txt_new.setFont(myFont);
		txt_new.setBounds(300, 150, 250, 25);

		txt_confirm = new JPasswordField();
		txt_confirm.setFont(myFont);
		txt_confirm.setBounds(300, 220, 250, 25);

		btn_change = new JButton("Change");
		btn_change.setFont(myFont);
		btn_change.setBounds(275, 300, 150, 30);

		lbl_error = new JLabel();
		lbl_error.setFont(myFont);
		lbl_error.setBounds(0, 350, 700, 30);
		lbl_error.setHorizontalAlignment(JLabel.CENTER);

		lbl_condition = new JLabel("Note : Password should contain a minimum of 8 characters, atleast a");
		lbl_condition.setBounds(50, 390, 650, 30);
		lbl_condition.setFont(myFont);

		lbl_condition1 = new JLabel("  capital letter, a small letter, a special character, a number .");
		lbl_condition1.setBounds(50, 435, 650, 30);
		lbl_condition1.setFont(myFont);

		change.add(lbl_old);
		change.add(lbl_new);
		change.add(lbl_confirm);

		change.add(txt_old);
		change.add(txt_new);
		change.add(txt_confirm);

		change.add(btn_change);
		change.add(lbl_error);
		change.add(lbl_condition);
		change.add(lbl_condition1);

		btn_change.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				lbl_error.setVisible(false);
			}
		});

		btn_change.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String old = txt_old.getText().trim();
				String neww = txt_new.getText().trim();
				String confirm = txt_confirm.getText().trim();

				if (!old.equals(user.getPassword())) {
					lbl_error.setForeground(Color.red);
					lbl_error.setText("Incorrect old password");
					lbl_error.setVisible(true);
					return;
				}
				if (!Validation.isStrongPassword(neww)) {
					lbl_error.setForeground(Color.red);
					lbl_error.setText("Invalid new password");
					lbl_error.setVisible(true);
					return;
				}
				if (old.equals(neww)) {
					lbl_error.setForeground(Color.red);
					lbl_error.setText("New Password should not be your old password");
					lbl_error.setVisible(true);
					return;
				}

				if (!neww.equals(confirm)) {
					lbl_error.setForeground(Color.red);
					lbl_error.setText("Password not matching");
					lbl_error.setVisible(true);
					return;
				}
				if (new UserDB().changePassword(user.getUserId(), old, confirm)) {
					user.setPassword(confirm);
					lbl_error.setForeground(Color.green);
					lbl_error.setText("Password changed successfully !!!");
					lbl_error.setVisible(true);
				}

			}
		});
	}
}
