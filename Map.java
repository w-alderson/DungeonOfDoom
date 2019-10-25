import java.io.*;
/**
 * Reads and contains in memory the map of the game. Adapted from Map class on Moodle.
 *
 */
public class Map {

    //Representation of the map.
    private char[][] map;
    private String mapName;
    private int goldRequired;

    //Counter for map reader.
    private int i;
    //Counter for map reader.
    private int j;

    private boolean mapCreated;
    private int height;
    private int length;
    private String line;


    /**
     * Constructor method.
     */
    public Map() {

        mapCreated = false;
        i = 0;
    }

    /**
     * Takes the input from GameLogic to read the file first line by line to derermine the map name, gold required and
     * length and height of the map itself, then a second BufferedReader to scan character by charecter through the map.
     * @param filename the filename chosen (e.g. Map1.txt) or the file path.
     */

    protected void generateMap(String filename) {

        //Finding the name, gold required, height and length of the map.
        try {
            BufferedReader sizeMap = new BufferedReader(new FileReader(filename));

            mapName = sizeMap.readLine().replaceAll("name ", "");

            goldRequired = Integer.parseInt(Character.toString(sizeMap.readLine().charAt(4)));

            //Calculate the width of the map using the first line
            length = sizeMap.readLine().length();

            height = 1;

            //Calculate the height
            while (sizeMap.readLine() != null) {
                height++;
            }

            map = new char[height][length];

            sizeMap.close();
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found.");
        } catch (IOException e) {
            System.err.println("Error.");
            return;
        }


        //Writing the map itself to a 2D Char array.
        try {
            BufferedReader constructMap = new BufferedReader(new FileReader(filename));

            constructMap.readLine(); // Skips map name
            constructMap.readLine(); // Skips gold required

            while (i < height) {
                j = 0;
                line = constructMap.readLine();
                while (j < length) {
                    map[i][j] = line.charAt(j);
                    System.out.print(map[i][j]);
                    j++;
                }
                System.out.print("\n");
                i++;
            }
            constructMap.close();
            mapCreated = true;
            System.out.println("Map Generated! Good Luck!");
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found.");
        } catch (IOException e) {
            System.err.println("Error generating map.");
            return;
        }
    }

    /**
     * @return : Gold required to exit the current map.
     */
    protected int getGoldRequired() {
        return goldRequired;
    }

    /**
     * @return : The map as stored in memory.
     */
    protected char[][] getMap() {
        return map;
    }

    /**
     * @return The length of the map if the map has been created, or 0 otherwise.
     */

    protected int displayLength() {
        if (mapCreated) {
            return length;
        } else {
            return 0;
        }
    }

    /**
     * @return The height of the map if the map has been created, or 0 otherwise.
     */

    protected int displayHeight() {
        if (mapCreated) {
            return height;
        } else {
            return 0;
        }
    }

    /**
     * @return True if the map has been created.
     */

    protected boolean mapSuccessfullyCreated() {
        return mapCreated;
    }

    /**
     * Mutator method. Changes any map tile to '.'.
     * @param y The y coordinate of the tile to be changed.
     * @param x The x coordinate of the tile to be changed.
     */
    protected void changeMap(int y, int x) {
       map[y][x] = '.';
    }

}