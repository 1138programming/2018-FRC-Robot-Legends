package frc.team1138.robot.subsystems;

import frc.team1138.robot.RobotMap;
import frc.team1138.robot.commands.PivotWithJoystick;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Pivot extends Subsystem
{
	private TalonSRX pivotMotor;
	private DigitalInput pivotUpperLimit, pivotLowerLimit;

	public Pivot()
	{
		pivotMotor = new TalonSRX(12);
		// INFO Pivot lower limit switch - DIO 0
		// INFO Pivot upper limit switch - DIO 1
		pivotLowerLimit = new DigitalInput(0);
		pivotUpperLimit = new DigitalInput(1);
		pivotMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		pivotMotor.setInverted(true);
	}

	public void initDefaultCommand()
	{
		setDefaultCommand(new PivotWithJoystick());
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public void pivotRunToLimit(double direction)
	{

		SmartDashboard.putNumber("Pivot Encoder", pivotEncoderPosition());
		SmartDashboard.putBoolean("Upper Limit", pivotIsAtUpperLimit());
		SmartDashboard.putBoolean("Lower Limit", pivotIsAtLowerLimit());

		if (direction <= -RobotMap.KDeadZoneLimit && pivotIsAtLowerLimit() == false)
		{
			pivotMotor.set(ControlMode.PercentOutput, -RobotMap.KPivotSpeed);
		}
		else if (direction >= RobotMap.KDeadZoneLimit && pivotIsAtUpperLimit() == false)
		{
			pivotMotor.set(ControlMode.PercentOutput, RobotMap.KPivotSpeed);
		}
		else
		{
			stopPivot();
		}
		if (pivotIsAtUpperLimit())
		{
			pivotMotor.getSensorCollection().setQuadraturePosition(0, 10); // The encoder 0 is at the high position
		}
	}

	public boolean pivotIsAtUpperLimit()
	{
		return pivotUpperLimit.get();
	}

	public boolean pivotIsAtLowerLimit()
	{
		return pivotLowerLimit.get();
	}

	public int pivotEncoderPosition()
	{
		return (pivotMotor.getSensorCollection().getQuadraturePosition());
	}

	public void stopPivot()
	{
		pivotMotor.set(ControlMode.PercentOutput, 0);
	}
}