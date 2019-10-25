/**
 * Contains the main logic part of the game, as it processes. Adapted from GameLogic on Moodle.
 *
 */

public class GameLogic {

    private Map map;
    private HumanPlayer human;
    private BotPlayer bot;
    private boolean endFlag;
    private Player[] players;
    private String output;
    private Player currentPlayer;

    /**
     * Default constructor, creates new instances of Map, HumanPlayer and BotPlayer.
     */
    public GameLogic() {
        map = new Map();
        human = new HumanPlayer();
        bot = new BotPlayer();
        endFlag = true;
        players = new Player[2];

    }

    /**
     * Checks if the game is running
     *
     * @return if the game is running.
     */
    protected boolean gameRunning() {
        return endFlag;
    }

    /**
     * Returns the gold required to win.
     *
     * @return : Gold required to win.
     */
    protected String hello() {
        return "Gold to win: " + map.getGoldRequired();
    }

    /**
     * Returns the gold currently owned by the player.
     *
     * @return : Gold currently owned.
     */
    protected String gold() {
        return "Gold owned: " + human.getGoldOwned();
    }

    /**
     * Checks if movement is legal and updates player's location on the map.
     *
     * @return : Protocol if success or not.
     */

    protected String move(char direction) {
        switch (direction) {
            case 'N':
                output = currentPlayer.movementProcessing('N',map.getMap());
                break;

            case 'E':
                output = currentPlayer.movementProcessing('E',map.getMap());
                break;

            case 'S':
                output = currentPlayer.movementProcessing('S',map.getMap());
                break;

            case 'W':
                output = currentPlayer.movementProcessing('W',map.getMap());
                break;
        }
        return output;
    }

    /**
     * Converts the map from a 2D char array to a single string.
     *
     * @return : A String representation of the game map (for Human players), null for the Bot.
     */
    protected String look() {
        String constructor = "";

        if (currentPlayer != bot) {
            int startingXValue = currentPlayer.getXPosition() - 2;
            int startingYValue = currentPlayer.getYPosition() - 2;
            int xCount;
            int yCount = startingYValue;


            while (yCount <= startingYValue + 4) {
                xCount = startingXValue;
                while (xCount <= startingXValue + 4) {
                    //Displays H on the map where the human is without changing the map itself.
                    if (yCount == human.getYPosition() && xCount == human.getXPosition()) {
                        constructor = constructor.concat("H");
                        xCount++;
                    }
                    //Displays B on the map where the bot is without changing the map itself.
                    else if (yCount == bot.getYPosition() && xCount == bot.getXPosition()) {
                        constructor = constructor.concat("B");
                        xCount++;
                    } else {
                        try {
                            constructor = constructor.concat(Character.toString(map.getMap()[yCount][xCount]));
                        }
                        //Prints a '#' beyond the edge of the map.
                        catch (ArrayIndexOutOfBoundsException e) {
                            constructor = constructor.concat("#");
                        } finally {
                            xCount++;
                        }
                    }
                }
                //Starts a new line
                if (yCount != startingYValue + 4) {
                    constructor = constructor.concat("\n");
                }
                yCount++;
            }
            return constructor;
        }
        //Calls lookForHuman with the relative position of H if within range, otherwise returns calls with (0,0).
        else {
            if(Math.abs(bot.getXPosition() - human.getXPosition()) <= 2 &&
                    Math.abs(bot.getYPosition() - human.getYPosition()) <= 2) {
                bot.lookForHuman(bot.getYPosition() - human.getYPosition(),
                        human.getXPosition() - bot.getXPosition());
                return null;
            }
            //If human is beyond the scope of the 5X5 grid.
            else{
                bot.lookForHuman(0,0);
                return null;
            }
        }
    }

    /**
     * Processes the player's pickup command, updating the map and the player's gold amount.
     *
     * @return If the player successfully picked-up gold or not.
     */
    protected String pickup() {
        if (map.getMap()[human.getYPosition()][human.getXPosition()] == 'G') {
            human.goldIncrease(true);
            map.changeMap(human.getYPosition(),human.getXPosition());
            return "SUCCESS";
        } else {
            return "FAIL";
        }
    }

    /**
     * Quits the game, shutting down the application.
     */
    protected void quitGame() {
        if (map.getMap()[human.getYPosition()][human.getXPosition()] == 'E' &&
                (map.getGoldRequired() - human.getGoldOwned()) == 0) {
            System.out.println("WIN");
        } else {
            System.out.println("LOSE");
        }
        endFlag = false;
        System.exit(0);
    }

    /**
     * Processes the input from currentplayer.getNextAction(). Calls various methods in GameLogic to complete the task
     * and prints the result if required.
     * @param command command recieved from the user/bot. Note bot is limited to MOVE N/S/E/W and LOOK.
     */

    protected void processCommand(String command) {
        switch (command) {
            case "HELLO":
                output = hello();
                break;

            case "GOLD":
                output = gold();
                break;

            case "MOVE N":
            case "MOVE E":
            case "MOVE S":
            case "MOVE W":
                output = move(command.charAt(5));
                break;

            case "PICKUP":
                output = pickup();
                break;

            case "LOOK":
                output = look();
                break;

            case "QUIT":
                quitGame();
                break;

            case "INVALID":
                output = "INVALID";
                break;
        }
        //If the human and bot are on the same tile, quit the game.
        if(human.getXPosition() == bot.getXPosition() && human.getYPosition() == bot.getYPosition()){
            quitGame();
        }
        //Only print to the console for human goes.
        if(currentPlayer != bot && output!=null) {
            System.out.println(output);
        }
    }

    /**
     * Sets up the game. Allows users to select their map and tracks player turns, cycling between players until
     * the game is stopped.
     */
    protected void setup(){

        while(!map.mapSuccessfullyCreated()){
            String mapChoice = human.chooseMap();

            switch (mapChoice) {
                case "1":
                    map.generateMap("Map1.txt");
                    break;
                case "2":
                    map.generateMap("Map2.txt");
                    break;
                case "3":
                    map.generateMap("Map3.txt");
                    break;
                default:
                    map.generateMap(mapChoice);
                    break;
            }
        }
        //Place both players in the map.
        human.placePlayer(map.getMap(),map.displayHeight(),map.displayLength());
        bot.placePlayer(map.getMap(),map.displayHeight(),map.displayLength());
        players[0] = human;
        players[1] = bot;

        //Track player turn and call getNextAction() accordingly.
        while (gameRunning()) {
            for (Player p : players) {
                currentPlayer = p;
                processCommand(p.getNextAction());
            }
        }
    }

    public static void main(String[] args) {

        GameLogic logic = new GameLogic();
        logic.setup();
    }
}