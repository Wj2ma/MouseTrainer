/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mousetrainer;

/**
 *
 * @author dushz77
 */
import java.awt.event.*;
import java.awt.*;

public class MouseController extends Object implements MouseListener
{
    private MouseTrainerGUI view;
    private MouseTrainerModel model;
    
    private Target selected;
    private int score;
    
    public MouseController(MouseTrainerGUI v, MouseTrainerModel m)
    {
        view = v;
        model = m;
    }
    
    public void mouseClicked(MouseEvent e){}
    
    public void mousePressed(MouseEvent e)
    {
        if (e.getComponent().equals(view.getStarter()))
        {
            if (view.isStarter())
                view.skip();
        }
        
        else if (view.isPaused() || view.isStart());
        
        else
        {
            try {
                selected = (Target) e.getComponent();
            } catch (ClassCastException ex){}

            Point p = e.getPoint();

            if (selected != null)
            {
                score = selected.calculateScore(p);
                if (score == -150)
                    score = view.findNewScore(selected, p);
            }

            else
                score = -150;

            if (score != 0)
            {
                view.showShot(score, p, selected);
                model.addScore(score);
            }

            selected = null;
        }
    }
    
    public void mouseReleased(MouseEvent e){}
    
    public void mouseEntered(MouseEvent e){}
    
    public void mouseExited(MouseEvent e){}
}
