package GUI;

import Console.Consultation;
import Console.Doctor;
import Console.Patient;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

;

public class PatientFormPanel extends JPanel
{

    static final String password = "123";
    static JTextField nameF, surNameF, mobNumF, patientIdF;
    static DatePicker birthdayPicker;
    static JComboBox patientIdDropDown;
    static JComboBox doctorDropDown1;
    static JComboBox doctorDropDown2;
    static JSpinner numOfHoursSpinner1;
    static JSpinner numOfHoursSpinner2;
    static DateTimePicker dateTimePicker1;
    static DateTimePicker dateTimePicker2;
    static JTextArea noteArea1;
    static JTextArea noteArea2;
    static JButton existPatientButton;
    static byte[] encryptedImgByteArray;
    static JPanel cardPanel;
    static CardLayout choiceCardLayout;

    public PatientFormPanel(ArrayList<Doctor> docArray, ArrayList<Consultation> conArray, ArrayList<Patient> patientArray)
    {
        this.add(newOrOldPanel(docArray, conArray, patientArray));
    }

    protected static JPanel newOrOldPanel(ArrayList<Doctor> docArray, ArrayList<Consultation> conArray, ArrayList<Patient> patientArray)
    {
        choiceCardLayout = new CardLayout();
        cardPanel = new JPanel(choiceCardLayout);
        JPanel chooser = new JPanel(new BorderLayout());
        chooser.setBackground(Color.BLUE);

        cardPanel.add(chooser, "chooser");
        cardPanel.add(formPanelExisting(docArray, conArray, patientArray), "existing");
        cardPanel.add(formPanelNew(docArray, conArray, patientArray), "new");
        choiceCardLayout.show(cardPanel, "chooser");

        JLabel info = new JLabel("Please choose Patient type");


        info.setForeground(Color.white);
        info.setFont(new Font("Arial", Font.BOLD, 20));
        info.setBorder(new EmptyBorder(10, 10, 10, 10));

        existPatientButton = new JButton("Existing Patient");
        JButton newPatientButton = new JButton("New Patient");

        if (patientArray.size() == 0)
        {
            existPatientButton.setEnabled(false);
        }

        chooser.add(info, BorderLayout.NORTH);
        chooser.add(existPatientButton, BorderLayout.WEST);
        chooser.add(newPatientButton, BorderLayout.EAST);

        newPatientButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                choiceCardLayout.show(cardPanel, "new");
            }
        });

        existPatientButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                updatePatientDropDown(patientArray);
                choiceCardLayout.show(cardPanel, "existing");
            }
        });

        return cardPanel;
    }

    private static JPanel formPanelNew(ArrayList<Doctor> docArray, ArrayList<Consultation> conArray, ArrayList<Patient> patientArray)
    {

        JPanel formPanel = new JPanel(new BorderLayout());
        JPanel labels = new JPanel(new GridBagLayout());
        labels.setBackground(Color.CYAN);

        //---------------------- Labels --------------------------//

        JLabel name = new JLabel("Name :");
        JLabel surName = new JLabel("Surname :");
        JLabel DOB = new JLabel("Date of Birth :");
        JLabel mobNum = new JLabel("Mobile number :");
        JLabel custId = new JLabel("Patient ID :");
        JLabel doctorlbl = new JLabel("Doctor :");
        JLabel consultDateTimelbl = new JLabel("Consultation Date and Time :");
        JLabel numOfHours = new JLabel("Number of Hours :");
        JLabel notes = new JLabel("Medical Notes :");
        JLabel imageLbl = new JLabel("Add Image :");

        ArrayList<JComponent> labelsArray = new ArrayList<>(Arrays.asList(name, surName, DOB, mobNum, custId, doctorlbl, consultDateTimelbl, numOfHours, notes, imageLbl));
        arrayToPanel(labels, labelsArray, 0);

        //---------------------- Input Fields -----------------------//
        nameF = new JTextField("Ben", 20);
        surNameF = new JTextField("Ten", 20);
        mobNumF = new JTextField("042131231", 20);
        patientIdF = new JTextField("ben10000", 20);

        //DOB Thingy
        DatePickerSettings dateSettings = new DatePickerSettings();
        birthdayPicker = new DatePicker(dateSettings);
        dateSettings.setDateRangeLimits(LocalDate.now().minusYears(140), LocalDate.now());
        birthdayPicker.setDate(LocalDate.now().minusYears(20));

        //Doctor name DropDown
        doctorDropDown1 = new JComboBox(fullNameArrayMakerDoc(docArray));

        //Consultation Date and Time with GOOD TIME PICKER
        DatePickerSettings conDateSettings = new DatePickerSettings();
        dateTimePicker1 = new DateTimePicker(conDateSettings, null);
        conDateSettings.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(5));
        dateTimePicker1.setDateTimePermissive(LocalDateTime.now().plusDays(7));

        //Num of Hours
        SpinnerModel hourNumModel = new SpinnerNumberModel(1, 1, 4, 1);
        numOfHoursSpinner1 = new JSpinner(hourNumModel);

        //Note Area
        noteArea1 = new JTextArea("I have Bumps on my nose ;)", 10, 30);

        //Image Input
        JButton addImageButton = new JButton("Select Image");
        imgToByteArr(addImageButton);


        ArrayList<JComponent> inputsArray = new ArrayList<>(Arrays.asList(nameF, surNameF, birthdayPicker, mobNumF,
                patientIdF, doctorDropDown1, dateTimePicker1, numOfHoursSpinner1, noteArea1, addImageButton));
        arrayToPanel(labels, inputsArray, 1);


        JLabel greeting = new JLabel("Consultation Schedule Form - New Patient", SwingConstants.CENTER);
        greeting.setForeground(Color.white);
        greeting.setFont(new Font("Arial", Font.BOLD, 20));
        greeting.setBorder(new EmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(Color.BLUE);
        formPanel.add(greeting, BorderLayout.NORTH);
        formPanel.add(labels, BorderLayout.CENTER);
        formPanel.add(submitAndClearNew(docArray, conArray, formPanel, patientArray), BorderLayout.SOUTH);

        return formPanel;
    }


    private static JPanel formPanelExisting(ArrayList<Doctor> docArray, ArrayList<Consultation> conArray, ArrayList<Patient> patientArray)
    {
        {
            JPanel formPanel = new JPanel(new BorderLayout());
            JPanel labels = new JPanel(new GridBagLayout());
            labels.setBackground(Color.CYAN);

            //---------------------- Labels --------------------------//

            JLabel custId = new JLabel("Patient ID :");
            JLabel doctorlbl = new JLabel("Doctor :");
            JLabel consultDateTimelbl = new JLabel("Consultation Date and Time :");
            JLabel numOfHours = new JLabel("Number of Hours :");
            JLabel notes = new JLabel("Medical Notes :");
            JLabel imageLbl = new JLabel("Add Image :");

            ArrayList<JComponent> labelsArray = new ArrayList<>(Arrays.asList(custId, doctorlbl, consultDateTimelbl, numOfHours, notes, imageLbl));
            arrayToPanel(labels, labelsArray, 0);

            //---------------------- Input Fields -----------------------//

            //Patient ID
            patientIdDropDown = new JComboBox();
            updatePatientDropDown(patientArray);

            //Doctor name DropDown
            doctorDropDown2 = new JComboBox(fullNameArrayMakerDoc(docArray));

            //Consultation Date and Time with GOOD TIME PICKER
            DatePickerSettings dateSettings2 = new DatePickerSettings();
            dateTimePicker2 = new DateTimePicker(dateSettings2, null);
            dateSettings2.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(5));
            dateTimePicker2.setDateTimePermissive(LocalDateTime.now().plusDays(7));

            //Num of Hours
            SpinnerModel hourNumModel = new SpinnerNumberModel(1, 1, 4, 1);
            numOfHoursSpinner2 = new JSpinner(hourNumModel);

            //Note Area
            noteArea2 = new JTextArea("I have Bumps on my nose ;)", 10, 30);

            //Image Input
            JButton addImageButton = new JButton("Select Image");
            imgToByteArr(addImageButton);


            ArrayList<JComponent> inputsArray = new ArrayList<>(Arrays.asList(patientIdDropDown, doctorDropDown2, dateTimePicker2, numOfHoursSpinner2, noteArea2, addImageButton));
            arrayToPanel(labels, inputsArray, 1);


            JLabel greeting = new JLabel("Consultation Schedule Form - Existing Patient", SwingConstants.CENTER);
            greeting.setForeground(Color.white);
            greeting.setFont(new Font("Arial", Font.BOLD, 20));
            greeting.setBorder(new EmptyBorder(10, 10, 10, 10));
            formPanel.setBackground(Color.BLUE);
            formPanel.add(greeting, BorderLayout.NORTH);
            formPanel.add(labels, BorderLayout.CENTER);
            formPanel.add(submitAndClearExist(docArray, conArray, formPanel, patientArray), BorderLayout.SOUTH);

            return formPanel;
        }

    }

    private static void arrayToPanel(JPanel outPanel, ArrayList<JComponent> itemArray, int column)
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 30);

        for (int i = 0; i < itemArray.size(); i++)
        {
            gbc.gridx = column;
            gbc.gridy = i;
            outPanel.add(itemArray.get(i), gbc);
        }
    }

    private static String[] fullNameArrayMakerDoc(ArrayList<Doctor> doctorArrayList)
    {
        String[] docFullNames = new String[doctorArrayList.size()];
        for (int i = 0; i < doctorArrayList.size(); i++)
        {
            docFullNames[i] = doctorArrayList.get(i).getFullName();
        }
        return docFullNames;
    }

    private static void updatePatientDropDown(ArrayList<Patient> patientArray)
    {
        patientIdDropDown.removeAllItems();
        for (Patient patient : patientArray)
        {
            patientIdDropDown.addItem(patient.getFullName() + " - " + patient.getPatientId());
        }
    }

    private static JPanel submitAndClearNew(ArrayList<Doctor> docArray, ArrayList<Consultation> conArray, JPanel cardPanel, ArrayList<Patient> patientArray)
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.PINK);
        JButton submit = new JButton("Submit");
        buttonPanel.add(submit);
        JButton clear = new JButton("Clear");
        buttonPanel.add(clear);

        clear.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clearFields();
            }
        });

        submit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Doctor selectedDoc = docArray.get(doctorDropDown1.getSelectedIndex());
                int numOfHours = ((int) numOfHoursSpinner1.getValue());

                if (emptyChecker())
                {
                    JOptionPane.showMessageDialog(null, "Please fill all the necessary fields!");
                } else if (validateNewCustomer())
                {
                    JOptionPane.showMessageDialog(null, "Invalid inputs in one or more Fields!");

                } else if (duplicationChecker(patientArray))
                {
                    JOptionPane.showMessageDialog(null, "Entered Patient ID is already in use! Try a different Patient ID for the new Patient");
                } else
                {
                    if (!checkAvailability(selectedDoc, conArray, numOfHours))
                    {
                        JOptionPane.showMessageDialog(null, "Selected Doctor is not available in the given time period. A random Doctor has been assigned instead.");
                        selectedDoc = getRandDoc(selectedDoc, docArray);
                    }
                    Patient patient = new Patient(nameF.getText(), surNameF.getText(), birthdayPicker.getDate(),
                            mobNumF.getText(), patientIdF.getText());
                    patientArray.add(patient);

                    String originalString = noteArea1.getText();
                    String encryptedString = Encryptor.encryptNote(originalString, password);

                    Consultation consultation = new Consultation(selectedDoc, patient,
                            dateTimePicker1.getDateTimeStrict(), (numOfHours), getCostNew(), encryptedString, encryptedImgByteArray);

                    conArray.add(consultation);
                    new ConsultationInformationViewer(consultation);
                    clearFields();
                    existPatientButton.setEnabled(true);
                }
            }
        });
        return buttonPanel;
    }

    private static JPanel submitAndClearExist(ArrayList<Doctor> docArray, ArrayList<Consultation> conArray, JPanel cardPanel, ArrayList<Patient> patientArray)
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.PINK);
        JButton submit = new JButton("Submit");
        buttonPanel.add(submit);
        JButton clear = new JButton("Clear");
        buttonPanel.add(clear);

        clear.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clearFields();
            }
        });

        submit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Doctor selectedDoc = docArray.get(doctorDropDown2.getSelectedIndex());
                int numOfHours = ((int) numOfHoursSpinner2.getValue());

                if (!checkAvailability(selectedDoc, conArray, numOfHours))
                {
                    JOptionPane.showMessageDialog(null, "Selected Doctor is not available in the given time period. A random Doctor has been assigned instead.");
                    selectedDoc = getRandDoc(selectedDoc, docArray);
                }

                Patient patientExist = patientArray.get(patientIdDropDown.getSelectedIndex());
                String originalString = noteArea2.getText();
                String encryptedString = Encryptor.encryptNote(originalString, password);

                Consultation consultation = new Consultation(selectedDoc, patientExist, dateTimePicker2.getDateTimeStrict(),
                        numOfHours, getCostExisting(), encryptedString, encryptedImgByteArray);

                conArray.add(consultation);
                new ConsultationInformationViewer(consultation);
                clearFields();
            }
        });
        return buttonPanel;
    }


    public static void imgToByteArr(JButton addImgButton)
    {
        addImgButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser imageChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                imageChooser.setDialogTitle("Select an image");
                imageChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "png", "jpg", "jpeg", "bmp");
                imageChooser.addChoosableFileFilter(filter);

                int returnValue = imageChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION)
                {
                    File source = new File(imageChooser.getSelectedFile().getPath());
                    try
                    {
                        if (source != null)
                        {
                            encryptedImgByteArray = Encryptor.encryptByteArr(Files.readAllBytes(source.toPath()), "123");
                        }
                    } catch (IOException ee)
                    {
                        throw new RuntimeException(ee);
                    }
                }
            }
        });
    }

    public static int getCostExisting()
    {
        int cost = ((int) numOfHoursSpinner2.getValue()) * 25;
        return cost;
    }

    public static int getCostNew()
    {
        int cost = ((int) numOfHoursSpinner1.getValue()) * 15;
        return cost;
    }

    public static void backToChooser()
    {
        choiceCardLayout.show(cardPanel, "chooser");
    }

    public static boolean emptyChecker()
    {
        JTextField[] fieldArrayList = {nameF, surNameF, mobNumF, patientIdF};
        boolean isEmpty = false;

        for (JTextField textField : fieldArrayList)
        {
            if (textField.getText().equals(""))
            {
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    public static boolean duplicationChecker(ArrayList<Patient> patientArray)
    {
        boolean isDuplicate = false;

        for (Patient patient : patientArray)
        {
            if (patientIdF.getText().equals(patient.getPatientId()))
            {
                isDuplicate = true;
            }
        }

        return isDuplicate;
    }

    public static boolean checkAvailability(Doctor doctor, ArrayList<Consultation> consultArr, int numOfHours)
    {
        boolean availability = true;
        LocalDateTime pickedStartTime = dateTimePicker1.getDateTimeStrict();
        LocalDateTime pickedEndTime = pickedStartTime.plusHours(numOfHours);

        for (Consultation consultation : consultArr)
        {
            if (consultation.getDoctor() == doctor)
            {
                if (!((consultation.getStartDateTime().isAfter(pickedEndTime) || consultation.getStartDateTime() == pickedEndTime) ||
                        (consultation.getEndDateTime().isBefore(pickedStartTime) || consultation.getEndDateTime() == pickedStartTime)))
                {
                    availability = false;
                }
            }
        }
        return availability;
    }

    public static Doctor getRandDoc(Doctor busyDoc, ArrayList<Doctor> doctorsArray)
    {
        Doctor randomDoc = doctorsArray.get(new Random().nextInt(doctorsArray.size()));
        if (randomDoc == busyDoc)
        {
            getRandDoc(busyDoc, doctorsArray);
        }
        return randomDoc;
    }

    public static boolean validateNewCustomer()
    {
        if (nameValidator(nameF) || nameValidator(surNameF))
        {
            return false;
        }
        if (validateMobileNum())
        {
            return false;
        }
        return true;
    }

    public static boolean nameValidator(JTextField nameFld)
    {
        boolean notValid = false;
        String name = nameFld.getText();

        char[] nameChars = name.toCharArray();
        for (char character : nameChars)
        {
            if (Character.isDigit(character))
            {
                notValid = true;
            }
            if (Character.isWhitespace(character))
            {
                notValid = true;
            }
        }
        return notValid;
    }

    public static boolean validateMobileNum()
    {
        String mobNumString = mobNumF.getText();
        char[] mobNumChars = mobNumString.toCharArray();
        for (char character : mobNumChars)
        {
            if (Character.isWhitespace(character))
            {
                return false;
            }
            if (!Character.isDigit(character))
            {
                return false;
            }
        }
        if (mobNumChars.length != 10)
        {
            return false;
        }
        return true;
    }

    public static void clearFields()
    {
        JTextField[] fieldArrayList = {nameF, surNameF, mobNumF, patientIdF};
        for (JTextField txtFld : fieldArrayList)
        {
            txtFld.setText("");
        }
        noteArea1.setText("");
        noteArea2.setText("");
        birthdayPicker.setDate(LocalDate.now().minusYears(20));
        dateTimePicker1.setDateTimePermissive(LocalDateTime.now().plusDays(7));
        dateTimePicker2.setDateTimePermissive(LocalDateTime.now().plusDays(7));
    }
}


