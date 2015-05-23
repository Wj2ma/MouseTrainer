package mousetrainer;

import java.awt.*;
import javax.swing.*;

public class Target extends JComponent implements Runnable
{
    private MouseTrainerGUI view;
    private StartScreen screen;
    
    private int score = 0;
    private int totalTime;
    private int currentTime = 0;
    private int size;
    private int fade = 192;
    
    private Color fadingRed;
    private Color fadingGrey;
    private Color darkRed = new Color(192,0,0);
    private Color grey = new Color(192,192,192);
    
    private boolean hit = false;
    private boolean failed = false;
    private boolean paused = false;
    
    private boolean terminate = false;
    
    public static final int ONE = 20;
    public static final int TWO = 40;
    public static final int THREE = 60;
    public static final int FOUR = 80;
    public static final int FIVE = 100;
    public static final int SIX = 120;
    public static final int SEVEN = 140;
    public static final int EIGHT = 160;
    public static final int NINE = 180;
    public static final int TEN = 200;
    
    private int bounds1;
    private int bounds2;
    
    private long threadID;

    public Target(int s, int t, MouseTrainerGUI v)
    {
        super();
        view = v;
        size = s;
        totalTime = t;
        this.setPreferredSize(new Dimension(size,size));
    }
    
    public Target(int s, int t, StartScreen v)
    {
        super();
        screen = v;
        size = s;
        totalTime = t;
        this.setPreferredSize(new Dimension(size,size));
    }
     
    public void paintComponent(Graphics g)
    {
        int inc = size/10;
        
        if (hit || failed)
        {
            if (fade > 0)
            {
                fadingRed = new Color(fade,0,0);
                fadingGrey = new Color(fade,fade,fade);
                g.setColor(fadingRed);
                g.fillOval(0,0,size,size);
                g.setColor(fadingGrey);
                g.fillOval(inc, inc, size-(2*inc), size-(2*inc));
                g.setColor(fadingRed);
                g.fillOval(2*inc,2*inc,size-(4*inc),size-(4*inc));
                g.setColor(fadingGrey);
                g.fillOval(3*inc,3*inc,size-(6*inc),size-(6*inc));
                g.setColor(fadingRed);
                g.fillOval(4*inc,4*inc,size-(8*inc),size-(8*inc));
            }
            
            if (failed)
            {
                if (fade > 0)
                {
                    g.setColor(Color.BLACK);
                    int[] xPoints = {inc, inc*2, size/2, inc*8, inc*9, inc*6, inc*9, inc*8, size/2, inc*2, inc, inc*4};
                    int[] yPoints = {inc*2, inc, inc*4, inc, inc*2, size/2, inc*8, inc*9, inc*6, inc*9, inc*8, size/2};
                    g.fillPolygon(xPoints, yPoints, 12);
                }
                
                g.setColor(Color.RED);
                g.setFont(new Font("Ariel", Font.BOLD, 20));
                
                if (size > 40)
                    g.drawString("-1 Life", 0, size/2);
                else
                    g.drawString("-1 Life", 0, 25);
            }
        }          
            
        else
        {
            g.setColor(Color.RED);
            g.fillOval(0,0,size,size);
            g.setColor(Color.WHITE);
            g.fillOval(inc, inc, size-(2*inc), size-(2*inc));
            g.setColor(Color.RED);
            g.fillOval(2*inc,2*inc,size-(4*inc),size-(4*inc));
            g.setColor(Color.WHITE);
            g.fillOval(3*inc,3*inc,size-(6*inc),size-(6*inc));
            g.setColor(Color.RED);
            g.fillOval(4*inc,4*inc,size-(8*inc),size-(8*inc));

            g.setColor(darkRed);
            g.fillArc(0,0,size,size,0,currentTime);
            g.setColor(grey);
            g.fillArc(inc,inc,size-(2*inc),size-(2*inc),0,currentTime);
            g.setColor(darkRed);
            g.fillArc(2*inc,2*inc,size-(4*inc),size-(4*inc),0,currentTime);
            g.setColor(grey);
            g.fillArc(3*inc,3*inc,size-(6*inc),size-(6*inc),0,currentTime);
            g.setColor(darkRed);
            g.fillArc(4*inc,4*inc,size-(8*inc),size-(8*inc),0,currentTime);
        }
    }
    
    public void run()
    {
        for (int c=0; c<360; c++)
        {
            if (terminate)
                return;
            
            synchronized(this) 
            {
                while (paused)
                {
                    try{
                        this.wait();
                    } catch (InterruptedException ex){}
                }  
                
                if (terminate)
                    return;
            }
            
            if (hit)
                break;
            
            try{
                Thread.sleep(Math.round(totalTime/360));
            } catch (InterruptedException ex){}          
            currentTime++;
            this.repaint();
        }    
        
        if (!hit)
        {
            failed = true;
            
            if (screen == null)
                view.setNewBounds(this);
            else
                screen.setNewBounds(this);
        }
        
        for (int c=0; c < 193; c++)
        {
            if (terminate)
                return;
            
            synchronized(this) 
            {
                while (paused)
                {
                    try{
                        this.wait();
                    } catch (InterruptedException ex){}
                    
                    if (terminate)
                        return;
                }  
            }  
            
            try{
                Thread.sleep(2);
            } catch (InterruptedException ex){}
            fade--;
            this.repaint();
        }    
        
        if (hit)
        {
            if (screen == null)
                view.removeTarget(this, false);
            else
                screen.removeTarget(this);
        }
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex){}    
        
        if (terminate)
            return;
        
        synchronized(this) 
        {
            while (paused)
            {
                try{
                    this.wait();
                } catch (InterruptedException ex){}

                if (terminate)
                    return;
            }  
        }  
        
        if (!hit)
        {
            if (screen == null)
                view.removeTarget(this, true);
            else
                screen.removeTarget(this);
        }
    }
    
    public int calculateScore(Point p)
    {
        if (hit || failed)
            return -150;
        
        score = 0;
        
        int point = (int)(Math.pow((size/2)-p.x,2) + Math.pow((size/2)-p.y,2));
        int inc = size/5;
        if (point < (int)Math.pow(size/2,2))
        {
            score += 20;
            if (point < (int)Math.pow((size-inc)/2,2))
            {
                score += 20;
                if (point < (int)Math.pow((size-2*inc)/2,2))
                {
                    score += 20;
                    if (point < (int)Math.pow((size-3*inc)/2,2))
                    {
                        score += 20;
                        if (point < (int)Math.pow((size-4*inc)/2,2))
                        {
                            score += 20;
                            
                            if (p.x >= Math.round(size/2-size/50.0) && p.x <= Math.round(size/2+size/50) && p.y >= Math.round(size/2-size/50) && p.y <= Math.round(size/2+size/50))
                                score += 100;
                        }
                    }
                }
            }
            hit = true;
        }
        
        else
            score = -150;
        
        return score;
    }
    
    public boolean isHit()
    {
        return hit;
    }
    
    public boolean isFailed()
    {
        return failed;
    }
    
    public void pause()
    {
        paused = true;
    }
    
    synchronized void resume()
    {
        paused = false;
        this.notify();
    }
    
    public void setFinalBounds(int b1, int b2)
    {
        bounds1 = b1;
        bounds2 = b2;
    }
    
    public int getBoundsX()
    {
        return bounds1;
    }
    
    public int getBoundsY()
    {
        return bounds2;
    }
    
    public void terminate()
    {
        terminate = true;
    }
    
    
    public void setThreadID(long id)
    {
        threadID = id;
    }
    
    public long getThreadID()
    {
        return threadID;
    }
    
    public void setCurrentTime(int time)
    {
        currentTime = time;
        this.repaint();
    }
}

