import java.util.Random;

/**
 * Contains the logic for the moves of bot.
 */

public class BotPlayer extends Player {

    private String nextMove[];
    private int i;

    public BotPlayer() {
        super();
        int i = 0;
        nextMove = new String[4];

    }

    /**
     * Decides whether to call look or move the bot, based on the number of goes the bot has had.
     * @return Look every 5th go, to call look in GameLogic, or a move to cause the bot to move every other go.
     */
    String getNextAction() {

        if (i % 5 == 0) {
            i = 1;
            return "LOOK";
        } else {
            i++;
            //move - 2 to counteract the i++ before return statement and the i starting at 1 to end infinite loop.
            return nextMove[i-2];
        }
    }

    /**
     * If the human hasn't been seen, the bot moves in a random direction for the next 4 goes until look is
     * called again. If the human has been seen, the moves for the next 4 goes are added to a String array, moving
     * towards the last seen position of the human player.
     *
     * @param y The relative y position of the human player to the bot.
     * @param x The relative x position of the human player to the bot.
     */
    protected void lookForHuman(int y, int x) {
        //If Player isnt in the scope of look, and player hasn't been seen.
        if (y == 0 && x == 0) {
            for (int j = 0; j <= 3; j++) {
                Random rand = new Random();
                int randDirection = rand.nextInt(4);
                switch (randDirection) {
                    case 0:
                        nextMove[j] = "MOVE N";
                        break;
                    case 1:
                        nextMove[j] = "MOVE E";
                        break;
                    case 2:
                        nextMove[j] = "MOVE S";
                        break;
                    case 3:
                        nextMove[j] = "MOVE W";
                        break;
                }
            }
        } else {
            //Move towards the last known position of HumanPlayer.
            for (int j = 0; j <= 3; j++) {
                if (x < 0) {
                    nextMove[j] = "MOVE W";
                    System.out.println(nextMove[j]);
                    x++;
                } else if (x > 0) {
                    nextMove[j] = "MOVE E";
                    System.out.println(nextMove[j]);
                    x--;

                } else if (y < 0) {
                    nextMove[j] = "MOVE S";
                    System.out.println(nextMove[j]);
                    y++;
                } else if (y > 0) {
                    nextMove[j] = "MOVE N";
                    System.out.println(nextMove[j]);
                    y--;
                }
            }
        }
    }
}
