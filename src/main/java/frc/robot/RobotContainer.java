package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.ArmPoses.Poses;
import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    private final Joystick driver;
    private final Joystick rotater;
    private final XboxController operator;
    private final GenericHID buttonBoard;

    /* Drive Controls */
    private final int translationAxis;
    private final int strafeAxis;
    private final int rotationAxis;

    /*private final int translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;*/

    /* Driver Buttons */
    private final Trigger zeroGyro;
    private final Trigger robotCentric;

    private final Trigger intakeButton;
    private final Trigger outtakeButton;
  

    private final Trigger cubeButton;
    private final Trigger coneButton;
    
    /*private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);*/

    /* Subsystems */
    private final Swerve s_Swerve;
    private final Arm s_Arm;
    private ArmPoses poses;
    // TODO uncomment once intake exists
    //private final Intake s_Intake;


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
       
        /* Controllers */
        driver = new Joystick(0);
        rotater = new Joystick(1);

        operator = new XboxController(2);
        buttonBoard = new GenericHID(3);


        poses = new  ArmPoses();

        /* Drive Controls */
        translationAxis = Joystick.AxisType.kY.value;
        strafeAxis = Joystick.AxisType.kX.value;
        rotationAxis = Joystick.AxisType.kX.value;

        /* Driver Buttons */
        zeroGyro = new Trigger(()->driver.getRawButton(1));
        robotCentric = new Trigger(()-> driver.getRawButton(2));

        intakeButton = new Trigger(()-> driver.getRawButton(4));
        outtakeButton = new Trigger(()-> driver.getRawButton(5));
  

        

        coneButton = new Trigger(()-> operator.getRawButton(1));
        cubeButton = new Trigger(()-> operator.getRawButton(2));
       
       
        /* Subsystems */
        s_Swerve = new Swerve();
        // TODO Uncomment once intake exists.
        //s_Intake = new Intake();
        s_Arm = new Arm(poses.getArmPose(Poses.Home));
            
   

        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> Math.pow(driver.getRawAxis(translationAxis),3), 
                () -> Math.pow(driver.getRawAxis(strafeAxis),3), 
                () -> rotater.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );

        // Configure the button bindings
        configureButtonBindings();
        
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        // TODO uncomment once intake... exists.
        //intakeButton.onTrue(new TeleopIntake(s_Intake, s_Arm));
        //outtakeButton.onTrue(new TeleopOuttake(s_Intake));

        //coneButton.onTrue(new InstantCommand(() -> s_Intake.wantsCone()));
        //cubeButton.onTrue(new InstantCommand(() -> s_Intake.wantsCube()));

        // Button board (this is terrible)
        Trigger pounce = new Trigger(()->buttonBoard.getRawButton(1));
        pounce.onTrue(new ArmCommand(s_Arm, poses.getArmPose(Poses.PouncePreScore)) );
       
        
        Trigger coneL1 = new Trigger(()->buttonBoard.getRawButton(2));
        coneL1.onTrue(new ArmCommand(s_Arm, poses.getArmPose(Poses.ConeScoreL1)) );

        Trigger coneL2 = new Trigger(()->buttonBoard.getRawButton(3));
        coneL2.onTrue(new ArmCommand(s_Arm, poses.getArmPose(Poses.ConeScoreL2)) );

        Trigger coneL3 = new Trigger(()->buttonBoard.getRawButton(4));
        coneL3.onTrue(new ArmCommand(s_Arm, poses.getArmPose(Poses.ConeScoreL3)) );

        Trigger cubeL1 = new Trigger(()->buttonBoard.getRawButton(5));
        cubeL1.onTrue(new ArmCommand(s_Arm, poses.getArmPose(Poses.CubeScoreL1)) );

        Trigger cubeL2 = new Trigger(()->buttonBoard.getRawButton(6));
        cubeL2.onTrue(new ArmCommand(s_Arm, poses.getArmPose(Poses.CubeScoreL2)) );

        Trigger cubeL3 = new Trigger(()->buttonBoard.getRawButton(7));
        cubeL3.onTrue(new ArmCommand(s_Arm, poses.getArmPose(Poses.CubeScoreL3)) );

        Trigger pounceDriveUp = new Trigger(()->buttonBoard.getRawButton(8));
        pounceDriveUp.onTrue(new ArmCommand(s_Arm, poses.getArmPose(Poses.PounceDriveUpWindow)) );

        Trigger pickConeDriveUp = new Trigger(()->buttonBoard.getRawButton(9));
        pickConeDriveUp.onTrue(new ArmCommand(s_Arm, poses.getArmPose(Poses.PickDriveUpWindow)) );

        Trigger pickCubeDriveUp = new Trigger(()->buttonBoard.getRawButton(10));
        pickCubeDriveUp.onTrue(new ArmCommand(s_Arm, poses.getArmPose(Poses.PickDriveUpWindow)) );


        // Xbox controller

        Trigger home = new Trigger(() -> operator.getStartButton());
        home.onTrue(new ArmCommand(s_Arm, poses.getArmPose(Poses.Home)) );

       

      
       
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new exampleAuto(s_Swerve);
    }
}
