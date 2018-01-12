package main.java.frc.team1138.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

//import main.java.frc.team1138.robot.commands.ExampleCommand;
import main.java.frc.team1138.robot.commands.ShiftBase;
import main.java.frc.team1138.robot.commands.KickBall;
import main.java.frc.team1138.robot.commands.CollectBall;
import main.java.frc.team1138.robot.commands.StopCollect;
import main.java.frc.team1138.robot.commands.FlywheelForward;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	// 3 Joysticks - 2 Logitech and 1 xbox
	private Joystick leftController, rightController, xBoxController;
	
	// Logitech Buttons
	public JoystickButton shiftButton, reverseDriveButton;
	
	// XBox Button definitions   The ones that are commented out, we aren't using currently
	public JoystickButton buttonA, buttonB, buttonX, buttonY;
	// public JoystickButton buttonLB     // Left Bumper
	// public JoystickButton buttonRB     // Right Bumper
	// public JoystickButton buttonSelect // Select Button
	// public JoystickButton buttonStart  // Start Button
	
	public OI() {
		//Logitech and xbox controllers.
		//INFO Left Joystick is 0, Right is 1, XBox is 2
		leftController = new Joystick(0);
		rightController = new Joystick(1);
		xBoxController = new Joystick(2);
		
		//Logitech buttons
		shiftButton = new JoystickButton(leftController, 1);
		reverseDriveButton = new JoystickButton(rightController, 1);

		/****** XBox Button definitions   The ones that are commented out, we aren't using currently ***/
		buttonA	= new JoystickButton(xBoxController, 1);	//Shoot low goal
		buttonB	= new JoystickButton(xBoxController, 2);	//Collect Ball
		buttonX = new JoystickButton(xBoxController, 3);	//Panic Kick
		buttonY = new JoystickButton(xBoxController, 4);	//Shoot High Goal
		//	buttonLB = new JoystickButton(xBoxController, 5);		//left bumper
		//	buttonRB) = new JoystickButton(xBoxController, 6);		//right bumper
		//	buttonSelect = new JoystickButton(xBoxController, 7);	//Select button
		//	buttonStart = new JoystickButton(xBoxController, 8);	//Start button

		shiftButton.whenPressed(new ShiftBase());
		buttonX.whenPressed(new KickBall());
		buttonB.whenPressed(new CollectBall());
		buttonA.whenPressed(new StopCollect());
		buttonY.whenPressed(new FlywheelForward());
//		buttonB.whenReleased(new DrivePosition());
	}
	
	public double getRightController(){			//Right controller is right side drive
		return rightController.getY();
	}

	public double getLeftController(){			//Left controller is left side drive
		return leftController.getY();
	}

	public boolean getLeftTrigger(){				//left controller's trigger is the shifter
		return shiftButton.get();
	}

	public boolean getRightTrigger(){			//right controller's trigger reverses the base direction
		return reverseDriveButton.get();
	}

	public double getLeftXBoxAxis(){			//left xbox axis controls the shooter cage pivot
		return -xBoxController.getRawAxis(1);
	}

	//controls the collector arms, signal from joystick is reversed
	public double getRightXBoxAxis(){
		return -xBoxController.getRawAxis(5);
	}

	public int getXBoxPOV(){					//POV controls the collector arms
		return xBoxController.getPOV();
	}
}