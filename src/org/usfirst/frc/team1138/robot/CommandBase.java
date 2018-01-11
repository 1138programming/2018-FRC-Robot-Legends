package org.usfirst.frc.team1138.robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1138.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1138.robot.subsystems.Pneumatics;
import org.usfirst.frc.team1138.robot.subsystems.Pivot;
import org.usfirst.frc.team1138.robot.subsystems.Shooter;
import org.usfirst.frc.team1138.robot.subsystems.Collector;
import org.usfirst.frc.team1138.robot.OI;

public abstract class CommandBase extends Command {
	// Create a single static instance of all of your subsystems. The following
	// line should be repeated for each subsystem in the project.
	public static DriveTrain driveTrain = new DriveTrain();
	public static Pneumatics pneumatics = new Pneumatics();
	public static Pivot pivot = new Pivot();
	public static Shooter shooter = new Shooter();
	public static Collector collector = new Collector();
	public static OI oi = new OI();
}