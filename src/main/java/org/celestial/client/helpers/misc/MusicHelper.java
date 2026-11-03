package org.celestial.client.helpers.misc;

import org.celestial.client.Celestial;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicHelper {

    public static void playSound(final String url) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(Celestial.class.getResourceAsStream("/assets/minecraft/celestial/songs/" + url));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {

            }
        }).start();
    }

}
