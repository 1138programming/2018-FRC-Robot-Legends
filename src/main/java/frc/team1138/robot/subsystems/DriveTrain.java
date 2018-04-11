package frc.team1138.robot.subsystems;

import frc.team1138.robot.commands.DriveWithJoysticks;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class DriveTrain extends Subsystem
{
	private TalonSRX rightTopMotor;
	private TalonSRX rightRearMotor;
	private TalonSRX leftRearMotor;
	private TalonSRX leftTopMotor;
	private TalonSRX rightFrontMotor;
	private TalonSRX leftFrontMotor;
	private DoubleSolenoid shiftSolenoid;

	public double ticksPerInch;
	public final double KRamp = 6.0;
	public final double KDeadZoneLimit = 0.1;
	public final int KLeftMaster = 6;
	public final double KHalfSpeed = 0.5;
	public final int KRightMaster = 2;
	public final double KWheelRadius = 4.0;
	public final double KPI = 3.14;
	public final double KTicksPerRev = 256;

	public DriveTrain()
	{
		rightFrontMotor = new TalonSRX(KRightMaster);

		rightTopMotor = new TalonSRX(1);

		rightRearMotor = new TalonSRX(3);
		rightRearMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

		// rightFrontMotor.setSafetyEnabled(false);
		// rightFrontMotor.setVoltageRampRate(KRamp); - Removed

		rightTopMotor.set(ControlMode.Follower, KRightMaster); // Set the top motor as a slave
		rightTopMotor.setSensorPhase(true); // invert the top motor relative to the master
		rightRearMotor.set(ControlMode.Follower, KRightMaster); // Set the rear motor as a slave

		leftRearMotor = new TalonSRX(4);

		leftTopMotor = new TalonSRX(5);

		leftFrontMotor = new TalonSRX(KLeftMaster);

		// leftFrontMotor.setSafetyEnabled(false);
		// leftFrontMotor.setVoltageRampRate(KRamp); - Removed
		rightTopMotor.setInverted(true);
		rightRearMotor.setInverted(true);

		leftFrontMotor.setInverted(true);
		leftRearMotor.setInverted(true);
		leftTopMotor.setInverted(true);

		leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

		shiftSolenoid = new DoubleSolenoid(0, 1);

		leftTopMotor.set(ControlMode.Follower, KLeftMaster);
		leftTopMotor.setSensorPhase(true);
		leftRearMotor.set(ControlMode.Follower, KLeftMaster);

		ticksPerInch = KTicksPerRev / (KWheelRadius * KPI);
	}

	public void initDefaultCommand()
	{
		setDefaultCommand(new DriveWithJoysticks());
	}

	/**********************************************************************************
	 * The drive will take the values for the left and right Logitech joystick axes.
	 * If the drive base is in reverse mode, it will reverse them. We allow for a
	 * small deadzone
	 */
	public void tankDrive(double left, double right, double direction)
	{
		left *= direction;
		if (left > KDeadZoneLimit || left < -KDeadZoneLimit)
		{
			leftFrontMotor.set(ControlMode.PercentOutput, left*KHalfSpeed);
		}
		else
		{
			leftFrontMotor.set(ControlMode.PercentOutput, 0);
		}

		right *= direction;
		if (right > KDeadZoneLimit || right < -KDeadZoneLimit)
		{
			rightFrontMotor.set(ControlMode.PercentOutput, right*KHalfSpeed);
		}
		else
		{
			rightFrontMotor.set(ControlMode.PercentOutput, 0);
		}
		// SmartDashboard.putNumber("Left Motor", (double) left);
		// SmartDashboard.putNumber("RightMOtor", (double) right);
	}

	public void stopBase()
	{
		rightFrontMotor.set(ControlMode.PercentOutput, 0);
		leftFrontMotor.set(ControlMode.PercentOutput, 0);
	}

	public void downShift()
	{
		shiftSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	public void upShift()
	{
		shiftSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	/*
	 * If the shifter solenoid is off or in high gear - switch to low gear (forward)
	 * If it is in low gear - switch to high gear (reverse)
	 */
	public void toggleShift()
	{
		if (shiftSolenoid.get() != DoubleSolenoid.Value.kForward)
		{
			downShift();
		}
		else
		{
			upShift();
		}
	}

	public void driveForward(double speed, double distance)
	{
		double encoder = 0;
		leftFrontMotor.getSensorCollection().setQuadraturePosition(0, 10);
		while (encoder < distance)
		{
			rightFrontMotor.set(ControlMode.PercentOutput, speed);
			leftFrontMotor.set(ControlMode.PercentOutput, speed);
			encoder = leftFrontMotor.getSensorCollection().getQuadraturePosition();
		}
	}

	public void driveBackward(double speed, double distance)
	{
		double encoder = 0;
		leftFrontMotor.getSensorCollection().setQuadraturePosition(0, 10);
		while (encoder > distance)
		{
			rightFrontMotor.set(ControlMode.PercentOutput, -speed);
			leftFrontMotor.set(ControlMode.PercentOutput, -speed);
			encoder = leftFrontMotor.getSensorCollection().getQuadraturePosition();
		}
	}

}