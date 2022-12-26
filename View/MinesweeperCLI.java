package View;
import java.util.Scanner;

import Model.Cell;
import Model.Minesweeper;
import Model.GameState;
import Model.MinesweeperException;
public class MinesweeperCLI {
    private final static int ROWS = 4;
    private final static int COLS = 4;
    private final static int MINE_COUNT = 2;
    private final static String RESET_MESSAGE = "Resetting to a new game...";
    private static Minesweeper game;
    
    /**
     * Prints a help message with the available commands.
     */
    private static void help() {
        System.out.println(
            "Mines: " + MINE_COUNT + 
            "\nCommands:" +
            "\n  help - displays this message" +
            "\n  pick <row> <col> - uncovers cell at <row> <col>" +
            "\n  hint - displays a safe selection" +
            "\n  solve - executes all moves to solve the game" +
            "\n  reset - resets to a new game" +
            "\n  quit - quits the game"
        );
    }

    /**
     * Executes a command based on the passed tokens
     * @param tokens array of Strings, seperated by ' ' characters
     * @return true if the command is valid, false otherwise
     */
    private static boolean executeCommand(String[] tokens) {
        boolean isInvalidComand = false;
        switch (tokens[0]) {
            case "pick":
                try {
                    int row = Integer.parseInt(tokens[1]);
                    int col = Integer.parseInt(tokens[2]);
                    game.makeSelection(new Cell(row, col));
                }
                // Cell has already been uncovered or Cell is out of board bounds
                catch (MinesweeperException e) {
                    System.out.println(e.getMessage());
                }
                // Invalid command was entered
                catch (Exception e) {
                   isInvalidComand = true;
                }
                if (game.getGameState() == GameState.LOST || game.getGameState() == GameState.WON) {
                    System.out.println(game.getGameState());
                    System.out.println(game);
                    System.out.println(RESET_MESSAGE);
                    game.initialize();
                }
                break;
            case "help": 
                help();
                break;
            case "hint":
                System.out.println("Give " + game.getPossibleSelections().toArray()[0] + " a try");
                break;
            case "reset":
                System.out.println(RESET_MESSAGE);
                game.initialize();
                break;
            default: 
                isInvalidComand = true;
        }
        return isInvalidComand;
    }

    

    public static void main(String[] args) throws MinesweeperException  {
        try (Scanner input = new Scanner(System.in)) {
            game = new Minesweeper(ROWS, COLS, MINE_COUNT);
            System.out.println("Welcome to Minesweeper!!!");
            help();
            while (true) {
                System.out.println(game);
                System.out.println("\nMoves: " + game.getMoveCount());
                System.out.print("\nEnter Command: ");
                String command = input.nextLine();
                // end program if user enters 'quit'
                if (command.equals("quit")) {
                    break;
                }
                String[] tokens = command.split(" ");
                boolean isInvalidCommand = executeCommand(tokens);
                // If invalid command is made (meaning no command was executed), inform user
                if (isInvalidCommand) {
                    System.out.println("Invalid Command");
                }
            }
        }
    }
}

    