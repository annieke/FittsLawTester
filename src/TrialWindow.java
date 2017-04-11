import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

/**
 * Trial window for Fitt's Law Tester
 * 
 * @author Annie Ke, Dartmouth 17S CS67 Human Computer Interaction 
 */
public class TrialWindow extends JFrame {
	int id; 
	private ArrayList<Condition> conditions;
	private static final int width = 700, height = 700;
	private static final int startWidth = 38;
	private boolean success = true; 
	int trial = 0;
	long tStart; 
	long tEnd; 
	long time;
	
	
	JComponent trialArea; 
	Graphics currentGraphics; 
	JButton start; 
	JLabel trialsLeft;
	JButton target; 
	JLabel timel; 
	
	BufferedWriter summaryBW;
	FileWriter summaryFW; 
	BufferedWriter trajectoryBW;
	FileWriter trajectoryFW; 
	File summary;
	File trajectory; 
	
	public TrialWindow(int id, int trial, ListModel ampmodel, ListModel widmodel) {
		this.id = id; 
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
		
		// create files
		summary = new File("subject"+Integer.toString(id)+"summary.txt");
		trajectory = new File("subject"+Integer.toString(id)+"trajectory.txt");
		try {
			summary.createNewFile();
			trajectory.createNewFile();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
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
		// MouseListener to detect click failures outside of buttons
		trialArea.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				handlePress(event.getPoint());
			}
		});
		
		trialsLeft = new JLabel(Integer.toString(conditions.size()) + " trial(s) left");
		trialsLeft.setBounds(0, 0, 100, 20);
		trialArea.add(trialsLeft);
		
		start = new JButton("start");
		start.setBounds(0, height-startWidth, startWidth, startWidth);
		start.setMargin(new Insets(0, 0, 0, 0));
		start.setBackground(Color.BLACK);
		start.setForeground(Color.WHITE);
		start.setOpaque(true);
		start.setBorder(null);
		
		// handling response of first start click
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start.setBackground(Color.WHITE);
				start.setForeground(Color.BLACK);
				if (timel != null) trialArea.remove(timel);
				success = true;
				tStart = System.currentTimeMillis();
				runTrial(trial); 
				trial++;
			}
		});
		trialArea.add(start);
	}
	
	private void runTrial(int trial) {
		if (trial < conditions.size()) {
			Condition condition = conditions.get(trial);
			target = new JButton();
			target.setBackground(Color.GREEN);
			target.setMargin(new Insets(0, 0, 0, 0));
			target.setOpaque(true);
			target.setBorder(null);
			
			// find a random location to place the target button
			double randomAngle = Math.random() * Math.PI/2; 
			int tarX = (int) (Math.sin(randomAngle)*condition.amp); 
			int tarY = (int) (Math.cos(randomAngle)*condition.amp);
			target.setBounds(tarX, height-startWidth-tarY, condition.wid, condition.wid);
			
			// print time next to clicked button, prepare for next trial or exiting
			target.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tEnd = System.currentTimeMillis();
					time = tEnd - tStart;
					timel = new JLabel(Double.toString(time) + "ms");
					timel.setBounds((int)target.getBounds().getMaxX(), 
							(int)target.getBounds().getMaxY(), 150, 20);
					trialArea.add(timel);
					writeToSummary(trial+1, condition.amp, condition.wid); 
					
					trialArea.remove(target);
					start.setBackground(Color.BLACK);
					start.setForeground(Color.WHITE);
					
					if (trial < conditions.size() - 1) {
						trialsLeft.setText(Integer.toString(conditions.size()-trial) 
								+ " trial(s) left");
					} else {
						trialsLeft.setText("No trials left");
						JLabel thankyou = new JLabel("Thank you for your participation! "
								+ "Click start button again to exit");
						thankyou.setBounds(150, 325, 600, 50);
						trialArea.add(thankyou);
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
	
	private void writeToSummary(int trial, int amp, int wid) {
		try {
			summaryFW = new FileWriter(summary.getAbsoluteFile(), true);
			summaryBW = new BufferedWriter(summaryFW);
			
			int startPosX = (int)(start.getBounds().getMaxX()+start.getBounds().getMinX())/2;
			int startPosY = (int)(start.getBounds().getMaxY()+start.getBounds().getMinY())/2;
			
			int targetPosX = (int)(target.getBounds().getMaxX()+target.getBounds().getMinX())/2;
			int targetPosY = (int)(target.getBounds().getMaxY()+target.getBounds().getMinY())/2;
			
			int successInt = (success) ? 1 : 0;
			
			String result = Integer.toString(id)+" "+Integer.toString(trial)+" "
					+Integer.toString(amp)+" "+Integer.toString(wid)+" ("
					+Integer.toString(startPosX)+", "+Integer.toString(startPosY)+") ("
					+Integer.toString(targetPosX)+", "+Integer.toString(targetPosY)+") "
					+Integer.toString((int)time)+" "+Integer.toString(successInt);
			
			summaryBW.write(result);
			summaryBW.newLine();
			
			summaryBW.close();
			summaryFW.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void handlePress(Point p) {
		success = false;
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
