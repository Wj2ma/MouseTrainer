package mousetrainer;

import javax.swing.*;
import java.awt.*;

public class Cursor extends JComponent implements Runnable
{
    private int screenWidth;
    private int screenHeight;
    
    private StartScreen startScreen;
    
    private double xInc;
    private double yInc;
    
    private int[] xPoints = new int[7];
    private int[] yPoints = new int[7];
    
    public Cursor(int width, int height)
    {
        super();
        screenWidth = width;
        screenHeight = height;        
        Dimension size = new Dimension (screenWidth/84, screenWidth/42);
        this.setPreferredSize(size);
    }
    
    public Cursor(int width, int height, StartScreen s)
    {
        super();
        startScreen = s;
        screenWidth = width;
        screenHeight = height;        
        Dimension size = new Dimension (screenWidth/84, screenWidth/42);
        this.setPreferredSize(size);
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);   
        
        xPoints[0] = 0 + (int)Math.round(xInc);
        xPoints[1] = screenWidth/84 + (int)Math.round(xInc);
        xPoints[2] = screenWidth/140 + (int)Math.round(xInc);
        xPoints[3] = screenWidth/105 + (int)Math.round(xInc);
        xPoints[4] = screenWidth/140 + (int)Math.round(xInc);
        xPoints[5] = screenWidth/210 + (int)Math.round(xInc);
        xPoints[6] = 0 + (int)Math.round(xInc);
        yPoints[0] = 0 + (int)Math.round(yInc);
        yPoints[1] = screenWidth/70 + (int)Math.round(yInc);
        yPoints[2] = screenWidth/70 + (int)Math.round(yInc);
        yPoints[3] = (int)Math.round(screenWidth/46.6666667 + yInc);
        yPoints[4] = (int)Math.round(screenWidth/44.21052632 + yInc);
        yPoints[5] = screenWidth/70 + (int)Math.round(yInc);
        yPoints[6] = (int)Math.round(screenWidth/54.19354839 + yInc);
        
        g.setColor(Color.WHITE);
        g.fillPolygon(xPoints, yPoints, 7);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 7);
    }
    
    public void run()
    {
        xInc = 2*screenWidth/3.0;
        yInc = 2*screenHeight/3.0;
        this.repaint();
        
        double slopeX = xInc - (screenWidth/3.0 + 68);
        double slopeY = yInc - (screenHeight/3.0 + 68);
        
        if (slopeX > slopeY)
        {
            slopeX = slopeX/slopeY;
            slopeY = 1;
        }
        
        else
        {
            slopeY = slopeY/slopeX;
            slopeX = 1;
        }
        
        while (xInc > screenWidth/3 + 68 || yInc > screenHeight/3 + 68)
        {
            if (xInc > screenWidth/3 + 68)
                xInc -= slopeX;
            if (yInc > screenHeight/3 + 68)
                yInc -= slopeY;
            this.repaint();
            
            try {
                Thread.sleep(Math.round(6000.0/screenWidth));
            } catch (InterruptedException ex) {}
        }
        
        try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}
        
        startScreen.check();
        
        try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {}
        
        slopeX = xInc - (2*screenWidth/3.0 + 90);
        slopeY = yInc - (screenHeight/2.0 + 90);
        
        if (slopeX > slopeY)
        {
            slopeX = slopeX/slopeY;
            slopeY = 1;
        }
        
        else
        {
            slopeY = slopeY/slopeX;
            slopeX = 1;
        }
        
        while (xInc < 2*screenWidth/3 + 90 || yInc < screenHeight/2 + 90)
        {
            if (xInc < 2*screenWidth/3 + 90)
                xInc += slopeX;
            if (yInc < screenHeight/2 + 90)
                yInc += slopeY;
            
            this.repaint();
            
            try {
                Thread.sleep(Math.round(3200.0/screenWidth));
            } catch (InterruptedException ex) {}
        }
        
        try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}
        
        startScreen.check();
        
        try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {}
        
        slopeX = xInc - (screenWidth/4.0 + 45);
        slopeY = yInc - (5*screenHeight/7.0 + 40);
        
        if (slopeX > slopeY)
        {
            slopeX = slopeX/slopeY;
            slopeY = 1;
        }
        
        else
        {
            slopeY = slopeY/slopeX;
            slopeX = 1;
        }
        
        while (xInc > screenWidth/4 + 45 || yInc < 5*screenHeight/7 + 40)
        {
            if (xInc > screenWidth/4 + 45)
                xInc += slopeX;
            if (yInc < 5*screenHeight/7 + 40)
                yInc += slopeY;
            
            this.repaint();
            
            try {
                Thread.sleep(Math.round(7500.0/screenWidth));
            } catch (InterruptedException ex) {}
        }
        
        try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}
        
        startScreen.check();
        
        try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
        
        slopeX = xInc - screenWidth/2.0;
        slopeY = yInc - screenWidth/4.0;
        
        if (slopeX > slopeY)
        {
            slopeX = slopeX/slopeY;
            slopeY = 1;
        }
        
        else
        {
            slopeY = slopeY/slopeX;
            slopeX = 1;
        }  
        
        while (xInc < screenWidth/2 || yInc > screenWidth/4)
        {
            if (xInc < screenWidth/2)
                xInc += slopeX;
            if (yInc > screenWidth/4)
                yInc += slopeY;
            
            this.repaint();
            
            try {
                Thread.sleep(Math.round(7000.0/screenWidth));
            } catch (InterruptedException ex) {}
        }
        
        try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}
        
        startScreen.check();
    }
    
    public void removeShot(Shot s)
    {
        this.remove(s);
        this.repaint();
    }
}
