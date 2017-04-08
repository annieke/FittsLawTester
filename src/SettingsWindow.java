import java.util.*;
import java.awt.*;
import java.awt.event.*;

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
		JSpinner ids = new JSpinner();
		
		JLabel triall = new JLabel("Trials per condition:");
		JSpinner trials = new JSpinner(); 
		
		JLabel ampl = new JLabel("Amplitudes: (pixels)");
		JSpinner amps = new JSpinner(); 
		JButton addAmp = new JButton("+"); 
		JButton delAmp = new JButton("-");
		JScrollPane ampscroll = new JScrollPane();
		DefaultListModel<Integer> ampmodel = new DefaultListModel<Integer>();
		JList<Integer> amplist = new JList<Integer>(ampmodel); 
		ampscroll.setViewportView(amplist);
		addAmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ampmodel.addElement((Integer) amps.getValue());
			}
		});
		
		JLabel widl = new JLabel("Widths: (pixels)");
		JSpinner wids = new JSpinner(); 
		JButton addWid = new JButton("+"); 
		JButton delWid = new JButton("-");
		JScrollPane widscroll = new JScrollPane();
		DefaultListModel<Integer> widmodel = new DefaultListModel<Integer>(); 
		JList<Integer> widlist = new JList<Integer>(widmodel); 
		widscroll.setViewportView(widlist);
		addWid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				widmodel.addElement((Integer) wids.getValue());
			}
		});
		
		JLabel indl = new JLabel("Indices of Difficulty:");
		JScrollPane indscroll = new JScrollPane();
		JList indlist = new JList(new DefaultListModel()); 
		indscroll.setViewportView(indlist);
		
		JLabel totall = new JLabel("Total trials: "); 
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		JButton cancel = new JButton("Cancel"); 
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addComponent(idl)
					.addComponent(ids, 50, 80, 100))
				.addGroup(layout.createSequentialGroup()
					.addComponent(triall)
					.addComponent(trials, 50, 80, 100))
				.addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    	.addComponent(ampl)
                        .addGroup(layout.createSequentialGroup()
                        	.addComponent(amps)
                            .addComponent(addAmp)
                            .addComponent(delAmp))
                        .addComponent(ampscroll)
                        .addComponent(totall))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    	.addComponent(widl)
                    	.addGroup(layout.createSequentialGroup()
                    		.addComponent(wids)
                            .addComponent(addWid)
                            .addComponent(delWid))
                        .addComponent(widscroll))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                	.addComponent(indl)
                    .addComponent(indscroll)
                    .addGroup(layout.createSequentialGroup()
                    	.addComponent(ok)
                    	.addComponent(cancel)))))
		);
		
		
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(idl)
				.addComponent(ids))
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(triall)
				.addComponent(trials))
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(ampl)
				.addComponent(widl)
				.addComponent(indl))
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		    	.addComponent(amps)
		    	.addComponent(addAmp)
		    	.addComponent(delAmp)
		    	.addComponent(wids)
		    	.addComponent(addWid)
		    	.addComponent(delWid))
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		    	.addComponent(ampscroll)
		    	.addComponent(widscroll)
		    	.addComponent(indscroll))
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		    	.addComponent(totall)
		    	.addComponent(ok)
		    	.addComponent(cancel))
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
