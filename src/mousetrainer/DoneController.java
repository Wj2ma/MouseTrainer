package mousetrainer;

import java.awt.event.*;
import javax.swing.*;

public class DoneController extends Object implements ActionListener
{
    private MouseTrainerGUI view;
    private MouseTrainerModel model;
    private int size;
    private int targets;
    private int time;
    private int lives;
    
    private boolean targetsError = false;
    private boolean timeError = false;
    private boolean livesError = false;
    
    private JComboBox size1;
    private JTextField targets1;
    private JTextField time1;
    private JTextField lives1;
    
    public DoneController(MouseTrainerGUI v, MouseTrainerModel m, JComboBox s, JTextField ts, JTextField t, JTextField l)
    {
        view = v;
        model = m;    
        size1 = s;
        targets1 = ts;
        time1 = t;
        lives1 = l;
    }
    
    public void actionPerformed(ActionEvent e)
    {        
        size = Integer.parseInt((String)size1.getSelectedItem());
        
        try {
            targets = Integer.parseInt(targets1.getText());
        } catch (NumberFormatException ex)
        {
            targetsError = true;
        }

        try {
            time = Integer.parseInt(time1.getText());
        } catch (NumberFormatException ex)
        {
            timeError = true;
        }
        
        try {
            lives = Integer.parseInt(lives1.getText());
        } catch (NumberFormatException ex)
        {
            livesError = true;
        }
        
        if (time == 0)
            timeError = true;
        if (targets == 0)
            targetsError = true;
        
        if (targetsError || timeError || livesError)
        {
            view.showError(targetsError, timeError, livesError);
            targetsError = false;
            timeError = false;
            livesError = false;
        }
        
        else
        {
            model.initializeGame(size,targets,time,lives);
            view.showGame();
            Thread thread = new Thread(view);
            thread.start();
        }
    }
}
