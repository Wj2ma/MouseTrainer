package mousetrainer;

import java.applet.*;
import java.net.*;

public class ShotSound extends Applet
{
    private AudioClip song; // Sound player
    private URL songPath;
    ShotSound(String filename)
    {
        try
        {
            songPath = new URL(getCodeBase(), filename);
           song = Applet.newAudioClip(songPath);
        }
        catch(Exception e){
            System.out.println("failed");
        } // Satisfy the catch
    }
    public void playSound()
    {
        song.play(); // Play
    }
    public void stopSound()
    {
        song.stop(); // Stop
    }
}

