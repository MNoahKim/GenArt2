import java.awt.*;

public class Particle {
    // initialize x, y, color, radius
    private int x, y, radius, xDirection, xSpeed, yDirection, ySpeed;
    private Color color;

    private int width, height;

    //variables for circular motion
    private double angle = 0;
    private double angularSpeed = 0.03;
    private int orbitRadius = 50;
    private int centerX;
    private int centerY;

    //life cycle variables
    private double timeSinceTouchedMilli; //last time a particle touched another
    private double nextSpawnAllowedMilli; //cooldown to prevent infinite spawning


    // initial setup of the particle
    public Particle(int x, int y, int radius, Color color, int windowWidth, int windowHeight, double spawnTimer) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;

        xDirection = 1;
        xSpeed = (int) (2 + Math.random() * (5 - 2));

        yDirection = 1;
        ySpeed = (int) (2 + Math.random() * (5 - 2));


        width = windowWidth;
        height = windowHeight;

        centerX = randInt(0, windowWidth);
        centerY = randInt(0, windowHeight);
        angle = randInt(0, 50);
        angularSpeed = randDouble(100, 200);
        orbitRadius = randInt(10, 40);

        timeSinceTouchedMilli = System.currentTimeMillis();
        nextSpawnAllowedMilli = System.currentTimeMillis() + spawnTimer;
    }

    public int randInt(int min, int max) {
        return (int) (min + Math.random() * (max - min));
    }

    public double randDouble(double min, double max) {
        return (min + Math.random() * (max - min));
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(x, y, radius * 2, radius * 2);
    }

    public void updateParticle() {
        if (Math.random() >= .99 || x <= 0 || x + (radius * 2) > width) {
            xDirection *= -1;
            if (x < 0) {
                x = 0;
            } else if (x + (radius * 2) > width) {
                x = width - (radius * 2);
            }
        }
        if (Math.random() >= .99 || y <= 0 || y + (radius * 2) >= height) {
            yDirection *= -1;
            if (x < 0) {
                x = 0;
            } else if (x + (radius * 2) > width) {
                x = width - (radius * 2);
            }
        }

        if (Math.random() >= .6) {
            xSpeed = (xSpeed + randInt(1,6));
        }

        if (Math.random() >= .3){
            if (xSpeed > 6){
                xSpeed = (xSpeed - randInt(1,6));
            }
        }

        if (Math.random() >= .3){
            if (ySpeed > 6){
                ySpeed = (ySpeed - randInt(1,6));
            }
        }

        if (Math.random() >= .6) {
            ySpeed = (ySpeed + randInt(1,6));
        }

        x += xSpeed * xDirection;
        y += ySpeed * yDirection;
    }

    public void linearMotion() {
        if (Math.random() >= .99 || x <= 0 || x + (radius * 2) > width) {
            xDirection *= -1;
            if (x < 0) {
                x = 0;
            } else if (x + (radius * 2) > width) {
                x = width - (radius * 2);
            }

        }
        if (Math.random() >= .99 || y <= 0 || y + (radius * 2) >= height) {
            yDirection *= -1;
            if (y < 0) {
                y = 0;
            } else if (y + (radius * 2) > height) {
                y = height - (radius * 2);
            }

        }

        x += xSpeed * xDirection;
        y += ySpeed * yDirection;
    }

    //helpers to spawn particles
    public int getRadius() {
        return radius;
    }

    public int getCenterX(){
        return radius + x;
    }

    public int getCenterY(){
        return radius + y;
    }

    public void touched(double nowMilli){
        timeSinceTouchedMilli = nowMilli;
    }

    public boolean canSpawn(double nowMilli){
        return nowMilli >= nextSpawnAllowedMilli;
    }

    public void setSpawnCooldown(double nowMilli, double cooldownMilli){
        nextSpawnAllowedMilli = nowMilli + cooldownMilli;
    }
}


//    public void updateParticles(){
//       angle += angularSpeed;
//        x = (int)(centerX + orbitRadius * Math.cos(angle));
//        y = (int)(centerY + orbitRadius * Math.sin(angle));
//    }
//    public void updateParticle(){
//        if (Math.random() >= .99 || x <= 0 || x + (radius * 2) > width) {
//            xDirection *= -1;
//        }
//        x += xSpeed * xDirection;
//
//        if (Math.random() >= .99 || y <= 0 || y + (radius * 2) >= height){
//            yDirection *= -1;
//        }
//        y += ySpeed * yDirection;
//
//
//    }