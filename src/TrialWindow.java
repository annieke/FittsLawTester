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
	private static final int width = 750, height = 750;
	private boolean startPressed = false; 
	private boolean targetPressed = false; 
	
	JComponent trialArea; 
	Graphics currentGraphics; 
	JButton start; 
	
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
		
		setupTrialArea(); 
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(trialArea, BorderLayout.CENTER);
		pack();
		
	}
	
	private void setupTrialArea() {
		trialArea = new JPanel(); 
		trialArea.setPreferredSize(new Dimension(width, height));
		trialArea.setLayout(null);
		
		start = new JButton("start");
		start.setBounds(50, height-88, 38, 38);
		start.setMargin(new Insets(0, 0, 0, 0));
		start.setBackground(Color.BLACK);
		start.setForeground(Color.WHITE);
		start.setOpaque(true);
		start.setBorder(null);
		trialArea.add(start);
	}

}


class Condition {
	int amplitude; 
	int width;
	
	public Condition(int amp, int wid) {
		amplitude = amp; 
		width = wid;
	}
}
