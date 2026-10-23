// ============================================================
//  PRACTICLE 1 - Part A - Task 3: Rock Paper Scissors Game using Enum
//  File: PRACTICLE_1/RockPaperScissors.java
// ============================================================

import java.util.Scanner;
import java.util.Random;

public class RockPaperScissors {
    
    enum Choice {
        ROCK, PAPER, SCISSORS;
    }
    
    enum Result {
        WIN, LOSE, DRAW;
    }
    
    public static Result determineWinner(Choice player, Choice computer) {
        if (player == computer) {
            return Result.DRAW;
        }
        
        return switch (player) {
            case ROCK -> (computer == Choice.SCISSORS) ? Result.WIN : Result.LOSE;
            case PAPER -> (computer == Choice.ROCK) ? Result.WIN : Result.LOSE;
            case SCISSORS -> (computer == Choice.PAPER) ? Result.WIN : Result.LOSE;
        };
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        
        int playerWins = 0;
        int computerWins = 0;
        int draws = 0;
        
        System.out.println("=== Rock Paper Scissors Game ===\n");
        System.out.println("Game Rules:");
        System.out.println("- Rock beats Scissors");
        System.out.println("- Scissors beats Paper");
        System.out.println("- Paper beats Rock\n");
        
        while (true) {
            System.out.println("Choose: ROCK, PAPER, or SCISSORS (or 'QUIT' to exit)");
            System.out.print("Your choice: ");
            String input = sc.next().toUpperCase();
            
            if (input.equals("QUIT")) {
                break;
            }
            
            Choice playerChoice;
            try {
                playerChoice = Choice.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("✗ Invalid choice! Please enter ROCK, PAPER, or SCISSORS.\n");
                continue;
            }
            
            // Computer makes random choice
            Choice computerChoice = Choice.values()[random.nextInt(3)];
            
            System.out.println("Computer chose: " + computerChoice);
            
            Result result = determineWinner(playerChoice, computerChoice);
            
            switch (result) {
                case WIN -> {
                    System.out.println("🎉 You WIN! " + playerChoice + " beats " + computerChoice);
                    playerWins++;
                }
                case LOSE -> {
                    System.out.println("😞 You LOSE! " + computerChoice + " beats " + playerChoice);
                    computerWins++;
                }
                case DRAW -> {
                    System.out.println("🤝 It's a DRAW!");
                    draws++;
                }
            }
            
            System.out.println("\n--- Score ---");
            System.out.println("Player: " + playerWins + " | Computer: " + computerWins + " | Draws: " + draws);
            System.out.println("-".repeat(40) + "\n");
        }
        
        System.out.println("\n=== Final Score ===");
        System.out.println("Your Wins: " + playerWins);
        System.out.println("Computer Wins: " + computerWins);
        System.out.println("Draws: " + draws);
        
        int totalGames = playerWins + computerWins + draws;
        if (totalGames > 0) {
            System.out.printf("Win Rate: %.1f%%%n", (playerWins * 100.0 / totalGames));
        }
        
        System.out.println("\nThanks for playing!");
        sc.close();
    }
}
