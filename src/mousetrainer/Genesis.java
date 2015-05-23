package mousetrainer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Genesis extends JComponent implements Runnable
{
    private StartScreen startScreen;
    
    private int radius;
    private int radius2;
    private Point center;
    private double increment;
    private int screenWidth;
    private int screenHeight;
    
    private int type = 11;
    
    Color color;    
    
    public Genesis(StartScreen s, int r, int width, int height)
    {
        super();
        startScreen = s;
        radius = r;
        screenWidth = width;
        screenHeight = height;
        center = new Point(-radius, screenHeight/2);
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        int points = 33;
        
        double angle = Math.PI / points;
        
        int[] xPoints = new int[points*2];
        int[] yPoints = new int[points*2];
        
        radius2 = radius;
        
        increment = (radius2 - radius2/4)/51;
        for (int fade=0; fade<255; fade+=5)
        {
            for (int i = 0; i < points*2; i++)
            {
                double r = (i & 1) == 0 ? radius2/2 : radius2/3;
                Point2D.Double p = new Point2D.Double(center.x + Math.cos(i * angle) * r, center.y + Math.sin(i * angle) * r);
                xPoints[i] = (int)Math.round(p.getX());
                yPoints[i] = (int)Math.round(p.getY());
            }
            
            color = new Color(fade,fade,255);           
            g.setColor(color);
            
            if (fade >= 50 - type*5)
                g.fillPolygon(xPoints,yPoints, points*2);

            if (fade < 50)
                radius2 -= (increment/2);
            else
                radius2 -= increment;            
        }
    }
    
    public void run()
    {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex){}
        
        for (int c=0; c<(screenWidth+2*radius)/4; c++)
        {
            center.x+=4;
            this.repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex){}
        }
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex){}
        
        for (int c=0; c<(screenWidth+2*radius)/4; c++)
        {
            center.x-=4;
            this.repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex){}
        }
        
        center.x = screenWidth/2;
        center.y = 0 - radius;
        int increment;
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex){}
        
        for (int c=4; c < screenHeight/2 + radius; c+=4)
        {
            center.y += 4;
            
            this.repaint();
            
            if (c <= screenHeight/3)
                increment = 1;
            else if (c <= screenHeight/2)
                increment = 2;
            else if (c <= screenHeight/2 + radius/8)
                increment = 4;
            else if (c <= screenHeight/2 + radius/4)
                increment = 7;
            else if (c <= screenHeight/2 + radius/2)
                increment = 9;
            else
                increment = 12;
            
            try {
                Thread.sleep(increment);
            } catch (InterruptedException ex){}
        }
        
        startScreen.done();
        
        boolean backwards = false;
        
        while (true)
        {
            if (type == 11)
            {
                backwards = true;
                type --;
            }
            
            else if (type == 1)
            {
                backwards = false;
                type++;
            }
            
            else
            {
                if (backwards)
                    type--;
                else
                    type++;
            }
            
            this.repaint();
            
            try {
                Thread.sleep(120);
            } catch(InterruptedException ex) {}
        }
    }
}
