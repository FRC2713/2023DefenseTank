package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lights extends SubsystemBase {

    public static Lights instance;

    public static Lights getInstance(){
        if(instance == null){
            instance = new Lights();
        }
        return  instance;
    }
    private final AddressableLED strip;
    private final AddressableLEDBuffer buffer;

    private Lights(){
        strip = new AddressableLED(Constants.RobotMap.lightsPort);
        buffer = new AddressableLEDBuffer(Constants.LightConstants.stripLength);
        strip.setLength(Constants.LightConstants.stripLength);
        strip.setData(buffer);
        strip.start();
    }

    public void periodic(){
        solidPercent(100.0, Color.kBurlywood);
    }

    private void solidPercent(double percent, Color color) {
        for (int i = 0; i < MathUtil.clamp(Constants.LightConstants.stripLength * percent, 0, Constants.LightConstants.stripLength); i++) {
            buffer.setLED(i, color);
        }

    }
}
