package mousetrainer;

import java.awt.event.*;

public class PauseController extends Object implements ActionListener
{
    MouseTrainerGUI view;
    
    public PauseController(MouseTrainerGUI v)
    {
        view = v;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("Pause"))
            view.pause(false);
        
        else if (e.getActionCommand().equals("Resume"))
            view.resume(false);
        
        else if (e.getActionCommand().equals("Quit"))
            view.pause(true);
    }
}
