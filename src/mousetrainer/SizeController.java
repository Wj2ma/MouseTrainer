package mousetrainer;

import java.awt.event.*;
import javax.swing.*;

public class SizeController extends Object implements ActionListener
{
    MouseTrainerGUI view;
    
    public SizeController(MouseTrainerGUI v)
    {
        view = v;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        JComboBox box = (JComboBox) e.getSource();
        
        if (box.getSelectedItem().equals("1"))
            view.changeTarget(1);
        else if (box.getSelectedItem().equals("2"))
            view.changeTarget(2);
        else if (box.getSelectedItem().equals("3"))
            view.changeTarget(3);
        else if (box.getSelectedItem().equals("4"))
            view.changeTarget(4);
        else if (box.getSelectedItem().equals("5"))
            view.changeTarget(5);
        else if (box.getSelectedItem().equals("6"))
            view.changeTarget(6);
        else if (box.getSelectedItem().equals("7"))
            view.changeTarget(7);
        else if (box.getSelectedItem().equals("8"))
            view.changeTarget(8);
        else if (box.getSelectedItem().equals("9"))
            view.changeTarget(9);
        else if (box.getSelectedItem().equals("10"))
            view.changeTarget(10);
    }
}
