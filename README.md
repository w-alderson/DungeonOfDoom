# DungeonOfDoom

## Introduction:
Dungeon of Doom involves a player moving around a selected map picking up gold and avoiding a computer controlled player before exiting the map.

## Instillation:
To install and run the game, make sure you include each of the .java files (GameLogic.java, Player.java, HumanPlayer.java, Bot.java and Map.java) and the .txt files containing the maps (currently Map1.txt, Map2.txt, Map3.txt). The number of maps may be increased by altering the setup method in GameLogic to include a choice for a third named map and the possibleMaps array in HumanPlayer to include the name of the additional map. Additional maps should contain the name of the map on the first line and the gold required to win on the second line in the form:
'win X', where X is the amount of gold required to win.
It is important that all .java and .txt files are placed in the same directory. The game may be run calling GameLogic.java using your java compiler of choice.

## How to Play:
Firstly, select your map from the pre-installed maps, or select to play using your own map, entering the path to your .txt file.
You may play using the following commands:
>'HELLO' - Displays the gold currently held by you, the player.<br/>'GOLD' - Displays how much gold is required to complete the level.<br/>'PICKUP' - Picks up gold if you are on a gold tile. Note, the program will respond if this pickup was a success or a failure.<br/>'LOOK' - Displays a 5X5 tile view of the map around you. Note, the human player is displayed as 'H' and the computer controlled bot is displayed as 'B'.<br/>'QUIT' - Ends the game. If you are on an exit tile and have the required amount of gold, you have won, otherwise you have lost andDun the game is over. 

Good Luck!

## Program Structure:
The program itself is made of 5 classes. GameLogic creates the main method and calls the player classes to initiate the game. Inputs are then accepted from the user (through HumanPlayer and Map) to navigate the game. These inputs are further processed in the processCommand method, calling other methods within GameLogic, printing their outcome if desired.

Player is an abstract superclass which determines the nature of both HumanPlayer and BotPlayer. Player introduces placePlayer, which places players at a random location on the map (excluding forbidden tiles such as '#' and 'G') and ensures that two players are not placed on the same tile. This superclass also includes accessor methods such as getXposition and getYPosition to allow other classes to gain access to these fields, meaning each players location is stored solely in this class and subclasses of this class. The movement processing method checks if an attempted movement (called by GameLogic) is acceptable. It returns SUCCESS and updates the respective players location if the move is legal, or returns FAIL for illegal moves. Finally, Player introduces an abstract method getNextAction which is called by GameLogic.

HumanPlayer extends Player. It contains a chooseMap method which allows the user to select from pre-installed maps or choose another, requesting a location. The getNextAction method (as required by the superclass) allows the user to enter their move and is read using a BufferedReader. It then iterates through an array of allowed commands, and feeds the input to GameLogic only if the command is legal. In every other case, INVALID is returned, which will be printed in GameLogic. This class also has an accessor method, getGoldOwned, called by GameLogic when a user types 'GOLD' or quits the game. The mutator method goldIncreased is called by GameLogic when the user successfully picks up gold.

BotPlayer extends player. It contains the getNextAction method which returns ‘LOOK' every fifth go. This calls the look method in GameLogic, which calls lookForHuman with the parameters x and y, the relative location of human to bot (entering 0,0 if the human is beyond the scope of the look method). This then maps out the next 4 turns for bot, moving towards the location of human at the last look. If human is out of the scope of look for bot, bot moves in a random direction until look is next called. 

The Map class contains the method generateMap. This is called by GameLogic when a user selects a map in the setup. It uses multiple BufferedReaders to first determines the name of the map, the gold required and the length and height of the map itself. It then uses a second bufferedReader to read the .txt file to a 2D char array. The class contains multiple accessor methods, getGoldRequired, getMap, displayLength, displayHeight and mapSuccessfullyCreated which are all called in GameLogic. The mutator method changeMap is called by GameLogic in the event that gold is successfully picked up, removing the gold from the map. 

Throughout the development of this program, measures have been taken to ensure user inputs are handled correctly, using try/catch statements where appropriate when erroneous information may have been entered.
