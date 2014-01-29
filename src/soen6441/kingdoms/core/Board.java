/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soen6441.kingdoms.core;

/**
 * This class represents the board with 5 rows and 6 columns and a function to
 * place the Placeable(tile or castle) on the Board
 *
 * 
 */
public class Board {

    public static final int NUM_ROWS = 5;
    public static final int NUM_COLS = 6;
    public Placeable[][] slots = new Placeable[NUM_ROWS][NUM_COLS];

    //kelly 
    //to emty the board slots at the beginning of each epoch
    Board() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                slots[i][j] = null;
            }
        }
    }

    /**
     * Check if given row / col is empty
     *
     * @param i The index of the row where the object should be placed
     * @param j The index of the column where the object should be placed
     * @return true if slot is empty
     */
    public boolean isEmpty(int i, int j) {
        return slots[i][j] == null;
    }

    /**
     * Check if board is full
     *
     * @return true if board is full, false otherwise
     */
    public boolean isFull() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (slots[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    //
    /**
     * Method to place a tile or castle on the board
     *
     * @param plc The object to be placed on the board.Which could be a tile or
     * a castle
     * @param i The index of the row where the object should be placed
     * @param j The index of the column where the object should be placed
     * @return 0 If the tile was successfully placed and 1 if the tile could not
     * be placed at the specified location
     */
    public int setPlacable(Placeable plc, int i, int j) {
        //tarnum
        if (slots[i][j] == null) {
            slots[i][j] = plc;
           
            return 0;
        } else {
            System.out.println("You cannot place here.");
            return 1;

        }
    }

    public static boolean isWizardTile(Placeable p) {
        return (p instanceof Tile
                && ((Tile) p).getType().equals(Tile.WIZARD));
    }

    public  static boolean isGoldMineTile(Placeable p) {
        return (p instanceof Tile
                && ((Tile) p).getType().equals(Tile.GOLD_MINE));
    }

    public  static boolean isDragonTile(Placeable p) {
        return (p instanceof Tile
                && ((Tile) p).getType().equals(Tile.DRAGON));
    }

    public  static boolean isMountainTile(Placeable p) {
        return (p instanceof Tile
                && ((Tile) p).getType().equals(Tile.MOUNTAIN));
    }

    public  static boolean isResourceTile(Placeable p) {
        return (p instanceof Tile
                && ((Tile) p).getType().equals(Tile.RESOURCE));
    }

    public  static boolean isHazardTile(Placeable p) {
        return (p instanceof Tile
                && ((Tile) p).getType().equals(Tile.HAZARD));
    }

    /**
     * Return a string representation of this board
     *
     * @return a string representation of this board
     */
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Board[\n");
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                buffer.append(slots[i][j]);
                buffer.append("\t");
            }
            buffer.append("\n");
        }
        buffer.append(']');
        return buffer.toString();
    }

    /**
     * Get number of empty slots on the board
     *
     * @return number of empty slots on the board
     */
    public int getNumEmptySlots() {
        int count = 0;
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (slots[i][j] == null) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Get number of non-empty slots on the board
     *
     * @return number of non-empty slots on the board
     */
    public int getNumNonEmptySlots() {
        return (NUM_ROWS * NUM_COLS) - getNumEmptySlots();
    }

    /**
     * Check if board is more than half full
     *
     * @return true if board is more than half full, false otherwise
     */
    public boolean isMoreThanHalfFull() {
        return (getNumEmptySlots() > (NUM_ROWS * NUM_COLS / 2));
    }

    /**
     * Check if there is a Wizard tile orth. adjacent to the given position
     * @param row row index
     * @param col col index
     * @return true if there is a Wizard tile orth. adjacent to the given position, false otherwise
     */
    private boolean isWizardOrthAdjacentToPos(int row, int col) {
        return ( (row > 0 && isWizardTile(slots[row - 1][col])) ||
                 (row < NUM_ROWS - 1 && isWizardTile(slots[row + 1][col])) ||
                 (col > 0 && isWizardTile(slots[row][col - 1])) || 
                 (col < NUM_COLS - 1 && isWizardTile(slots[row][col + 1])) );
    }
    
    /**
     * Calculate score for a player based on the current state of the board
     *
     * @param playerId ID of the player
     * @return score for a player
     */
    public int calculateScoreForPlayer(int playerId) {
        int score = 0;
        for (int y = 0; y < NUM_ROWS; y++) {
            int s = calculateScoreForRowOrCol(playerId, y, 0, true);
           score += s;
        }
        for (int x = 0; x < NUM_COLS; x++) {
            int s = calculateScoreForRowOrCol(playerId, 0, x, false);
            score += s;
        }
        return score;
    }
    
    /**
     * Calculate score for player of a row/col
     * @param playerId ID of player we calculate score for
     * @param row row index, always 0 if we are scanning cols
     * @param col col index, always 0 if we are scanning rows
     * @param scanRow true if calculating score for player of a row, false otherwise
     * @return 
     */
    private int calculateScoreForRowOrCol(int playerId, int row, int col, boolean scanRow) {
        int score = 0;
        
        int sumRes = 0;                 // cumulated value of Resource tiles
        int sumHaz = 0;                 // cumulated value of Hazard tiles
        int sumCastleRankForPlayer = 0; // cumulated rank value of castles belong to Player we caluclate score for
        boolean sawGoldMine = false;    // saw a Gold Mine tile?
        boolean sawDragon = false;      // saw a Dragon tile?
        boolean sawMountain = false;    // saw a Mountain tile?
        
        while (true) {
            
            // Slot to be examined
            Placeable slot = slots[row][col];
            if (slot != null) {
                if (isResourceTile(slot)) {
                    sumRes += ((Tile) slot).getValue();

                } else if (isHazardTile(slot)) {
                    sumHaz += ((Tile) slot).getValue();

                } else if (slot instanceof Castle
                        && ((Castle) slot).getplayerId() == playerId) {
                    if (isWizardOrthAdjacentToPos(row, col)) {
                        // Wizard next to the castle
                        sumCastleRankForPlayer += ((Castle) slot).getRankValue() + 1;
                    } else {
                        sumCastleRankForPlayer += ((Castle) slot).getRankValue();
                    }

                } else if (isGoldMineTile(slot)) {
                    sawGoldMine = true;

                } else if (isDragonTile(slot)) {
                    sawDragon = true;

                }
                if (isMountainTile(slot)) {
                    sawMountain = true;
                }
            }
            
            // go on to the next slot
            if (scanRow) {
                col++;
            } else {
                row++;
            }
            
            // if encountered a Mountain tile, calculate a sub-total of score
            // based on what we have seen so far
            // OR
            // if reached end of row/column, calculate final score for that row/column
            if ( sawMountain ||
                 row >= NUM_ROWS ||
                 col >= NUM_COLS ) {
                if (sawDragon) {
                    sumRes = 0;
                }
                int sumResPlusHaz = sumRes + sumHaz;
                if (sawGoldMine) {
                    sumResPlusHaz *= 2;
                }
                score += sumCastleRankForPlayer * sumResPlusHaz;
                
                // Forget about what we have seen before
                sumRes = 0;
                sumHaz = 0;
                sumCastleRankForPlayer = 0;
                sawGoldMine = false;
                sawDragon = false;
                sawMountain = false;
                
                if ( row >= NUM_ROWS ||
                     col >= NUM_COLS ) {
                    break;  // stop scanning
                }
            }
        }
        
        return score;
    }    
}