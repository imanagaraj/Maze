///**
// *
// */
//package edu.wm.cs.cs301.amazebycarolineaparna.falstad;
//
//
//import generation.Order;
//
//import javax.swing.JComboBox;
//import javax.swing.JComponent;
//import javax.swing.JButton;
//
//import java.awt.BorderLayout;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyListener;
//import java.io.File;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//
///**
// * This class is a wrapper class to startup the Maze game as a Java application
// *
// *
// * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
// * Paul Falstad granted permission to modify and use code for teaching purposes.
// * Refactored by Peter Kemper
// *
// * TODO: use logger for output instead of Sys.out
// */
//public class MazeApplication extends JFrame {
//
//	// not used, just to make the compiler, static code checker happy
//	private static final long serialVersionUID = 1L;
//
//	private KeyListener keyListener;
//	private SimpleKeyListener kl;
//
//	protected MazeController controller ;
//	private ManualDriver driver;
//	private Order builder;
//	private BasicRobot robot;
//	private RobotDriver robotDriver;
//	protected JFrame frame;
//	//private MazeListener listener;
//
//	JComboBox<String> driverBox;
//	JComboBox<String> algorithmBox;
//	JComboBox<String> skillBox;
//
//	JButton button;
//
//	String driverInput;
//	String algorithm;
//	int skill;
//
//
//	//private JButton button;
//	/**
//	 * Constructor
//	 */
//	public MazeApplication() {
//		super() ;
//		System.out.println("MazeApplication: maze will be generated with a randomized algorithm.");
//
//
//		driver = new ManualDriver();
//		robot = new BasicRobot();
//		driver.setRobot(robot);
//		controller = new MazeController(this.driver);
//		robot.setMaze(controller);
//		// controller = new MazeController();
////		builder = new Order();
//
////		driver.robot = controller.getRobot();
//		//System.out.println("manual driver");
//
////		JComponent newContentPane = new JPanel(new BorderLayout());
////	      //newContentPane.add(label, BorderLayout.PAGE_START);
////	        JComponent newContentPane2 = new JPanel(new BorderLayout());
////	        JComponent newContentPane3 = new JPanel(new BorderLayout());
//
//			//frame = new JFrame ("Maze" );
//
//
//
//		init();
//	}
//
//	/**
//	 * Constructor that loads a maze from a given file or uses a particular method to generate a maze
//	 */
//	public MazeApplication(String parameter) {
//		super() ;
//		// scan parameters
//		// Case 1: Prim
//		if ("Prim".equalsIgnoreCase(parameter))
//		{
//			System.out.println("MazeApplication: generating random maze with Prim's algorithm");
//			controller = new MazeController(Order.Builder.Prim) ;
//			init() ;
//			return ;
//		}
//		// Case 2: Eller
//		// TODO: for P2 assignment, please add code for Eller's algorithm here
//		if ("Eller".equalsIgnoreCase(parameter))
//		{
//			System.out.println("MazeApplication: generating random maze with Eller's algorithm");
//			controller = new MazeController(Order.Builder.Eller) ;
//			init() ;
//			return ;
//		}
//
//		// Case 3: a file
//		File f = new File(parameter) ;
//		if (f.exists() && f.canRead())
//		{
//			System.out.println("MazeApplication: loading maze from file: " + parameter);
//			controller = new MazeController(parameter) ;
//			init();
//			return ;
//		}
//
//		// Case 4: Wizard
//		if("Wizard".equalsIgnoreCase(parameter)){
//			System.out.println("MazeApplication: unknown parameter value:" + parameter + "ignored operating in default mode.");
//			controller = new MazeController(this.robotDriver);
//			init();
//		}
//
//		//Case 5: Wall Follower
//		if("Wall Follower".equalsIgnoreCase(parameter)){
//			System.out.println("MazeApplication: unknown parameter value:" + parameter + "ignored operating in default mode.");
//			controller = new MazeController(this.robotDriver);
//			init();
//		}
//
//		//Case 6: Pledge
//
//		if("Pledge".equalsIgnoreCase(parameter)){
//			System.out.println("MazeApplication: unknown parameter value:" + parameter + "ignored operating in default mode.");
//			controller = new MazeController(this.robotDriver);
//			init();
//		}
//
//		//Case 6: Explorer
//
//		if("Explorer".equalsIgnoreCase(parameter)){
//				System.out.println("MazeApplication: unknown parameter value:" + parameter + "ignored operating in default mode.");
//				controller = new MazeController(this.robotDriver);
//				init();
//		}
//
//		// Default case:
//		System.out.println("MazeApplication: unknown parameter value: " + parameter + " ignored, operating in default mode.");
//		controller = new MazeController() ;
//		init() ;
//	}
//
//
//
//	/**
//	 * Initializes some internals and puts the game on display.
//	 */
//	protected void init() {
//		add(controller.getPanel()) ;
//
//		kl = new SimpleKeyListener(this, controller, this.driver) ;
//		addKeyListener(kl) ;
//		MazeListener listener = new MazeListener(this, controller, this);
//		//kl = new SimpleKeyListener(this, controller, driver) ;
//
//		setSize(400, 400) ;
//		setVisible(true) ;
//
//
//		String [] algorithm = {"DFS","Eller","Prim"};
//		String [] driver = {"Wall Follower", "Wizard", "Pledge", "Explorer", "Manual Driver" };
//		String [] skill = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
//
//		driverBox = new JComboBox<String>(driver);
//		algorithmBox = new JComboBox<String>(algorithm);
//		skillBox = new JComboBox<String>(skill);
//
//		button = new JButton("Start");
//
////		driverBox = new JComboBox("driver");
////		algorithmBox = new JComboBox("algorithm");
////		skillBox = new JComboBox("skill");
//
//		controller.getPanel().setPreferredSize(new Dimension (400, 400));
//		add(controller.getPanel(), BorderLayout.CENTER);
//		add(algorithmBox, BorderLayout.NORTH);
//		add(driverBox, BorderLayout.EAST);
//		add(skillBox, BorderLayout.WEST);
//		add(button, BorderLayout.SOUTH);
//
//		button.addActionListener(listener);
//
//		pack();
//		setVisible(true);
//
//		setFocusable(true);
//
//
//		controller.init();
//
//		//kl.setDriver(driver);
//
//	}
//
//	/**
//	 * Main method to launch Maze as a java application.
//	 * The application can be operated in two ways. The intended normal operation is to provide no parameters
//	 * and the maze will be generated by a particular algorithm. If a filename is given, the maze will be loaded
//	 * from that file. The latter option is useful for development to check particular mazes.
//	 * @param args is optional, first parameter is a filename with a given maze
//	 */
//	public static void main(String[] args) {
//		MazeApplication a ;
//		switch (args.length) {
//		case 1 : a = new MazeApplication(args[0]);
//		break ;
//		case 0 :
//		default : a = new MazeApplication() ;
//		break ;
//		}
//		a.repaint() ;
//	}
//
//}
