package mousetrainer;

import javax.swing.*;
import java.awt.*;

public class Arrows extends JComponent
{
    private int screenWidth;
    private int screenHeight;
    private int page;
    
    public Arrows(int width, int height)
    {
        super();
        screenWidth = width;
        screenHeight = height;
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(screenWidth/336));
        
        if (page == 7)
        {          
            g2.drawLine(screenWidth/16, 30, screenWidth/16, screenHeight/8);          
            g2.drawLine(screenWidth/16, 30, screenWidth/16 - 10, 40);
            g2.drawLine(screenWidth/16, 30, screenWidth/16 + 10, 40);
            g2.drawLine(15*screenWidth/16, 30, 15*screenWidth/16, screenHeight/8);
            g2.drawLine(15*screenWidth/16, 30, 15*screenWidth/16 - 10, 40);
            g2.drawLine(15*screenWidth/16, 30, 15*screenWidth/16 + 10, 40);
        }
        
        else if (page == 9)
        {
            g2.drawLine(screenWidth/4, screenHeight-(int)(screenHeight/18.8)-3, screenWidth/4, 7*screenHeight/8-(int)(screenHeight/18.8) + 27);
            g2.drawLine(screenWidth/4, screenHeight-(int)(screenHeight/18.8)-3, screenWidth/4 - 10, screenHeight-(int)(screenHeight/18.8)-13);
            g2.drawLine(screenWidth/4, screenHeight-(int)(screenHeight/18.8)-3, screenWidth/4 + 10, screenHeight-(int)(screenHeight/18.8)-13);
            g2.drawLine(3*screenWidth/4, screenHeight-(int)(screenHeight/18.8)-3, 3*screenWidth/4, 7*screenHeight/8-(int)(screenHeight/18.8) + 27);
            g2.drawLine(3*screenWidth/4, screenHeight-(int)(screenHeight/18.8)-3, 3*screenWidth/4 - 10, screenHeight-(int)(screenHeight/18.8)-13);
            g2.drawLine(3*screenWidth/4, screenHeight-(int)(screenHeight/18.8)-3, 3*screenWidth/4 + 10, screenHeight-(int)(screenHeight/18.8)-13);
        }
        
        else if (page == 10)
        {
            g2.drawLine(screenWidth/4, screenHeight-(int)(screenHeight/18.8)-3, screenWidth/4, 7*screenHeight/8-(int)(screenHeight/18.8) + 27);
            g2.drawLine(screenWidth/4, screenHeight-(int)(screenHeight/18.8)-3, screenWidth/4 - 10, screenHeight-(int)(screenHeight/18.8)-13);
            g2.drawLine(screenWidth/4, screenHeight-(int)(screenHeight/18.8)-3, screenWidth/4 + 10, screenHeight-(int)(screenHeight/18.8)-13);
        }
        
        else if (page == 11)
        {
            g2.drawLine(3*screenWidth/4, screenHeight-(int)(screenHeight/18.8)-3, 3*screenWidth/4, 7*screenHeight/8-(int)(screenHeight/18.8) + 27);
            g2.drawLine(3*screenWidth/4, screenHeight-(int)(screenHeight/18.8)-3, 3*screenWidth/4 - 10, screenHeight-(int)(screenHeight/18.8)-13);
            g2.drawLine(3*screenWidth/4, screenHeight-(int)(screenHeight/18.8)-3, 3*screenWidth/4 + 10, screenHeight-(int)(screenHeight/18.8)-13);
        }
    }
    
    public void setPage(int p)
    {
        page = p;
        this.repaint();
    }
}
