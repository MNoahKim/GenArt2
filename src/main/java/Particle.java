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

    // initial setup of the particle
    public Particle(int x, int y, int radius, Color color, int windowWidth, int windowHeight) {
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
        angularSpeed = randDouble(0.01, 0.5);
        orbitRadius = randInt(10, 40);
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
        }
        x += xSpeed * xDirection;

        if (Math.random() >= .99 || y <= 0 || y + (radius * 2) >= height) {
            yDirection *= -1;
        }
        y += ySpeed * yDirection;

        if (Math.random() >= .999) {
            angle += angularSpeed;
            x = (int) (x + orbitRadius * Math.cos(angle));
            y = (int) (y + orbitRadius * Math.sin(angle));
        }
    }
}

//    public void updateParticle(){
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