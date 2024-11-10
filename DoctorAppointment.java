import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Doctor {
    private String name;
    private String specialization;
    private List<String> availableSlots;

    public Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
        this.availableSlots = new ArrayList<>();
    }

    public void addSlot(String slot) {
        availableSlots.add(slot);
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public List<String> getAvailableSlots() {
        return availableSlots;
    }

    public void removeSlot(int slotIndex) {
        if (slotIndex >= 0 && slotIndex < availableSlots.size()) {
            availableSlots.remove(slotIndex);
        }
    }
}

class Patient {
    private String name;
    private int age;
    private String contact;

    public Patient(String name, int age, String contact) {
        this.name = name;
        this.age = age;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }
}

class Appointment {
    private Doctor doctor;
    private Patient patient;
    private String timeSlot;

    public Appointment(Doctor doctor, Patient patient, String timeSlot) {
        this.doctor = doctor;
        this.patient = patient;
        this.timeSlot = timeSlot;
    }

    public void display() {
        System.out.println("Appointment Details:");
        System.out.println("Doctor: " + doctor.getName() + " (" + doctor.getSpecialization() + ")");
        System.out.println("Patient: " + patient.getName());
        System.out.println("Time Slot: " + timeSlot);
    }
}

class AppointmentSystem {
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> appointments;

    public AppointmentSystem() {
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void bookAppointment(int doctorIndex, String patientName, int slotIndex) {
        if (doctorIndex < 0 || doctorIndex >= doctors.size()) {
            System.out.println("Invalid doctor selection.");
            return;
        }

        Doctor doctor = doctors.get(doctorIndex);
        Patient patient = null;

        for (Patient p : patients) {
            if (p.getName().equals(patientName)) {
                patient = p;
                break;
            }
        }

        if (patient != null && slotIndex >= 0 && slotIndex < doctor.getAvailableSlots().size()) {
            String timeSlot = doctor.getAvailableSlots().get(slotIndex);
            appointments.add(new Appointment(doctor, patient, timeSlot));
            doctor.removeSlot(slotIndex);
            System.out.println("Appointment booked successfully.");
        } else {
            System.out.println("Appointment could not be booked. Please check details and try again.");
        }
    }

    public void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            for (Appointment appt : appointments) {
                appt.display();
                System.out.println("--------------------------");
            }
        }
    }

    public void displayDoctors() {
        System.out.println("Available doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". " + doctors.get(i).getName() + " (" + doctors.get(i).getSpecialization() + ")");
        }
    }

    public Doctor getDoctorByIndex(int index) {
        if (index >= 0 && index < doctors.size()) {
            return doctors.get(index);
        }
        return null;
    }

    public void displaySlots(Doctor doctor) {
        System.out.println("Available time slots for " + doctor.getName() + ":");
        List<String> slots = doctor.getAvailableSlots();
        for (int i = 0; i < slots.size(); i++) {
            System.out.println((i + 1) + ". " + slots.get(i));
        }
    }
}

public class AppointmentBookingSystem {
    public static void main(String[] args) {
        AppointmentSystem system = new AppointmentSystem();


        Doctor doc1 = new Doctor("Dr. Smith", "Cardiologist");
        doc1.addSlot("10:00 AM");
        doc1.addSlot("11:00 AM");
        doc1.addSlot("1:00 PM");
        system.addDoctor(doc1);

        Doctor doc2 = new Doctor("Dr. Johnson", "Dermatologist");
        doc2.addSlot("9:30 AM");
        doc2.addSlot("10:30 AM");
        doc2.addSlot("11:30 AM");
        doc2.addSlot("12:30 AM");
        system.addDoctor(doc2);

        Scanner scanner = new Scanner(System.in);

        

        System.out.print("Enter patient name: ");
        String patientName = scanner.nextLine();
        System.out.print("Enter patient age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter patient contact: ");
        String contact = scanner.nextLine();


        Patient newPatient = new Patient(patientName, age, contact);
        system.addPatient(newPatient);

        system.displayDoctors();
        System.out.print("Select the doctor's serial number to book: ");
        int doctorIndex = scanner.nextInt() - 1;
        scanner.nextLine(); 

        Doctor selectedDoctor = system.getDoctorByIndex(doctorIndex);
        if (selectedDoctor == null) {
            System.out.println("Invalid doctor selection.");
            scanner.close();
            return;
        }

        system.displaySlots(selectedDoctor);
        System.out.print("Select the time slot number to book: ");
        int slotIndex = scanner.nextInt() - 1;
        scanner.nextLine(); 
        system.bookAppointment(doctorIndex, patientName, slotIndex);


        system.viewAppointments();

        scanner.close();
    }
}

