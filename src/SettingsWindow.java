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
		GroupLayout layout = new GroupLayout(contentPane);
		contentPane.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		JLabel idl = new JLabel("Subject ID:");
		JTextField idtf = new JTextField("ID", 5);
		
		JLabel triall = new JLabel("Trials per condition:");
		JTextField trialtf = new JTextField("Trials", 5); 
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(idl)
					.addComponent(idtf))
				.addGroup(layout.createSequentialGroup()
						.addComponent(triall)
						.addComponent(trialtf))
			)
		);
		
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(idl)
						.addComponent(idtf)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(triall)
						.addComponent(trialtf)
				)
		);
		add(contentPane);
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
