package frc.robot.subsystems;

import frc.robot.Constants;
import java.util.concurrent.CancellationException;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;

enum IntakeState
{
    empty,
    wantsCone,
    wantsCube,
    hasCone,
    hasCube
}

public class Intake extends SubsystemBase
{
    private final double SPEED = 0.5;
    private IntakeState intakeState;
    //motors
    private CANSparkMax intakeMotor;
    private CANSparkMax undertakeMotor;
    private CANSparkMax undertakeFollowMotor;

    CANSparkMax[] motors = {intakeMotor, undertakeMotor, undertakeFollowMotor};

    private DoubleSolenoid intakeArms;
    private DoubleSolenoid presenter;
    private DoubleSolenoid vaccum;

    private DigitalInput sensor;

    public Intake()
    {
        intakeState = IntakeState.empty;

        intakeMotor = new CANSparkMax(Constants.Intake.intakeMotorID, MotorType.kBrushless);
        undertakeMotor = new CANSparkMax(Constants.Intake.undertakeMotorID, MotorType.kBrushless);
        undertakeFollowMotor = new CANSparkMax(Constants.Intake.undertakeFollowMotorID, MotorType.kBrushless);

        for(CANSparkMax i : motors)
        {
            i.setIdleMode(IdleMode.kCoast);
            i.setSmartCurrentLimit(40, 15);
        }

        undertakeFollowMotor.follow(undertakeMotor);

        intakeArms = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.Intake.intakeArmsDownChannel, Constants.Intake.intakeArmsUpChannel);
        presenter = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.Intake.presenterUpChannel, Constants.Intake.presenterDownChannel);
        vaccum = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.Intake.vaccumForwardChannel, Constants.Intake.vaccumReverseChannel);

        sensor = new DigitalInput(Constants.Intake.sensorChannel);
    }

    public void startIntake()
    {
        intakeMotor.set(SPEED);
        undertakeMotor.set(SPEED);
        intakeArms.set(Value.kForward);
        presenter.set(Value.kForward);
    }

    public void runIntake()
    {
        switch(intakeState)
        {
            case empty:
                break;
            case wantsCone:
                if(getSensorValue()) intakeState = IntakeState.hasCone;
                break;
            case wantsCube:
                if(getSensorValue()) intakeState = IntakeState.hasCube;
                break;
            case hasCone:
                //new WaitCommand(1);
                stopIntake();
                break;
            case hasCube:
                //new WaitCommand(1);
                stopIntake();
                break;
        }
    }

    //useless as of now
    public void turnArmsOff()
    {
        intakeArms.set(Value.kOff);
    }

    public void stopIntake()
    {
        intakeMotor.set(0);
        undertakeMotor.set(0);
        intakeArms.set(Value.kReverse);
    }

    public void setPresenter(boolean extended)
    {
        if(extended)
        {
            presenter.set(Value.kForward);
            return;
        }
        presenter.set(Value.kReverse);
    }

    public void setVaccum(Value state)
    {
        vaccum.set(state);
    }

    public boolean getSensorValue()
    {
        return sensor.get();
    }

    public IntakeState getIntakeState()
    {
        return intakeState;
    }

    public void setIntakeState(IntakeState state)
    {
        intakeState = state;
    }
    
}
