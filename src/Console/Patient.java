package Console;

import java.time.LocalDate;

public class Patient extends Person
{
    private String patientId;

    public Patient(String name, String surname, LocalDate dateOfBirth, String mobileNumber, String patientId)
    {
        super(name, surname, dateOfBirth, mobileNumber);
        this.patientId = patientId;
    }

    public String getPatientId()
    {
        return patientId;
    }

    public void setPatientId(String patientId)
    {
        this.patientId = patientId;
    }
}
