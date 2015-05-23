package mousetrainer;

import java.awt.event.*;

public class CycleController extends Object implements ActionListener
{
    private MouseTrainerGUI view;
    
    private int page = 1;
    
    public CycleController(MouseTrainerGUI v)
    {
        view = v;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("Next"))
            page++;
        else if (e.getActionCommand().equals("Previous"))
            page--;
        
        view.changePage(page);
    }
    
    public void reset()
    {
        page = 1;
    }
}
