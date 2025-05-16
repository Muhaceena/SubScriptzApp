import java.util.*;

class User {
    private String name;
    private String email;
    private String password;
    private List<Subscription> subscriptions = new ArrayList<>();
    private List<String> paymentHistory = new ArrayList<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public List<String> getPaymentHistory() {
        return paymentHistory;
    }

    public void subscribe(Subscription subscription) {
        subscriptions.add(subscription);
        paymentHistory.add(subscription.getPlan().getName() + " - " + subscription.getPlan().getPrice());
    }

    public void cancelSubscription(int index) {
        if (index >= 0 && index < subscriptions.size()) {
            subscriptions.remove(index);
        }
    }
}

class Plan {
    private final String name;
    private final int price;

    public Plan(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

class Subscription {
    private final Plan plan;
    private final String startDate;

    public Subscription(Plan plan, String startDate) {
        this.plan = plan;
        this.startDate = startDate;
    }

    public Plan getPlan() {
        return plan;
    }

    public String getStartDate() {
        return startDate;
    }
}

public class SubScriptzzz {
    private static final Scanner sc = new Scanner(System.in);
    private static final Map<Integer, List<Plan>> categoryPlans = new HashMap<>();
    private static final Map<Integer, String> categoryNames = new HashMap<>();
    private static final List<User> registeredUsers = new ArrayList<>();

    public static void main(String[] args) {
        initializePlans();

        while (true) {
            System.out.println("==============================");
            System.out.println("Welcome to SubScriptz!");
            System.out.println("1. Register\n2. Login\n3. Exit");
            System.out.print("Choose an option: ");
            int option = sc.nextInt(); sc.nextLine();

            if (option == 1) {
                registerUser();
            } else if (option == 2) {
                User user = loginUser();
                if (user != null) {
                    mainMenu(user);
                }
            } else {
                System.out.println("Goodbye!");
                return;
            }
        }
    }

    public static void registerUser() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("Invalid email format.");
            return;
        }
        for (User u : registeredUsers) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Email already registered.");
                return;
            }
        }
        System.out.print("Password: ");
        String pass = sc.nextLine();
        registeredUsers.add(new User(name, email, pass));
        System.out.println("Registered successfully! Please login to continue. \n");
    }

    public static User loginUser() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        for (User u : registeredUsers) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(pass)) {
                System.out.println("Login successful.\n");
                return u;
            }
        }
        System.out.println("Invalid email or password.\n");
        return null;
    }

    public static void mainMenu(User user) {
        while (true) {
            System.out.println("\n------ Main Menu ------");
            System.out.println("1. View Categories\n2. Subscribe\n3. My Subscriptions\n4. Cancel Subscription\n5. Payment History\n6. Profile Settings\n7. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1 -> viewCategories();
                case 2 -> subscribe(user);
                case 3 -> viewMySubscriptions(user);
                case 4 -> cancelSubscription(user);
                case 5 -> viewPaymentHistory(user);
                case 6 -> profileSettings(user);
                case 7 -> {
                    System.out.println("Logging out...\n");
                    return;
                }
                default -> System.out.println("Invalid option.\n");
            }
        }
    }

    public static void initializePlans() {
        categoryNames.put(1, "Magazines");
        categoryNames.put(2, "Software");
        categoryNames.put(3, "Music");
        categoryNames.put(4, "Video Streaming");
        categoryNames.put(5, "Fitness");
        categoryNames.put(6, "Gaming");
        categoryNames.put(7, "Books");
        categoryNames.put(8, "News");

        categoryPlans.put(1, Arrays.asList(
                new Plan("Time Magazine", 100),
                new Plan("National Geographic", 150),
                new Plan("Reader's Digest", 80)));

        categoryPlans.put(2, Arrays.asList(
                new Plan("CodePro IDE", 499),
                new Plan("WriterPro", 199),
                new Plan("DesignKit", 299)));

        categoryPlans.put(3, Arrays.asList(
                new Plan("Spotify Premium", 119),
                new Plan("Apple Music", 120),
                new Plan("YouTube Music", 99)));

        categoryPlans.put(4, Arrays.asList(
                new Plan("Netflix", 499),
                new Plan("Hotstar", 399),
                new Plan("Amazon Prime Video", 299)));

        categoryPlans.put(5, Arrays.asList(
                new Plan("FitOn Pro", 149),
                new Plan("CureFit", 199),
                new Plan("Nike Training Club", 129)));

        categoryPlans.put(6, Arrays.asList(
                new Plan("Xbox Game Pass", 699),
                new Plan("PlayStation Plus", 749),
                new Plan("Steam Subscription", 399)));

        categoryPlans.put(7, Arrays.asList(
                new Plan("Kindle Unlimited", 150),
                new Plan("Audible", 199),
                new Plan("Scribd", 129)));

        categoryPlans.put(8, Arrays.asList(
                new Plan("NY Times Digital", 100),
                new Plan("The Hindu ePaper", 99),
                new Plan("BBC News Premium", 89)));
    }

    public static void viewCategories() {
        System.out.println("\nAvailable Categories:");
        for (Map.Entry<Integer, String> entry : categoryNames.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }

    public static void subscribe(User user) {
        viewCategories();
        System.out.print("Select category number: ");
        int cat = sc.nextInt(); sc.nextLine();

        if (!categoryPlans.containsKey(cat)) {
            System.out.println("Invalid category.");
            return;
        }

        List<Plan> plans = categoryPlans.get(cat);
        for (int i = 0; i < plans.size(); i++) {
            System.out.println((i + 1) + ". " + plans.get(i).getName() + " - " + plans.get(i).getPrice());
        }
        System.out.print("Select a plan: ");
        int planIndex = sc.nextInt() - 1; sc.nextLine();

        if (planIndex >= 0 && planIndex < plans.size()) {
            Plan selected = plans.get(planIndex);
            user.subscribe(new Subscription(selected, new Date().toString()));
            System.out.println("Subscribed to: " + selected.getName());
        } else {
            System.out.println("Invalid plan.");
        }
    }

    public static void viewMySubscriptions(User user) {
        List<Subscription> subs = user.getSubscriptions();
        if (subs.isEmpty()) {
            System.out.println("No subscriptions found.");
            return;
        }
        for (int i = 0; i < subs.size(); i++) {
            Subscription s = subs.get(i);
            System.out.println((i + 1) + ". " + s.getPlan().getName() + " - Subscribed on: " + s.getStartDate());
        }
    }

    public static void cancelSubscription(User user) {
        viewMySubscriptions(user);
        System.out.print("Enter subscription number to cancel: ");
        int idx = sc.nextInt() - 1; sc.nextLine();
        user.cancelSubscription(idx);
        System.out.println("Subscription cancelled.");
    }

    public static void viewPaymentHistory(User user) {
        List<String> history = user.getPaymentHistory();
        if (history.isEmpty()) {
            System.out.println("No payments found.");
            return;
        }
        for (String entry : history) {
            System.out.println(entry);
        }
    }

    private static void profileSettings(User user) {
        System.out.println("1. Change Email\n2. Change Password");
        int opt = sc.nextInt(); sc.nextLine();

        if (opt == 1) {
            System.out.print("New email: ");
            user.setEmail(sc.nextLine());
            System.out.println("Email updated.");
        } else if (opt == 2) {
            System.out.print("New password: ");
            user.setPassword(sc.nextLine());
            System.out.println("Password updated.");
        }
    }
}
