package Console;

import java.time.LocalDate;
import java.util.Comparator;

public class Doctor extends Person
{
    private String medicalLicenceNum;
    private String specialisation;
    private static int docCount = 0;

    public Doctor(String name, String surname, LocalDate dateOfBirth,String mobileNumber,
                  String medicalLicenceNum, String specialisation)
    {
        super(name, surname, dateOfBirth, mobileNumber);
        this.medicalLicenceNum = medicalLicenceNum;
        this.specialisation = specialisation;
        docCount++;
    }

    public String getMedicalLicenceNum()
    {
        return medicalLicenceNum;
    }

    public void setMedicalLicenceNum(String medicalLicenceNum)
    {
        this.medicalLicenceNum = medicalLicenceNum;
    }

    public String getSpecialisation()
    {
        return specialisation;
    }

    public void setSpecialisation(String specialisation)
    {
        this.specialisation = specialisation;
    }

    public static Comparator<Doctor> DocSurNameComparator = new Comparator<Doctor>()
    {
        @Override
        public int compare(Doctor d1, Doctor d2)
        {
            String docName1 = d1.getSurname().toUpperCase();
            String docName2 = d2.getSurname().toUpperCase();

            return docName1.compareTo(docName2);
        }
    };

    public static int getDocCount()
    {
        return docCount;
    }

    public static void setDocCount(int docCount)
    {
        Doctor.docCount = docCount;
    }

    public static void deduceDocCount()
    {
        docCount--;
    }

    //Should be moved to somewhere else
    public void printRecord()
    {
        System.out.printf("%-15s %-15s %-15s %-25s %s \n", this.getSurname(), this.getName(),
                this.getDateOfBirthAsString(), this.medicalLicenceNum, this.specialisation);
    }

}
