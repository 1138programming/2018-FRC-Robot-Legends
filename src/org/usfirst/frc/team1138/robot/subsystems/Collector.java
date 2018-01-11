package org.usfirst.frc.team1138.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import org.usfirst.frc.team1138.robot.RobotMap;
import org.usfirst.frc.team1138.robot.commands.CollectorWithJoystick;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/*
 * This subsystem comprises the a motor for each collector arm, and
 * A single moter to run the roller.  There are also 2 limit switches that
 * signal the upper and lower limit of collector arm travel.
 */
public class Collector extends Subsystem {
	private TalonSRX rightCollectorMotor, leftCollectorMotor;
	private DigitalInput collectorLowerLimit, collectorUpperLimit;
	//TODO add PID to collector motors
	//TODO figure out encoder values for the drive position and the collect position
	final int KCollectorMaster = 11;	//Talon number for the left collector motor
	final double KCollectorSpeed = 0.33; //We don't run the collector very fast
	final int KCollectorDrivePosition = 1000;
	final int KCollectorCollectPosition = 500;	//
	
	public Collector() {
		rightCollectorMotor = new TalonSRX(11);
		leftCollectorMotor = new TalonSRX(9);
		rightCollectorMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		collectorUpperLimit = new DigitalInput(5);
		collectorLowerLimit = new DigitalInput(2);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new CollectorWithJoystick());
	}
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	//TODO need to determine encoder values for each of these positions

	//This routine is for testing the collector.  It will move the collector up or down
	//based on the right XBox controller until it reaches the limit switch.
	//We need to use this to get the encoder values for the various positions we want.
	//TODO can we light up an indicator when the robot is in a particular range?
	
	public void collectorRunToLimitSwitch(double direction)
	{
		SmartDashboard.putNumber("Left Collector Encoder", collectorLeftEncoderPosition());
		SmartDashboard.putNumber("Left Collector Encoder", collectorRightEncoderPosition());

		if (direction >= RobotMap.KDeadZoneLimit && collectorIsAtUpperLimit() == false)//moving up
		{
			leftCollectorMotor.set(ControlMode.PercentOutput, KCollectorSpeed);
			rightCollectorMotor.set(ControlMode.PercentOutput, -KCollectorSpeed);

		}
		else if (direction <= -RobotMap.KDeadZoneLimit && collectorIsAtLowerLimit() == false)//moving down
		{
			leftCollectorMotor.set(ControlMode.PercentOutput, -KCollectorSpeed);
			rightCollectorMotor.set(ControlMode.PercentOutput, KCollectorSpeed);
		}
		else
		{
			leftCollectorMotor.set(ControlMode.PercentOutput, 0);
			rightCollectorMotor.set(ControlMode.PercentOutput, 0);
		}
		if(collectorIsAtUpperLimit())
		{
			leftCollectorMotor.getSensorCollection().setQuadraturePosition(0, 10);
		}

	}

	public void collectorCollectPosition()
	{
		if (leftCollectorMotor.getSensorCollection().getQuadraturePosition() > KCollectorCollectPosition )
			{
				while(leftCollectorMotor.getSensorCollection().getQuadraturePosition() > KCollectorCollectPosition)
				{
					leftCollectorMotor.set(ControlMode.PercentOutput, -KCollectorSpeed);
				}
			}
			else if (leftCollectorMotor.getSensorCollection().getQuadraturePosition() < KCollectorCollectPosition )
			{
				while(leftCollectorMotor.getSensorCollection().getQuadraturePosition() < KCollectorCollectPosition)
				{
					leftCollectorMotor.set(ControlMode.PercentOutput, KCollectorSpeed);
				}
			}
	}

	public void collectorDrivePosition()
	{
		if (leftCollectorMotor.getSensorCollection().getQuadraturePosition() > KCollectorDrivePosition )
		{
			while(leftCollectorMotor.getSensorCollection().getQuadraturePosition() > KCollectorDrivePosition)
			{
				leftCollectorMotor.set(ControlMode.PercentOutput, -KCollectorSpeed);
			}
		}
		else if (leftCollectorMotor.getSensorCollection().getQuadraturePosition() < KCollectorDrivePosition )
		{
			while(leftCollectorMotor.getSensorCollection().getQuadraturePosition() < KCollectorDrivePosition)
			{
				leftCollectorMotor.set(ControlMode.PercentOutput, KCollectorSpeed);
			}
		}
		stopCollector();
	}
	//Move the collector up until we hit the limit switch
	public void collectorHighPosition()
	{
		while(!collectorIsAtUpperLimit())
		{
			leftCollectorMotor.set(ControlMode.PercentOutput, -KCollectorSpeed);
			SmartDashboard.putNumber("Left Collector Encoder", collectorLeftEncoderPosition());
			SmartDashboard.putNumber("Left Collector Encoder", collectorRightEncoderPosition());
		}
	}

	//Move the collector down until we hit the limit switch.
	//TODO we need to initialize the encoders (and test their direction) so that
	//when we start in the arms up position, the encoders reflect that
	public void collectorRaiseBot()
	{
		while(!collectorIsAtLowerLimit())
		{
			leftCollectorMotor.set(ControlMode.PercentOutput, -KCollectorSpeed);
			SmartDashboard.putNumber("Left Collector Encoder", collectorLeftEncoderPosition());
			SmartDashboard.putNumber("Left Collector Encoder", collectorRightEncoderPosition());
		}
		if (collectorIsAtLowerLimit())
		{
			leftCollectorMotor.getSensorCollection().setQuadraturePosition(0, 10);	// reset the encoders
			rightCollectorMotor.getSensorCollection().setQuadraturePosition(0, 10);
		}
	}

	public boolean collectorIsAtUpperLimit()
	{
		return collectorUpperLimit.get();
	}

	public boolean collectorIsAtLowerLimit()
	{
		return collectorLowerLimit.get();
	}

	public int collectorLeftEncoderPosition()
	{
		return leftCollectorMotor.getSensorCollection().getQuadraturePosition();
	}

	public int collectorRightEncoderPosition()
	{
		return rightCollectorMotor.getSensorCollection().getQuadraturePosition();
	}

	public void stopCollector()
	{
		leftCollectorMotor.set(ControlMode.PercentOutput, 0);
	}
}