import java.io.*;

/**
 * Subclass to Player. Runs the game with a human player and contains code needed to read inputs.
 * Adapted from HumanPlayer on Moodle.
 */

public class HumanPlayer extends Player {

    private String userInput;
    private String userMapChoice;
    private int humanGoldOwned;
    private String[] acceptedCommands = new String[] {"HELLO","GOLD","MOVE N","MOVE E",
                                                      "MOVE S", "MOVE W", "PICKUP","LOOK","QUIT"};
    private String[] possibleMaps = new String[] {"Very small Labyrinth of Doom","Scary scary Dungeon",
                                                  "Pro Dungeon"};
    private int counter;

    /**
     * Constructor method for HumanPlayer
     */
    public HumanPlayer() {

        super();
        humanGoldOwned = 0;

    }

    /**
     * Allows the human player to choose the map.
     * @return Choice selected by player.
     */

    protected String chooseMap(){
        counter = 1;
        System.out.println("Please choose from the following maps: ");
        for(String s : possibleMaps) {
            System.out.println(counter + ". \"" + s + "\"");
            counter++;
        }
        System.out.println(counter+". Import your own map");
        try {
            BufferedReader readerMap = new BufferedReader(new InputStreamReader(System.in));
            userMapChoice = readerMap.readLine().trim();
            try {
                if (Integer.parseInt(userMapChoice.trim()) < counter) {
                    return userMapChoice;
                }
                else if (Integer.parseInt(userMapChoice.trim()) == counter){
                    System.out.println("Please enter the file location and name of the map.");
                    BufferedReader fileLocation = new BufferedReader(new InputStreamReader(System.in));
                    return fileLocation.readLine();

                }
                else {
                    System.out.println("Please choose a number 1 to " + (counter ));
                    return "retry";
                }
            }
            catch(ArithmeticException e){
                System.out.println("Please choose a number 1 to " + (counter ));
                return "retry";
                }
            catch(NumberFormatException e){
                System.out.println("Please choose a number 1 to " + (counter ));
                return "retry";
            }
        }
        catch (IOException e){
            return null;
        }



    }
    /**
     * Processes the command. It should return a reply in form of a String, as the protocol dictates.
     * Otherwise it should return the string "Invalid".
     *
     * @return : Processed output or Invalid if the @param command is wrong.
     */
    protected String getNextAction() {
        try {
            //Accept input from the command-line
            BufferedReader readerMove = new BufferedReader(new InputStreamReader(System.in));
            userInput = readerMove.readLine().trim().toUpperCase();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return null;
        }
        //Checks the input is a valid command using acceptedCommands, returns "INVALID" otherwise.
        for (String s:acceptedCommands){
            if (userInput.equals(s)){
                return userInput;
            }
        }
        return "INVALID";
    }

    /**
     * Accessor method.
     * @return The gold owned by the human player.
     */

    protected int getGoldOwned(){
        return humanGoldOwned;
    }

    /**
     * Mutator method, increases the amount of gold owned by 1.
     * @param successOrFail, only increases gold if the turn was a success in GameLogic.
     */
    protected void goldIncrease(boolean successOrFail){
        if(successOrFail) {
            humanGoldOwned++;
        }
    }

}