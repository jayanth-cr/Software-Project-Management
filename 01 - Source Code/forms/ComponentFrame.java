package forms;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
@SuppressWarnings("serial")
public class ComponentFrame extends JFrame{
	Font myFont;
	JLabel background;
	String path = "";
	ComponentFrame(String title){
		// Font
		myFont = new Font("MyFont",Font.ROMAN_BASELINE, 20);
		setTitle(title);
		setLayout(null);
		getContentPane().setBackground(Color.white);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader("..\\03 - Background Image\\path.txt"));
			path = reader.readLine();
		} catch (Exception e) {
			System.out.println("File Not Found !!!");
		} 
		ImageIcon img = new  ImageIcon(path);
		
		background = new JLabel("",img,JLabel.CENTER);
		background.setBounds(0, 0, 2000, 1010);
		
		
	}

}
