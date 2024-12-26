import java.util.Random;
import java.util.Scanner;

public class NumberGuessing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerBound = 1;
        int upperBound = 100;
        int maxAttempts = 5;
        int score = 0;
        int round = 1;

        System.out.println("*****Welcome to Guess the Number!*****");

        while (true) {
            System.out.println("\nRound " + round);

            int randomNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
            int attempts = 0;

            while (attempts < maxAttempts) {
                System.out.print("Guess a number between " + lowerBound + " and " + upperBound + ": ");
                int guess = scanner.nextInt();
                attempts++;

                if (guess == randomNumber) {
                    System.out.println("Waoo!!! Congratulations! You guessed the number in " + attempts + " attempts.");
                    score += maxAttempts - attempts + 1; // Award points based on attempts
                    break;
                } else if (guess < randomNumber) {
                    System.out.println("Guessing Too low. Try again.");
                } else {
                    System.out.println("Guessing Too high. Try again.");
                }
            }

            if (attempts == maxAttempts) {
                System.out.println("You've reached the maximum number of attempts :)...... The number was " + randomNumber);
            }

            round++;

            System.out.print("Wanna play another round? (yes/no): ");
            String playAgain = scanner.next();
            if (!playAgain.equalsIgnoreCase("yes")) {
                break;
            }
        }

        System.out.println("\nOh No..... Game Over! Your final score is: " + score);
        scanner.close();
    }
}