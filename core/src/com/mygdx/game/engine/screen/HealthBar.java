package com.mygdx.game.engine.screen;

// retrieve current and max health values, update current health value

public class HealthBar {
    private int currentHealth;
    private int maxHealth;

    public HealthBar(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setCurrentHealth(int newHealth) {
        if (newHealth > maxHealth) {
            currentHealth = maxHealth;
        } else if (newHealth < 0) {
            currentHealth = 0;
        } else {
            currentHealth = newHealth;
        }
    }
}

