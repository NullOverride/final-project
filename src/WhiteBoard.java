import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;


public class WhiteBoard extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private DataTable dTable;
	private Point pt;
	private Canvas can;
	private Server serverAccepter;
	private Client clientHandler;
	private ArrayList<ObjectOutputStream> outputs;
	private JComboBox comboBox;
	private String[] fonts;
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
		can = new Canvas();
		FlowLayout flowLayout = (FlowLayout) can.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		can.setBackground(Color.WHITE);
		serverAccepter = null;
		clientHandler = null;
		outputs = new ArrayList<>();
		
		// MENU BAR
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		
		//////////////////////////////////
		//FILE I/O
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		final JMenuItem mntmNew = new JMenuItem("New..");
		mnFile.add(mntmNew);
		mntmNew.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				can.reset();
				dTable.reset();
				repaint();
			}
		});
		final JMenuItem mntmOpen = new JMenuItem("Open XML..");
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
		
		final JMenuItem mntmSave = new JMenuItem("Save As XML...");
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
		
		final JMenuItem mntmSaveAsPng = new JMenuItem("Save as PNG...");
		mnFile.add(mntmSaveAsPng);
		mntmSaveAsPng.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog("Enter File Name", null) + ".png";
				if(result != null)
				{
					try {
						DShape selected = can.getSelected();
						can.setSelected(null);
						BufferedImage image = (BufferedImage) can.createImage(can.getWidth(), can.getHeight());
						Graphics g = image.getGraphics();
				        can.paintAll(g);
				        g.dispose();
				        javax.imageio.ImageIO.write(image, "PNG", new File(result));
				        can.setSelected(selected);
					} catch (IOException o) {
						o.printStackTrace();
					}
				}
			}
		});
		//////////////////////////////////
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//////////////////////////////////
		//CANVAS MOUSE LISTENERS
		final MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pt = e.getPoint();
				can.setSelected(e.getX(), e.getY());
				if(serverAccepter != null && outputs.size() != 0)
				{
					doSend("select", can.getSelected());
				}
			}
			public void mouseClicked(MouseEvent e) {
				pt = e.getPoint();
				repaint();
			}
		};

		//Drag a shape
		final MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
			private int dx;
			private int dy;
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
				
				int x = e.getX();
				int y = e.getY();
				
				dx = x - pt.x;
				dy = y - pt.y;
				
				DShape sp = can.getSelected();
				if(pt.x < sp.getX()+sp.getWidth() +9 && pt.x > sp.getX()+sp.getWidth()-9 	//bottom right
						&& pt.y < sp.getY()+sp.getHeight() + 9 && pt.y > sp.getY() + sp.getHeight() - 9)
				{
					sp.setWidth(sp.getWidth() + dx);
					sp.setHeight(sp.getHeight() +  dy);
					pt = e.getPoint();
				}
				else if(pt.x < sp.getX()+9 && pt.x > sp.getX()-9 	//top left
						&& pt.y < sp.getY() + 9 && pt.y > sp.getY() - 9)
				{
					sp.setX(sp.getX() + dx);
					sp.setY(sp.getY() +dy);
					sp.setWidth(sp.getWidth() - dx);
					sp.setHeight(sp.getHeight() -  dy);
					pt = e.getPoint();
				}
				else if(pt.x < sp.getX()+sp.getWidth() +9 && pt.x > sp.getX()+sp.getWidth()-9 	//top right
						&& pt.y < sp.getY()+ 9 && pt.y > sp.getY() - 9)
				{
					sp.setY(sp.getY()+dy);
					sp.setWidth(sp.getWidth() + dx);
					sp.setHeight(sp.getHeight() - dy);
					pt = e.getPoint();
				}
				else if(pt.x < sp.getX()+9 && pt.x > sp.getX()-9 	//bottom left
						&& pt.y < sp.getY()+sp.getHeight() + 9 && pt.y > sp.getY() + sp.getHeight() - 9)
				{
					sp.setX(sp.getX()+dx);
					sp.setWidth(sp.getWidth() -dx);
					sp.setHeight(sp.getHeight() + dy);
					pt = e.getPoint();
				}
				else if(sp.getBounds().contains(pt))
				{
					sp.setX(sp.getX() + dx);
					sp.setY(sp.getY() + dy);
					pt = e.getPoint();
					repaint();
				}
				repaint();
				dTable.updateRow(can.getSelected().getShapeModel());
				if (serverAccepter != null && outputs.size() > 0)
				{
					//sync(); // Bad and inefficient
					doSend("change", can.getSelected());
				}
			}
		};
		can.addMouseListener(mouseAdapter);
		can.addMouseMotionListener(mouseMotionAdapter);
		//////////////////////////////////
		
		
		//////////////////////////////////
		// SHAPE BUTTONS
		final JLabel lblAdd = new JLabel("ADD");
		lblAdd.setHorizontalAlignment(SwingConstants.LEFT);
		
		final JButton btnRectangle = new JButton("Rect");
		btnRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Creation code
				DRectModel rectModel = new DRectModel(25, 25, 50, 50, Color.LIGHT_GRAY, can.getCollection().size() + 1);
				DRect dr = new DRect();
				dr.setAll(rectModel.getX(), rectModel.getY(), rectModel.getWidth(), rectModel.getHeight(), rectModel.getColor(), rectModel.getID());
				can.addShape(dr);
				dTable.addRow(rectModel);
				if (serverAccepter != null && outputs.size() > 0)
				{
					doSend("add", dr);
				}
			}
		});
		
		final JButton btnNewButton = new JButton("Oval");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Creation code
				DOvalModel ovalModel = new DOvalModel(25, 25, 50, 50, Color.LIGHT_GRAY, can.getCollection().size() + 1);
				DOval dO = new DOval();
				dO.setAll(ovalModel.getX(), ovalModel.getY(), ovalModel.getWidth(), ovalModel.getHeight(), ovalModel.getColor(), ovalModel.getID());
				can.addShape(dO);
				dTable.addRow(ovalModel);
				if (serverAccepter != null && outputs.size() > 0)
				{
					doSend("add", dO);
				}
			}
		});
		
		final JButton btnLine = new JButton("Line");
		btnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Creation code
				DLineModel lineModel = new DLineModel(25, 25, 50, 50, Color.LIGHT_GRAY, can.getCollection().size() + 1);
				DLine dL = new DLine();
				dL.setAll(lineModel.getX(), lineModel.getY(), lineModel.getWidth(), lineModel.getHeight(), lineModel.getColor(), lineModel.getID());
				can.addShape(dL);
				dTable.addRow(lineModel);
				if (serverAccepter != null && outputs.size() > 0)
				{
					doSend("add", dL);
				}
			}
		});
		
		// FONT SELECTOR
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fonts = ge.getAvailableFontFamilyNames();
		comboBox = new JComboBox(fonts);
		
		final JButton btnText = new JButton("Text");
		btnText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Creation code
				DTextModel textModel = new DTextModel(25, 25, 75, 50, Color.LIGHT_GRAY, can.getCollection().size() + 1);
				String text = textField.getText();
				//can.setText(text);
				DText dT = new DText();
				dT.setAll(textModel.getX(), textModel.getY(), textModel.getWidth(), textModel.getHeight(), textModel.getColor(), textModel.getID());
				dT.setInput(text);
				String font = (String) comboBox.getSelectedItem();
				dT.setFont(font);
				can.addShape(dT);
				dTable.addRow(textModel);
				if (serverAccepter != null && outputs.size() > 0)
				{
					doSend("add", dT);
				}
			}
		});
		//////////////////////////////////
		
		//////////////////////////////////
		//TEXT / COLOR / ARRANGEMENT
		final JButton btnSetColor = new JButton("Set Color");
		btnSetColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(can.getSelected() != null)
				{
					can.changeColor(JColorChooser.showDialog(null, "Choose a Color", getForeground()));
					if (serverAccepter != null && outputs.size() > 0)
					{
						//sync(); // Bad and inefficient
						doSend("change", can.getSelected());
					}
				}
			}
		});
		
		textField = new JTextField("Hello");
		textField.setColumns(10);
		
		
		final JButton btnMoveToFront = new JButton("Move To Front");
		btnMoveToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (can.getSelected() != null)
				{
					can.moveSelectedToFront();
					dTable.moveRowUp(can.getSelected().getShapeModel());
					if(serverAccepter != null && outputs.size() != 0)
					{
						doSend("front", can.getSelected());
					}
				}
			}
		});
		
		final JButton btnMoveToBack = new JButton("Move To Back");
		btnMoveToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (can.getSelected() != null)
				{
					can.moveSelectedToBack();
					dTable.moveRowDown(can.getSelected().getShapeModel());
					if(serverAccepter != null && outputs.size() != 0)
					{
						doSend("back", can.getSelected());
					}
				}
			}
		});
		
		final JButton btnRemoveShape = new JButton("Remove Shape");
		btnRemoveShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (can.getSelected() != null)
				{
					dTable.removeRow(can.getSelected().getShapeModel());
					can.removeSelected();
					repaint();
					if(serverAccepter != null && outputs.size() != 0)
					{
						doSend("remove", can.getSelected());
					}
				}
			}
		});
		//////////////////////////////////
		
		//////////////////////////////////
		//CONNECTION AND SERVER 
		
		// Labels (Client Mode/Server Mode
		final JLabel lblClientMode = new JLabel("CLIENT MODE");
		lblClientMode.setForeground(Color.RED);
		lblClientMode.setVisible(false);

		final JLabel lblServerMode = new JLabel("SERVER MODE");
		lblServerMode.setHorizontalAlignment(SwingConstants.LEFT);
		lblServerMode.setForeground(Color.RED);
		lblServerMode.setVisible(false);
		
		final JMenu mnConnection = new JMenu("Connection");
		menuBar.add(mnConnection);
		
		final JMenuItem mntmStartServer = new JMenuItem("Start Server");
		mnConnection.add(mntmStartServer);
		mntmStartServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doServer();
				mnConnection.setEnabled(false);
				lblServerMode.setVisible(true);
			}
		});
		final JMenuItem mntmJoinServer = new JMenuItem("Join Server");
		mnConnection.add(mntmJoinServer);
		mntmJoinServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doClient();
				can.reset();
				// Disable all buttons on client except save
				mntmJoinServer.setEnabled(false);
				mntmStartServer.setEnabled(false);
				mntmNew.setEnabled(false);
				mntmOpen.setEnabled(false);
				btnLine.setEnabled(false);
				btnMoveToBack.setEnabled(false);
				btnMoveToFront.setEnabled(false);
				btnNewButton.setEnabled(false);
				btnRectangle.setEnabled(false);
				btnRemoveShape.setEnabled(false);
				btnSetColor.setEnabled(false);
				btnText.setEnabled(false);
				lblClientMode.setVisible(true);
				can.removeMouseListener(mouseAdapter);
				can.removeMouseMotionListener(mouseMotionAdapter);
			}
		});
		//////////////////////////////////
		
		//////////////////////////////////
		// DATA TABLE
		dTable = new DataTable();
		JScrollPane scrollPane = new JScrollPane();
		table = new JTable(dTable);
		scrollPane.setViewportView(table);
		//////////////////////////////////
		
		//////////////////////////////////
		// POSITIONING
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnMoveToFront)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnMoveToBack)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRemoveShape, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(23))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(btnSetColor)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(2)
								.addComponent(lblAdd)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnRectangle)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnNewButton)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnLine)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnText))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(lblServerMode)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblClientMode)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
							.addGap(3)))
					.addGap(5)
					.addComponent(can, GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAdd)
						.addComponent(btnRectangle)
						.addComponent(btnNewButton)
						.addComponent(btnLine)
						.addComponent(btnText))
					.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
					.addComponent(btnSetColor)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblServerMode)
						.addComponent(lblClientMode))
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnMoveToFront)
						.addComponent(btnMoveToBack)
						.addComponent(btnRemoveShape))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
				.addComponent(can, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
	}
			//Server Class
			class Server extends Thread {
				
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
			                outputs = new ArrayList<ObjectOutputStream>();
			                addOutput(new ObjectOutputStream(toClient.getOutputStream()));
			                	sync();
			            }
			        } catch (IOException ex) {
			            ex.printStackTrace();
			            JOptionPane.showMessageDialog(rootPane, "Failed to Start Server. Please restart the application.");
			            System.exit(0); // Bad way to handle
			        }
			    }
			}
			

			//Client Class
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
			                String xmlString = (String) in.readObject();
			                XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));
			                Command cmd = (Command) decoder.readObject();
			                decoder.close();
			                switch(cmd.getCommand())
			                {
			                	case "add":
			                		can.addShape(cmd.getShape());
			                		dTable.addRow(cmd.getShape().getShapeModel());
			                		break;
			                	case "remove":
			                		dTable.removeRow(cmd.getShape().getShapeModel());
			                		can.removeSelected(); // fix
			                		break;
			                	case "front":
			                		can.moveSelectedToFront();
			                		dTable.moveRowUp(cmd.getShape().getShapeModel());
			                		break;
			                	case "back":
			                		can.moveSelectedToBack();
			                		dTable.moveRowDown(cmd.getShape().getShapeModel());
			                		break;
			                	case "change":
			                		can.updateShape(cmd.getShape());
			                		dTable.updateRow(cmd.getShape().getShapeModel());
			                		break;
			                	case "reset":
			                		can.reset();
			                		dTable.reset();
			                		break;
			                }
			            }
			        }
			        catch (Exception ex) { // IOException and ClassNotFoundException
			        	ex.printStackTrace();
			        	JOptionPane.showMessageDialog(rootPane, "Failed to Connect. Please restart the application.");
			        	System.exit(0); //bad way to handle
			        }				   
				}
			}

			public void doClient() {
				String result = JOptionPane.showInputDialog("Connect to host:port", "127.0.0.1:5555");
				while (result == null)
				{
					result = JOptionPane.showInputDialog("Connect to host:port", "127.0.0.1:5555");
				}
				if (result != null) {
					String[] parts = result.split(":");
					clientHandler = new Client(parts[0].trim(), Integer.parseInt(parts[1].trim()));
					clientHandler.start();
					JOptionPane.showMessageDialog(can, "Successful connection to server.");
				}
			}

			public void doServer() {
				String result = JOptionPane.showInputDialog("Enter Port Number (100 - 25565)", 5555);
				while (Integer.parseInt(result) < 100 || Integer.parseInt(result) > 25565)
				{
					result = JOptionPane.showInputDialog("Enter a Valid Port Number from 100 to 25565", 5555);
				}
				if (result != null) {
					serverAccepter = new Server(Integer.parseInt(result.trim()));
					serverAccepter.start();
					JOptionPane.showMessageDialog(can, "Server started successfully.");
				}
			}
			
		    public void doSend(String command, DShape shape) {
		        Command cmd = new Command();
		        cmd.setCommand(command);
		        cmd.setShape(shape);
		        sendRemote(cmd);
		    }
		    public synchronized void addOutput(ObjectOutputStream out) {
		        outputs.add(out);
		    }
		    public synchronized void sendRemote(Command message) {
		        // Convert the message object into an xml string.
		        OutputStream memStream = new ByteArrayOutputStream();
		        XMLEncoder encoder = new XMLEncoder(memStream);
		        encoder.writeObject(message);
		        encoder.close();
		        String xmlString = memStream.toString();
		        // Now write that xml string to all the clients.
		        Iterator<ObjectOutputStream> it = outputs.iterator();
		        while (it.hasNext()) {
		            ObjectOutputStream out = it.next();
		            try {
		                out.writeObject(xmlString);
		                out.flush();
		            }
		            catch (Exception ex) {
		                ex.printStackTrace();
		                it.remove();
		            }
		        }
		    }
		    public void sync() {
		    	if (outputs.size() != 0) {
		    		doSend("reset", null);
		    		for(DShape s: can.getCollection())
		    		{
		    			doSend("add", s);
		    		}
		    	}
		    }
}