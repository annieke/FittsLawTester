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
	
	private JPanel display; 
	private JButton prev;
	private JButton next; 
	
	File file; 
	
	public VisualizerWindow(File file) {
		setTitle("Visualizer"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		this.file = file; 
		
		display = new JPanel(); 
		display.setPreferredSize(new Dimension(width, height));
		display.setLayout(null);
		
		prev = new JButton("Previous trial");
		next = new JButton("Next trial"); 
		JPanel buttons = new JPanel(); 
		buttons.add(prev);
		buttons.add(next);
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(display, BorderLayout.CENTER);
		cp.add(buttons, BorderLayout.SOUTH);
		pack();
	}

}
