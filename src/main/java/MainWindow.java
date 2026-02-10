import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindow extends JPanel {
    // 1 initial setup of window
    // 1 Panel is the Canvas where we will draw the Art
    // extend makes the MainWindow object a drawable object
    private final int WINDOW_WIDTH = 1920;
    private final int WINDOW_HEIGHT = 1080;

    private final int NUM_PARTICLES = 4;

    private final int SPAWN_DISTANCE = 40;
    private final int SPAWN_TIMER = 1000;
    private final int SPAWN_COUNT = 5;
    private final int MAX_PARTICLES = 250;

    //Particle p;

    ArrayList<Particle> particles;

    public MainWindow() {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)); // starting size of window


        particles = new ArrayList<>();

        for (int i = 0; i < NUM_PARTICLES; i++) {
            //min + Math.random() * (max-min)
            int x = randInt(0, WINDOW_WIDTH);
            int y = randInt(0, WINDOW_HEIGHT);
            //create a local radius variable, and assign a value between 5-15
            int radius = randInt(20,30);
            //create RGB values between 0-255 to assign a random Color to new Color
            int colorR = randInt(0, 255);
            int colorG = randInt(0, 255);
            int colorB = randInt(0, 255);
            particles.add(new Particle(x, y, radius, new Color(colorR, colorG, colorB), WINDOW_WIDTH, WINDOW_HEIGHT, SPAWN_TIMER));
        }

        // runs every 16 ms (1000/16 ~ 60fps)
        Timer timer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        timer.start();
    }

    public int randInt(int min, int max) {
        return (int) (min + Math.random() * (max - min));
    }

    public static void main(String[] args) {
        // frame is the window (bar, close button, resizing)
        JFrame frame = new JFrame("Title Here");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // actually quit the program when the window closes

        // initial setup of the window
        MainWindow mainWindow = new MainWindow();
        frame.setContentPane(mainWindow); // puts our drawable object into the frame
        frame.pack(); // sizes the frame to match our preferred size
        frame.setLocationRelativeTo(null); // opens the window in the middle of the screen
        frame.setVisible(true); // false would hide the window
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Particle pTemp : particles) {
            pTemp.draw(g2);
        }
    }


    public void update() {
        for (Particle pTemp : particles) {
            pTemp.updateParticle();
        }

        double now = System.currentTimeMillis();
        ArrayList <Particle> particlesToAdd = new ArrayList<>();

        for(int i = 0; i < particles.size(); i++){
            Particle a = particles.get(i);
            for (int j = i+1; j < particles.size(); j++){
                Particle b = particles.get(j);

                if (isTouching(a,b)){
                    //System.out.println("Touching (" + a.getCenterX() + ", " + a.getCenterY() + ") (" + b.getCenterX() + ", " + b.getCenterY() + ")");
                    //reset spawn timers
                    a.touched(now);
                    b.touched(now);

                    if(a.canSpawn(now) && b.canSpawn(now)){
                        a.setSpawnCooldown(now, SPAWN_TIMER);
                        b.setSpawnCooldown(now, SPAWN_TIMER);
                        for (int k = 0; k < SPAWN_COUNT; k++){
                            if(particles.size() < MAX_PARTICLES) {
                                Particle toAdd = spawnParticleNear(a, b);
                                particlesToAdd.add(toAdd);
                            }
                        }


                    }
                }
            }
        }
        particles.addAll(particlesToAdd);


    }

    private boolean isTouching(Particle a, Particle b){
        //difference between the two centers
        double deltaX = a.getCenterX() - b.getCenterX();
        double deltaY = a.getCenterY() - b.getCenterY();

        //distance between centers squared
        double distanceBetweenCentersSquared = deltaX * deltaX + deltaY * deltaY;

        //sum of both particle radii
        double combinedRadius = a.getRadius() + b.getRadius();

        //if distance between centers is less than or equal to
        // combined radius, the particles are touching or overlapping
        return distanceBetweenCentersSquared <= combinedRadius * combinedRadius;
    }

    private Particle spawnParticleNear(Particle a, Particle b){
        //midpoint between the centers of the two particles
        double collisionMidpointX = (a.getCenterX() + b.getCenterX()) / 2.0;
        double collisionMidpointY = (a.getCenterY() + b.getCenterY()) / 2.0;

        //random offset to spread out particles
        int randomOffsetX = randInt(-SPAWN_DISTANCE, SPAWN_DISTANCE);
        int randomOffsetY = randInt(-SPAWN_DISTANCE, SPAWN_DISTANCE);

        int spawnPositionX = (int)(collisionMidpointX + randomOffsetX);
        int spawnPositionY = (int)(collisionMidpointY + randomOffsetY);

        Color randomColor = new Color(randInt(0, 255), (randInt(0, 255)), randInt(0, 255));

        return new Particle(spawnPositionX, spawnPositionY, (a.getRadius() + b.getRadius() / 2), randomColor, WINDOW_WIDTH, WINDOW_HEIGHT, SPAWN_TIMER);

    }
}