package PetCareScheduler;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Pet with all necessary information and appointments
 */
public class Pet {
    private String petId;
    private String name;
    private String speciesBreed;
    private int age;
    private String ownerName;
    private String contactInfo;
    private LocalDate registrationDate;
    private List<Appointment> appointments;

    // Constructor
    public Pet(String petId, String name, String speciesBreed, int age, 
               String ownerName, String contactInfo, LocalDate registrationDate) {
        this.petId = petId;
        this.name = name;
        this.speciesBreed = speciesBreed;
        this.age = age;
        this.ownerName = ownerName;
        this.contactInfo = contactInfo;
        this.registrationDate = registrationDate;
        this.appointments = new ArrayList<>();
    }

    // Default constructor
    public Pet() {
        this.appointments = new ArrayList<>();
        this.registrationDate = LocalDate.now();
    }

    // Getters
    public String getPetId() {
        return petId;
    }

    public String getName() {
        return name;
    }

    public String getSpeciesBreed() {
        return speciesBreed;
    }

    public int getAge() {
        return age;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments); // Return copy to maintain encapsulation
    }

    // Setters
    public void setPetId(String petId) {
        this.petId = petId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeciesBreed(String speciesBreed) {
        this.speciesBreed = speciesBreed;
    }

    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Age cannot be negative");
        }
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    // Methods to manage appointments
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public boolean removeAppointment(Appointment appointment) {
        return appointments.remove(appointment);
    }

    // Override toString method
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Pet{" +
                "ID: '" + petId + "'" +
                ", Name: '" + name + "'" +
                ", Species/Breed: '" + speciesBreed + "'" +
                ", Age: " + age +
                ", Owner: '" + ownerName + "'" +
                ", Contact: '" + contactInfo + "'" +
                ", Registered: " + registrationDate.format(formatter) +
                ", Total Appointments: " + appointments.size() +
                '}';
    }
}