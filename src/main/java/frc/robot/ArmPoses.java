package frc.robot;

import java.util.ArrayList;

import frc.robot.subsystems.ArmPose;



public class ArmPoses
{
    public enum Poses
    {
        Home,
        FloorPickCone,
        FloorPickCube,
        PouncePreScore,
        ConeScoreL1,
        ConeScoreL2,
        ConeScoreL3,
        CubeScoreL1,
        CubeScoreL2,
        CubeScoreL3,
        Travel,
        PickFromSubstation,
        PounceDriveUpWindow,
        PickDriveUpWindow
    }

    private ArmPose[] poseList;

    public ArmPoses()
    {
        poseList = new ArmPose[Poses.values().length];
       

        // General
        poseList[Poses.Home.ordinal()] = 
            new ArmPose(0, 0, 0,  false);
      
        poseList[Poses.PouncePreScore.ordinal()] = 
            new ArmPose(0, 0, 0,  false);

        poseList[Poses.Travel.ordinal()] = 
            new ArmPose(0, 0, 0,  false);

        poseList[Poses.PickFromSubstation.ordinal()] = 
            new ArmPose(0, 0, 0,  false);
      
        poseList[Poses.PickDriveUpWindow.ordinal()] = 
            new ArmPose(0, 0, 0,  false);

        poseList[Poses.PounceDriveUpWindow.ordinal()] = 
            new ArmPose(0, 0, 0,  false);


        // Cone
        poseList[Poses.FloorPickCone.ordinal()] = 
            new ArmPose(0, 0, 0,  false);

        
        poseList[Poses.ConeScoreL1.ordinal()] = 
            new ArmPose(0, 0, 0,  false);
      
        poseList[Poses.ConeScoreL2.ordinal()] = 
            new ArmPose(0, 0, 0,  false);

        poseList[Poses.ConeScoreL3.ordinal()] = 
            new ArmPose(0, 0, 0,  false);



        // Cube
        poseList[Poses.FloorPickCube.ordinal()] = 
            new ArmPose(0, 0, 0,  false);
        

        poseList[Poses.CubeScoreL1.ordinal()] = 
            new ArmPose(0, 0, 0,  false);
      
        poseList[Poses.CubeScoreL2.ordinal()] = 
            new ArmPose(0, 0, 0,  false);

        poseList[Poses.CubeScoreL3.ordinal()] = 
            new ArmPose(0, 0, 0,  false);
          
        
    }


    public ArmPose getArmPose(Poses p)
    {
        return poseList[p.ordinal()];
    }
   

}
