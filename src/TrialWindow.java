import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Trial window for Fitt's Law Tester
 * 
 * @author Annie Ke, Dartmouth 17S CS67 Human Computer Interaction 
 */
public class TrialWindow extends JFrame {
	private ArrayList<Condition> conditions;
	private static final int width = 700, height = 700;
	private static final int startWidth = 38;
	private boolean startPressed = false; 
	private boolean targetPressed = false; 
	int trial = 0;
	
	JComponent trialArea; 
	Graphics currentGraphics; 
	JButton start; 
	JLabel trialsLeft;
	JButton target; 
	
	public TrialWindow(int id, int trial, ListModel ampmodel, ListModel widmodel) {
		setTitle("Trials"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		conditions = new ArrayList<Condition>();
		for (int i = 0; i < ampmodel.getSize(); i++) {
			for (int j = 0; j < widmodel.getSize(); j++) {
				for (int k = 0; k < trial; k++) {
					Condition condition = 
							new Condition((int)ampmodel.getElementAt(i), (int)widmodel.getElementAt(j));
					conditions.add(condition);
				}
			}
		}
		Collections.shuffle(conditions);
		trialsLeft = new JLabel(Integer.toString(conditions.size()) + " trial(s) left");
		
		setupTrialArea(); 
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(trialArea, BorderLayout.CENTER);
		cp.add(trialsLeft, BorderLayout.NORTH);
		pack();
	}
	
	private void setupTrialArea() {
		trialArea = new JPanel(); 
		trialArea.setPreferredSize(new Dimension(width, height));
		trialArea.setLayout(null);
		
		start = new JButton("start");
		start.setBounds(0, height-startWidth, startWidth, startWidth);
		start.setMargin(new Insets(0, 0, 0, 0));
		start.setBackground(Color.BLACK);
		start.setForeground(Color.WHITE);
		start.setOpaque(true);
		start.setBorder(null);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start.setBackground(Color.WHITE);
				start.setForeground(Color.BLACK);
				runTrial(trial); 
				trial++;
			}
		});
		trialArea.add(start);
	}
	
	private void runTrial(int i) {
		if (i < conditions.size()) {
			Condition condition = conditions.get(i);
			target = new JButton();
			double randomAngle = Math.random() * Math.PI/2; 
			int tarX = (int) (Math.sin(randomAngle)*condition.amp); 
			int tarY = (int) (Math.cos(randomAngle)*condition.amp);
			target.setBounds(tarX, height-startWidth-tarY, condition.wid, condition.wid);
			target.setBackground(Color.GREEN);
			target.setMargin(new Insets(0, 0, 0, 0));
			target.setOpaque(true);
			target.setBorder(null);
			target.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					trialArea.remove(target);
					start.setBackground(Color.BLACK);
					start.setForeground(Color.WHITE);
					if (i < conditions.size() - 1) {
						trialsLeft.setText(Integer.toString(conditions.size()-trial) 
								+ " trial(s) left");
					} else {
						trialsLeft.setText("No trials left, click start again to exit");
					}
					trialArea.repaint();
				}
			});
			trialArea.add(target);
			trialArea.repaint();
		} else {
			this.setVisible(false);
			this.dispose();
		}
	}

}


class Condition {
	int amp; 
	int wid;
	
	public Condition(int amp, int wid) {
		this.amp = amp; 
		this.wid = wid;
	}
}
