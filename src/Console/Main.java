//package Console;
//
//import java.util.InputMismatchException;
//import java.util.Scanner;
//
//public class Main
//{
//    private static Scanner userIn = new Scanner(System.in);
//
//    public static void main(String[] args)
//    {
//        WestminsterSkinConsultationManager westMain = new WestminsterSkinConsultationManager();
//
//        westMain.loadFile();
//        westMain.launchGUI();
//
//        boolean menuLoop = true;
//        while (menuLoop)//  Loops the menu
//        {
//            try
//            {
//                System.out.println("""
//                        100 or ADD: Add a new Doctor.
//                        101 or DEL: Delete a Doctor.
//                        102 or PRT: Print the existing Doctors list.
//                        103 or SAV: Save to file.
//                        104 or GUI: Launch GUI.
//                        999 or EXT: Exit the Program.
//                        """);
//
//                System.out.print("Please select an option : ");
//                String menuIn = userIn.next().toUpperCase();
//                String consumer = userIn.nextLine();
//               westMain.printDollars();
//                switch (menuIn)
//                {
//                    case "100", "ADD" -> westMain.addDoctor();
//                    case "101", "DEL" -> westMain.removeDoctor();
//                    case "102", "PRT" -> westMain.printDoctors();
//                    case "103", "SAV" -> westMain.saveToFile();
//                    case "104", "GUI" -> westMain.launchGUI();
//                    case "999", "EXT" ->
//                    {
//                        System.out.println("Exiting the program....");
//                        menuLoop = false;
//                    }
//                    default -> System.out.println("Please enter a valid input."); //used for input validation
//                }
//            } catch (InputMismatchException ex)
//            {
//                westMain.printDoctors();
//                System.out.println("Invalid input!");
//                String consumer = userIn.nextLine(); // This is used to consume the remaining user input from Scanner
//            }
//            westMain.printDollars();
//        }
//
//    }
//}
