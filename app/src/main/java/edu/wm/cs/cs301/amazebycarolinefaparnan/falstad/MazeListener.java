//package edu.wm.cs.cs301.amazebycarolineaparna.falstad;
//
//import java.awt.Container;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import generation.Order;
//
///**
// * Class: MazeListener
// *
// * Responsibilities: Takes input from the combo boxes and applies the correct choices
// * to generate the user's desired maze.
// *
// * Collaborators: ActionListener, MazeApplication, MazeController
// *
// * @author Caroline Fagan and Aparna Nagaraj
// *
// */
//
//public class MazeListener implements ActionListener{
//
//	MazeApplication application;
//	MazeController controller;
//	Container parent;
//
//
//	public MazeListener(MazeApplication application, MazeController controller, Container parent){
//
//		this.application = application;
//		this.controller = controller;
//		this.parent = parent;
//
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent actionEvent) {
//		// TODO Auto-generated method stub
//
//		//startbutton pressed
//
//		String algorithm = (String) application.algorithmBox.getSelectedItem();
//		System.out.print(algorithm);
//
//		if (algorithm == "Prim"){
//			System.out.println("Maze Application generating a random maze Prim");
//
//			controller.updateBuilder(Order.Builder.Prim);
//		}
//
//		else if(algorithm == "Eller"){
//			System.out.println("Maze Application generating a random maze Eller");
//
//			controller.updateBuilder(Order.Builder.Eller);
//		}
//
//		else{
//			controller.updateBuilder(Order.Builder.DFS);
//		}
//
//		String driver = (String) application.driverBox.getSelectedItem();
//		System.out.println(driver);
//
//		if (driver == "Wall Follower"){
//			System.out.println("Using Wall Follower Driver");
//
//			WallFollower wallfollower = new WallFollower();
//			controller.updateDriver(wallfollower);
//		}
//
//		else if(driver == "Wizard"){
//			System.out.println("Using Wizard Driver");
//
//			Wizard wizard = new Wizard();
//
//			controller.updateDriver(wizard);
//		}
//
//		else if(driver == "Pledge"){
//
//			System.out.println("Using Pledge Driver");
//			Pledge pledge = new Pledge();
//
//			controller.updateDriver(pledge);
//		}
//
//		else if(driver == "Explorer"){
//
//			System.out.println("Using Explorer Driver");
//			Explorer explorer = new Explorer();
//
//			controller.updateDriver(explorer);
//		}
//
//		else{
//
//			ManualDriver manual = new ManualDriver();
//			controller.updateDriver(manual);
//		}
//
//		String skill = (String)application.skillBox.getSelectedItem();
//		int skillSelect = Integer.parseInt(skill);
//		controller.switchToGeneratingScreen(skillSelect + 48);
//
//		parent.remove(application.skillBox);
//		parent.remove(application.driverBox);
//		parent.remove(application.algorithmBox);
//		parent.remove(application.button);
//
//
//		application.pack();
//
//		parent.repaint();
//
//
//		}
//
//
//
//		}
//
//
