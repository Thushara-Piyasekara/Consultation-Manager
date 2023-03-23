package GUI;

import Console.Consultation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ConsultationInformationViewer extends JFrame
{
    public ConsultationInformationViewer(Consultation consultation)
    {
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setLayout(new BorderLayout());
        summaryPanel.setBackground(Color.BLUE);

        JLabel greeting = new JLabel("Consultation Information Summary", SwingConstants.CENTER);
        greeting.setForeground(Color.white);
        greeting.setFont(new Font("Arial", Font.BOLD, 20));

        greeting.setBorder(new EmptyBorder(10, 10, 10, 10));
        summaryPanel.add(greeting, BorderLayout.NORTH);

        JPanel informationPanel = new JPanel(new GridBagLayout());
        informationPanel.setBackground(Color.yellow);
        summaryPanel.add(informationPanel, BorderLayout.CENTER);

        JLabel consultId = new JLabel("Consultation ID :");
        JLabel name = new JLabel("Name :");
        JLabel DOB = new JLabel("Date of Birth :");
        JLabel mobNum = new JLabel("Mobile number :");
        JLabel custId = new JLabel("Customer ID :");
        JLabel doctorlbl = new JLabel("Doctor :");
        JLabel consultDateTimelbl = new JLabel("Consultation Date and Time :");
        JLabel numOfHours = new JLabel("Number of Hours :");
//        JLabel notes = new JLabel("Medical Notes :");
//        JLabel imageLbl = new JLabel("Image :");
        JLabel cost = new JLabel("Cost :");


        JLabel consultIdInfo = new JLabel(consultation.getConsultationId());
        JLabel patientNameInfo = new JLabel(consultation.getPatient().getFullName());
        JLabel DOBInfo = new JLabel(consultation.getPatient().getDateOfBirthAsString());
        JLabel mobNumInfo = new JLabel(consultation.getPatient().getMobileNumber());
        JLabel custIdInfo = new JLabel(consultation.getPatient().getPatientId());
        JLabel doctorNameInfo = new JLabel(consultation.getDoctor().getFullName());
        JLabel consultStartTimeInfo = new JLabel(consultation.getStartTimeAsString());
        JLabel numOfHoursInfo = new JLabel(String.valueOf(consultation.getNumOfHours()));

//        JButton showNotesImg = new JButton("View Notes and Images");

//        JLabel notesInfo = new JLabel("Medical Note has been Encrypted for patient privacy");
//        JLabel imageInfo = new JLabel("Hehe:)");
        JLabel costInfo = new JLabel("£"+ consultation.getCost());
        costInfo.setFont(new Font("Arial", Font.BOLD,13));
        costInfo.setForeground(Color.RED);


//        ArrayList<JComponent> labelsArray = new ArrayList<>(Arrays.asList(consultId,name, DOB, mobNum, custId, doctorlbl,
//                consultDateTimelbl, numOfHours, notes, imageLbl,cost));
//        ArrayList<JComponent> infoLabelsArray = new ArrayList<>(Arrays.asList(consultIdInfo,patientNameInfo,DOBInfo,mobNumInfo,custIdInfo,
//                doctorNameInfo,consultStartTimeInfo,numOfHoursInfo,notesInfo,imageInfo,costInfo));

        ArrayList<JComponent> labelsArray = new ArrayList<>(Arrays.asList(consultId,name, DOB, mobNum, custId, doctorlbl,
                consultDateTimelbl, numOfHours,cost));
        ArrayList<JComponent> infoLabelsArray = new ArrayList<>(Arrays.asList(consultIdInfo,patientNameInfo,DOBInfo,mobNumInfo,custIdInfo,
                doctorNameInfo,consultStartTimeInfo,numOfHoursInfo,costInfo));


        arrayToPanel(informationPanel, labelsArray, 0);
        arrayToPanel(informationPanel, infoLabelsArray, 1);

        this.add(summaryPanel);
        this.setVisible(true);
        this.setSize(800, 900);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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

}
