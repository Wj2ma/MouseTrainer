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

public class Shot extends JComponent implements Runnable
{    
    MouseTrainerGUI view;
    StartScreen screen;
    
    private int score;
    
    private Color color;
    private Color grey;
    
    private int fade1 = 130;
    private int fade2;
    private int fade3;
    private int fade4;
    
    private boolean paused = false;
    private boolean terminate = false;
    
    public Shot(int s, MouseTrainerGUI v)
    {
        super();
        
        view = v;
        
        if (s == 200)
        {
            fade2 = 255;
            fade3 = 0;
            fade4 = 255;
        }
        
        else if (s == 100)
        {
            fade2 = 50;
            fade3 = 255;
            fade4 = 0;
        }
        
        else if (s == 80)
        {
            fade2 = 120;
            fade3 = 255;
            fade4 = 0;
        }
        
        else if (s == 60)
        {
            fade2 = 190;
            fade3 = 255;
            fade4 = 0;
        }
        
        else if (s == 40)
        {
            fade2 = 255;
            fade3 = 255;
            fade4 = 0;
        }
        
        else if (s == 20)
        {
            fade2 = 255;
            fade3 = 165;
            fade4 = 0;
        }
        
        else
        {
            fade2 = 255;
            fade3 = 0;
            fade4 = 0;
        }
        
        score = s;
    }
    
    public Shot(int s, StartScreen v)
    {
        super();
        
        screen = v;
        
        if (s == 200)
        {
            fade2 = 255;
            fade3 = 0;
            fade4 = 255;
        }
        
        else if (s == 100)
        {
            fade2 = 50;
            fade3 = 255;
            fade4 = 0;
        }
        
        else if (s == 80)
        {
            fade2 = 120;
            fade3 = 255;
            fade4 = 0;
        }
        
        else if (s == 60)
        {
            fade2 = 190;
            fade3 = 255;
            fade4 = 0;
        }
        
        else if (s == 40)
        {
            fade2 = 255;
            fade3 = 255;
            fade4 = 0;
        }
        
        else if (s == 20)
        {
            fade2 = 255;
            fade3 = 165;
            fade4 = 0;
        }
        
        else if (s == 1000)
        {
            fade2 = 0;
            fade3 = 255;
            fade4 = 255;
        }
        
        else
        {
            fade2 = 255;
            fade3 = 0;
            fade4 = 0;
        }
        
        score = s;
    }
    
    public void paintComponent(Graphics g)
    {
        grey = new Color(fade1, fade1, fade1);
        g.setColor(grey);
        g.drawOval(0,0,6,6);
        
        color = new Color(fade2, fade3, fade4);
        g.setColor(color);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if (score>0)
            g.drawString("+" + score, 20, 20);
        else
            g.drawString("" + score, 20, 20);   
    }
    
    public void run()
    {     
        for (int c=0; c<255; c++)
        {
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
            
            if (c >= 125)
                fade1--;
            if(fade2>0)
                fade2--;
            if(fade3>0)
                fade3--;
            if(fade4>0)
                fade4--;
            
            try{
                Thread.sleep(4);
            } catch (InterruptedException ex){}
            
            this.repaint();
        }
        
        if (screen == null)
            view.removeShot(this);
        else
            screen.removeShot(this);
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
    
    public void terminate()
    {
        terminate = true;
    }
}
