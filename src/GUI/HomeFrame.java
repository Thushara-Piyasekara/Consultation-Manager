package GUI;

import Console.Consultation;
import Console.Doctor;
import Console.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HomeFrame extends JFrame
{
    JButton viewDocsButton = new JButton("View All Doctors");
    JButton addConsultButton = new JButton("Schedule Consultation");
    JButton viewConsultButton = new JButton("View Consultations");

    public HomeFrame(ArrayList<Doctor> doctorArray, ArrayList<Consultation> consultationArray, ArrayList<Patient> patientArray)
    {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(1, 1));
        mainPanel.add(sideMenu(), BorderLayout.WEST);
        mainPanel.add(dynamicViewer(doctorArray, consultationArray,patientArray), BorderLayout.CENTER);

        this.add(mainPanel);

    }

    protected JPanel sideMenu()
    {
        JPanel sideMenuPanel = new JPanel(new GridLayout(6, 1, 10, 50));
        sideMenuPanel.setBackground(Color.blue);

        sideMenuPanel.add(viewDocsButton);
        sideMenuPanel.add(addConsultButton);
        sideMenuPanel.add(viewConsultButton);

        return sideMenuPanel;
    }

    protected JPanel dynamicViewer(ArrayList<Doctor> arrayList, ArrayList<Consultation> consultationArray, ArrayList<Patient> patientArray)
    {
        CardLayout cardLayout = new CardLayout();
        JPanel dynamicViewerPanel = new JPanel(cardLayout);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(Color.green);
        welcomePanel.add(new JLabel("Welcome To Westminster Skin Care Center :)"));

        DoctorTablePanel doctorTablePanel = new DoctorTablePanel(arrayList, consultationArray);
        doctorTablePanel.setBackground(Color.ORANGE);

        PatientFormPanel newFormPanel = new PatientFormPanel(arrayList, consultationArray,patientArray);
        newFormPanel.setBackground(Color.ORANGE);

        ConsultTablePanel consultationTablePanel = new ConsultTablePanel(consultationArray);
        consultationTablePanel.setBackground(Color.ORANGE);

        dynamicViewerPanel.add(welcomePanel, "welcome");
        dynamicViewerPanel.add(doctorTablePanel, "docTable");
        dynamicViewerPanel.add(newFormPanel, "newForm");
        dynamicViewerPanel.add(consultationTablePanel, "conTable");

        cardLayout.show(dynamicViewerPanel, "welcome");

        viewDocsButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cardLayout.show(dynamicViewerPanel, "docTable");
            }
        });

        viewConsultButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cardLayout.show(dynamicViewerPanel, "conTable");
                consultationTablePanel.updateTable();
            }
        });

        addConsultButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cardLayout.show(dynamicViewerPanel, "newForm");
                PatientFormPanel.backToChooser();
            }
        });

        return dynamicViewerPanel;
    }

//    private static void addActionToButton(JPanel dynamicViewerPanel, JButton button, CardLayout cardLayout, String constraint)
//    {
//        button.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                cardLayout.show(dynamicViewerPanel, constraint);
//            }
//        });
//    }


}
