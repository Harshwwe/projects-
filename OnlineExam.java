import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class User {
    String username;
    String password;
    String name;

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
}

class Question {
    String text;
    List<String> options;
    int correctIndex;

    public Question(String text, List<String> options, int correctIndex) {
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
    }
}

public class OnlineExam {
    static User currentUser;
    static List<User> users = new ArrayList<>();
    static List<Question> questions = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static Timer timer;

    public static void main(String[] args) {
        users.add(new User("user", "pass", "Test User"));
        questions.add(new Question("2 + 2 = ?", List.of("3", "4", "5"), 1));

        System.out.println("Welcome to Online Exam");
        login();
        if (currentUser != null) showMenu();
    }

    static void login() {
        System.out.print("Username: ");
        String u = input.nextLine();
        System.out.print("Password: ");
        String p = input.nextLine();

        for (User user : users) {
            if (user.username.equals(u) && user.password.equals(p)) {
                currentUser = user;
                System.out.println("Login successful!");
                return;
            }
        }
        System.out.println("Invalid.");
        login(); // Retry login
    }

    static void showMenu() {
        while (true) {
            System.out.println("\n1. Take Exam\n2. Update Profile\n3. Logout");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(input.nextLine());

            switch (choice) {
                case 1: takeExam(); break;
                case 2: updateProfile(); break;
                case 3: logout(); return;
                default: System.out.println("Invalid.");
            }
        }
    }

    static void takeExam() {
        int score = 0;
        startTimer(questions.size() * 5); // 5 seconds/question

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\n" + (i + 1) + ". " + q.text);
            for (int j = 0; j < q.options.size(); j++)
                System.out.println((j + 1) + ". " + q.options.get(j));

            System.out.print("Answer: ");
            try{
                int ans = Integer.parseInt(input.nextLine()) - 1;
                if (ans == q.correctIndex) score++;
            } catch (NumberFormatException e){
                System.out.println("Invalid Input.");
            }
        }
        timer.cancel();
        System.out.println("\nScore: " + score + "/" + questions.size());
    }

    static void startTimer(int seconds) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("\nTime's up!");
                System.exit(0); // Auto-submit
            }
        }, seconds * 1000);
    }

    static void updateProfile() {
        System.out.print("New Name: ");
        currentUser.name = input.nextLine();
        System.out.print("New Password: ");
        currentUser.password = input.nextLine();
        System.out.println("Updated!");
    }

    static void logout() {
        currentUser = null;
        System.out.println("Logged out.");
        login(); // Back to login
    }
}