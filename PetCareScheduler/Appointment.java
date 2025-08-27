package PetCareScheduler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an appointment for a pet
 */
public class Appointment {
    private String appointmentType;
    private LocalDateTime dateTime;
    private String notes;

    // Constructor
    public Appointment(String appointmentType, LocalDateTime dateTime, String notes) {
        this.appointmentType = appointmentType;
        this.dateTime = dateTime;
        this.notes = notes;
    }

    // Default constructor
    public Appointment() {
        this.notes = "";
    }

    // Getters
    public String getAppointmentType() {
        return appointmentType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getNotes() {
        return notes;
    }

    // Setters
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Override toString method
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Appointment{" +
                "Type: '" + appointmentType + "'" +
                ", Date & Time: " + dateTime.format(formatter) +
                ", Notes: '" + (notes.isEmpty() ? "None" : notes) + "'" +
                '}';
    }
}