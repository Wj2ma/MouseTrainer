package mousetrainer;

import java.awt.event.*;

public class FinishController extends Object implements ActionListener
{    
    private MouseTrainerGUI view;
    private MouseTrainerModel model;
    
    public FinishController(MouseTrainerGUI v, MouseTrainerModel m)
    {
        view = v;
        model = m;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        view.reset();
        model.reset();
    }
}
