package mousetrainer;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.plaf.FontUIResource;

public class MouseTrainerGUI extends JPanel implements Runnable
{
    private MouseTrainerModel model;
    
    private PrintWriter out;
    private Scanner in;
    
    private StartScreen startScreen;
    private JPanel main;
    private JPanel decoration1;
    private JPanel decoration2;
    private JLayeredPane game;
    private JPanel settings;
    private JPanel help;
    private JPanel edits;
    private JPanel edits2;
    private JPanel scoring;
    
    private JPanel gameOverScreen = new JPanel();
    private JPanel flowYourScore = new JPanel();
    private JPanel flowYourHighscore = new JPanel();
    private JPanel flowCongratulations = new JPanel();
    private JPanel flowGameOver = new JPanel();
    
    private Thread startThread;
    private boolean starter = true;
    
    private JButton start = new JButton("Start Standard Game");
    private JButton startCustom = new JButton("Start Custom Game");
    private JButton options = new JButton("Options");
    private JButton instructions = new JButton("Instructions");
    private JButton highScore = new JButton("High Scores");
    
    private JButton done = new JButton("Start Game");
    private JButton back1 = new JButton("Back");
    private JButton back2 = new JButton("Back");
    private JButton back3 = new JButton("Back");
    private JButton back4 = new JButton("Back");
    
    private JLabel score = new JLabel("Score:");
    private JLabel livesLeft = new JLabel("Lives:");
    private JButton pause = new JButton("Pause");
    private JButton quit = new JButton("Quit");
    private JLabel levels = new JLabel("Level");
    private JLabel beatLevelScore = new JLabel("Congratulations! You achieved a new level high score!");
    private JLabel beatLevelTotalScore = new JLabel("Congratulations! You achieved a new level total high score!");
    
    private JLabel warning = new JLabel("<html>Are you sure you want to quit?<br>Quitting will not save your high score</html>");
    private JButton no = new JButton("No");
    private JButton yes = new JButton("Yes");
    
    private JLabel paused = new JLabel("PAUSED");
    private boolean pauseOn = false;
    private boolean pauseOn2 = false;
    private boolean starting = false;
    
    private JButton next = new JButton("Next");
    private JButton previous = new JButton("Previous");
    private JPanel top;
    private JLayeredPane gameEx;
    private JLabel scoreEx = new JLabel("Score: 0");
    private JLabel livesEx = new JLabel("Lives: 10");
    private JButton pauseEx = new JButton("Pause");
    private JButton quitEx = new JButton("Quit");
    private JLabel pausedEx = new JLabel("PAUSED");
    private JLabel warningEx = new JLabel("<html>Are you sure you want to quit?<br>Quitting will not save your high score</html>");
    private JButton noEx = new JButton("No");
    private JButton yesEx = new JButton("Yes");
    private Target ex1;
    private Target ex2;
    private Target ex3;
    private Target ex4;
    private Target ex5;
    private Target ex6;
    private Target ex7;
    private Shot shotEx1;
    private Shot shotEx2;
    private Shot shotEx3;
    private Shot shotEx4;
    private Arrows arrow;
    private Cursor cursor;
    private JLabel instruct = new JLabel("<html>This is a target. Click it to gain points.</html>");
    private JLabel instruct2 = new JLabel("<html>Your score is kept here.</html>");
    
    private JLabel targetSizeLabel = new JLabel("Target Size:");
    private String[] sizes = {"1","2","3","4","5","6","7","8","9","10"};
    private JComboBox targetSize = new JComboBox(sizes);
    private JLabel numTargetsLabel = new JLabel("Amount of Targets at a Time:");
    private LimitField numTargets = new LimitField(3,2);
    private JLabel timerLabel = new JLabel("Target Lifespan (ms):");
    private LimitField timer = new LimitField(3,9);
    private JLabel livesLabel = new JLabel("Lives (0 for infinite):");
    private LimitField lives = new LimitField(3,3);
    private JLabel error = new JLabel("Error: 1 or more options have incorrect formatting");
    
    private final Target size1 = new Target(Target.ONE,0, this);
    private final Target size2 = new Target(Target.TWO,0, this);
    private final Target size3 = new Target(Target.THREE,0, this);
    private final Target size4 = new Target(Target.FOUR,0, this);
    private final Target size5 = new Target(Target.FIVE,0, this);
    private final Target size6 = new Target(Target.SIX,0, this);
    private final Target size7 = new Target(Target.SEVEN,0, this);
    private final Target size8 = new Target(Target.EIGHT,0, this);
    private final Target size9 = new Target(Target.NINE,0, this);
    private final Target size10 = new Target(Target.TEN,0, this);
    private Target currentSize;
    private JPanel targetHolder;
    
    private int screenWidth;
    private int screenHeight;
    private int instructionHeight;
    
    private Font font;
    private Font font2;
    private Font font3;
    private Font font4;
    private Font font5;
    private Font font6;
    
    private ArrayList <Target> targets = new ArrayList();
    private ArrayList <Thread> threads = new ArrayList();
    private ArrayList <Shot> shots = new ArrayList();
    private ArrayList <Thread> shotThread = new ArrayList();
    
    private MouseController mouseController;
    
    private int checked = 0;
    
    private JLabel gameOver = new JLabel("GAME OVER");
    private JLabel yourScore = new JLabel("Your Score: 100000");
    private JLabel congratulations = new JLabel();
    private JLabel yourHighscore = new JLabel();
    private JButton finish = new JButton("Finish");
    private JLabel countdown = new JLabel("3");
    
    private JButton resetScore = new JButton("Reset");
    private JButton resetCustom = new JButton("Reset");
    private JButton resetLevel = new JButton("Reset");
    private JButton resetScores = new JButton("Reset All");
    private JLabel highScore1 = new JLabel();
    private JLabel highScore2 = new JLabel();
    private JLabel highLevel = new JLabel();
    private JList highScoreLvls;
    private JScrollPane highScoreLevels;
    private String[] levelScores = new String[50];
    
    private Integer[] layers = {JLayeredPane.DEFAULT_LAYER, JLayeredPane.PALETTE_LAYER, JLayeredPane.MODAL_LAYER, JLayeredPane.POPUP_LAYER};
    private boolean moveBack = true;
    private int layerSpot = 0;
    
    private boolean standard = false;
    private int rounds;
    private int level = 1;
    
    public MouseTrainerGUI(MouseTrainerModel m, int width, int height)
    {
        super();
        this.model = m;
        this.model.setGUI(this);
        screenWidth = width;
        screenHeight = height;
        
        font = new Font("Blade Runner Movie Font", Font.BOLD, screenWidth/21);
        font2 = new Font("Ariel", Font.BOLD, screenWidth/84);
        font3 = new Font("Rockwell Extra Bold", Font.BOLD, screenWidth/33);
        font4 = new Font("Ariel", Font.BOLD, screenWidth/56);
        font5 = new Font("Ariel", Font.BOLD, screenWidth/15);    
        font6 = new Font("Blade Runner Movie Font", Font.BOLD, screenWidth/15);
        
        try {
            in = new Scanner(new File("saves.txt"));
            model.setHighScore(in.nextLine(), 1);
            model.setHighScore(in.nextLine(), 2);
            model.setHighLevel(in.nextLine());
            for (int counter=0; counter<50; counter++)
            {
                model.setScoreLevel(in.nextLine(), counter);
                model.setTotalScoreLevel(in.nextLine(), counter);
            }
        } catch (FileNotFoundException ex) {}      
            
        this.layoutView();
        this.registerControllers();
    }
    
    private void layoutView()
    {       
        this.setLayout(new BorderLayout());
               
        main = new JPanel();
        main.setLayout(new GridBagLayout());
        main.setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();       
        c.insets = new Insets(0,0,100,0);
        JLabel title = new JLabel("Mouse Trainer");      
        title.setFont(font);
        title.setForeground(Color.RED);
        main.add(title, c);               
        start.setFont(font2);
        startCustom.setFont(font2);
        options.setFont(font2);
        instructions.setFont(font2);  
        highScore.setFont(font2);
        c.insets = new Insets(0,(int)(screenWidth/8.4),(int)(screenHeight/31.333333),(int)(screenWidth/8.4));
        c.ipady = 30;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        main.add(start, c);
        c.gridy = 3;
        main.add(startCustom, c);
        c.gridy = 4;
        main.add(options, c);
        c.gridy = 5;
        main.add(instructions, c);    
        c.gridy = 6;
        main.add(highScore, c);
        
        decoration1 = new JPanel();
        decoration1.setLayout(new BorderLayout());
        decoration1.setBackground(Color.BLACK);
        decoration1.add(new Target((int)(screenWidth/8.4), 0, this), BorderLayout.NORTH);
        decoration1.add(new Target((int)(screenWidth/8.4), 0, this), BorderLayout.SOUTH);
        
        decoration2 = new JPanel();
        decoration2.setLayout(new BorderLayout());
        decoration2.setBackground(Color.BLACK);
        decoration2.add(new Target((int)(screenWidth/8.4), 0, this), BorderLayout.NORTH);
        decoration2.add(new Target((int)(screenWidth/8.4), 0, this), BorderLayout.SOUTH);
       
        game = new JLayeredPane();
        game.setLayout(null);
        game.setBackground(Color.BLACK);
        game.setOpaque(true);
        score.setForeground(Color.WHITE);
        score.setFont(font4);
        game.add(score);
        score.setBounds(screenWidth-(int)(screenWidth/7.64),0,(int)(screenWidth/7.64),25);
        livesLeft.setForeground(Color.WHITE);
        livesLeft.setFont(font4);
        game.add(livesLeft);
        livesLeft.setBounds(screenWidth/84,0,(int)(screenWidth/8.4),25);
        pause.setFont(font4);
        quit.setFont(font4);
        warning.setFont(font3);
        yes.setFont(font4);
        no.setFont(font4);  
        levels.setFont(font5);
        levels.setForeground(Color.BLACK);
        paused.setFont(font5);
        beatLevelScore.setFont(font4);
        beatLevelScore.setForeground(Color.BLACK);
        beatLevelTotalScore.setFont(font4);
        beatLevelTotalScore.setForeground(Color.BLACK);
        game.add(warning, JLayeredPane.DRAG_LAYER);
        game.add(yes, JLayeredPane.DRAG_LAYER);
        game.add(no, JLayeredPane.DRAG_LAYER);
        game.add(pause, JLayeredPane.DRAG_LAYER);
        game.add(quit, JLayeredPane.DRAG_LAYER);
        game.add(levels, JLayeredPane.DRAG_LAYER);
        game.add(beatLevelScore, JLayeredPane.DRAG_LAYER);
        game.add(beatLevelTotalScore, JLayeredPane.DRAG_LAYER);
        game.moveToFront(pause);
        game.moveToFront(quit);
        game.add(paused, JLayeredPane.DRAG_LAYER);
        paused.setForeground(Color.YELLOW);
        warning.setBackground(Color.BLACK);
        warning.setForeground(Color.WHITE);
        warning.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        warning.setOpaque(true); 
        levels.setBounds(screenWidth/2 - screenWidth/4, screenHeight/2 - (int)(screenHeight/20), screenWidth/2, 2*(int)(screenHeight/20));
        levels.setHorizontalAlignment(SwingConstants.CENTER);
        levels.setVerticalAlignment(SwingConstants.CENTER);;
        beatLevelScore.setBounds(screenWidth/2 - screenWidth/3, screenHeight - (int)(screenHeight/4), 2*screenWidth/3, (int)(screenHeight/12));
        beatLevelScore.setHorizontalAlignment(SwingConstants.CENTER);
        beatLevelScore.setVerticalAlignment(SwingConstants.CENTER);
        beatLevelTotalScore.setBounds(screenWidth/2 - screenWidth/3, screenHeight - (int)(screenHeight/6), 2*screenWidth/3, screenHeight/12);
        beatLevelTotalScore.setHorizontalAlignment(SwingConstants.CENTER);
        beatLevelTotalScore.setVerticalAlignment(SwingConstants.CENTER);
        pause.setBounds(0, screenHeight-(int)(screenHeight/18.8)+2, screenWidth/2, (int)(screenHeight/18.8));
        quit.setBounds(screenWidth/2, screenHeight-(int)(screenHeight/18.8)+2, screenWidth/2, (int)(screenHeight/18.8));
        warning.setBounds(screenWidth/2-(int)(screenWidth/3.36), screenHeight/3, 2*(int)(screenWidth/3.36), (int)(screenHeight/4.94));
        yes.setBounds(screenWidth/2-(int)(screenWidth/3.36), 2*screenHeight/3, 2*(int)(screenWidth/8.2), (int)(screenHeight/15.67));
        no.setBounds(screenWidth/2+(int)(screenWidth/17), 2*screenHeight/3, 2*(int)(screenWidth/8.2), (int)(screenHeight/15.67));
        paused.setBounds(screenWidth/2 - (int)(screenWidth/6.72), screenHeight/2-(int)(screenHeight/10.4), 2*(int)(screenWidth/6.72), (int)(screenHeight/10.4));
        warning.setVisible(false);
        yes.setVisible(false);
        no.setVisible(false);
        paused.setVisible(false);    
        levels.setVisible(false);
        beatLevelScore.setVisible(false);
        beatLevelTotalScore.setVisible(false);
        congratulations.setHorizontalAlignment(SwingConstants.CENTER);
        gameOver.setFont(font6);
        yourScore.setFont(font3);
        congratulations.setFont(font3);
        yourHighscore.setFont(font3);
        finish.setFont(font4);
        gameOver.setForeground(Color.RED);
        yourScore.setForeground(Color.WHITE);
        yourHighscore.setForeground(Color.WHITE);
        congratulations.setForeground(Color.WHITE);
        gameOverScreen.setLayout(new GridLayout(4,1));
        gameOverScreen.setBackground(Color.BLACK);
        flowGameOver.setBackground(Color.BLACK);
        flowYourScore.setBackground(Color.BLACK);
        flowYourHighscore.setBackground(Color.BLACK);
        flowCongratulations.setBackground(Color.BLACK);
        flowGameOver.setLayout(new FlowLayout(FlowLayout.CENTER));
        flowYourScore.setLayout(new FlowLayout(FlowLayout.CENTER));
        flowYourHighscore.setLayout(new FlowLayout(FlowLayout.CENTER));
        flowCongratulations.setLayout(new FlowLayout(FlowLayout.CENTER));
        flowGameOver.add(gameOver);
        flowYourScore.add(yourScore);
        flowYourHighscore.add(yourHighscore);
        flowCongratulations.add(congratulations);
        gameOverScreen.add(flowGameOver);
        gameOverScreen.add(flowYourScore);
        gameOverScreen.add(flowCongratulations);
        gameOverScreen.add(flowYourHighscore);
        game.add(gameOverScreen);
        game.add(finish);       
        gameOverScreen.setBounds(0,0,screenWidth,screenHeight-(int)(screenHeight/18.8)+2);
        finish.setBounds(screenWidth/2 - screenWidth/6, screenHeight-(int)(screenHeight/18.8)+2, 2*screenWidth/6, (int)(screenHeight/18.8));
        gameOver.setVisible(false);
        yourScore.setVisible(false);
        yourHighscore.setVisible(false);
        congratulations.setVisible(false);
        finish.setVisible(false);
        countdown.setFont(font5);
        countdown.setForeground(Color.RED);
        game.add(countdown, JLayeredPane.DRAG_LAYER);
        countdown.setBounds(screenWidth/2 - (int)(screenWidth/33.6), screenHeight/2 - (int)(screenHeight/21.8),2*(int)(screenWidth/33.6), 2*(int)(screenHeight/21.8));
        countdown.setVisible(false);      
        
        edits = new JPanel();
        edits.setLayout(new BorderLayout());
        edits.setBackground(Color.BLACK);
        edits2 = new JPanel();
        edits2.setLayout(new GridBagLayout());
        edits2.setBackground(Color.BLACK);
        GridBagConstraints c2 = new GridBagConstraints();       
        JLabel optionHeading = new JLabel("Please Choose Your Desired Settings");        
        optionHeading.setFont(font3);      
        optionHeading.setForeground(Color.WHITE);
        JPanel heading = new JPanel();
        heading.add(optionHeading);
        heading.setBackground(Color.BLACK);
        targetSizeLabel.setFont(font4);
        numTargetsLabel.setFont(font4);
        timerLabel.setFont(font4);
        livesLabel.setFont(font4);
        numTargets.setFont(font4);
        timer.setFont(font4);
        lives.setFont(font4);
        targetSize.setFont(font4);
        targetSizeLabel.setForeground(Color.WHITE);
        numTargetsLabel.setForeground(Color.WHITE);
        timerLabel.setForeground(Color.WHITE);
        livesLabel.setForeground(Color.WHITE);
        edits.add(heading, BorderLayout.NORTH);
        c2.insets = new Insets(0,0,(int)(screenHeight/9.4),0);
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.ipady = 20;
        c2.ipadx = (int)(screenWidth/16.8);
        c2.gridy = 2;
        c2.weightx = 1;
        edits2.add(targetSizeLabel, c2);
        c2.weightx = 1;
        c2.ipadx = screenWidth/4;
        c2.insets = new Insets(0,0,(int)(screenHeight/9.4),0);
        edits2.add(targetSize, c2);
        targetSize.setSelectedIndex(4);
        c2.ipadx = 0;
        c2.weightx = 2;
        c2.weighty = 2;
        c2.ipady = (int)(screenHeight/4.7);
        c2.ipadx = (int)(screenWidth/8.4);
        currentSize = size5;
        targetHolder = new JPanel();
        targetHolder.setBackground(Color.BLACK);
        targetHolder.add(size5);
        c2.insets = new Insets(0,0,0,0);
        edits2.add(targetHolder, c2);   
        c2.insets = new Insets(0,0,(int)(screenHeight/11.75),0);
        c2.weighty = 1;
        c2.ipady = 20;
        c2.ipadx = (int)(screenWidth/16.8);
        c2.gridy = 3;
        c2.weightx = 1;
        edits2.add(numTargetsLabel, c2);
        c2.ipadx = screenWidth/4;
        c2.weightx = 1;
        edits2.add(numTargets, c2);
        c2.ipadx = (int)(screenWidth/16.8);
        c2.gridy = 4;
        c2.weightx = 1;
        edits2.add(timerLabel, c2);
        c2.ipadx = screenWidth/4;
        c2.weightx = 1;
        edits2.add(timer, c2);
        c2.ipadx = (int)(screenWidth/16.8);
        c2.gridy = 5;
        c2.weightx = 1;
        edits2.add(livesLabel, c2);
        c2.ipadx = screenWidth/4;
        c2.weightx = 1;
        edits2.add(lives, c2);
        edits.add(edits2, BorderLayout.CENTER);   
        done.setFont(font4);
        back1.setFont(font4);
        error.setFont(font4);
        error.setForeground(Color.RED);
        JPanel doneP = new JPanel();
        doneP.setBackground(Color.BLACK);
        doneP.setLayout(new GridBagLayout());
        GridBagConstraints c3 = new GridBagConstraints();
        c3.fill = GridBagConstraints.HORIZONTAL;
        c3.weightx = 1;
        c3.gridy = 1;
        doneP.add(new JLabel(), c3);
        c3.weightx = 1;
        doneP.add(error, c3);
        c3.weightx = 5;
        doneP.add(new JLabel(), c3);
        c3.gridy = 2;
        doneP.add(new JLabel(), c3);
        c3.weightx = 1;
        doneP.add(done, c3);
        c3.weightx = 1;
        doneP.add(new JLabel(), c3);
        c3.gridy = 3;
        doneP.add(new JLabel(), c3);
        c3.weightx = 1;
        doneP.add(back1, c3);
        c3.weightx = 1;
        doneP.add(new JLabel(), c3);      
        edits.add(doneP, BorderLayout.SOUTH);
        error.setForeground(Color.BLACK);
        
        help = new JPanel();
        help.setBackground(Color.BLACK);
        help.setLayout(new BorderLayout());
        back2.setFont(font4);
        JPanel backP = new JPanel();
        backP.setBackground(Color.BLACK);
        backP.setLayout(new GridLayout(1,3));
        backP.add(new JLabel());
        backP.add(back2);
        backP.add(new JLabel());
        help.add(backP, BorderLayout.SOUTH);
        backP.setBounds(0,0,screenWidth,(int)(screenHeight/18.8));     
        top = new JPanel();
        top.setLayout(new GridLayout(1,2));
        previous.setFont(font4);
        next.setFont(font4);                
        top.add(previous);
        top.add(next);
        help.add(top, BorderLayout.NORTH);
        top.setBounds(0,0,screenWidth,(int)(screenHeight/18.8));
        instructionHeight = screenHeight-top.getBounds().height-backP.getHeight();
        scoreEx.setFont(font4);
        livesEx.setFont(font4);
        pauseEx.setFont(font4);
        quitEx.setFont(font4);
        pausedEx.setFont(font5);
        warningEx.setFont(font3);
        noEx.setFont(font4);
        yesEx.setFont(font4);
        instruct.setFont(font4);
        instruct2.setFont(font4);
        scoreEx.setForeground(Color.WHITE);
        livesEx.setForeground(Color.WHITE);
        pausedEx.setForeground(Color.YELLOW);
        warningEx.setForeground(Color.WHITE);
        warningEx.setBackground(Color.BLACK);
        warningEx.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        warningEx.setOpaque(true);
        instruct.setForeground(Color.WHITE);
        instruct2.setForeground(Color.WHITE);
        arrow = new Arrows(screenWidth,instructionHeight);
        cursor = new Cursor(screenWidth, screenHeight);
        ex1 = new Target(Target.EIGHT,15,this);
        ex2 = new Target(Target.THREE,15,this);
        ex3 = new Target(Target.FIVE,15,this);
        ex4 = new Target(Target.FIVE,15,this);
        ex5 = new Target(Target.FIVE,15,this);
        ex6 = new Target(Target.FIVE,15,this);
        ex7 = new Target(Target.EIGHT,15,this);
        ex3.setCurrentTime(120);
        ex5.setCurrentTime(120);
        ex6.setCurrentTime(120);      
        shotEx1 = new Shot(40,this);
        shotEx2 = new Shot(200,this);
        shotEx3 = new Shot(-150,this);
        shotEx4 = new Shot(100,this);
        gameEx = new JLayeredPane();
        gameEx.setLayout(null);
        gameEx.setBackground(Color.BLACK);
        gameEx.add(scoreEx);
        gameEx.add(livesEx);
        gameEx.add(pauseEx);
        gameEx.add(quitEx);
        gameEx.add(pausedEx);
        gameEx.add(warningEx);
        gameEx.add(noEx);
        gameEx.add(yesEx);
        gameEx.add(ex1);
        gameEx.add(ex2);
        gameEx.add(ex3);
        gameEx.add(ex4);
        gameEx.add(ex5);
        gameEx.add(ex6);
        gameEx.add(ex7);
        gameEx.add(shotEx1, JLayeredPane.PALETTE_LAYER);
        gameEx.add(shotEx2, JLayeredPane.PALETTE_LAYER);
        gameEx.add(shotEx3, JLayeredPane.PALETTE_LAYER);
        gameEx.add(shotEx4, JLayeredPane.PALETTE_LAYER);
        gameEx.add(arrow);
        gameEx.add(cursor, JLayeredPane.MODAL_LAYER);
        gameEx.add(instruct);
        gameEx.add(instruct2);
        scoreEx.setBounds(screenWidth-(int)(screenWidth/7.64),0,(int)(screenWidth/7.64),25);
        livesEx.setBounds(screenWidth/84,0,(int)(screenWidth/8.4),25);
        pauseEx.setBounds(0, instructionHeight-(int)(instructionHeight/18.8)+2, screenWidth/2, (int)(instructionHeight/18.8));
        quitEx.setBounds(screenWidth/2, instructionHeight-(int)(instructionHeight/18.8)+2, screenWidth/2, (int)(instructionHeight/18.8));
        warningEx.setBounds(screenWidth/2-(int)(screenWidth/3.36), instructionHeight/3, 2*(int)(screenWidth/3.36), (int)(instructionHeight/4.94));
        noEx.setBounds(screenWidth/2+(int)(screenWidth/17), 2*instructionHeight/3, 2*(int)(screenWidth/8.2), (int)(instructionHeight/15.67));
        yesEx.setBounds(screenWidth/2-(int)(screenWidth/3.36), 2*instructionHeight/3, 2*(int)(screenWidth/8.2), (int)(instructionHeight/15.67));
        pausedEx.setBounds(screenWidth/2 - (int)(screenWidth/6.72), instructionHeight/2-(int)(instructionHeight/10.4), 2*(int)(screenWidth/6.72), (int)(instructionHeight/9.8));       
        ex1.setBounds(screenWidth/2-80, instructionHeight/2-80, 160,160);
        ex2.setBounds(screenWidth/3, instructionHeight/3, 60,60);
        ex3.setBounds(screenWidth/2-50, instructionHeight/2-50, 100,100);
        ex4.setBounds(screenWidth/2-50, instructionHeight/2-50, 100,100);
        ex5.setBounds(screenWidth/6, 4*instructionHeight/6, 100,100);
        ex6.setBounds(7*screenWidth/10, 3*instructionHeight/10, 100,100);
        ex7.setBounds(screenWidth/2-80, instructionHeight/2-80, 160,160);
        shotEx1.setBounds(screenWidth/2 + 39, instructionHeight/2 + 39, 80,80);
        shotEx2.setBounds(screenWidth/2 - 3, instructionHeight/2 - 3, 80,80);
        shotEx3.setBounds(screenWidth/3 + 67, instructionHeight/3 + 67, 80,80);
        shotEx4.setBounds(screenWidth/6+52, 4*instructionHeight/6+50, 80, 80);
        arrow.setBounds(0,0,screenWidth,instructionHeight);
        cursor.setBounds(screenWidth/2 + 42, instructionHeight/2 + 42, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
        instruct.setBounds(0,0,screenWidth,screenHeight/5);
        pauseEx.setEnabled(false);
        quitEx.setEnabled(false);
        yesEx.setEnabled(false);
        noEx.setEnabled(false);
        Thread eg4 = new Thread(ex4);
        Thread eg1 = new Thread(ex7);
        ex4.setFinalBounds(screenWidth/2-50,instructionHeight/2-50);
        eg1.start();
        ex7.calculateScore(new Point(50,50));
        eg4.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex){}          
        ex7.terminate();
        ex4.terminate();  
        help.add(gameEx, BorderLayout.CENTER);
        
        settings = new JPanel();
        settings.setBackground(Color.BLACK);
        settings.setLayout(new BorderLayout());
        back3.setFont(font4);
        JPanel backP2 = new JPanel();
        backP2.setBackground(Color.BLACK);
        backP2.setLayout(new GridLayout(1,3));
        backP2.add(new JLabel());
        backP2.add(back3);
        backP2.add(new JLabel());
        settings.add(backP2, BorderLayout.SOUTH);
        
        scoring = new JPanel();
        scoring.setLayout(new BorderLayout());
        JLabel scoreTitle = new JLabel("High Scores");
        scoreTitle.setFont(font3);
        scoreTitle.setForeground(Color.WHITE);
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.BLACK);
        scorePanel.add(scoreTitle);
        scoring.add(scorePanel, BorderLayout.NORTH);
        JPanel center = new JPanel();
        center.setLayout(new GridBagLayout());
        center.setBackground(Color.BLACK);
        GridBagConstraints c4 = new GridBagConstraints();
        JPanel score1 = new JPanel();
        score1.setBackground(Color.BLACK);
        score1.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        score1.setLayout(new GridBagLayout());
        c4.fill = GridBagConstraints.HORIZONTAL;
        c4.weightx = 2;
        c4.insets = new Insets((int)(screenHeight/37.6),(int)(screenWidth/33.6),(int)(screenHeight/37.6),(int)(screenWidth/11.2));
        score1.add(highScore1, c4);
        c4.weightx = 1;
        c4.fill = GridBagConstraints.NONE;
        c4.insets = new Insets((int)(screenHeight/37.6),(int)(screenWidth/11.2),(int)(screenHeight/37.6),(int)(screenWidth/33.6));
        c4.ipadx = (int)(screenWidth/16.8);
        score1.add(resetScore, c4);
        JPanel score2 = new JPanel();
        score2.setBackground(Color.BLACK);
        score2.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        score2.setLayout(new GridBagLayout());        
        c4.weightx = 2;
        c4.fill = GridBagConstraints.HORIZONTAL;
        c4.insets = new Insets((int)(screenHeight/37.6),(int)(screenWidth/33.6),(int)(screenHeight/37.6),(int)(screenWidth/11.2));
        c4.ipadx = 0;
        score2.add(highScore2, c4);
        c4.weightx = 1;
        c4.fill = GridBagConstraints.NONE;
        c4.insets = new Insets((int)(screenHeight/37.6),(int)(screenWidth/11.2),(int)(screenHeight/37.6),(int)(screenWidth/33.6));
        c4.ipadx = (int)(screenWidth/16.8);
        score2.add(resetCustom, c4); 
        JPanel levelPane = new JPanel();
        levelPane.setBackground(Color.BLACK);
        levelPane.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        levelPane.setLayout(new GridBagLayout());
        c4.weightx = 2;
        c4.fill = GridBagConstraints.HORIZONTAL;
        c4.insets = new Insets((int)(screenHeight/37.6),(int)(screenWidth/33.6),(int)(screenHeight/37.6),(int)(screenWidth/7.466666667));
        c4.ipadx = 0;
        levelPane.add(highLevel, c4);
        c4.weightx = 1;
        c4.fill = GridBagConstraints.NONE;
        c4.insets = new Insets((int)(screenHeight/37.6),(int)(screenWidth/7.466666667),(int)(screenHeight/37.6),(int)(screenWidth/33.6));
        c4.ipadx = (int)(screenWidth/16.8);
        levelPane.add(resetLevel, c4);
        c4.fill = GridBagConstraints.HORIZONTAL;
        JPanel scoresPane = new JPanel();
        scoresPane.setBackground(Color.BLACK);
        scoresPane.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        scoresPane.setLayout(new BorderLayout());
        for (int count=0; count<50; count++)
            levelScores[count] = "Level " + (count+1) + ": " + model.getScoreLevel(count);
        highScoreLvls = new JList(levelScores);
        highScoreLvls.setLayoutOrientation(JList.VERTICAL);
        highScoreLvls.setFont(font4);
        highScoreLvls.setForeground(Color.WHITE);
        highScoreLvls.setBackground(Color.BLACK);
        highScoreLevels = new JScrollPane(highScoreLvls);  
        highScoreLevels.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel titleScores = new JLabel("Highest Score for each Level");
        titleScores.setFont(font4);
        titleScores.setForeground(Color.WHITE);
        titleScores.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        scoresPane.add(highScoreLevels, BorderLayout.CENTER);
        scoresPane.add(resetScores, BorderLayout.SOUTH);    
        c4.insets = new Insets((int)(screenHeight/37.6), screenWidth/4, 0, screenWidth/4);
        c4.weightx = 0;
        c4.gridy = 1;
        center.add(score1, c4);
        c4.gridy = 2;
        c4.insets = new Insets((int)(screenHeight/37.6), screenWidth/4, 0, screenWidth/4);
        center.add(score2, c4);    
        c4.gridy = 3;
        center.add(levelPane, c4);
        c4.gridy = 4;
        center.add(titleScores, c4);
        c4.gridy = 5;
        c4.insets = new Insets(0,screenWidth/4,0,screenWidth/4);
        c4.ipady = screenHeight/4;
        center.add(scoresPane, c4);
        highScore1.setForeground(Color.WHITE);
        highScore2.setForeground(Color.WHITE);
        highLevel.setForeground(Color.WHITE);
        highScore1.setFont(font4);
        highScore2.setFont(font4);
        highLevel.setFont(font4);
        resetScore.setFont(font4);
        resetCustom.setFont(font4);
        resetLevel.setFont(font4);
        resetScores.setFont(font4);
        resetScore.setActionCommand("1");
        resetCustom.setActionCommand("2");
        resetLevel.setActionCommand("3");
        resetScores.setActionCommand("4");
        scoring.add(center, BorderLayout.CENTER);
        back4.setFont(font4);
        JPanel backP3 = new JPanel();
        backP3.setBackground(Color.BLACK);
        backP3.setLayout(new GridLayout(1,3));
        backP3.add(new JLabel());
        backP3.add(back4);
        backP3.add(new JLabel());
        scoring.add(backP3, BorderLayout.SOUTH);
        
        startScreen = new StartScreen(this, screenWidth, screenHeight);
        startThread = new Thread(startScreen);
        startThread.start();

        this.add(startScreen, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    private void registerControllers()
    {
        MainController mainController = new MainController(this, model, screenWidth, screenHeight);
        start.addActionListener(mainController);
        startCustom.addActionListener(mainController);
        options.addActionListener(mainController);
        instructions.addActionListener(mainController);
        highScore.addActionListener(mainController);
        
        DoneController doneController = new DoneController(this, model, targetSize, numTargets, timer, lives);
        done.addActionListener(doneController);
        
        CycleController cycleController = new CycleController(this);
        next.addActionListener(cycleController);
        previous.addActionListener(cycleController);
        
        BackController backController = new BackController(this);
        back1.addActionListener(backController);        
        back3.addActionListener(backController);
        back4.addActionListener(backController);
        
        BackController backController2 = new BackController(this, cycleController);
        back2.addActionListener(backController2);
        
        SizeController sizeController = new SizeController(this);
        targetSize.addActionListener(sizeController);
        
        mouseController = new MouseController(this, model);
        game.addMouseListener(mouseController);
        startScreen.addMouseListener(mouseController);
        
        FinishController finishController = new FinishController(this, model);
        finish.addActionListener(finishController);
        
        PauseController pauseController = new PauseController(this);
        pause.addActionListener(pauseController);
        quit.addActionListener(pauseController);
        
        QuitController quitController = new QuitController(this, model);
        yes.addActionListener(quitController);
        no.addActionListener(quitController);  
        
        ResetController resetController = new ResetController(this, model);
        resetScore.addActionListener(resetController);
        resetCustom.addActionListener(resetController);
        resetLevel.addActionListener(resetController);
        resetScores.addActionListener(resetController);
    }
    
    public void showMain()
    {
        this.removeAll();
        this.add(main, BorderLayout.CENTER);
        this.add(decoration1, BorderLayout.WEST);
        this.add(decoration2, BorderLayout.EAST);
        this.validate();
        this.repaint();
    }
    
    public void showGame()
    {        
        numTargets.setBackground(Color.WHITE);
        timer.setBackground(Color.WHITE);
        lives.setBackground(Color.WHITE);       
        error.setForeground(Color.BLACK);
        this.removeAll();
        this.add(game, BorderLayout.CENTER);
        score.setText("Score: " + model.getScore());
        livesLeft.setText("Lives: " + model.getLives());
        pause.setVisible(true);
        quit.setVisible(true);
        this.validate();
        this.repaint();
    }
    
    public void showCustom()
    {
        this.removeAll();
        this.add(edits, BorderLayout.CENTER);
        this.validate();
        this.repaint();
    }
    
    public void showInstructions()
    {
        this.removeAll();
        this.add(help, BorderLayout.CENTER);
        this.changePage(1);
        this.validate();
        this.repaint();
    }
    
    public void showOptions()
    {
        this.removeAll();
        this.add(settings, BorderLayout.CENTER);
        this.validate();
        this.repaint();
    }
    
    public void showHighScores()
    {
        this.removeAll();
        this.add(scoring, BorderLayout.CENTER);
        highScore1.setText("High Score for Standard Games: " + model.getHighscore(1));
        highScore2.setText("High Score for Custom Games: " + model.getHighscore(2));
        highLevel.setText("Highest Level Achieved: " + model.getHighLevel());
        for (int count=0; count<50; count++)
            levelScores[count] = "<html><pre>Level " + (count+1) + " Score: " + model.getScoreLevel(count) + "\t\tLevel " + (count+1) + " Total Score: " + model.getTotalScoreLevel(count) + "</pre></html>";
        this.validate();
        this.repaint();
    }
    
    public void changeTarget(int t)
    {
        edits2.remove(currentSize);
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 2;
        c.ipady = 20;
        c.insets = new Insets(0,100,100,100);
        c.fill = GridBagConstraints.HORIZONTAL;
        
        switch (t)
        {
            case 1:
                currentSize = size1;
                targetHolder.removeAll();
                targetHolder.add(size1);
                targetHolder.validate();
                targetHolder.repaint();
                break;
            case 2:
                currentSize = size2;
                targetHolder.removeAll();
                targetHolder.add(size2);
                targetHolder.validate();
                targetHolder.repaint();
                break;
            case 3:
                currentSize = size3;
                targetHolder.removeAll();
                targetHolder.add(size3);
                targetHolder.validate();
                targetHolder.repaint();
                break;
            case 4:
                currentSize = size4;
                targetHolder.removeAll();
                targetHolder.add(size4);
                targetHolder.validate();
                targetHolder.repaint();
                break;
            case 5:
                currentSize = size5;
                targetHolder.removeAll();
                targetHolder.add(size5);
                targetHolder.validate();
                targetHolder.repaint();
                break;
            case 6:
                currentSize = size6;
                targetHolder.removeAll();
                targetHolder.add(size6);
                targetHolder.validate();
                targetHolder.repaint();
                break;
            case 7:
                currentSize = size7;
                targetHolder.removeAll();
                targetHolder.add(size7);
                targetHolder.validate();
                targetHolder.repaint();
                break;
            case 8:
                currentSize = size8;
                targetHolder.removeAll();
                targetHolder.add(size8);
                targetHolder.validate();
                targetHolder.repaint();
                break;
            case 9:
                currentSize = size9;
                targetHolder.removeAll();
                targetHolder.add(size9);
                targetHolder.validate();
                targetHolder.repaint();
                break;
            case 10:
                currentSize = size10;
                targetHolder.removeAll();
                targetHolder.add(size10);
                targetHolder.validate();
                targetHolder.repaint();
                break;
        }       
    }
    
    public void startRound()
    {          
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {}
        
        synchronized (this)
        {
            while (pauseOn2)
                try {
                    this.wait();
                } catch (InterruptedException ex) {}
        }
        
        for (int count=0; count<model.getTargets(); count++)
        {
            targets.add(new Target(model.getSize(), model.getTime(), this));
            game.add(targets.get(count), JLayeredPane.DRAG_LAYER);
            game.moveToBack(targets.get(count));
            int bounds1 = (int)(Math.random()*(screenWidth-model.getSize()+1));
            int bounds2 = (int)(Math.random()*(screenHeight-model.getSize()+1-(int)(screenHeight/18.8)+2));
            targets.get(count).setBounds(bounds1, bounds2, model.getSize(), model.getSize());
            targets.get(count).setFinalBounds(bounds1, bounds2);
            targets.get(count).addMouseListener(mouseController);
            threads.add(new Thread(targets.get(count)));
            targets.get(count).setThreadID(threads.get(count).getId());
        }
        
        for (Thread t: threads)
            t.start();        
    }
    
    public void update()
    {
        score.setText("Score: " + model.getScore());
        livesLeft.setText("Lives: " + model.getLives());
    }
    
    public void finish()
    {
        targets.clear();
        threads.clear();
        
        if (model.isLose())
            this.endGame();
        else
            this.startRound();
    }
    
    public void finishStandard()
    {
        targets.clear();
        threads.clear();
        
        if (model.isLose())
            this.endGame();
        
        else if (rounds == 10)
        {
            model.levelUp();
            rounds = 0;
            level++;
            
            levels.setText("Level " + level);
            levels.setVisible(true);
            Color c;
            
            if (model.isBeatLevelScore())
                beatLevelScore.setVisible(true);
            if (model.isBeatTotalScore())
                beatLevelTotalScore.setVisible(true);
            
            for (int fade=0; fade<255; fade++)
            {
                c = new Color(fade,fade,fade);
                levels.setForeground(c);
                beatLevelScore.setForeground(c);
                beatLevelTotalScore.setForeground(c);
                
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {}
                
                synchronized (this)
                {
                    while (pauseOn2)
                        try {
                            this.wait();
                        } catch (InterruptedException ex) {}
                }
            }
            
            try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {}
            
            synchronized (this)
            {
                while (pauseOn2)
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {}
            }
            
            for (int fade=255; fade>0; fade--)
            {
                c = new Color(fade,fade,fade);
                levels.setForeground(c);
                beatLevelScore.setForeground(c);
                beatLevelTotalScore.setForeground(c);
                
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {}
                
                synchronized (this)
                {
                    while (pauseOn2)
                        try {
                            this.wait();
                        } catch (InterruptedException ex) {}
                }
            }
            
            levels.setVisible(false);
            beatLevelScore.setVisible(false);
            beatLevelTotalScore.setVisible(false);
            rounds = 1;
            this.startRound();
        }
        
        else
        {
            rounds++;
            this.startRound();   
        }        
    }
    
    private void endGame()
    {
        yourScore.setText("Score: " + model.getScore());
        yourHighscore.setText("Your High Score: " + model.getHighscore());
        congratulations.setText("<html><div style=\"text-align: center;\">Congratulations! You beat your<br>high score by " + model.getBeatenScore() + " points!</html>");
        gameOver.setVisible(true);
        yourScore.setVisible(true);
        if (model.getBeatenScore() > 0)
            congratulations.setVisible(true);
        yourHighscore.setVisible(true);
        finish.setVisible(true);
        livesLeft.setVisible(false);
        score.setVisible(false);
        pause.setVisible(false);
        quit.setVisible(false);
        
        try {
            out = new PrintWriter(new File("saves.txt"));
        } catch (FileNotFoundException ex) {}
        
        out.println(this.convert(model.getHighscore(1)));
        out.println(this.convert(model.getHighscore(2)));
        out.println(this.convert2(model.getHighLevel()));
        for (int counter=0; counter<50; counter++)
        {
            out.println(this.convert2(model.getScoreLevel(counter)));
            out.println(this.convert2(model.getTotalScoreLevel(counter)));
        }
        out.close();
    }
    
    public void removeTarget(Target t, boolean b)
    {
        for (Target t2: targets)
            if (t2.equals(t))
                game.remove(t);
        if (b)
            model.removeLife();
        
        checked++;
        game.repaint();
        
        if (checked >= model.getTargets())
        {
            checked = 0;
            if (standard)
                this.finishStandard();
            else
                this.finish();          
        }
    }
    
    public void showShot(int s, Point p, Target t)
    {        
        shots.add(new Shot(s, this));
        Shot shot = shots.get(shots.size()-1);
        game.add(shot, JLayeredPane.DRAG_LAYER);
        game.moveToFront(shot);
        
        if (t != null)
        {
            shot.setBounds(t.getBounds().x+p.x-3,t.getBounds().y+p.y-3,80,80);
            
            if (t.isHit())
                for (Thread th: threads)
                    if (th.getId() == t.getThreadID())
                    {
                        th.interrupt();
                        break;
                    }
        }
        
        else
            shot.setBounds(p.x-3,p.y-3,80,80);
        
        shotThread.add(new Thread(shot));
        shotThread.get(shotThread.size()-1).start();       
        
        if (t != null)
            if (t.isHit())
            {
                game.setLayer(t, layers[layerSpot]);
                if (moveBack)
                {
                    game.moveToBack(t);
                    moveBack = false;
                }
                
                else
                {
                    game.moveToFront(t);
                    moveBack = true;
                    if (layerSpot < 3)
                        layerSpot++;
                    else
                        layerSpot = 0;
                }
            }
    }
    
    public void removeShot(Shot s)
    {
        game.remove(s);
        shots.remove(s);
        game.repaint();
    }
    
    public int findNewScore(Target t, Point p)
    {
        int x = t.getBounds().x+p.x;
        int y = t.getBounds().y+p.y;
        
        for (int counter=0; counter<targets.size(); counter++)
        {
            int x2 = targets.get(counter).getBoundsX() + targets.get(counter).getWidth()/2 - x;
            int y2 = targets.get(counter).getBoundsY() + targets.get(counter).getHeight()/2 - y;
            if (Math.pow(x2,2) + Math.pow(y2,2) < Math.pow(targets.get(counter).getWidth()/2,2) && !targets.get(counter).isHit())
                return targets.get(counter).calculateScore(new Point(x-targets.get(counter).getBounds().x,y-targets.get(counter).getBounds().y));
        }     
        
        return -150;
    }
    
    public void reset()
    {
        gameOver.setVisible(false);
        yourScore.setVisible(false);
        congratulations.setVisible(false);
        yourHighscore.setVisible(false);
        finish.setVisible(false);
        livesLeft.setVisible(true);
        score.setVisible(true);
        standard = false;
        rounds = 0;
        level = 1;
        
        if (pauseOn)
        {
            pauseOn = false;
            yes.setVisible(false);
            no.setVisible(false);
            warning.setVisible(false);
            quit.setEnabled(true);
            pause.setEnabled(true);
            this.resumeAll();
            
            for (Target t: targets)
            {
                t.terminate();
                game.remove(t);
            }
            
            for (Shot s: shots)
            {
                s.terminate();
                game.remove(s);
            }
            
            for (Thread t: threads)
                t.interrupt();
            
            for (Thread s: shotThread)
                s.interrupt();
            
            threads.clear();
            targets.clear();
            shots.clear();
            shotThread.clear();
            checked = 0;
            
            try {
            out = new PrintWriter(new File("saves.txt"));
            } catch (FileNotFoundException ex) {}

            out.println(this.convert(model.getHighscore(1)));
            out.println(this.convert(model.getHighscore(2)));
            out.println(this.convert2(model.getHighLevel()));
            for (int counter=0; counter<50; counter++)
            {
                out.println(this.convert2(model.getScoreLevel(counter)));
                out.println(this.convert2(model.getTotalScoreLevel(counter)));
            }
            out.close();
        }
        
        this.showMain();
    }
    
    public void setNewBounds(Target t)
    {
        t.setBounds(t.getBoundsX(), t.getBoundsY(), 200, 200);
    }
    
    public void run()
    {
        starting = true;
        countdown.setVisible(true);
        pause.setEnabled(false);
        quit.setEnabled(false);
        game.moveToFront(countdown);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex){}
        countdown.setForeground(Color.YELLOW);
        countdown.setText("2");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex){}
        countdown.setForeground(Color.GREEN);
        countdown.setText("1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex){}
        countdown.setVisible(false);
        countdown.setText("3");
        
        starting = false;
        pause.setEnabled(true);
        quit.setEnabled(true);
        
        if (pauseOn)
        {
            for (Target t: targets)
                t.resume();
            
            for (Shot s: shots)
                s.resume();
            
            pauseOn = false;
            quit.setEnabled(true);
            pause.setEnabled(true);
            this.resumeAll();
        }
        
        else if (standard)
        {           
            levels.setText("Level " + level);
            levels.setVisible(true);
            Color c;
            
            for (int fade=0; fade<255; fade++)
            {
                c = new Color(fade,fade,fade);
                levels.setForeground(c);
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {}
                
                synchronized (this)
                {
                    while (pauseOn2)
                        try {
                            this.wait();
                        } catch (InterruptedException ex) {}
                }
            }
            
            try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {}
            
            for (int fade=255; fade>0; fade--)
            {
                c = new Color(fade,fade,fade);
                levels.setForeground(c);
                
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {}
                
                synchronized (this)
                {
                    while (pauseOn2)
                        try {
                            this.wait();
                        } catch (InterruptedException ex) {}
                }
            }
            
            levels.setVisible(false);
            rounds = 1;
            this.startRound();
        }
        
        else
            this.startRound();               
    }
    
    public void pause(boolean quitting)
    {
        quit.setEnabled(false);
        
        for (Target t: targets)
            t.pause();
        
        for (Shot s: shots)
            s.pause();
        
        this.pauseAll();
        
        if (quitting)
        {
            warning.setVisible(true);
            yes.setVisible(true);
            no.setVisible(true);
            pause.setEnabled(false);
            game.moveToFront(warning);
            game.moveToFront(yes);
            game.moveToFront(no);
        }
        
        else
        {
            paused.setVisible(true);
            game.moveToFront(paused);
            pause.setText("Resume");
        }
        
        pauseOn = true;
    }
    
    public void resume(boolean quitting)
    {
        if (quitting)
        {           
            no.setVisible(false);
            yes.setVisible(false);
            warning.setVisible(false);
        }
        
        else
        {
            pause.setEnabled(false);
            paused.setVisible(false);
            pause.setText("Pause");
        }
        
        Thread thread = new Thread(this);
        thread.start();
    }
    
    public boolean isPaused()
    {
        return pauseOn;
    }
    
    public boolean isStart()
    {
        return starting;
    }
    
    public void showError(boolean targetsError, boolean timeError, boolean livesError)
    {
        error.setForeground(Color.RED);
        
        if (targetsError)
            numTargets.setBackground(Color.RED);
        else
            numTargets.setBackground(Color.WHITE);
        
        if (timeError)
            timer.setBackground(Color.RED);
        else 
            timer.setBackground(Color.WHITE);
        
        if (livesError)
            lives.setBackground(Color.RED);
        else
            lives.setBackground(Color.WHITE);
    }
    
    public void changePage(int page)
    {
        if (page == 1)
        {
            scoreEx.setText("Score: 0");
            livesEx.setText("Lives: 10");
            cursor.setVisible(true);
            cursor.setBounds(screenWidth/2 + 42, instructionHeight/2 + 42, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
            instruct.setText("<html>This is a target. Click it to gain points.</html>");
            instruct.setBounds(0,0,screenWidth,screenHeight/5);
            instruct2.setVisible(false);
            instruct2.setText("");
            instruct2.setBounds(0,0,0,0);
            previous.setEnabled(false);
            next.setEnabled(true);
            ex1.setVisible(true);
            ex2.setVisible(false);
            ex3.setVisible(false);
            ex4.setVisible(false);
            ex5.setVisible(false);
            ex6.setVisible(false);
            ex7.setVisible(false);
            shotEx1.setVisible(false);
            shotEx2.setVisible(false);
            shotEx3.setVisible(false);
            shotEx4.setVisible(false);
            pausedEx.setVisible(false);
            pauseEx.setText("Pause");
            warningEx.setVisible(false);
            yesEx.setVisible(false);
            noEx.setVisible(false);
            arrow.setPage(page);          
        }
        
        else if (page == 2)
        {
            scoreEx.setText("Score: 40");
            livesEx.setText("Lives: 10");
            cursor.setVisible(true);
            cursor.setBounds(screenWidth/2 + 42, instructionHeight/2 + 42, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
            instruct.setText("<html>The scoring starts at 20 points for the outermost circle and increases by 20 for each inner circle, rewarding up to 100 points for clicking the center circle.</html>");
            instruct.setBounds(0,0,screenWidth,screenHeight/5);
            instruct2.setVisible(false);
            instruct2.setText("");
            instruct2.setBounds(0,0,0,0);
            previous.setEnabled(true);
            next.setEnabled(true);
            ex1.setVisible(false);
            ex2.setVisible(false);
            ex3.setVisible(false);
            ex4.setVisible(false);
            ex5.setVisible(false);
            ex6.setVisible(false);
            ex7.setVisible(true);
            shotEx1.setVisible(true);
            shotEx2.setVisible(false);
            shotEx3.setVisible(false);
            shotEx4.setVisible(false);
            pausedEx.setVisible(false);
            pauseEx.setText("Pause");
            warningEx.setVisible(false);
            yesEx.setVisible(false);
            noEx.setVisible(false);
            arrow.setPage(page);   
        }
        
        else if (page == 3)
        {
            scoreEx.setText("Score: 240");
            livesEx.setText("Lives: 10");
            cursor.setVisible(true);
            cursor.setBounds(screenWidth/2, instructionHeight/2, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
            instruct.setText("<html>However, 200 points will be rewarded if the exact center is clicked.</html>");
            instruct.setBounds(0,0,screenWidth,screenHeight/5);
            instruct2.setVisible(false);
            instruct2.setText("");
            instruct2.setBounds(0,0,0,0);
            previous.setEnabled(true);
            next.setEnabled(true);
            ex1.setVisible(false);
            ex2.setVisible(false);
            ex3.setVisible(false);
            ex4.setVisible(false);
            ex5.setVisible(false);
            ex6.setVisible(false);
            ex7.setVisible(true);
            shotEx1.setVisible(false);
            shotEx2.setVisible(true);
            shotEx3.setVisible(false);
            shotEx4.setVisible(false);
            pausedEx.setVisible(false);
            pauseEx.setText("Pause");
            warningEx.setVisible(false);
            yesEx.setVisible(false);
            noEx.setVisible(false);
            arrow.setPage(page);     
        }
        
        else if (page == 4)
        {
            scoreEx.setText("Score: 90");
            livesEx.setText("Lives: 10");
            cursor.setVisible(true);
            cursor.setBounds(screenWidth/3+70, instructionHeight/3+70, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
            instruct.setText("<html>Be careful of where you click because if you miss a target, you will lose 150 points.</html>");
            instruct.setBounds(0,0,screenWidth,screenHeight/5);
            instruct2.setVisible(false);
            instruct2.setText("");
            instruct2.setBounds(0,0,0,0);
            previous.setEnabled(true);
            next.setEnabled(true);
            ex1.setVisible(false);
            ex2.setVisible(true);
            ex3.setVisible(false);
            ex4.setVisible(false);
            ex5.setVisible(false);
            ex6.setVisible(false);
            ex7.setVisible(false);
            ex7.setVisible(false);
            shotEx1.setVisible(false);
            shotEx2.setVisible(false);
            shotEx3.setVisible(true);
            shotEx4.setVisible(false);
            pausedEx.setVisible(false);
            pauseEx.setText("Pause");
            warningEx.setVisible(false);
            yesEx.setVisible(false);
            noEx.setVisible(false);
            arrow.setPage(page);     
        }
        
        else if (page == 5)
        {
            scoreEx.setText("Score: 90");
            livesEx.setText("Lives: 10");
            cursor.setVisible(false);
            cursor.setBounds(screenWidth/2, instructionHeight/2, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
            instruct.setText("<html>This dark part of the target represents the elapsed time of the target. It expands in a counter-clockwise fashion.</html>");
            instruct.setBounds(0,0,screenWidth,screenHeight/5);
            instruct2.setVisible(false);
            instruct2.setText("");
            instruct2.setBounds(0,0,0,0);
            previous.setEnabled(true);
            next.setEnabled(true);
            ex1.setVisible(false);
            ex2.setVisible(false);
            ex3.setVisible(true);
            ex4.setVisible(false);
            ex5.setVisible(false);
            ex6.setVisible(false);
            ex7.setVisible(false);
            shotEx1.setVisible(false);
            shotEx2.setVisible(false);
            shotEx3.setVisible(false);
            shotEx4.setVisible(false);
            pausedEx.setVisible(false);
            pauseEx.setText("Pause");
            warningEx.setVisible(false);
            yesEx.setVisible(false);
            noEx.setVisible(false);
            arrow.setPage(page);     
        }
        
        else if (page == 6)
        {
            scoreEx.setText("Score: 90");
            livesEx.setText("Lives: 9");
            ex1.setVisible(true);
            cursor.setVisible(false);
            cursor.setBounds(screenWidth/2, instructionHeight/2, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
            instruct.setText("<html>When the dark part does a full rotation, the target will fade away and you will lose a life.</html>");
            instruct.setBounds(0,0,screenWidth,screenHeight/5);
            instruct2.setVisible(false);
            instruct2.setText("");
            instruct2.setBounds(0,0,0,0);
            previous.setEnabled(true);
            next.setEnabled(true);
            ex1.setVisible(false);
            ex2.setVisible(false);
            ex3.setVisible(false);
            ex4.setVisible(true);
            ex5.setVisible(false);
            ex6.setVisible(false);
            ex7.setVisible(false);
            shotEx1.setVisible(false);
            shotEx2.setVisible(false);
            shotEx3.setVisible(false);
            shotEx4.setVisible(false);
            pausedEx.setVisible(false);
            pauseEx.setText("Pause");
            warningEx.setVisible(false);
            yesEx.setVisible(false);
            noEx.setVisible(false);
            arrow.setPage(page);     
        }
        
        else if (page == 7)
        {
            scoreEx.setText("Score: 90");
            livesEx.setText("Lives: 9");
            cursor.setVisible(false);
            cursor.setBounds(screenWidth/2 + 42, instructionHeight/2 + 42, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
            instruct.setText("<html>Your lives are shown here.</html>");
            instruct.setBounds(0,instructionHeight/20,screenWidth/7,screenHeight/5);
            instruct2.setVisible(true);
            instruct2.setText("<html>Your score is shown here.</html>");
            instruct2.setBounds(screenWidth - screenWidth/8,instructionHeight/20,screenWidth/7,screenHeight/5);
            previous.setEnabled(true);
            next.setEnabled(true);
            ex1.setVisible(false);
            ex2.setVisible(false);
            ex3.setVisible(false);
            ex4.setVisible(false);
            ex5.setVisible(false);
            ex6.setVisible(false);
            ex7.setVisible(false);
            shotEx1.setVisible(false);
            shotEx2.setVisible(false);
            shotEx3.setVisible(false);
            shotEx4.setVisible(false);
            pausedEx.setVisible(false);
            pauseEx.setText("Pause");
            warningEx.setVisible(false);
            yesEx.setVisible(false);
            noEx.setVisible(false);
            arrow.setPage(page);   
        }
        
        else if (page == 8)
        {
            scoreEx.setText("Score: 190");
            livesEx.setText("Lives: 9");
            cursor.setVisible(true);
            cursor.setBounds(screenWidth/6 + 55, 4*instructionHeight/6 + 53, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
            instruct.setText("<html>The task of the game is to rack up as many points as possible. Clicking fast and accurate is the key to success."
                           + " When all targets are clicked, a new set of targets will appear. This process will continue until all lives are lost.</html>");
            instruct.setBounds(0,0,screenWidth,screenHeight/5);
            instruct2.setVisible(false);
            instruct2.setText("");
            instruct2.setBounds(0,0,0,0);
            previous.setEnabled(true);
            next.setEnabled(true);
            ex1.setVisible(false);
            ex2.setVisible(false);
            ex3.setVisible(true);
            ex4.setVisible(false);
            ex5.setVisible(true);
            ex6.setVisible(true);
            ex7.setVisible(false);
            shotEx1.setVisible(false);
            shotEx2.setVisible(false);
            shotEx3.setVisible(false);
            shotEx4.setVisible(true);
            pausedEx.setVisible(false);
            pauseEx.setText("Pause");
            warningEx.setVisible(false);
            yesEx.setVisible(false);
            noEx.setVisible(false);
            arrow.setPage(page);   
        }
        
        else if (page == 9)
        {
            scoreEx.setText("Score: 190");
            livesEx.setText("Lives: 9");
            cursor.setVisible(true);
            cursor.setBounds(screenWidth/4, instructionHeight - instructionHeight/26, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
            instruct.setText("<html>The bottom buttons are the pause and quit buttons.</html>");
            instruct.setBounds(0,0,screenWidth,screenHeight/5);
            instruct2.setVisible(false);
            instruct2.setText("");
            instruct2.setBounds(0,0,0,0);
            previous.setEnabled(true);
            next.setEnabled(true);
            ex1.setVisible(false);
            ex2.setVisible(false);
            ex3.setVisible(false);
            ex4.setVisible(false);
            ex5.setVisible(false);
            ex6.setVisible(false);
            ex7.setVisible(false);
            shotEx1.setVisible(false);
            shotEx2.setVisible(false);
            shotEx3.setVisible(false);
            shotEx4.setVisible(false);
            pausedEx.setVisible(false);
            pauseEx.setText("Pause");
            warningEx.setVisible(false);
            yesEx.setVisible(false);
            noEx.setVisible(false);
            arrow.setPage(page);   
        }
        
        else if (page == 10)
        {
            scoreEx.setText("Score: 190");
            livesEx.setText("Lives: 9");
            cursor.setVisible(true);
            cursor.setBounds(screenWidth/4, instructionHeight - instructionHeight/26, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
            instruct.setText("<html>Clicking the pause button will pause the game and a PAUSED sign will appear to indicate that the game is paused. Clicking "
                           + "the resume button will resume the game.</html>");
            instruct.setBounds(0,0,screenWidth,screenHeight/5);
            instruct2.setVisible(false);
            instruct2.setText("");
            instruct2.setBounds(0,0,0,0);
            previous.setEnabled(true);
            next.setEnabled(true);
            ex1.setVisible(false);
            ex2.setVisible(false);
            ex3.setVisible(false);
            ex4.setVisible(false);
            ex5.setVisible(false);
            ex6.setVisible(false);
            ex7.setVisible(false);
            shotEx1.setVisible(false);
            shotEx2.setVisible(false);
            shotEx3.setVisible(false);
            shotEx4.setVisible(false);
            pausedEx.setVisible(true);
            pauseEx.setText("Resume");
            warningEx.setVisible(false);
            yesEx.setVisible(false);
            noEx.setVisible(false);
            arrow.setPage(page);   
        }
        
        else if (page == 11)
        {
            scoreEx.setText("Score: 190");
            livesEx.setText("Lives: 9");
            cursor.setVisible(true);
            cursor.setBounds(3*screenWidth/4, instructionHeight - instructionHeight/26, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
            instruct.setText("<html>Clicking the quit button will pause the game and bring up a quit confirmation warning. Clicking \"no\" will resume the game"
                           + " and clicking \"yes\" will quit the game.</html>");
            instruct.setBounds(0,0,screenWidth,screenHeight/5);
            instruct2.setVisible(false);
            instruct2.setText("");
            instruct2.setBounds(0,0,0,0);
            previous.setEnabled(true);
            next.setEnabled(true);
            ex1.setVisible(false);
            ex2.setVisible(false);
            ex3.setVisible(false);
            ex4.setVisible(false);
            ex5.setVisible(false);
            ex6.setVisible(false);
            ex7.setVisible(false);
            shotEx1.setVisible(false);
            shotEx2.setVisible(false);
            shotEx3.setVisible(false);
            shotEx4.setVisible(false);
            pausedEx.setVisible(false);
            pauseEx.setText("Pause");
            warningEx.setVisible(true);
            yesEx.setVisible(true);
            noEx.setVisible(true);
            arrow.setPage(page);   
        }
        
        else if (page == 12)
        {
            scoreEx.setText("Score: 190");
            livesEx.setText("Lives: 9");
            cursor.setVisible(false);
            cursor.setBounds(screenWidth/4, instructionHeight - instructionHeight/26, cursor.getPreferredSize().width, cursor.getPreferredSize().height);
            instruct.setText("<html>This game is intended to help improve mouse accuracy in a fun way. Enjoy!</html>");
            instruct.setBounds(0,0,screenWidth,screenHeight/5);
            instruct2.setVisible(false);
            instruct2.setText("");
            instruct2.setBounds(0,0,0,0);
            previous.setEnabled(true);
            next.setEnabled(false);
            ex1.setVisible(true);
            ex2.setVisible(false);
            ex3.setVisible(false);
            ex4.setVisible(false);
            ex5.setVisible(false);
            ex6.setVisible(false);
            ex7.setVisible(false);
            shotEx1.setVisible(false);
            shotEx2.setVisible(false);
            shotEx3.setVisible(false);
            shotEx4.setVisible(false);
            pausedEx.setVisible(false);
            pauseEx.setText("Pause");
            warningEx.setVisible(false);
            yesEx.setVisible(false);
            noEx.setVisible(false);
            arrow.setPage(page);   
        }
    }
    
    public StartScreen getStarter()
    {
        return startScreen;
    }
    
    public boolean isStarter()
    {
        return starter;
    }
    
    public void setStarter()
    {
        starter = false;
    }
    
    public void skip()
    {
        startScreen.skip();
    }
    
    public void showConfirmation(int i)
    {       
        JFrame frame = new JFrame();
        frame.setBackground(Color.WHITE);
        frame.setSize((int)(screenWidth/4.2), (int)(screenWidth/8.4));
        
        JLabel message = new JLabel("Are you sure you want to reset this score?");
        message.setFont(font4);
        
        UIManager.put("OptionPane.buttonFont", new FontUIResource(font4));
        int n = JOptionPane.showConfirmDialog(frame, message, "", JOptionPane.YES_NO_OPTION);    
        
        if (n == 0)
        {
            if (i == 1)
                model.resetHighScore();
            else if (i == 2)
                model.resetCustomScore();
            else if (i == 3)
                model.resetHighLevel();
            else
                model.resetAllScores();
            
            highScore1.setText("High Score for Standard Games: " + model.getHighscore(1));
            highScore2.setText("High Score for Custom Games: " + model.getHighscore(2));
            highLevel.setText("Highest Level Achieved: " + model.getHighLevel());
            
            for (int count=0; count<50; count++)
                levelScores[count] = "<html><pre>Level " + (count+1) + " Score: " + model.getScoreLevel(count) + "\t\tLevel " + (count+1) + " Total Score: " + model.getTotalScoreLevel(count) + "</pre></html>";
            
            highScoreLvls.repaint();
            
            try {
                out = new PrintWriter(new File("saves.txt"));
            } catch (FileNotFoundException ex) {}

            out.println(this.convert(model.getHighscore(1)));
            out.println(this.convert(model.getHighscore(2)));
            out.println(this.convert2(model.getHighLevel()));
            for (int counter=0; counter<50; counter++)
            {
                out.println(this.convert2(model.getScoreLevel(counter)));
                out.println(this.convert2(model.getTotalScoreLevel(counter)));
            }
            out.close();
        }
    }
    
    private String convert(int score)
    {
        String conversion = "";
        char[] ranges = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] falseRanges = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        
        for (int c=0; c<10; c++)
            conversion += falseRanges[(int)(Math.random()*36)];
        
        if (score == 0)
            for (int c=0; c<6; c++)
                conversion += falseRanges[(int)(Math.random()*(21)) + 15];
        
        else
        {
            String realScore = "";
            while (score > 0)
            {
                realScore = ranges[score%16] + realScore;
                score /= 16;
            }
            
            while (realScore.length() < 6)
                realScore = "0" + realScore;
            
            conversion += realScore;
        }
        
        for (int c=0; c<10; c++)
            conversion += falseRanges[(int)(Math.random()*36)];
        
        return conversion;
    }
    
    private String convert2(int score)
    {
        String conversion = "";
        char[] ranges = {'A','B','C','D','E','F','G','H'};
        char[] falseRanges = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] falseRanges2 = {'0','1','2','3','4','5','6','7','8','9','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        
        for (int c=0; c<10; c++)
            conversion += falseRanges[(int)(Math.random()*36)];
        
        if (score == 0)
            for (int c=0; c<6; c++)
                conversion += falseRanges2[(int)(Math.random()*28)];
        
        else
        {
            String realScore = "";
            while (score > 0)
            {
                realScore = ranges[score%8] + realScore;
                score /= 8;
            }
            
            while (realScore.length() < 6)
                realScore = "A" + realScore;
            
            conversion += realScore;
        }
        
        for (int c=0; c<10; c++)
            conversion += falseRanges[(int)(Math.random()*36)];
        
        return conversion;
    }
    
    public void startStandardGame(String s)
    {
        standard = true;
        level = Integer.parseInt(s);
    }
    
    synchronized void resumeAll()
    {
        pauseOn2 = false;
        this.notifyAll();
    }
    
    public void pauseAll()
    {
        pauseOn2 = true;
    }
}
