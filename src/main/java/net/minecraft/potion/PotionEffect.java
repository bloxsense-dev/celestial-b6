package net.minecraft.potion;

public class PotionEffect {
    private Potion potion;
    private int duration;
    private int amplifier;
    private boolean isAmbient;
    private boolean showParticles;

    public PotionEffect(Potion potion, int duration, int amplifier, boolean isAmbient, boolean showParticles) {
        this.potion = potion;
        this.duration = duration;
        this.amplifier = amplifier;
        this.isAmbient = isAmbient;
        this.showParticles = showParticles;
    }

    public PotionEffect(Potion potion, int duration, int amplifier) {
        this(potion, duration, amplifier, false, true);
    }

    public PotionEffect(Potion potion, int duration) {
        this(potion, duration, 0);
    }

    public Potion getPotion() {
        return this.potion;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    public boolean getIsAmbient() {
        return this.isAmbient;
    }

    public boolean doesShowParticles() {
        return this.showParticles;
    }

    public String getEffectName() {
        return this.potion != null ? this.potion.getName() : "";
    }
}
