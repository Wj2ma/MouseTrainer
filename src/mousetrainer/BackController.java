package mousetrainer;

import java.awt.event.*;

public class BackController extends Object implements ActionListener
{
    private MouseTrainerGUI view;
    private CycleController controller;
    
    public BackController(MouseTrainerGUI v)
    {
        view = v;
    }
    
    public BackController(MouseTrainerGUI v, CycleController c)
    {
        view = v;
        controller = c;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        view.showMain();
        
        if (controller != null)
            controller.reset();
    }
}
