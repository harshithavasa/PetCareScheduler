package PetCareScheduler;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main application class for Pet Care Scheduler
 */
public class PetCareScheduler {
    private static List<Pet> pets = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String PETS_FILE = "pets_data.txt";
    private static final String APPOINTMENTS_FILE = "appointments_data.txt";

    public static void main(String[] args) {
        System.out.println("=== Welcome to Pet Care Scheduler ===");

        // Load existing data
        loadDataFromFiles();

        boolean running = true;
        while (running) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        registerPet();
                        break;
                    case 2:
                        scheduleAppointment();
                        break;
                    case 3:
                        storeDataToFiles();
                        break;
                    case 4:
                        displayRecords();
                        break;
                    case 5:
                        generateReports();
                        break;
                    case 6:
                        storeDataToFiles(); // Auto-save before exit
                        System.out.println("Thank you for using Pet Care Scheduler. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }

    /**
     * Display main menu options
     */
    private static void displayMenu() {
        System.out.println("\n=== Pet Care Scheduler Menu ===");
        System.out.println("1. Register a Pet");
        System.out.println("2. Schedule an Appointment");
        System.out.println("3. Store Data to Files");
        System.out.println("4. Display Records");
        System.out.println("5. Generate Reports");
        System.out.println("6. Exit");
        System.out.print("Please select an option (1-6): ");
    }

    /**
     * Register a new pet with validation
     */
    private static void registerPet() {
        System.out.println("\n=== Register New Pet ===");

        try {
            // Generate unique Pet ID
            String petId;
            do {
                System.out.print("Enter Pet ID: ");
                petId = scanner.nextLine().trim().toUpperCase();

                if (petId.isEmpty()) {
                    System.out.println("Pet ID cannot be empty. Please try again.");
                    continue;
                }

                if (findPetById(petId) != null) {
                    System.out.println("Pet ID already exists. Please choose a different ID.");
                    continue;
                }
                break;
            } while (true);

            // Get pet name
            System.out.print("Enter Pet Name: ");
            String name = scanner.nextLine().trim();
            while (name.isEmpty()) {
                System.out.println("Pet name cannot be empty.");
                System.out.print("Enter Pet Name: ");
                name = scanner.nextLine().trim();
            }

            // Get species/breed
            System.out.print("Enter Species/Breed: ");
            String speciesBreed = scanner.nextLine().trim();
            while (speciesBreed.isEmpty()) {
                System.out.println("Species/Breed cannot be empty.");
                System.out.print("Enter Species/Breed: ");
                speciesBreed = scanner.nextLine().trim();
            }

            // Get age with validation
            int age;
            while (true) {
                try {
                    System.out.print("Enter Age: ");
                    age = Integer.parseInt(scanner.nextLine().trim());
                    if (age < 0) {
                        System.out.println("Age cannot be negative. Please enter a valid age.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age format. Please enter a number.");
                }
            }

            // Get owner name
            System.out.print("Enter Owner Name: ");
            String ownerName = scanner.nextLine().trim();
            while (ownerName.isEmpty()) {
                System.out.println("Owner name cannot be empty.");
                System.out.print("Enter Owner Name: ");
                ownerName = scanner.nextLine().trim();
            }

            // Get contact info
            System.out.print("Enter Contact Info: ");
            String contactInfo = scanner.nextLine().trim();
            while (contactInfo.isEmpty()) {
                System.out.println("Contact info cannot be empty.");
                System.out.print("Enter Contact Info: ");
                contactInfo = scanner.nextLine().trim();
            }

            // Set registration date to today
            LocalDate registrationDate = LocalDate.now();

            // Create and add new pet
            Pet newPet = new Pet(petId, name, speciesBreed, age, ownerName, contactInfo, registrationDate);
            pets.add(newPet);

            System.out.println("\n✓ Pet registered successfully!");
            System.out.println("Pet Details: " + newPet);

        } catch (Exception e) {
            System.out.println("Error occurred while registering pet: " + e.getMessage());
        }
    }

    /**
     * Schedule an appointment for a pet
     */
    private static void scheduleAppointment() {
        System.out.println("\n=== Schedule Appointment ===");

        if (pets.isEmpty()) {
            System.out.println("No pets registered. Please register a pet first.");
            return;
        }

        try {
            // Display all pets
            System.out.println("Available Pets:");
            for (int i = 0; i < pets.size(); i++) {
                System.out.println((i + 1) + ". " + pets.get(i).getName() + " (ID: " + pets.get(i).getPetId() + ")");
            }

            // Get pet ID
            System.out.print("\nEnter Pet ID for appointment: ");
            String petId = scanner.nextLine().trim().toUpperCase();

            Pet selectedPet = findPetById(petId);
            if (selectedPet == null) {
                System.out.println("Pet with ID " + petId + " not found.");
                return;
            }

            // Get appointment type
            System.out.println("\nAppointment Types:");
            System.out.println("1. Vet Visit");
            System.out.println("2. Vaccination");
            System.out.println("3. Grooming");
            System.out.println("4. Other");

            String appointmentType;
            while (true) {
                System.out.print("Select appointment type (1-4): ");
                try {
                    int typeChoice = Integer.parseInt(scanner.nextLine().trim());
                    switch (typeChoice) {
                        case 1: appointmentType = "Vet Visit"; break;
                        case 2: appointmentType = "Vaccination"; break;
                        case 3: appointmentType = "Grooming"; break;
                        case 4: 
                            System.out.print("Enter custom appointment type: ");
                            appointmentType = scanner.nextLine().trim();
                            while (appointmentType.isEmpty()) {
                                System.out.print("Appointment type cannot be empty: ");
                                appointmentType = scanner.nextLine().trim();
                            }
                            break;
                        default:
                            System.out.println("Invalid choice. Please select 1-4.");
                            continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            // Get appointment date and time
            LocalDateTime appointmentDateTime;
            while (true) {
                try {
                    System.out.print("Enter appointment date and time (yyyy-MM-dd HH:mm): ");
                    String dateTimeInput = scanner.nextLine().trim();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    appointmentDateTime = LocalDateTime.parse(dateTimeInput, formatter);

                    // Validate that appointment is in the future
                    if (appointmentDateTime.isBefore(LocalDateTime.now())) {
                        System.out.println("Appointment must be scheduled for a future date and time.");
                        continue;
                    }
                    break;

                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date/time format. Please use yyyy-MM-dd HH:mm (e.g., 2024-12-25 14:30)");
                }
            }

            // Get notes (optional)
            System.out.print("Enter notes (optional, press Enter to skip): ");
            String notes = scanner.nextLine().trim();

            // Create and add appointment
            Appointment newAppointment = new Appointment(appointmentType, appointmentDateTime, notes);
            selectedPet.addAppointment(newAppointment);

            System.out.println("\n✓ Appointment scheduled successfully!");
            System.out.println("Pet: " + selectedPet.getName());
            System.out.println("Appointment: " + newAppointment);

        } catch (Exception e) {
            System.out.println("Error occurred while scheduling appointment: " + e.getMessage());
        }
    }

    /**
     * Display records based on user choice
     */
    private static void displayRecords() {
        System.out.println("\n=== Display Records ===");
        System.out.println("1. All registered pets");
        System.out.println("2. All appointments for a specific pet");
        System.out.println("3. Upcoming appointments for all pets");
        System.out.println("4. Past appointment history for all pets");

        try {
            System.out.print("Select display option (1-4): ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    displayAllPets();
                    break;
                case 2:
                    displayPetAppointments();
                    break;
                case 3:
                    displayUpcomingAppointments();
                    break;
                case 4:
                    displayPastAppointments();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }    
    /**
     * Display all registered pets
     */
    private static void displayAllPets() {
        System.out.println("\n=== All Registered Pets ===");
        if (pets.isEmpty()) {
            System.out.println("No pets registered.");
            return;
        }

        for (Pet pet : pets) {
            System.out.println(pet);
        }
    }

    /**
     * Display appointments for a specific pet
     */
    private static void displayPetAppointments() {
        if (pets.isEmpty()) {
            System.out.println("No pets registered.");
            return;
        }

        System.out.print("Enter Pet ID: ");
        String petId = scanner.nextLine().trim().toUpperCase();
        Pet pet = findPetById(petId);

        if (pet == null) {
            System.out.println("Pet not found.");
            return;
        }

        System.out.println("\n=== Appointments for " + pet.getName() + " ===");
        List<Appointment> appointments = pet.getAppointments();

        if (appointments.isEmpty()) {
            System.out.println("No appointments found for this pet.");
            return;
        }

        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    /**
     * Display upcoming appointments for all pets
     */
    private static void displayUpcomingAppointments() {
        System.out.println("\n=== Upcoming Appointments ===");
        LocalDateTime now = LocalDateTime.now();
        boolean hasUpcoming = false;

        for (Pet pet : pets) {
            List<Appointment> upcomingAppointments = pet.getAppointments().stream()
                    .filter(app -> app.getDateTime().isAfter(now))
                    .sorted(Comparator.comparing(Appointment::getDateTime))
                    .collect(Collectors.toList());

            if (!upcomingAppointments.isEmpty()) {
                System.out.println("\nPet: " + pet.getName() + " (ID: " + pet.getPetId() + ")");
                for (Appointment appointment : upcomingAppointments) {
                    System.out.println("  " + appointment);
                }
                hasUpcoming = true;
            }
        }

        if (!hasUpcoming) {
            System.out.println("No upcoming appointments found.");
        }
    }

    /**
     * Display past appointments for all pets
     */
    private static void displayPastAppointments() {
        System.out.println("\n=== Past Appointments ===");
        LocalDateTime now = LocalDateTime.now();
        boolean hasPast = false;

        for (Pet pet : pets) {
            List<Appointment> pastAppointments = pet.getAppointments().stream()
                    .filter(app -> app.getDateTime().isBefore(now))
                    .sorted(Comparator.comparing(Appointment::getDateTime).reversed())
                    .collect(Collectors.toList());

            if (!pastAppointments.isEmpty()) {
                System.out.println("\nPet: " + pet.getName() + " (ID: " + pet.getPetId() + ")");
                for (Appointment appointment : pastAppointments) {
                    System.out.println("  " + appointment);
                }
                hasPast = true;
            }
        }

        if (!hasPast) {
            System.out.println("No past appointments found.");
        }
    }

    /**
     * Generate reports
     */
    private static void generateReports() {
        System.out.println("\n=== Generate Reports ===");
        System.out.println("1. Pets with upcoming appointments in the next week");
        System.out.println("2. Pets overdue for vet visit (no vet visit in last 6 months)");

        try {
            System.out.print("Select report type (1-2): ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    generateWeeklyReport();
                    break;
                case 2:
                    generateOverdueVetReport();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    /**
     * Generate report for pets with appointments in next week
     */
    private static void generateWeeklyReport() {
        System.out.println("\n=== Pets with Appointments in Next Week ===");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeek = now.plusWeeks(1);
        boolean hasAppointments = false;

        for (Pet pet : pets) {
            List<Appointment> weeklyAppointments = pet.getAppointments().stream()
                    .filter(app -> app.getDateTime().isAfter(now) && app.getDateTime().isBefore(nextWeek))
                    .sorted(Comparator.comparing(Appointment::getDateTime))
                    .collect(Collectors.toList());

            if (!weeklyAppointments.isEmpty()) {
                System.out.println("\nPet: " + pet.getName() + " (ID: " + pet.getPetId() + ")");
                System.out.println("Owner: " + pet.getOwnerName() + " | Contact: " + pet.getContactInfo());
                for (Appointment appointment : weeklyAppointments) {
                    System.out.println("  " + appointment);
                }
                hasAppointments = true;
            }
        }

        if (!hasAppointments) {
            System.out.println("No appointments scheduled for the next week.");
        }
    }

    /**
     * Generate report for pets overdue for vet visits
     */
    private static void generateOverdueVetReport() {
        System.out.println("\n=== Pets Overdue for Vet Visit ===");
        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);
        boolean hasOverdue = false;

        for (Pet pet : pets) {
            // Find the most recent vet visit
            Optional<Appointment> lastVetVisit = pet.getAppointments().stream()
                    .filter(app -> app.getAppointmentType().toLowerCase().contains("vet"))
                    .max(Comparator.comparing(Appointment::getDateTime));

            // Check if pet needs vet visit
            if (!lastVetVisit.isPresent() || lastVetVisit.get().getDateTime().isBefore(sixMonthsAgo)) {
                System.out.println("\nPet: " + pet.getName() + " (ID: " + pet.getPetId() + ")");
                System.out.println("Owner: " + pet.getOwnerName() + " | Contact: " + pet.getContactInfo());

                if (!lastVetVisit.isPresent()) {
                    System.out.println("Last Vet Visit: Never");
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    System.out.println("Last Vet Visit: " + lastVetVisit.get().getDateTime().format(formatter));
                }
                hasOverdue = true;
            }
        }

        if (!hasOverdue) {
            System.out.println("All pets are up to date with vet visits.");
        }
    }

    /**
     * Store pet and appointment data to files
     */
    private static void storeDataToFiles() {
        try {
            // Store pets data
            try (PrintWriter petWriter = new PrintWriter(new FileWriter(PETS_FILE))) {
                for (Pet pet : pets) {
                    petWriter.println(pet.getPetId() + "|" + pet.getName() + "|" + pet.getSpeciesBreed() + 
                                     "|" + pet.getAge() + "|" + pet.getOwnerName() + "|" + pet.getContactInfo() + 
                                     "|" + pet.getRegistrationDate());
                }
            }

            // Store appointments data
            try (PrintWriter appointmentWriter = new PrintWriter(new FileWriter(APPOINTMENTS_FILE))) {
                for (Pet pet : pets) {
                    for (Appointment appointment : pet.getAppointments()) {
                        appointmentWriter.println(pet.getPetId() + "|" + appointment.getAppointmentType() + 
                                                "|" + appointment.getDateTime() + "|" + appointment.getNotes());
                    }
                }
            }

            System.out.println("\n✓ Data saved successfully to files.");

        } catch (IOException e) {
            System.out.println("Error saving data to files: " + e.getMessage());
        }
    }

    /**
     * Load pet and appointment data from files (private method)
     */
    private static void loadDataFromFiles() {
        try {
            // Load pets data
            File petsFile = new File(PETS_FILE);
            if (petsFile.exists()) {
                try (BufferedReader petReader = new BufferedReader(new FileReader(petsFile))) {
                    String line;
                    while ((line = petReader.readLine()) != null) {
                        String[] parts = line.split("\\|");
                        if (parts.length == 7) {
                            Pet pet = new Pet(parts[0], parts[1], parts[2], 
                                            Integer.parseInt(parts[3]), parts[4], 
                                            parts[5], LocalDate.parse(parts[6]));
                            pets.add(pet);
                        }
                    }
                }
            }

            // Load appointments data
            File appointmentsFile = new File(APPOINTMENTS_FILE);
            if (appointmentsFile.exists()) {
                try (BufferedReader appointmentReader = new BufferedReader(new FileReader(appointmentsFile))) {
                    String line;
                    while ((line = appointmentReader.readLine()) != null) {
                        String[] parts = line.split("\\|");
                        if (parts.length >= 3) {
                            Pet pet = findPetById(parts[0]);
                            if (pet != null) {
                                String notes = parts.length > 3 ? parts[3] : "";
                                Appointment appointment = new Appointment(parts[1], 
                                                                        LocalDateTime.parse(parts[2]), notes);
                                pet.addAppointment(appointment);
                            }
                        }
                    }
                }
            }

            if (pets.size() > 0) {
                System.out.println("Loaded " + pets.size() + " pets from existing data files.");
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Note: Could not load existing data files. Starting with empty database.");
        }
    }

    /**
     * Find pet by ID
     */
    private static Pet findPetById(String petId) {
        return pets.stream()
                .filter(pet -> pet.getPetId().equalsIgnoreCase(petId))
                .findFirst()
                .orElse(null);
    }
}