import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JTextField;
import javax.swing.JTable;

import java.awt.Color;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;


public class WhiteBoard extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private DataTable dTable;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WhiteBoard frame = new WhiteBoard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WhiteBoard() {
		setTitle("White Board Turbo 2000");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		
		// DRAWING CANVAS
		
		final Canvas can = new Canvas();
		can.setBackground(Color.WHITE);
		
		// MENU BAR
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New..");
		mnFile.add(mntmNew);
		mntmNew.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				can.reset();
				dTable.reset();
				repaint();
			}
		});
		JMenuItem mntmOpen = new JMenuItem("Open..");
		mnFile.add(mntmOpen);
		mntmOpen.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog("Enter File Name", null) + ".xml";
				if(result != null)
				{
					try {
						XMLDecoder xmlIn = new XMLDecoder(
								new BufferedInputStream(
										new FileInputStream(
												new File(result))));
						DShape[] array = (DShape[]) xmlIn.readObject();
						xmlIn.close();
						// TODO :ADD CLEAR - clear()
						for(DShape d: array) {
			                can.addShape(d);
			            }
					} catch (IOException o) {
						o.printStackTrace();
					}
				}
			}
		});
		
		JMenuItem mntmSave = new JMenuItem("Save As XML...");
		mnFile.add(mntmSave);
		
		mntmSave.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog("Enter File Name", null) + ".xml";
				if(result != null)
				{
					try {
						XMLEncoder xmlOut = new XMLEncoder(
								new BufferedOutputStream(
										new FileOutputStream(
												new File(result))));
						DShape[] array = can.getCollection().toArray(new DShape[0]);
						
						xmlOut.writeObject(array);
						xmlOut.close();
					} catch (IOException o) {
						o.printStackTrace();
					}
				}
			}
		});
		
		JMenuItem mntmSaveAsPng = new JMenuItem("Save as PNG...");
		mnFile.add(mntmSaveAsPng);
		mntmSaveAsPng.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog("Enter File Name", null) + ".png";
				if(result != null)
				{
					try {
						BufferedImage image = (BufferedImage) can.createImage(can.getWidth(), can.getHeight());
						Graphics g = image.getGraphics();
				        can.paintAll(g);
				        g.dispose();
				        javax.imageio.ImageIO.write(image, "PNG", new File(result));
					} catch (IOException o) {
						o.printStackTrace();
					}
				}
			}
		});
		
		JMenu mnConnection = new JMenu("Connection");
		menuBar.add(mnConnection);
		
		JMenuItem mntmStartServer = new JMenuItem("Start Server");
		mnConnection.add(mntmStartServer);
		mntmStartServer.addActionListener(new ActionListener() {
			class Server extends Thread{
				
				private int port;

				public Server(int port) {
					this.port = port;
				}
				
				public void run() {
			        try {
			            ServerSocket serverSocket = new ServerSocket(port);
			            while (true) {
			                Socket toClient = null;
			                toClient = serverSocket.accept();
			                //addOutput(new ObjectOutputStream(toClient.getOutputStream()));
			            }
			        } catch (IOException ex) {
			            ex.printStackTrace(); 
			        }
			    }
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog("Enter Port Number (100 - 25565)", 0);
				while (Integer.parseInt(result) < 100 || Integer.parseInt(result) > 25565)
				{
					result = JOptionPane.showInputDialog("Enter a Valid Port Number from 100 to 25565", 0);
				}
				if (result != null) {
		            System.out.println("server: start");
		            Server serverAccepter = new Server(Integer.parseInt(result.trim()));
		            serverAccepter.start();
		        }
			}
		});
		JMenuItem mntmJoinServer = new JMenuItem("Join Server");
		mnConnection.add(mntmJoinServer);
		mntmStartServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				class Client extends Thread {
					private String name;
					private int port;
					
					public Client(String name, int port) {
						this.name = name;
						this.port = port;
					}
					
					public void run() {
				        try {
				            Socket toServer = new Socket(name, port);
				            ObjectInputStream in = new ObjectInputStream(toServer.getInputStream());
				            while (true) {
				            	String verb = (String) in.readObject();
				                String xmlString = (String) in.readObject();
				                XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));
				                DShapeModel shape = (DShapeModel) decoder.readObject();
				                decoder.close();
				                switch(verb)
				                {
				                	case "add":
				                		can.addShape(shape);
				                		break;
				                	case "remove":
				                		//TODO Remove
				                		break;
				                	case "front":
				                		can.moveSelectedToFront();
				                		break;
				                	case "back":
				                		can.moveSelectedToBack();
				                		break;
				                	case "change":
				                		//TODO
				                		break;
				                }

				            }
				        }
				        catch (Exception ex) { // IOException and ClassNotFoundException
				        	ex.printStackTrace();
				        }				   
					}
				}
				String result = JOptionPane.showInputDialog("Connect to host:port", "127.0.0.1:5555");
				while (result == null)
				{
					result = JOptionPane.showInputDialog("Connect to host:port", "127.0.0.1:5555");
				}
				if (result != null) {
					String[] parts = result.split(":");
					Client clientHandler = new Client(parts[0].trim(), Integer.parseInt(parts[1].trim()));
					clientHandler.start();
				}
			}
		});
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Select a shape
		can.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				can.setSelected(e.getX(), e.getY());
			}

		});
		
		//Drag a shape
		can.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				DShape sp = can.getSelected();
				if(sp != null)
				{
					int dx = e.getX() - sp.getX();
					int dy = e.getY() - sp.getY();
					sp.setX(sp.getX() + dx);
					sp.setY(sp.getY() + dy);
					repaint();
				}
			}
		});
		// BUTTONS
		
		JLabel lblAdd = new JLabel("ADD");
		
		JButton btnRectangle = new JButton("Rect");
		btnRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Yay for random shapes
				int random = (int) (Math.random() * 400);
				int random2 = (int) (Math.random() * 400);
				int random3 = (int) (Math.random() * 200);
				int random4 = (int) (Math.random() * 200);
				Color colors[] = {Color.black, Color.red, Color.blue, Color.cyan,
						Color.green, Color.magenta, Color.orange, Color.YELLOW,
						Color.pink, Color.LIGHT_GRAY};
				Color randomColor = colors[(int) (Math.random() * colors.length)];
				// End randomness
				DRectModel rectModel = new DRectModel(random, random2, random3, random4, randomColor);
				can.addShape(rectModel);
				dTable.addRow(rectModel);
			}
		});
		
		JButton btnNewButton = new JButton("Oval");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Yay for random shapes
				int random = (int) (Math.random() * 400);
				int random2 = (int) (Math.random() * 400);
				int random3 = (int) (Math.random() * 200);
				int random4 = (int) (Math.random() * 200);
				Color colors[] = {Color.black, Color.red, Color.blue, Color.cyan,
						Color.green, Color.magenta, Color.orange, Color.YELLOW,
						Color.pink, Color.LIGHT_GRAY};
				Color randomColor = colors[(int) (Math.random() * colors.length)];
				// End randomness
				DOvalModel ovalModel = new DOvalModel(random, random2, random3, random4, randomColor);
				can.addShape(ovalModel);
				dTable.addRow(ovalModel);
			}
		});
		
		JButton btnLine = new JButton("Line");
		btnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Yay for random shapes
				int random = (int) (Math.random() * 200);
				int random2 = (int) (Math.random() * 200);
				int random3 = (int) (Math.random() * 200);
				int random4 = (int) (Math.random() * 200);
				Color colors[] = {Color.black, Color.red, Color.blue, Color.cyan,
						Color.green, Color.magenta, Color.orange, Color.YELLOW,
						Color.pink, Color.LIGHT_GRAY};
				Color randomColor = colors[(int) (Math.random() * colors.length)];
				// End randomness
				DLineModel lineModel = new DLineModel(random, random2, random3, random4, randomColor);
				can.addShape(lineModel);
				dTable.addRow(lineModel);
			}
		});
		
		JButton btnText = new JButton("Text");
		btnText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnSetColor = new JButton("Set Color");
		btnSetColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(can.getSelected() != null)
				{
					can.changeColor(JColorChooser.showDialog(null, "Choose a Color", getForeground()));
				}
			}
		});
		
		textField = new JTextField();
		textField.setColumns(10);
		
		// FONT SELECTOR
		JComboBox comboBox = new JComboBox();
		
		JButton btnMoveToFront = new JButton("Move To Front");
		btnMoveToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (can.getSelected() != null)
				{
					can.moveSelectedToFront();
					dTable.moveRowUp(can.getSelected().getShapeModel());
				}
			}
		});
		
		JButton btnMoveToBack = new JButton("Move To Back");
		btnMoveToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (can.getSelected() != null)
				{
					can.moveSelectedToBack();
					dTable.moveRowDown(can.getSelected().getShapeModel());
				}
			}
		});
		
		JButton btnRemoveShape = new JButton("Remove Shape");
		btnRemoveShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (can.getSelected() != null)
				{
					dTable.removeRow(can.getSelected().getShapeModel());
					can.removeSelected();
					repaint();
				}
			}
		});
		
		// TABLE
		dTable = new DataTable();
		JScrollPane scrollPane = new JScrollPane();
		table = new JTable(dTable);
		scrollPane.setViewportView(table);
		
		// Group buttons together with gaps and all the positioning stuff......
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblAdd)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRectangle)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnLine)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnText))
						.addComponent(btnSetColor)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnMoveToFront)
							.addGap(16)
							.addComponent(btnMoveToBack)
							.addGap(18)
							.addComponent(btnRemoveShape))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(can, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(can, GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAdd)
						.addComponent(btnRectangle)
						.addComponent(btnNewButton)
						.addComponent(btnLine)
						.addComponent(btnText))
					.addGap(18)
					.addComponent(btnSetColor)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRemoveShape)
						.addComponent(btnMoveToFront)
						.addComponent(btnMoveToBack))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
		);
		

		contentPane.setLayout(gl_contentPane);
	}
}