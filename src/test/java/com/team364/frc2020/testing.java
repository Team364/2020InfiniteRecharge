package com.team364.frc2020;

import org.junit.Test;


public class testing {
    public double var;
    @Test
    public void run(){
        System.out.println(iterativeDeadband(0.1, 0.05, 0.1));
    }


    public boolean iterativeDeadband(double deadband, double... vars){
        for(double var:vars){
            if(Math.abs(var) > deadband){
                return true;
            }
        }
        return false;
    }

}