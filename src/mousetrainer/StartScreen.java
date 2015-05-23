/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mousetrainer;

/**
 *
 * @author dushz77
 */
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.*;

public class StartScreen extends JLayeredPane implements Runnable
{
    private MouseTrainerGUI view;
    
    private int screenWidth;
    private int screenHeight;
    
    private boolean notDone = true;
    private boolean check = false;
    
    private Target target;
    private Target target2;
    private Target target3;
    
    private Shot shot;
    private Shot shot2;
    private Shot shot3;
    private Shot shot4;
    
    private boolean skip1 = false;
    
    public StartScreen(MouseTrainerGUI v, int sw, int sh)
    {
        super();
        view = v;
        screenWidth = sw;
        screenHeight = sh;
        this.setBackground(Color.BLACK);
        this.setOpaque(true);
        this.setLayout(null);
    }
    
    public void run()
    {
        Genesis logo = new Genesis(this, screenWidth/4, screenWidth, screenHeight);
        this.add(logo);
        logo.setBounds(0,0,screenWidth,screenHeight);
        Thread logoThread = new Thread(logo);
        logoThread.start();       
        
        synchronized (this)
        {
            while (notDone)
            {
                try{
                    this.wait();
                } catch (InterruptedException ex){}
            }
        }
        
        if (!skip1)
        {
            try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex){}

            JLabel genesis = new JLabel("GENESIS");
            genesis.setForeground(Color.BLACK);
            genesis.setFont(new Font("Showcard Gothic", Font.BOLD, screenWidth/17));
            this.add(genesis);
            genesis.setBounds(3*screenWidth/8, screenHeight/2 + screenWidth/8 + screenHeight/20,5*screenWidth/16,screenHeight/11);
            Color color = new Color(0, 0, 0);
            for (int fade=0; fade<255; fade++)
            {
                if (skip1)
                    break;
                color = new Color(fade,fade,fade);
                genesis.setForeground(color);
                try {
                    Thread.sleep(8);
                } catch (InterruptedException ex){}
            }

            if (!skip1)
                try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex){}       
        }
        
        this.removeAll();
        this.repaint();
        
        skip1 = false;
        check = false;
            
        try {
                Thread.sleep(1000);
            } catch (InterruptedException ex){}
        
        Cursor cursor = new Cursor(screenWidth, screenHeight, this);
        if (!skip1)
        {
            target = new Target(Target.FIVE, 2000, this);
            target2 = new Target(Target.NINE, 2000, this);
            target3 = new Target(Target.TWO, 1000, this);
            shot = new Shot(60, this);
            shot2 = new Shot(200, this);
            shot3 = new Shot(-150, this);
            shot4 = new Shot(1000, this);
            this.add(target);
            target.setBounds(screenWidth/3,screenHeight/3,100,100);
            Thread t1 = new Thread(target);           
            this.add(cursor, JLayeredPane.MODAL_LAYER);
            cursor.setBounds(0,0,screenWidth,screenHeight);
            t1.start();
            Thread cursorThread = new Thread(cursor);
            cursorThread.start();
        }

        synchronized (this)
        {
            while (!check)
            {
                try{
                    this.wait();
                } catch (InterruptedException ex){}
            }
        }

        if (!skip1)
        {
            check = false;
            target.calculateScore(new Point(50,50));
            this.add(shot, JLayeredPane.PALETTE_LAYER);
            shot.setBounds(screenWidth/3 + 65, screenHeight/3 + 65, 80, 80);
            Thread s1 = new Thread(shot);
            s1.start();
            this.add(target2);
            target2.setBounds(2*screenWidth/3, screenHeight/2,180,180);
            Thread t2 = new Thread(target2);
            t2.start();
        }

        synchronized (this)
        {
            while (!check)
            {
                try{
                    this.wait();
                } catch (InterruptedException ex){}
            }
        }

        if (!skip1)
        {
            check = false;
            target2.calculateScore(new Point(100,100));
            this.add(shot2, JLayeredPane.PALETTE_LAYER);
            shot2.setBounds(2*screenWidth/3 + 87, screenHeight/2 + 87, 80, 80);
            Thread s2 = new Thread(shot2);
            s2.start();
            this.add(target3);
            target3.setBounds(screenWidth/4, 5*screenHeight/7,40,40);
            Thread t3 = new Thread(target3);
            t3.start();
        }

        synchronized (this)
        {
            while (!check)
            {
                try{
                    this.wait();
                } catch (InterruptedException ex){}
            }
        }

        GiantTarget gT = new GiantTarget(this, screenWidth, screenHeight);
        if (!skip1)
        {
            check = false;
            this.add(shot3, JLayeredPane.PALETTE_LAYER);
            shot3.setBounds(screenWidth/4 + 41, 5*screenHeight/7 + 37, 80, 80);
            Thread s3 = new Thread(shot3);
            s3.start();

            try {
                    Thread.sleep(750);
                } catch (InterruptedException ex){}

            if (!skip1)
            {              
                this.add(gT, JLayeredPane.PALETTE_LAYER);
                gT.setBounds(0,0,screenWidth,screenHeight);
                Thread gTh = new Thread(gT);
                gTh.start();
            }
        }

        synchronized (this)
        {
            while (!check)
            {
                try{
                    this.wait();
                } catch (InterruptedException ex){}
            }
        }

        if (!skip1)
        {
            gT.confirm();
            check = false;
            this.add(shot4, JLayeredPane.MODAL_LAYER);
            shot4.setBounds(screenWidth/2 - 3,screenWidth/4 - 3,80,80);
            Thread s4 = new Thread(shot4);
            s4.start();
        }
        
        if (skip1)
        {
            this.removeAll();
            this.repaint();
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
        }
        
        JLabel mouseTrainer = new JLabel("Mouse Trainer");
        mouseTrainer.setFont(new Font("Blade Runner Movie Font", Font.BOLD, screenWidth/19));
        mouseTrainer.setForeground(Color.RED);
        this.add(mouseTrainer, JLayeredPane.DEFAULT_LAYER);
        mouseTrainer.setBounds(screenWidth/4, screenWidth/8, screenWidth/2, screenWidth/4);
        cursor.setVisible(false);

        synchronized (this)
        {
            while (!check)
            {
                try{
                    this.wait();
                } catch (InterruptedException ex){}
            }
        }

        if (skip1)
            this.remove(gT);

        JLabel credits = new JLabel("Created by: William");
        credits.setFont(new Font("Ariel", Font.BOLD, screenWidth/33));
        JLabel loading = new JLabel("Loading");
        loading.setFont(new Font("Ariel", Font.BOLD, screenWidth/33));
        loading.setForeground(Color.WHITE);
        this.add(credits);
        this.add(loading);
        credits.setBounds(screenWidth/2 - screenWidth/6, screenHeight/2, screenWidth/3, screenHeight/5);
        loading.setBounds(screenWidth/2 - screenWidth/10, screenHeight - screenHeight/10, screenWidth/5, screenHeight/10);

        Color color;
        for (int fade=0; fade<255; fade++)
        {
            if (skip1)
                fade = 255;
            color = new Color(fade,fade,fade);
            credits.setForeground(color);
            loading.setForeground(color);
            try {
                    Thread.sleep(3);
                } catch (InterruptedException ex){}         
        }

        for (int counter=0; counter<3; counter++)
        {               
            try {
                    Thread.sleep(300);
                } catch (InterruptedException ex){}

            loading.setText("Loading.");

            try {
                    Thread.sleep(300);
                } catch (InterruptedException ex){}

            loading.setText("Loading..");

            try {
                    Thread.sleep(300);
                } catch (InterruptedException ex){}

            loading.setText("Loading...");

            try {
                    Thread.sleep(300);
                } catch (InterruptedException ex){}

            loading.setText("Loading");
        }

        this.removeAll();
        this.repaint();

        try {
                Thread.sleep(500);
            } catch (InterruptedException ex){}
        
        view.showMain();
    }
    
    synchronized void done()
    {
        notDone = false;
        this.notify();
    }
    
    public void removeTarget(Target t)
    {
        this.remove(t);
        this.repaint();
    }
    
    public void removeShot(Shot s)
    {
        this.remove(s);
        this.repaint();
    }
    
    public void setNewBounds(Target t)
    {
        t.setBounds(t.getBounds().x, t.getBounds().y, 200, 200);
    }
    
    synchronized void skip()
    {
        notDone = false;
        skip1 = true;
        check = true;
        this.notify();
    }
    
    synchronized void check()
    {
        check = true;
        this.notify();
    }
}
