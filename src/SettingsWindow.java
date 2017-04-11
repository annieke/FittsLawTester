import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

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
	int maxAmp = 0; 
	
	private JLabel widl;
	private JSpinner wids; 
	private JButton addWid; 
	private JButton delWid;
	private JScrollPane widscroll;
	private DefaultListModel<Integer> widmodel; 
	private JList<Integer> widlist;
	
	private JLabel indl;
	private JScrollPane indscroll;
	private SortedListModel<Double> indmodel;
	private JList<Double> indlist; 
	
	private JLabel totall;
	private JButton ok;
	private JButton cancel;
	private JLabel outBoundsNote; 
	
	private JButton visualizer; 
	
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
				if (!ampmodel.contains((Integer) amps.getValue()) 
						&& (Integer)amps.getValue() >= 0 && (Integer)amps.getValue() < 650) {
					ampmodel.addElement((Integer) amps.getValue());
					if ((Integer)amps.getValue() > maxAmp) maxAmp = (Integer)amps.getValue();
					totall.setText("Total trials: " 
						+ Integer.toString((int)trials.getValue() * ampmodel.size() * widmodel.size()));
					updateIndices();
				}
			}
		});
		delAmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!amplist.isSelectionEmpty()) {
					totall.setText("Total trials: " 
							+ Integer.toString((int)trials.getValue() * ampmodel.size() * widmodel.size()));
					ampmodel.remove(amplist.getSelectedIndex());
					updateIndices();
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
				if (!widmodel.contains((Integer) wids.getValue()) 
						&& (Integer)wids.getValue() > 0 && (Integer)wids.getValue() < 650
						&& (Integer)wids.getValue()+maxAmp < 650) {
					widmodel.addElement((Integer)wids.getValue());
					totall.setText("Total trials: " 
							+ Integer.toString((int)trials.getValue() * ampmodel.size() * widmodel.size()));
					updateIndices();
				}
			}
		});
		delWid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!widlist.isSelectionEmpty()) {
					widmodel.remove(widlist.getSelectedIndex());
					totall.setText("Total trials: " 
							+ Integer.toString((int)trials.getValue() * ampmodel.size() * widmodel.size()));
					updateIndices(); 
				}
			}
		});
		
		indl = new JLabel("Indices of Difficulty:");
		indscroll = new JScrollPane();
		indmodel = new SortedListModel<Double>();
		indlist = new JList<Double>(indmodel); 
		indscroll.setViewportView(indlist);
		
		totall = new JLabel("Total trials: "); 
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = (int) ids.getValue();
				int trial = (int) trials.getValue();
				TrialWindow trialframe = new TrialWindow(id, trial, ampmodel, widmodel);
				trialframe.setVisible(true);
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
		
		outBoundsNote = new JLabel("*Out-of-bounds values won't add.");
		
		visualizer = new JButton("Select file to visualize"); 
		visualizer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File workingDirectory = new File(System.getProperty("user.dir"));
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(workingDirectory);
				int retVal = fileChooser.showOpenDialog(null);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile(); 

					VisualizerWindow visualizer = new VisualizerWindow(selectedFile);
					visualizer.setVisible(true);
					setVisible(false);
					dispose();
				}
			}
		});
		
		setLayout();
		add(contentPane);
		pack(); 
		
	}
	
	private void updateIndices() {
		indmodel = new SortedListModel<Double>(); 
		indlist.setModel(indmodel);
		for (int i = 0; i < ampmodel.size(); i++) {
			for (int j = 0; j < widmodel.size(); j++) {
				double index = Math.log(2*ampmodel.getElementAt(i)/widmodel.getElementAt(j))/Math.log(2);
				indmodel.add(index);
			}
		}
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
	                    .addComponent(totall)
	                    .addComponent(outBoundsNote)
	                    .addComponent(visualizer))
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
			    .addComponent(outBoundsNote)
			    .addComponent(visualizer));
		
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

/*
 * SortedListModeled adapted from java2s.com
 */
class SortedListModel<Double> extends AbstractListModel<Double> {
	SortedSet<Double> model;

	public SortedListModel() {
		model = new TreeSet<Double>();
	}
	
	public int getSize() {
		return model.size();
	}

	public Double getElementAt(int index) {
	    return (Double)model.toArray()[index];
	}

	public void add(Double element) {
	    if (model.add(element)) {
	    	fireContentsChanged(this, 0, getSize());
	  }
	}
	
	public void addAll(Double elements[]) {
		Collection<Double> c = Arrays.asList(elements);
	    model.addAll(c);
	    fireContentsChanged(this, 0, getSize());
	}

	public void clear() {
		model.clear();
	    fireContentsChanged(this, 0, getSize());
	}

	public boolean contains(Double element) {
		return model.contains(element);
	}

	public Double firstElement() {
		return model.first();
	}

	public Iterator iterator() {
		return model.iterator();
	}

	public Double lastElement() {
		return model.last();
	}

	public boolean removeElement(Double element) {
		boolean removed = model.remove(element);
	    if (removed) {
	      fireContentsChanged(this, 0, getSize());
	    }
	    return removed;
	}
}
