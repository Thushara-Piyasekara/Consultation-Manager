package Console;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Predicate;

import GUI.*;


public class WestminsterSkinConsultationManager implements SkinConsultationManager, Serializable
{
    private static ArrayList<Doctor> docArray = new ArrayList<Doctor>();
    private static ArrayList<Consultation> consultationArray = new ArrayList<Consultation>();
    private static ArrayList<Patient> patientArray = new ArrayList<Patient>();
    private static Scanner userIn = new Scanner(System.in);
    private static WestminsterSkinConsultationManager westMain = new WestminsterSkinConsultationManager();

    public static void main(String[] args)
    {

        westMain.loadFile();
//        westMain.launchGUI();

        boolean menuLoop = true;
        while (menuLoop)//  Loops the menu
        {
            try
            {
                System.out.println("""
                        100 or ADD: Add a new Doctor.
                        101 or DEL: Delete a Doctor.
                        102 or PRT: Print the existing Doctors list.
                        103 or SAV: Save to file.
                        104 or GUI: Launch GUI.
                        999 or EXT: Exit the Program.
                        """);

                System.out.print("Please select an option : ");
                String menuIn = userIn.next().toUpperCase();
                String consumer = userIn.nextLine();
                printDollars();
                switch (menuIn)
                {
                    case "100", "ADD" -> westMain.addDoctor();
                    case "101", "DEL" -> westMain.removeDoctor();
                    case "102", "PRT" -> westMain.printDoctors();
                    case "103", "SAV" -> westMain.saveToFile();
                    case "104", "GUI" -> westMain.launchGUI();
                    case "999", "EXT" ->
                    {
                        System.out.println("Exiting the program....");
                        menuLoop = false;
                    }
                    default -> System.out.println("Please enter a valid input."); //used for input validation
                }
            } catch (InputMismatchException ex)
            {
                printDollars();
                System.out.println("Invalid input!");
                String consumer = userIn.nextLine(); // This is used to consume the remaining user input from Scanner
            }
            printDollars();
        }
    }

    private static void printDollars() //Prints a line with dollar sign to divide the outputs(used for merely aesthetic purposes)
    {
        System.out.println("\n" + "$".repeat(40) + "\n");
    }

    public void addDoctor()
    {
        try
        {
            if (Doctor.getDocCount() <= 10)
            {
                boolean inValidInput = true;
                while (inValidInput)
                {
                    printDollars();

                    System.out.print("Enter the first name : ");
                    String firstName = userIn.nextLine();
                    inValidInput = validateNamesIn(firstName);
                    if (inValidInput)
                        continue;

                    System.out.print("Enter the surname : ");
                    String surname = userIn.nextLine();
                    inValidInput = validateNamesIn(surname);
                    if (inValidInput)
                        continue;

                    System.out.print("Enter the Date of Birth(like 30/09/1995) : ");
                    String dobString = userIn.nextLine();
                    LocalDate TestDate = Person.stringToLocalDate(dobString);


                    System.out.print("Enter the mobile number : ");
                    String mobileNumber = userIn.nextLine();
                    inValidInput = validateMobileNum(mobileNumber);
                    if (inValidInput)
                        continue;

                    System.out.print("Enter the Medical License Number : ");
                    String medicalLicenceNum = userIn.nextLine();

                    System.out.print("Enter the Specialisation : ");
                    String specialisation = userIn.nextLine();

                    Doctor doctor = new Doctor(firstName, surname, TestDate, mobileNumber, medicalLicenceNum, specialisation);
                    docArray.add(doctor);
                    System.out.println("Successfully Added a Doctor to the System!");
                    inValidInput = false;
                }

            } else
            {
                System.out.println("Cannot add more than 10 Doctors! Please remove a Doctor in order to add a new Doctor.");
            }

        } catch (DateTimeParseException e)
        {
            System.out.println("Invalid Date! Please use 23/05/2001 format!");
            addDoctor();
        }
    }

    public void removeDoctor()
    {
        printDoctors();
        printDollars();
        System.out.print("Enter the medical license number of the doctor you want to remove : ");
        String licenseToRemove = userIn.nextLine();
        Boolean correctLicense = false;

        for (Doctor doc : docArray)
        {
            if (doc.getMedicalLicenceNum().equals(licenseToRemove))
            {
                correctLicense = true;
            }
        }
        if (!correctLicense)
        {
            System.out.println("Wrong license number!");
        } else
        {
            Predicate<Doctor> condition = Doctor -> Doctor.getMedicalLicenceNum().equals(licenseToRemove);
            docArray.removeIf(condition);
            Doctor.deduceDocCount();
            System.out.println("Doctor Successfully Deleted!");
        }
    }

    public void printDoctors()
    {
        if (docArray.size() == 0)
        {
            System.out.println("No doctors in the list! Please add doctors first to view the list!");
        } else
        {
            Collections.sort(docArray, Doctor.DocSurNameComparator);

            System.out.printf("%-15s %-15s %-15s %-25s %s \n", "Surname", "Name", "DOB", "Medical Licence Number", "Specialisation");
            for (Doctor doc : docArray)
            {
                doc.printRecord();
            }
        }
    }

    public void saveToFile()
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(new File("Customer_Data.txt"));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(docArray);
            objectOut.writeObject(consultationArray);
            objectOut.writeObject(patientArray);

            System.out.println("-------------- Data saved successfully! --------------");
            objectOut.close();
            fileOut.close();

        } catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        } catch (IOException e)
        {
            System.out.println("IO exception");
        }
    }


    public void loadFile()
    {
        try
        {
            FileInputStream fileIn = new FileInputStream(new File("Customer_Data.txt"));
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            docArray = (ArrayList<Doctor>) objectIn.readObject();
            consultationArray = (ArrayList<Consultation>) objectIn.readObject();
            patientArray = (ArrayList<Patient>) objectIn.readObject();

            objectIn.close();
            fileIn.close();

            System.out.println("-------------- Data from previous session loaded Successfully! --------------");
        } catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        } catch (IOException e)
        {
            System.out.println("IO Error");
        } catch (ClassNotFoundException e)
        {
            System.out.println("Class not found in File");
        }
    }


    public void launchGUI()
    {
        HomeFrame GUI = new HomeFrame(docArray, consultationArray, patientArray);
        GUI.setVisible(true);
        GUI.setBounds(10, 10, 1900, 800);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GUI.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                saveToFile();
            }
        });

        System.out.println("GUI Launched Successfully!");
    }

    public static boolean validateNamesIn(String name)
    {
        char[] nameChars = name.toCharArray();
        if (nameChars.length == 0)
        {
            System.out.println("Please type a name!");
            return true;
        }
        else
        {
            for (char character : nameChars)
            {
                if (Character.isDigit(character))
                {
                    System.out.println("Names cannot have numbers!");
                    return true;
                }
                else if (Character.isWhitespace(character))
                {
                    System.out.println("Please input a name without whitespaces!");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validateMobileNum(String mobNumString)
    {
        char[] mobNumChars = mobNumString.toCharArray();
        if (mobNumChars.length == 0)
        {
            System.out.println("Please type a Mobile Number!");
            return true;
        }
        for (char character : mobNumChars)
        {
            if (Character.isWhitespace(character))
            {
                System.out.println("Please input a Mobile Number without whitespaces!");
                return true;
            }
            else if (!Character.isDigit(character))
            {
                System.out.println("Mobile Numbers cannot have letters!");
                return true;
            }
        }
        if (mobNumChars.length != 10)
        {
            System.out.println("Mobile number should have 10 numbers!");
            return true;
        }
        return false;
    }
}




