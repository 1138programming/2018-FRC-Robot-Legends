package org.usfirst.frc.team1138.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Timer;

public class Shooter extends Subsystem {
	private TalonSRX leftFlywheelMotor;
	private TalonSRX rightFlywheelMotor;
	private TalonSRX rollerMotor;
	private DoubleSolenoid shooterSolenoid;
	
	final double KFlywheelForwardSpeed = 1;
	final double KFlywheelReverseSpeed = 0.3;
	final double	KRollerReverseSpeed = -1;
	final double KRollerForwardSpeed = 1;;

	public Shooter() {
		rightFlywheelMotor = new TalonSRX(7);
		leftFlywheelMotor = new TalonSRX(8);
		shooterSolenoid = new DoubleSolenoid(2, 3);
		//leftFlywheelMotor.setSafetyEnabled(false);
		leftFlywheelMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		leftFlywheelMotor.setInverted(true);

		rightFlywheelMotor.set(ControlMode.Follower, 8); //talon number of the left flywheel motor

		rollerMotor = new TalonSRX(10);
	}
	
	public void initDefaultCommand() {
	}

	public void panicKick()
	{
		shooterSolenoid.set(DoubleSolenoid.Value.kForward);
		Timer.delay(0.5);
		shooterSolenoid.set(DoubleSolenoid.Value.kReverse);
		Timer.delay(0.5);
		shooterSolenoid.set(DoubleSolenoid.Value.kOff);
	}

	public void runFlywheelsForward()
	{
		leftFlywheelMotor.set(ControlMode.PercentOutput, KFlywheelForwardSpeed);
	}

	public void runFlywheelsReverse()
	{
		leftFlywheelMotor.set(ControlMode.PercentOutput, KFlywheelReverseSpeed);
	}

	public void stopFlywheels()
	{
		leftFlywheelMotor.set(ControlMode.PercentOutput, 0);
	}

	public void runRollerForward()
	{
		rollerMotor.set(ControlMode.PercentOutput, KRollerForwardSpeed);
	}

	public void runRollerReverse()
	{
		rollerMotor.set(ControlMode.PercentOutput, KRollerReverseSpeed);
	}

	public void stopShooter()
	{
		leftFlywheelMotor.set(ControlMode.PercentOutput, 0);
		rollerMotor.set(ControlMode.PercentOutput, 0);
	}
	
}