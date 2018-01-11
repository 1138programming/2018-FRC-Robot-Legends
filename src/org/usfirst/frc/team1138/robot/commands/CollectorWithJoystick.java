package org.usfirst.frc.team1138.robot.commands;

import org.usfirst.frc.team1138.robot.CommandBase;

public class CollectorWithJoystick extends CommandBase {
	public CollectorWithJoystick() {
		requires(CommandBase.collector);
	}
	// Called just before this Command runs the first time
	public void initialize()
	{

	}

	// Called repeatedly when this Command is scheduled to run
	public void execute()
	{
		CommandBase.collector.collectorRunToLimitSwitch(oi.getRightXBoxAxis());
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