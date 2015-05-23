package mousetrainer;

public class MouseTrainerModel
{
    private MouseTrainerGUI view;
    
    private int lives;
    private int size;
    private int targets;
    private int time;
    private int score = 0;
    private int levelScore = 0;
    private int highscore = 0;
    private int customHighscore = 0;
    private int highLevel = 1;
    private int[] highScoreLevel = new int[50];
    private int[] totalScoreLevel = new int[50];
    private int beatenScore = 0;
    
    private boolean lose = false;
    private boolean infinite = false;
    private boolean custom = false;
    private boolean customizedLevel = false;
    private boolean beatLevelScore = false;
    private boolean beatTotalScore = false;
    
    private int level = 1;
    
    private int[] sizes;
    private int[] times;
    private int[] targetAmounts;
    
    public MouseTrainerModel()
    {
        super();
        sizes = new int[10];
        times = new int[25];
        targetAmounts = new int[99];
        
        for (int counter=20; counter <= 200; counter+=20)
            sizes[counter/20 - 1] = counter;
        for (int counter=1000; counter <= 25000; counter+=1000)
            times[counter/1000-1] = counter;
        for (int counter = 1; counter <= 99; counter++)
            targetAmounts[counter-1] = counter;               
    }
    
    public void setGUI(MouseTrainerGUI v)
    {
        this.view = v;
    }
    
    public void initializeGame(String s)
    {
        lives = 10;
        size = 10;
        targets = 1;
        time = 3000;
        custom = false;
        
        int l = Integer.parseInt(s);
        for (int counter=1; counter<l; counter++)
        {
            this.levelUp();
            customizedLevel = true;
        }
    }
    
    public void initializeGame(int s, int ts, int t, int l)
    {
        lives = l;
        size = s;
        targets = ts;
        time = t;
        custom = true;
        
        if (lives == 0)
            infinite = true;
    }
    
    public int getLives()
    {
        if (infinite)
            return 999;
        
        return lives;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public int getHighscore()
    {
        if (custom)
            return customHighscore;
        
        return highscore;
    }
    
    public int getHighscore(int i)
    {
        if (i == 2)
            return customHighscore;
        
        return highscore;
    }
    
    public int getHighLevel()
    {
        return highLevel;
    }
    
    public int getScoreLevel(int index)
    {
        return highScoreLevel[index];
    }
    
    public int getTotalScoreLevel(int index)
    {
        return totalScoreLevel[index];
    }
    
    public int getBeatenScore()
    {
        return beatenScore;
    }
    
    public int getTargets()
    {
        return targets;
    }
    
    public int getTime()
    {
        return time;
    }
    
    public int getSize()
    {
        switch (size)
        {
            case 1:
                return Target.ONE;
            case 2:
                return Target.TWO;
            case 3:
                return Target.THREE;
            case 4:
                return Target.FOUR;
            case 5:
                return Target.FIVE;
            case 6:
                return Target.SIX;
            case 7:
                return Target.SEVEN;
            case 8:
                return Target.EIGHT;
            case 9:
                return Target.NINE;
            case 10: 
                return Target.TEN;
        }
        return 0;
    }
    
    public void addScore(int s)
    {
        score += s;
        levelScore += s;
        view.update();
    }
    
    public void removeLife()
    {
        if (!infinite)
        {
            lives --;
            if (lives <= 0)
            {
                lives = 0;
                lose = true;
                this.endGame();
            }

            view.update();
        }
    }
    
    public boolean isLose()
    {
        return lose;
    }
    
    private void endGame()
    {
        if (score > customHighscore && custom)
        {
            beatenScore = score - customHighscore;
            customHighscore = score;
        }
        
        else if (score > highscore && !custom)
        {
            beatenScore = score - highscore;
            highscore = score;
        }
    }
    
    public void reset()
    {
        score = 0;
        lives = 10;
        targets = 0;
        levelScore = 0;
        customizedLevel = false;
        custom = false;
        beatenScore = 0;
        lose = false;
        infinite = false;
        level = 1;
    }
    
    public void resetHighScore()
    {
        highscore = 0;
    }
    
    public void resetCustomScore()
    {
        customHighscore = 0;
    }
    
    public void resetHighLevel()
    {
        highLevel = 0;
    }
    
    public void resetAllScores()
    {
        for (int count=0; count<50; count++)
        {
            highScoreLevel[count] = 0;
            totalScoreLevel[count] = 0;
        }
    }
    
    public void setHighScore(String line, int n)
    {
        if (n == 1)
            highscore = this.convert(line);
        else
            customHighscore = this.convert(line);
    }
    
    private int convert(String line)
    {
        line = line.substring(10, 16);
        String numbers = "0123456789ABCDEF";
        char[] fakes = {'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        
        boolean fake = false;
        
        for (char f: fakes)
        {
            if (line.indexOf(f) > -1)
                fake = true;
        }
        
        int realScore;
        
        if (!fake) 
            realScore = numbers.indexOf(line.charAt(0))*1048576 + numbers.indexOf(line.charAt(1))*65536 + numbers.indexOf(line.charAt(2))*4096 +
                    numbers.indexOf(line.charAt(3))*256 + numbers.indexOf(line.charAt(4))*16 + numbers.indexOf(line.charAt(5));
        
        else
            realScore = 0;
        
        return realScore;
    }
    
    private int convert2(String line)
    {
        line = line.substring(10, 16);
        String numbers = "ABCDEFGH";
        char[] fakes = {'1','2','3','4','5','6','7','8','9','0','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        
        boolean fake = false;
        
        for (char f: fakes)
        {
            if (line.indexOf(f) > -1)
                fake = true;
        }
        
        int realScore;
        
        if (!fake) 
            realScore = numbers.indexOf(line.charAt(0))*32768 + numbers.indexOf(line.charAt(1))*4096 + numbers.indexOf(line.charAt(2))*512 +
                    numbers.indexOf(line.charAt(3))*64 + numbers.indexOf(line.charAt(4))*8 + numbers.indexOf(line.charAt(5));
        else
            realScore = 0;
        
        return realScore;
    }
    
    public void setHighLevel(String line)
    {
        highLevel = this.convert2(line);
    }
    
    public void setScoreLevel(String line, int index)
    {
        highScoreLevel[index] = this.convert2(line);
    }
    
    public void setTotalScoreLevel(String line, int index)
    {
        totalScoreLevel[index] = this.convert2(line);
    }
    
    public boolean isBeatLevelScore()
    {
        return beatLevelScore;
    }
    
    public boolean isBeatTotalScore()
    {
        return beatTotalScore;
    }
    
    public void levelUp()
    {
        beatLevelScore = false;
        beatTotalScore = false;
        
        if (level <= 50)
        {
            if (highScoreLevel[level-1] < levelScore && !customizedLevel)
            {
                highScoreLevel[level-1] = levelScore;
                beatLevelScore = true;
            }

            else
                beatLevelScore = false;

            if (totalScoreLevel[level-1] < score && !customizedLevel)
            {
                totalScoreLevel[level-1] = score;
                beatTotalScore = true;
            }

            else
                beatTotalScore = false;
        }
        
        levelScore = 0;
        level++;
        
        if (highLevel < level && !customizedLevel)
            highLevel = level;
        
        if (level == 2)
            size = 7;
        else if (level == 3)
            size = 4;
        else if (level == 4)
            size = 2;
        else if (level == 5)
        {
            size = 5;
            targets = 3;
        }
        else if (level == 6)
        {
            size = 4;
            targets = 4;
        }
        else if (level == 7)
        {
            size = 7;
            time = 2000;
        }
        else if (level == 8)
        {
            size = 5;
            targets = 5;
            time = 3000;
        }
        else if (level == 9)
        {
            size = 3;
            targets = 3;
            time = 2500;
        }
        else if (level == 10)
        {
            size = 10;
            targets = 30;
            time = 10000;
        }
        else if (level == 11)
        {
            size = 8;
            targets = 1;
            time = 1500;
        }
        else if (level == 12)
        {
            size = 5;
            time = 1500;
        }
        else if (level == 12)
        {
            size = 1;
            time = 2000;
        }
        else if (level == 13)
        {
            targets = 10;
            time = 4000;
            size = 7;
        }
        else if (level == 14)
        {
            targets = 5;
            time = 3000;
            size = 4;
        }
        else if (level == 15)
        {
            targets = 10;
            size = 3;
            time = 6000;
        }
        else if (level == 16)
        {
            targets = 4;
            size = 8;
            time = 2000;
        }
        else if (level == 17)
        {
            targets = 1;
            size = 5;
            time = 1000;
        }
        else if (level == 18)
        {
            size = 3;
        }
        else if (level == 19)
        {
            targets = 2;
            size = 10;
            time = 1250;
        }
        else if (level == 20)
        {
            size = 5;
            targets = 50;
            time = 16500;
        }
        else if (level == 21)
        {
            targets = 5;
            size = 10;
            time = 1500;
        }
        else if (level == 22)
        {
            targets = 2;
            size = 10;
            time = 1100;
        }
        else if (level == 23)
        {
            targets = 10;
            size = 5;
            time = 4000;
        }
        else if (level == 24)
        {
            size = 3;
            targets = 10;
            time = 6000;
        }
        else if (level == 25)
        {
            size = 2;
        }
        else if (level == 26)
        {
            targets = 3;
            size = 8;
            time = 1200;
        }
        else if (level == 27)
        {
            size = 4;
            targets = 15;
            time = 6000;
        }
        else if (level == 28)
        {
            size = 7;
            targets = 7;
            time = 2700;
        }
        else if (level == 29)
        {
            targets = 10;
            size = 4;
            time = 4500;
        }
        else if (level == 30)
        {
            targets = 75;
            size = 3;
            time = 29000;
        }
        else if (level == 31)
        {
            targets = 2;
            size = 7;
            time = 1100;
        }
        else if (level == 32)
        {
            targets = 3;
            time = 1500;
        }
        else if (level == 33)
        {
            targets = 6;
            time = 3333;
            size = 3;
        }
        else if (level == 34)
        {
            size = 7;
            targets = 4;
            time = 1800;
        }
        else if (level == 35)
        {
            targets = 5;
            time = 2100;
        }
        else if (level == 36)
        {
            targets = 6;
            time = 2222;
        }
        else if (level == 37)
        {
            targets = 7;
            time = 2500;
        }
        else if (level == 38)
        {
            targets = 8;
            time = 2800;
        }
        else if (level == 39)
        {
            targets = 9;
            time = 3000;
        }
        else if (level == 40)
        {
            targets = 77;
            time = 22222;
        }
        else if (level == 41)
        {
            targets = 50;
            size = 2;
        }
        else if (level == 42)
        {
            targets = 20;
            size = 5;
            time = 6300;
        }
        else if (level == 43)
        {
            targets = 30;
            size = 4;
            time = 10500;
        }
        else if (level == 44)
        {
            targets = 10;
            size = 3;
            time = 5000;
        }
        else if (level == 50)
        {
            targets = 99;
            size = 5;
            time = 25000;
        }
        else if (level == 51)
        {
            size = 5;
            targets = 1;
            time = 750;
        }
        else
        {
            if (targets == 1)
            {
                targets++;
                time += 500;
            }
            else if (targets < 5)
            {
                time += 300;
                targets++;
            }
            else if (targets < 99)
            {
                time += 200;
            }
            
            else
            {
                if (size > 1)
                    size--;
                targets = 1;
                time = 750;
            }
        }
    }
}
