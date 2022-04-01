package computer_master_main;

import computer_master_setUp.gameSetUp;

public class Main {
    public static void main(String[] args) {
        gameSetUp game = new gameSetUp("SkyForce Game", 500, 600);
        game.start();
    }
}
