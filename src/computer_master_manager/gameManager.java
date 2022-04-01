package computer_master_manager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import computer_master_bullets.Bullet;
import computer_master_display.Display;
import computer_master_enemies.Enemy;
import computer_master_entity.Player;
import computer_master_setUp.gameSetUp;

public class gameManager implements KeyListener {
    private Player player;
    public static ArrayList<Bullet> bullet;
    private ArrayList<Enemy> enemies;
    private long current;
    private long delay;
    private int health;
    private int score;
    private boolean start;

    public gameManager() {

    }

    public void init() {
        Display.frame.addKeyListener(this);
        player = new Player((gameSetUp.gameWidth / 2) + 50, (gameSetUp.gameHeight - 60) + 50);
        player.init();
        bullet = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        current = System.nanoTime();
        delay = 2000;
        health = player.getHealth();
        score = 0;
    }

    public void tick() {
        if (start) {
            player.tick();
            for (int i = 0; i < bullet.size(); i++) {
                bullet.get(i).tick();
            }
            long breaks = (System.nanoTime() - current) / 1000000;
            if (breaks > delay) {
                for (int i = 0; i < 2; i++) {
                    Random rand = new Random();
                    int randX = rand.nextInt(450);
                    int randY = rand.nextInt(450);
                    if (health > 0) {
                        enemies.add(new Enemy(randX, -randY));
                    }
                }
                current = System.nanoTime();
            }
            //enemies
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).tick();
            }
        }

    }

    public void render(Graphics g) {
        if (start) {
            player.render(g);
            for (int i = 0; i < bullet.size(); i++) {
                bullet.get(i).render(g);
            }
            for (int i = 0; i < bullet.size(); i++) {
                if (bullet.get(i).getY() <= 50) {
                    bullet.remove(i);
                    i--;
                }
            }
            //enemies
            for (int i = 0; i < enemies.size(); i++) {
                if (!(enemies.get(i).getX() <= 50 || enemies.get(i).getX() >= 450 - 50 || enemies.get(i).getY() >= 450 - 50)) {
                    if (enemies.get(i).getY() >= 50) {
                        enemies.get(i).render(g);
                    }
                }

            }
            //enemies
            for (int i = 0; i < enemies.size(); i++) {
                int ex = enemies.get(i).getX();
                int ey = enemies.get(i).getY();
                //collision of enemy and player
                /* if(r1.x<r2.x+width &&
                 * r1.x+width>r2.x &&
                 * r1.y<r2.y+width &&
                 * r1.y+width>r2.y
                 */
                //r1=player
                //r2=enemies
                int px = player.getX();
                int py = player.getY();
                if (px < ex + 50 && px + 60 > ex && py < ey + 50 && py + 60 > ey) {
                    enemies.remove(i);
                    i--;
                    health--;
                    System.out.println(health);
                    if (health <= 0) {
                        enemies.removeAll(enemies);
                        player.setHealth(0);
                        start=false;
                    }
                }
                //bullets
                for (int j = 0; j < bullet.size(); j++) {
                    int bx = bullet.get(j).getX();
                    int by = bullet.get(j).getY();
                    //collision of enemy and player
                    /* if(r1.x<r2.x+width &&
                     * r1.x+width>r2.x &&
                     * r1.y<r2.y+width &&
                     * r1.y+width>r2.y
                     */
                    //r1=enemies
                    //r2=bullet
                    if (ex < bx + 6 && ex + 50 > bx && ey < by + 6 && ey + 50 > by) {
                        enemies.remove(i);
                        i--;
                        bullet.remove(j);
                        j--;
                        score += 5;
                    }
                }
                g.setColor(Color.blue);
                g.setFont(new Font("times new roman", Font.BOLD, 40));
                g.drawString("Score: " + score, 70, 500);
            }

        }else{
            g.setColor(Color.RED);
            g.setFont(new Font("Times new roman",Font.PLAIN,33));
        g.drawString("Nguyễn Phạm Hồng Phúc",90,(gameSetUp.gameHeight/2)+50);
            g.drawString("Ấn Enter để tiếp tục",120,(gameSetUp.gameHeight/2+40)+50);
        }
    }
    public void keyPressed(KeyEvent e){
        int source=e.getKeyCode();
        if(source==KeyEvent.VK_ENTER){
            start=true;
            init();
        };
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
