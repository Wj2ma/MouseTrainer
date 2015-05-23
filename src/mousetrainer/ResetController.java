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

public class ResetController extends Object implements ActionListener
{
    private MouseTrainerGUI view;
    private MouseTrainerModel model;
    
    public ResetController(MouseTrainerGUI v, MouseTrainerModel m)
    {
        view = v;
        model = m;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("1"))
            view.showConfirmation(1);
        
        else if (e.getActionCommand().equals("2"))
            view.showConfirmation(2);
        
        else if (e.getActionCommand().equals("3"))
            view.showConfirmation(3);
        
        else if (e.getActionCommand().equals("4"))
            view.showConfirmation(4);
    }
}

