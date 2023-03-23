package Console;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consultation implements Serializable
{
    private String consultationId;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int numOfHours;
    private int cost;
    private String notes;
    private byte[] encryptedImage;
    private static int consultationCount = 0;

    public Consultation(Doctor doctor, Patient patient, LocalDateTime StartDateTime, int numOfHours, int cost, String notes,byte[] encryptedImage)
    {
        consultationCount++;
        this.consultationId =String.format("%03d", consultationCount) + doctor.getMedicalLicenceNum();
        this.doctor = doctor;
        this.patient = patient;
        this.startDateTime = StartDateTime;
        this.numOfHours = numOfHours;
        this.cost = cost;
        this.notes = notes;
        this.encryptedImage = encryptedImage;
        endDateTime = startDateTime.plusHours(numOfHours);
    }


    public String getConsultationId()
    {
        return consultationId;
    }

    public void setConsultationId(String consultationId)
    {
        this.consultationId = consultationId;
    }

    public static int getConsultationCount()
    {
        return consultationCount;
    }

    public static void setConsultationCount(int consultationCount)
    {
        Consultation.consultationCount = consultationCount;
    }

    public Doctor getDoctor()
    {
        return doctor;
    }

    public void setDoctor(Doctor doctor)
    {
        this.doctor = doctor;
    }

    public Patient getPatient()
    {
        return patient;
    }

    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }

    public String getStartTimeAsString()
    {
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedString = startDateTime.format(customFormat);
        return formattedString;
    }

    public LocalDateTime getStartDateTime()
    {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime()
    {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime)
    {
        this.endDateTime = endDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime)
    {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTimeAsString()
    {
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedString = endDateTime.format(customFormat);
        return formattedString;
    }

    public int getNumOfHours()
    {
        return numOfHours;
    }

    public void setNumOfHours(int numOfHours)
    {
        this.numOfHours = numOfHours;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public byte[] getEncryptedImage()
    {
        return encryptedImage;
    }

    public void setEncryptedImage(byte[] encryptedImage)
    {
        this.encryptedImage = encryptedImage;
    }
}
