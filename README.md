====== Pet Care Scheduler ======
A Java console application to register pets, schedule and manage appointments, persist data to files, display records, and generate simple reports. Built with clean OOP design, encapsulation, validation, and Java File I/O.

Features
Register pets with a unique ID, validated age, and automatic registration date.

Schedule appointments with validated types and future date-time checks.

Persist data to files and load on startup.

Display:

All registered pets

All appointments for a specific pet

Upcoming appointments for all pets

Past appointment history for each pet

Generate reports:

Pets with appointments in the next 7 days

Pets overdue for a vet visit (no vet visit in 6+ months)

Robust input validation and error handling.

Tech Stack
Java 8+ (Recommended: Java 11 or later)

Standard libraries only:

java.time (LocalDate, LocalDateTime, DateTimeFormatter)

java.io (File I/O)

java.util (Collections, Scanner)

Project Structure
Appointment.java — Appointment model (type, date-time, notes) with encapsulation and toString.

Pet.java — Pet model (ID, name, species/breed, age, owner, contact, registration date, appointments) with encapsulation and toString.

PetCareScheduler.java — Console application: menu, input handling, file I/O, validations, and reports.

pets_data.txt — Saved pet records (pipe-delimited).

appointments_data.txt — Saved appointment records (pipe-delimited).

Getting Started
Prerequisites

Java JDK 8+ installed and available in PATH (javac, java).

Terminal/Command Prompt.

Compile

Option A: Compile all at once

javac *.java

Option B: Compile individually

javac Appointment.java

javac Pet.java

javac PetCareScheduler.java

Run

java PetCareScheduler

If using packages, run with the correct classpath and fully qualified name.

Usage Guide
Main Menu

Register a Pet

Schedule an Appointment

Store Data to Files

Display Records

Generate Reports

Exit

Register a Pet

Required: Unique Pet ID, Name, Species/Breed, Age (non-negative), Owner Name, Contact Info.

Registration date is auto-set to today.

Schedule an Appointment

Choose a pet by Pet ID.

Select type: Vet Visit, Vaccination, Grooming, or Other.

Enter date-time in format: yyyy-MM-dd HH:mm.

Must be a future date-time.

Optional notes.

Display Records

All registered pets

All appointments for a specific pet

Upcoming appointments (future)

Past appointment history

Reports

Next 7 days: Pets with upcoming appointments

Overdue vet: Pets with no vet visit in the last 6 months

Data Persistence
Files

pets_data.txt:

Format: petId|name|speciesBreed|age|ownerName|contactInfo|registrationDate

appointments_data.txt:

Format: petId|appointmentType|dateTime|notes

Load on Startup

The application attempts to load from both files if present.

Invalid/missing files are handled gracefully.

Save Manually or on Exit

Option 3 saves data immediately.

Exiting (option 6) also triggers save.

Error Handling and Validation
Pet ID duplication check.

Empty field checks for required inputs.

Age must be a non-negative integer.

Appointment must be in the future.

Date-time parsing with clear prompts and format guidance.

File I/O exceptions handled with user-friendly messages.

Date/Time Conventions
Registration date: LocalDate

Appointment date-time: LocalDateTime

Input/Output format: yyyy-MM-dd HH:mm

Comparisons use LocalDateTime.now() semantics.

====== Example Session ======
Menu

=== Pet Care Scheduler Menu ===

Register a Pet

Schedule an Appointment

Store Data to Files

Display Records

Generate Reports

Exit

Please select an option (1-6):

Register

Enter Pet ID: DOG001

Enter Pet Name: Buddy

Enter Species/Breed: Golden Retriever

Enter Age: 3

Enter Owner Name: John Smith

Enter Contact Info: 555-0123

✓ Pet registered successfully!

Pet Details: Pet{ID: 'DOG001', Name: 'Buddy', Species/Breed: 'Golden Retriever', Age: 3, Owner: 'John Smith', Contact: '555-0123', Registered: 2025-08-27, Total Appointments: 0}

Schedule

Enter Pet ID for appointment: DOG001

Select appointment type (1-4): 1

Enter appointment date and time (yyyy-MM-dd HH:mm): 2025-09-15 10:30

Enter notes (optional): Annual checkup

✓ Appointment scheduled successfully!

====== Troubleshooting ======
Package name mismatch

Error: The declared package "" does not match the expected package "PetCareScheduler"

Fix: Ensure all files share the same package line and folder structure, or remove package lines and keep all files in one folder.

Public class/file mismatch

Error: class Appointment is public, should be declared in a file named Appointment.java

Fix: Ensure the file name exactly matches the public class name.

Type not found

Error: Appointment cannot be resolved to a type

Fix: Confirm Appointment.java is compiled, in the same package, and on classpath; rebuild the project.

Undefined getter

Error: The method getName() is undefined for the type Pet

Fix: Ensure Pet has public String getName() and rebuild; check for import collisions/stale classes.
