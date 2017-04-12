import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

/**
 * Visualizer window for Fitt's Law Tester
 * 
 * @author Annie Ke, Dartmouth 17S CS67 Human Computer Interaction 
 */
public class VisualizerWindow extends JFrame {
	private static final int width = 700, height = 700;
	private static final int startWidth = 38;
	
	private JFrame frame; 
	private Container cp; 
	private JPanel display; 
	private JButton prev;
	private JButton next; 
	
	File file; 
	ArrayList<JPanel> panels = new ArrayList<JPanel>(); 
	private int currTrial = 0;
	
	public VisualizerWindow(File file) {
		setTitle("Visualizer"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		this.file = file; 
		frame = this; 
		cp = getContentPane();
		
		readFile(); 
		
		display = new JPanel(); 
		display.setPreferredSize(new Dimension(width, height));
		display.setLayout(new BorderLayout());
		JLabel startMsg = new JLabel("Begin viewing trials by clicking 'Next trial.'");
		display.add(startMsg, BorderLayout.CENTER);
		
		prev = new JButton("Previous trial");
		prev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currTrial > 1) {
					currTrial = currTrial - 1; 
					cp.remove(display);
					display = panels.get(currTrial-1);
					cp.add(display, BorderLayout.CENTER);
					frame.repaint();
					frame.pack();
				}
			}
		});
		next = new JButton("Next trial"); 
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currTrial < panels.size()) {
					currTrial = currTrial + 1; 
					cp.remove(display);
					display = panels.get(currTrial-1);
					cp.add(display, BorderLayout.CENTER);
					frame.repaint();
					frame.pack();
				}
				
			}
		});
		JPanel buttons = new JPanel(); 
		buttons.add(prev);
		buttons.add(next);
		
		cp.setLayout(new BorderLayout());
		cp.add(display, BorderLayout.CENTER);
		cp.add(buttons, BorderLayout.SOUTH);
		pack();
	}
	
	private void readFile() {
		try {
			String line; 
			BufferedReader br = new BufferedReader(new FileReader(file));
			ArrayList<Point> trajectory = new ArrayList<Point>(); 
			int trial = 0;
			int time = 0; 
			
			JPanel visual = new JPanel();
			initNewPanel(visual);
			
			while ((line = br.readLine()) != null) {
				String[] entry = line.split(" ");
				int thisTrial = Integer.parseInt(entry[1]);
				if (thisTrial > trial) {
					if (thisTrial > 1) {
						paintPointsAddPanel(visual, trajectory, trial, time); 
						
						visual = new JPanel(); 
						initNewPanel(visual);
						
						trajectory = new ArrayList<Point>(); 
					}
					trial = thisTrial; 
					JLabel target = new JLabel(); 
					int tarWidth = Integer.parseInt(entry[3]);
					target.setPreferredSize(new Dimension(tarWidth, tarWidth));
					target.setOpaque(true);
					target.setBackground(Color.GREEN);
					Point tarCenter = parseForPoint(entry[5]);
					
					target.setBounds(tarCenter.x-(tarWidth/2), tarCenter.y-(tarWidth/2), tarWidth, tarWidth);
					visual.add(target);
				}
				
				Point currPoint = parseForPoint(entry[8]); 
				trajectory.add(currPoint);
				time = Integer.parseInt(entry[6]); 
				
			}
			paintPointsAddPanel(visual, trajectory, trial, time); 
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void paintPointsAddPanel(JPanel visual, ArrayList<Point> points, int trial, int time) {	
		for (Point p: points) {
			JLabel point = new JLabel(""); 
			point.setPreferredSize(new Dimension(2, 2));
			point.setOpaque(true);
			point.setBackground(Color.RED);
			point.setBounds(p.x, p.y, 2, 2);
			point.setBorder(BorderFactory.createLineBorder(Color.RED));
			visual.add(point);
		}	
		JLabel triall = new JLabel("Trial #:"+Integer.toString(trial));
		triall.setBounds(20, 20, 100, 20);
		JLabel timel = new JLabel("Time: "+Integer.toString(time)); 
		timel.setBounds(20, 40, 100, 20);
		visual.add(triall);
		visual.add(timel);
		
		panels.add(trial-1, visual);
	}
	
	private Point parseForPoint(String coord) {
		String[] parts = coord.split(","); 
		int x = Integer.parseInt(parts[0].trim().substring(1).trim());
		int y = Integer.parseInt(parts[1].trim().substring(0, parts[1].trim().length() - 1).trim());
		Point point = new Point(x,y);
		
		return point; 
	}
	
	private void initNewPanel(JPanel visual) {
		visual.setPreferredSize(new Dimension(width, height));
		visual.setLayout(null);
		
		JLabel start = new JLabel("start"); 
		start.setPreferredSize(new Dimension(startWidth, startWidth));
		start.setForeground(Color.WHITE);
		start.setOpaque(true);
		start.setBackground(Color.BLACK);
		
		start.setBounds(0, height-startWidth, startWidth, startWidth);
		visual.add(start);
	}

}
