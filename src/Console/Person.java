package Console;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Person implements Serializable
{
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String mobileNumber;

    public Person(String name, String surname, LocalDate dateOfBirth, String mobileNumber)
    {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getFullName()
    {
        return name + " " + surname;
    }

    public String getDateOfBirthAsString()
    {
        return dateOfBirth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public void setDateOfBirth(LocalDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString()
    {
        return "Console.Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
    }

    public static LocalDate stringToLocalDate(String userInput) {

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(userInput, dateFormat);
    }
}
