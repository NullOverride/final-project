
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WhiteBoard extends JFrame
{
	 
	public WhiteBoard()
	{
		super("White Board Turbo 2000");
		setSize(1000, 500);
		setLayout(new GridLayout(1,2));
	
	}
	public static void main(String[] arg){
		 
		WhiteBoard board = new WhiteBoard();
		Canvas can = new Canvas();
		JPanel panel2 = getPanel(Color.WHITE);

		board.add(can, BorderLayout.WEST);
		board.add(panel2);
				
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setVisible(true);	
	
	}
    private static JPanel getPanel(Color c)
    {
        JPanel result = new JPanel();
        result.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        result.setBackground(c);
        return result;
    }
}
