/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mousetrainer;

/**
 *
 * @author dushz77
 */

import javax.swing.*;
import java.awt.*;

public class GiantTarget extends JComponent implements Runnable
{
    private StartScreen startScreen;
    
    private int screenWidth;
    private int screenHeight;
    
    private Color fadingRed;
    private Color fadingGrey;
    
    private int fade = 0;
    
    private int inc;
    private int increment;
    
    private boolean notDone = true;
    
    public GiantTarget(StartScreen s, int sw, int sh)
    {
        super();
        startScreen = s;
        screenWidth = sw;
        screenHeight = sh;
    }
    
    
    public void paintComponent(Graphics g)
    {
        fadingRed = new Color(fade,0,0);
        fadingGrey = new Color(fade,fade,fade);
        
        inc = screenWidth/20;
        if (notDone)
        {
            g.setColor(fadingRed);
            g.fillOval(screenWidth/4,0,screenWidth/2,screenWidth/2);
            g.setColor(fadingGrey);
            g.fillOval(screenWidth/4 + inc, inc, screenWidth/2-(2*inc), screenWidth/2-(2*inc));
            g.setColor(fadingRed);
            g.fillOval(screenWidth/4 + 2*inc,2*inc,screenWidth/2-(4*inc),screenWidth/2-(4*inc));
            g.setColor(fadingGrey);
            g.fillOval(screenWidth/4 + 3*inc,3*inc,screenWidth/2-(6*inc),screenWidth/2-(6*inc));
            g.setColor(fadingRed);
            g.fillOval(screenWidth/4 + 4*inc,4*inc,screenWidth/2-(8*inc),screenWidth/2-(8*inc));
        }
        
        else
        {
            g.setColor(fadingRed);
            g.fillArc(screenWidth/4 + increment,0 - increment,screenWidth/2,screenWidth/2,0,90);
            g.fillArc(screenWidth/4 - increment,0 - increment,screenWidth/2,screenWidth/2,90,90);
            g.fillArc(screenWidth/4 - increment,0 + increment,screenWidth/2,screenWidth/2,180,90);
            g.fillArc(screenWidth/4 + increment,0 + increment,screenWidth/2,screenWidth/2,270,90);
            g.setColor(fadingGrey);
            g.fillArc(screenWidth/4 + inc + increment,inc - increment,screenWidth/2-(2*inc),screenWidth/2-(2*inc),0,90);
            g.fillArc(screenWidth/4 + inc - increment,inc - increment,screenWidth/2-(2*inc),screenWidth/2-(2*inc),90,90);
            g.fillArc(screenWidth/4 + inc - increment,inc + increment,screenWidth/2-(2*inc),screenWidth/2-(2*inc),180,90);
            g.fillArc(screenWidth/4 + inc + increment,inc + increment,screenWidth/2-(2*inc),screenWidth/2-(2*inc),270,90);
            g.setColor(fadingRed);
            g.fillArc(screenWidth/4 + 2*inc + increment,2*inc - increment,screenWidth/2-(4*inc),screenWidth/2-(4*inc),0,90);
            g.fillArc(screenWidth/4 + 2*inc - increment,2*inc - increment,screenWidth/2-(4*inc),screenWidth/2-(4*inc),90,90);
            g.fillArc(screenWidth/4 + 2*inc - increment,2*inc + increment,screenWidth/2-(4*inc),screenWidth/2-(4*inc),180,90);
            g.fillArc(screenWidth/4 + 2*inc + increment,2*inc + increment,screenWidth/2-(4*inc),screenWidth/2-(4*inc),270,90);
            g.setColor(fadingGrey);
            g.fillArc(screenWidth/4 + 3*inc + increment,3*inc - increment,screenWidth/2-(6*inc),screenWidth/2-(6*inc),0,90);
            g.fillArc(screenWidth/4 + 3*inc - increment,3*inc - increment,screenWidth/2-(6*inc),screenWidth/2-(6*inc),90,90);
            g.fillArc(screenWidth/4 + 3*inc - increment,3*inc + increment,screenWidth/2-(6*inc),screenWidth/2-(6*inc),180,90);
            g.fillArc(screenWidth/4 + 3*inc + increment,3*inc + increment,screenWidth/2-(6*inc),screenWidth/2-(6*inc),270,90);
            g.setColor(fadingRed);
            g.fillArc(screenWidth/4 + 4*inc + increment,4*inc - increment,screenWidth/2-(8*inc),screenWidth/2-(8*inc),0,90);
            g.fillArc(screenWidth/4 + 4*inc - increment,4*inc - increment,screenWidth/2-(8*inc),screenWidth/2-(8*inc),90,90);
            g.fillArc(screenWidth/4 + 4*inc - increment,4*inc + increment,screenWidth/2-(8*inc),screenWidth/2-(8*inc),180,90);
            g.fillArc(screenWidth/4 + 4*inc + increment,4*inc + increment,screenWidth/2-(8*inc),screenWidth/2-(8*inc),270,90);
        }
    }
    
    public void run()
    {
        for (int c=0; c < 255; c++)
        {
            fade++;
            this.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex){}
        }
       
        synchronized (this)
        {
            while (notDone)
            {
                try {
                    this.wait();
                } catch (InterruptedException ex){}
            }
        }
        
        increment = 0;
        while (increment < screenWidth/4 || increment < screenHeight - screenWidth/4)
        {
            increment++;
            this.repaint();
            try {
                Thread.sleep(3);
            } catch (InterruptedException ex){}
        }
        
        startScreen.check();
    }
    
    synchronized void confirm()
    {
        notDone = false;
        this.notify();
    }
}
