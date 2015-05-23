package mousetrainer;

import java.awt.event.*;

public class QuitController extends Object implements ActionListener
{
    MouseTrainerGUI view;
    MouseTrainerModel model;
    
    public QuitController(MouseTrainerGUI v, MouseTrainerModel m)
    {
        view = v;
        model = m;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("Yes"))
        {
            view.reset();
            model.reset();
        }
        
        else if (e.getActionCommand().equals("No"))
            view.resume(true);
    }
}
