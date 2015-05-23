package mousetrainer;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.FontUIResource;

public class MainController extends Object implements ActionListener
{
    private MouseTrainerGUI view;
    private MouseTrainerModel model;
    
    private int screenWidth;
    private int screenHeight;
    
    public MainController(MouseTrainerGUI v, MouseTrainerModel m, int sw, int sh)
    {
        view = v;
        model = m;
        screenWidth = sw;
        screenHeight = sh;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("Start Standard Game"))
        {
            Object[] levels = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50"};
            JFrame frame = new JFrame();
            frame.setSize((int)(screenWidth/4.2), (int)(screenWidth/8.4));
            Font font = new Font("Ariel", Font.BOLD, screenWidth/56);
            JLabel command = new JLabel("Please choose a level to start on.");
            command.setFont(font);
            UIManager.put("OptionPane.buttonFont", new FontUIResource(font));          
            String s = (String) JOptionPane.showInputDialog(frame, command, "Level Chooser", JOptionPane.PLAIN_MESSAGE, null, levels, "1");
            
            if (s != null)
            {
                int confirm = 0;
                if (Integer.parseInt(s) > 1)
                {
                    command.setText("<html>Warning: Choosing a level other than 1 will not save your highest level <br>and highest level scores. Are you sure you want to continue?</html>");
                    confirm = JOptionPane.showConfirmDialog(frame, command, "Warning", JOptionPane.YES_NO_OPTION);            
                }
                
                if (confirm == 0)
                {
                    model.initializeGame(s);
                    view.startStandardGame(s);
                    view.showGame();
                    Thread thread = new Thread(view);
                    thread.start();
                }
            }
        }
        
        else if (e.getActionCommand().equals("Start Custom Game"))
            view.showCustom();
        
        else if (e.getActionCommand().equals("Options"))
            view.showOptions();
        
        else if (e.getActionCommand().equals("Instructions"))
            view.showInstructions();
        
        else if (e.getActionCommand().equals("High Scores"))
            view.showHighScores();
    }
}
