package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.*;

import static frc.robot.Constants.LightConstants.breathDuration;
import static frc.robot.Constants.LightConstants.stripLength;


public class Lights extends SubsystemBase {

    public static Lights instance;
    public static final Color[] prideSlide = {Color.kBlack, Color.kRed, Color.kDarkOrange, Color.kYellow, Color.kGreen, Color.kBlue, Color.kPurple, Color.kBlack, Color.kSkyBlue, Color.kLightPink, Color.kWhite, Color.kLightPink, Color.kLightBlue};
    public static Lights getInstance(){
        if(instance == null){
            instance = new Lights();
        }
        return  instance;
    }
    private final AddressableLED strip;
    private final AddressableLEDBuffer buffer;
    private Timer timer = new Timer();

    private Lights(){
        strip = new AddressableLED(Constants.RobotMap.lightsPort);
        buffer = new AddressableLEDBuffer(stripLength);
        strip.setLength(stripLength);
        strip.setData(buffer);
        strip.start();
    }

    public void periodic(){
        solidPercent(1.0, Color.kBurlywood);
    }

    private void solidPercent(double percent, Color color) {
        for (int i = 0; i < MathUtil.clamp(stripLength * percent, 0, stripLength); i++) {
            buffer.setLED(i, color);
        }
    }
    private void solidIndividual(int ledLocation, Color color){
        buffer.setLED(ledLocation, color);
    }

    public  void colorFade(Color c1, Color c2, double transitionTime) {
        timer.restart();
        double x = ((timer.get() % transitionTime) / transitionTime) * 2.0 * Math.PI;
        double ratio = (Math.sin(x) + 1.0) / 2.0;
        double red;
        double green;
        double blue;
        while (timer.get() >= transitionTime) {
            red = (c1.red * (1 - ratio)) + (c2.red * ratio);
            green = (c1.green * (1 - ratio)) + (c2.green * ratio);
            blue = (c1.blue * (1 - ratio)) + (c2.blue * ratio);
            solidPercent(1.0, new Color(red, green, blue));
        }
    }
    private void breath(double percent, Color c1, Color c2, double duration) {
        timer.restart();
        double x = ((Timer.getFPGATimestamp() % breathDuration) / breathDuration) * 2.0 * Math.PI;
        double ratio = (Math.sin(x) + 1.0) / 2.0;
        double red = (c1.red * (1 - ratio)) + (c2.red * ratio);
        double green = (c1.green * (1 - ratio)) + (c2.green * ratio);
        double blue = (c1.blue * (1 - ratio)) + (c2.blue * ratio);
        solidPercent(percent, new Color(red, green, blue));
    }
    public void blinkColors(double percent, Color... colors){
        blinkColors(percent,  0.25, colors);
    }
    public void blinkColors(double percent, double speed, Color... colors){
        for (Color color : colors){
            new SequentialCommandGroup(
                    new InstantCommand(() -> Lights.getInstance().solidPercent(percent, color)),
                    new WaitCommand(speed));
        }
    }
    public void colorTrain(double speed, Color[] colors){
        int colorArrayPos;
        int x = (((int)(Timer.getFPGATimestamp() * speed) % (colors.length - 1)) * 2);
        for(int i = x; i < stripLength + x; i+=2){
            colorArrayPos = (i-x)/2;
            while(colorArrayPos > colors.length - 1){
                colorArrayPos = colorArrayPos - (colors.length - 1);
            }
            solidIndividual(i, prideSlide[colorArrayPos]);
            solidIndividual(i++, prideSlide[colorArrayPos]);
        }
    }
    //I sure hope this wouldn't break shit if the robot has an odd number of LEDs, but I am to lazy to fix it

    public static class Commands {
        public static Command solidPercent(double percent, Color c1){
            return new InstantCommand(() -> Lights.getInstance().solidPercent(percent, c1));
        }
        public static Command colorFade(Color c1, Color c2, double transitionTime){
            return new RunCommand(() -> Lights.getInstance().colorFade(c1, c2, transitionTime));
        }
        public static Command breath(double percent, Color c1, Color c2, double duration){
            return new RunCommand(() -> Lights.getInstance().breath(percent, c1, c2, duration));
        }
        public static Command blinkColors(double percent, Color... colors){
            return new RunCommand(() -> Lights.getInstance().blinkColors(percent, colors));
        }
        public static Command prideTrain(){
            return new RunCommand( ()-> getInstance().colorTrain(1, prideSlide));
        }
    }
 }
