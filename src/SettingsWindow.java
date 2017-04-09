import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * Settings Window for Fitt's Law Tester
 * 
 * @author Annie Ke, Dartmouth 17S CS67 Human Computer Interaction 
 */
public class SettingsWindow extends JFrame {
	
	private JPanel contentPane; 
	private GroupLayout layout;
	
	private JLabel idl; 
	private JSpinner ids;
	
	private JLabel triall; 
	private JSpinner trials; 
	
	private JLabel ampl;
	private JSpinner amps;
	private JButton addAmp;
	private JButton delAmp;
	private JScrollPane ampscroll;
	private DefaultListModel<Integer> ampmodel;
	private JList<Integer> amplist;
	
	private JLabel widl;
	private JSpinner wids; 
	private JButton addWid; 
	private JButton delWid;
	private JScrollPane widscroll;
	private DefaultListModel<Integer> widmodel; 
	private JList<Integer> widlist;
	
	private JLabel indl;
	private JScrollPane indscroll;
	private DefaultListModel<Double> indmodel;
	private JList<Double> indlist; 
	
	private JLabel totall;
	private JButton ok;
	private JButton cancel;
	
	
	public SettingsWindow() {
		setTitle("Settings"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		contentPane = new JPanel(); 	
		layout = new GroupLayout(contentPane);
		contentPane.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		idl = new JLabel("Subject ID:");
		ids = new JSpinner();
		
		triall = new JLabel("Trials per condition:");
		trials = new JSpinner(); 
		JComponent comp = trials.getEditor();
	    JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
	    DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
	    formatter.setCommitsOnValidEdit(true);
	    trials.addChangeListener(new ChangeListener(){
	        @Override
	        public void stateChanged(ChangeEvent e) {
	        	totall.setText("Total trials: " 
						+ Integer.toString((int)trials.getValue() * ampmodel.size() * widmodel.size()));
	        }
	    });
		
		ampl = new JLabel("Amplitudes: (pixels)");
		amps = new JSpinner(); 
		addAmp = new JButton("+"); 
		delAmp = new JButton("-");
		ampscroll = new JScrollPane();
		ampmodel = new DefaultListModel<Integer>();
		amplist = new JList<Integer>(ampmodel); 
		ampscroll.setViewportView(amplist);
		addAmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!ampmodel.contains((Integer) amps.getValue()) && (Integer)wids.getValue() >= 0) {
					ampmodel.addElement((Integer) amps.getValue());
					totall.setText("Total trials: " 
						+ Integer.toString((int)trials.getValue() * ampmodel.size() * widmodel.size()));
				}
			}
		});
		delAmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!amplist.isSelectionEmpty()) {
					totall.setText("Total trials: " 
							+ Integer.toString((int)trials.getValue() * ampmodel.size() * widmodel.size()));
					ampmodel.remove(amplist.getSelectedIndex());
				}
			}
		});
		
		widl = new JLabel("Widths: (pixels)");
		wids = new JSpinner(); 
		addWid = new JButton("+"); 
		delWid = new JButton("-");
		widscroll = new JScrollPane();
		widmodel = new DefaultListModel<Integer>(); 
		widlist = new JList<Integer>(widmodel); 
		widscroll.setViewportView(widlist);
		addWid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!widmodel.contains((Integer) wids.getValue()) && (Integer)wids.getValue() > 0) {
					widmodel.addElement((Integer) wids.getValue());
					totall.setText("Total trials: " 
							+ Integer.toString((int)trials.getValue() * ampmodel.size() * widmodel.size()));
				}
			}
		});
		delWid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!widlist.isSelectionEmpty()) {
					widmodel.remove(widlist.getSelectedIndex());
					totall.setText("Total trials: " 
							+ Integer.toString((int)trials.getValue() * ampmodel.size() * widmodel.size()));
				}
			}
		});
		
		indl = new JLabel("Indices of Difficulty:");
		indscroll = new JScrollPane();
		indmodel = new DefaultListModel<Double>();
		indlist = new JList(indmodel); 
		indscroll.setViewportView(indlist);
		
		totall = new JLabel("Total trials: "); 
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		cancel = new JButton("Cancel"); 
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		setLayout();
		add(contentPane);
		pack(); 
		
	}
	
	private void setLayout() {
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
