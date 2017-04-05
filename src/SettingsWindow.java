import java.util.*;
import java.awt.*;

import javax.swing.*;

/**
 * Tester for Fitt's Law
 * 
 * @author Annie Ke, Dartmouth 17S CS67 Human Computer Interaction 
 */
public class SettingsWindow extends JFrame {
	
	private JPanel contentPane; 
	
	public SettingsWindow() {
		setTitle("Settings"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		
		contentPane = new JPanel(); 
		setContentPane(contentPane); 
		contentPane.setLayout(null);
		
		JLabel id = new JLabel("Subject ID:");
		contentPane.add(id);
		
		pack(); 
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsWindow settings = new SettingsWindow(); 
					settings.setVisible(true);
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}
}
