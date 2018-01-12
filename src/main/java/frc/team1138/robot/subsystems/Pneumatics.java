package main.java.frc.team1138.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.AnalogInput;

public class Pneumatics extends Subsystem {
	private Compressor pCompressor;
	private AnalogInput pressureSensor;

	public Pneumatics() {
		pCompressor = new Compressor(0);
		pressureSensor = new AnalogInput(0);
	}
	
	public void initDefaultCommand() {
		pCompressor.start();
	}

}