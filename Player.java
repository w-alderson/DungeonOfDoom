import java.util.Random;

/**
 * Superclass - Runs the game with a player (human or bot) and generates and monitors positions of players.
 *
 */
abstract class Player {

    private int positionFlag;
    private int xPosition;
    private int yPosition;
    private Random rand;
    private String output;
    /**
     * Constructor method for Player.
     */
    public Player() {
    }

    /**
     * Places players on the map in a random location.
     * @param charArray The map itself from the object in GameLogic.
     * @param height The heigh of the map.
     * @param length The length of the map.
     */
    protected void placePlayer(char[][] charArray,int height, int length){
        positionFlag = 0;
        rand = new Random();

        //Continues random numbers until player is placed on a non wall/gold tile.
        while (positionFlag == 0) {
            int randX = rand.nextInt(length);
            int randY = rand.nextInt(height);

            if (charArray[randY][randX] != '#' && charArray[randY][randX] != 'G') {
                xPosition = randX;
                yPosition = randY;
                positionFlag ++;
            }
        }
    }

    /**
     * Accessor method.
     * @return The x-coordinate of the player.
     */
    protected int getXPosition(){
        return xPosition;
    }

    /**
     * Accessor method.
     * @return The y-Coordinate of the player.
     */
    protected int getYPosition(){
        return yPosition;
    }

    /**
     * Mutator method. Checks to see if a requested move is legal and updates the position accordingly. Called in
     * GameLogic.
     * @param direction The direction of requested movement
     * @param map The map as in GameLogic
     * @return
     */
    protected String movementProcessing(char direction, char[][]map) {
        output = "FAIL";
        try {
            switch (direction) {
                case 'N':
                    if (map[yPosition - 1][xPosition] != '#') {
                        output = "SUCCESS";
                        yPosition--;
                    }
                    break;
                case 'E':
                    if (map[yPosition][xPosition + 1] != '#') {
                        output = "SUCCESS";
                        xPosition++;
                    }
                    break;
                case 'S':
                    if (map[yPosition + 1][xPosition] != '#') {
                        output = "SUCCESS";
                        yPosition++;
                    }
                    break;
                case 'W':
                    if (map[yPosition][xPosition - 1] != '#') {
                        output = "SUCCESS";
                        xPosition--;
                    }
                    break;
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {
            return "FAIL";
        }
        return output;
    }

    /**
     * Abstact method to get the next action from each user.
     * @return The next action int the form of an accepted command.
     */
    abstract String getNextAction();

}




