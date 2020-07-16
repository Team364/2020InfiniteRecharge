package com.team364.frc2020.misc.util;

public class Function {
    Runnable exe;
    public Function(Runnable exe){
        this.exe = exe;
    }
    public void run(){
        exe.run();
    }
}
