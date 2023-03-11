//package com.mygdx.game.engine.screen;
//
//import com.badlogic.gdx.graphics.Color;
//
//public class HealthBar {
//    private int currentHealth;
//    private int maxHealth;
//
//    // Constructor
//    public HealthBar(int maxHealth) {
//        this.maxHealth = maxHealth;
//        this.currentHealth = maxHealth;
//    }
//
//    // Getter and setter methods
//    public int getCurrentHealth() {
//        return this.currentHealth;
//    }
//
//    public int getMaxHealth() {
//        return this.maxHealth;
//    }
//
//    public void setCurrentHealth(int newHealth) {
//        if (newHealth > maxHealth) {
//            currentHealth = maxHealth;
//        } else if (newHealth < 0) {
//            currentHealth = 0;
//        } else {
//            currentHealth = newHealth;
//        }
//    }
//
//    // Method to render the health bar
//    public void render(Graphics2D g2d, int x, int y, int width, int height) {
//        // Calculate the current health percentage
//        double percentage = (double) currentHealth / (double) maxHealth;
//
//        // Set the bar color to green if the percentage is over 75%, yellow if between 30% and 75%, and red if below 30%
//        Color barColor;
//        if (percentage >= 0.75) {
//            barColor = Color.GREEN;
//        } else if (percentage >= 0.3) {
//            barColor = Color.YELLOW;
//        } else {
//            barColor = Color.RED;
//        }
//
//        // Draw the outline of the bar
//        g2d.setColor(Color.BLUE);
//        g2d.drawRect(x, y, width, height);
//
//        // Draw the filled part of the bar
//        g2d.setColor(barColor);
//        g2d.fillRect(x+1, y+1, (int) ((width-2) * percentage), height-1);
//    }
//}
//// HealthBar healthBar = new HealthBar(100);
//healthBar.setCurrentHealth(75); // Decrease the current health by 25%
//
//// Call the render method to draw the bar on the screen
//healthBar.render(g2d, 10, 10, 200, 20);
