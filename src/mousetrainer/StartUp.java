package mousetrainer;

import javax.swing.*;
import java.awt.*;

//4650 lines of code

public class StartUp
{
    public static void main(String []args)
    {  
        //Initialize the Frame
        JFrame f = new JFrame("Mouse Trainer");
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(d.width,d.height-(int)(d.height/20.88888889));
        if (f.getWidth() > 1280 && f.getHeight() >840)
            f.setSize(1280,840);
        
        ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Icon.png"));
        Image icon2 = icon.getImage();
        f.setIconImage(icon2);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MouseTrainerModel model = new MouseTrainerModel();
        MouseTrainerGUI main = new MouseTrainerGUI(model, f.getWidth(), f.getHeight()-30); 
        f.setContentPane(main);
        f.setVisible(true);
        f.setResizable(false);  
    }
}
