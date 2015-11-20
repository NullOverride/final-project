
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Canvas extends JPanel {
    ArrayList<DShape> collection;
    
    public Canvas(){
    	collection = new ArrayList<DShape>();
    	setLayout(null);
    	
    	JLabel item1 = new JLabel(" Add  ");
    	item1.setBounds(0,5,40,25);
    	
		JButton RectButton = new JButton(" Rect ");
		RectButton.setBounds(35,5,65,25);
		
		RectButton.addActionListener( new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        DRect rect = new DRect();
	        collection.add(rect);
	         }
	     });
		
		
		JButton OvalButton = new JButton(" Oval ");
		OvalButton.setBounds(95,5,65,25);
		
		OvalButton.addActionListener( new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        DOval oval = new DOval();
	        collection.add(oval);
	         }
	     });
		
		JButton LineButton = new JButton(" Line ");
		LineButton.setBounds(155,5,65,25);
		
		LineButton.addActionListener( new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        DLine line = new DLine();
	        collection.add(line);
	         }
	     });
		
		JButton TextButton = new JButton(" Text ");
		TextButton.setBounds(215,5,65,25);
		
		TextButton.addActionListener( new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        DText text = new DText();
	        collection.add(text);
	         }
	     });
		
		JButton ColorButton = new JButton(" Set Color ");
		ColorButton.setBounds(0, 50, 95, 25);
		
		JTextField text = new JTextField();
		text.setBounds(0, 100, 105, 20);
		
		JButton font = new JButton("Edwardian Script");
		font.setBounds(105, 100, 115, 20);
		
		JButton front = new JButton("Move to front");
		front.setBounds(5, 200, 105, 20);
		
		JButton back = new JButton("Move to back");
		back.setBounds(110, 200, 105, 20);
		
		JButton removeshape = new JButton("Remove shape");
		removeshape.setBounds(215, 200, 105, 20);
		
		JLabel panel2 = new JLabel();
		panel2.setBounds(0, 250, 500,250);
		panel2.setBackground(Color.WHITE);
    	panel2.setOpaque(true);
	
		add(item1);
		add(RectButton);
		add(OvalButton);
		add(LineButton);
		add(TextButton);
		add(ColorButton);
		add(text);
		add(font);
		add(front);
		add(back);
		add(removeshape);
		add(panel2);
    	
    }
   
    public void paintComponent(DShape shape){
    	
	   for(int i=0; i <collection.size(); i++){
		   
	   }
   }
	
   
}
