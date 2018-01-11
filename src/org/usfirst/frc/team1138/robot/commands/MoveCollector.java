package org.usfirst.frc.team1138.robot.commands;

import org.usfirst.frc.team1138.robot.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* This command reads the position of the XBox POV (DPad) and moves the collector
 * arms to the position specified.
 *  INFO POV UP - Collector arm to top limit
 *  INFO POV Left - Collector arm to Collect Position
 *  INFO POV Right - Collector arm to Drive Position
 *  INFO POV Down - Collector arm to minimum position which will raise the front of the robot
 *  */
//TODO verify that the position of the XBox POV gives the values I think it does - I think down is 0

public class MoveCollector extends CommandBase {
	public MoveCollector() {
		requires(CommandBase.collector);
	}
	// Called just before this Command runs the first time
	// If the collector isn't all the way up, raise it until it is.  Set the encoder
	// and then lower the collector to the drive position.
	//TODO This may have to change before competition because collector won't be in high position
	public void initialize()
	{
//		CommandBase.collector.collectorHighPosition();
//		CommandBase.collector.collectorDrivePosition();
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute()
	{
		int POVPosition = oi.getXBoxPOV();

		//POV value is the angle of POV in degrees or -1 if not pressed
		if (POVPosition == 0)	//Down POV
		{
			CommandBase.collector.collectorRaiseBot();
		}
		else if (POVPosition == 90)	//Right POV
		{
			CommandBase.collector.collectorDrivePosition();
		}
		else if (POVPosition == 180)	//Up POV
		{
			CommandBase.collector.collectorHighPosition();
		}
		else if (POVPosition == 270)	//Left POV
		{
			CommandBase.collector.collectorCollectPosition();
		}
		SmartDashboard.putNumber("Left Collector", CommandBase.collector.collectorLeftEncoderPosition());
		SmartDashboard.putNumber("Right Collector", CommandBase.collector.collectorRightEncoderPosition());
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished()
	{
		return false;
	}

	// Called once after isFinished returns true
	public void end()
	{

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	public void interrupted()
	{

	}
}