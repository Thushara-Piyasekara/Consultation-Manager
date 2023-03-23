package GUI;

import Console.Consultation;
import Console.Doctor;
import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DoctorTablePanel extends JPanel
{
    JTable doctorTable;
    DoctorTableModel doctorTableModel;
    static LocalDateTime pickedStartTime = LocalDateTime.now();
    static LocalDateTime pickedEndTime = LocalDateTime.now();
    static ArrayList<Consultation> consultArr;

    public DoctorTablePanel(ArrayList<Doctor> myList, ArrayList<Consultation> consultationArray)
    {

        doctorTableModel = new DoctorTableModel(myList);
        doctorTable = new JTable(doctorTableModel);
        doctorTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        doctorTable.getTableHeader().setBackground(Color.green);
        doctorTable.setFont(new Font("Arial", Font.PLAIN, 15));

        doctorTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(doctorTable);
        scrollPane.setPreferredSize(new Dimension(1260, 380));

        this.setLayout(new BorderLayout());
        this.add(availabilityCheckPanel(), BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);


        doctorTable.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer()
        {

            @Override
            public Component getTableCellRendererComponent(JTable myTable, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component renderer = super.getTableCellRendererComponent(myTable, value, isSelected, hasFocus, row, column);
                if (value == "Available")
                {
                    renderer.setBackground(Color.GREEN);
                } else
                {
                    renderer.setBackground(Color.RED);
                }
                return renderer;
            }
        });
        consultArr = consultationArray;
    }

    public JPanel availabilityCheckPanel()
    {
        JPanel avalCheckerPanel = new JPanel();
        JLabel dateTimeLbl = new JLabel("Time : ");
        JLabel numOfHoursLbl = new JLabel("Number of hours : ");

        JButton checkButton = new JButton("Check Availability");
        DateTimePicker picker = new DateTimePicker();
        picker.setDateTimePermissive(LocalDateTime.now().plusDays(7));
        SpinnerModel hourNumModel = new SpinnerNumberModel(1, 1, 4, 1);
        JSpinner numOfHoursSpinner = new JSpinner(hourNumModel);

        avalCheckerPanel.add(dateTimeLbl);
        avalCheckerPanel.add(picker);
        avalCheckerPanel.add(numOfHoursLbl);
        avalCheckerPanel.add(numOfHoursSpinner);
        avalCheckerPanel.add(checkButton);

        checkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pickedStartTime = picker.getDateTimeStrict();
                pickedEndTime = pickedStartTime.plusHours((int) numOfHoursSpinner.getValue());
                doctorTableModel.fireTableDataChanged();
            }
        });
        return avalCheckerPanel;
    }

    public static boolean checkAvailability(Doctor doctor)
    {
        boolean availability = true;

        for (Consultation consultation : consultArr)
        {
            if (consultation.getDoctor() == doctor)
            {
                if (!((consultation.getStartDateTime().isAfter(pickedEndTime) || consultation.getStartDateTime() == pickedEndTime) ||
                        (consultation.getEndDateTime().isBefore(pickedStartTime) ||consultation.getEndDateTime() == pickedStartTime)))
                {
                    availability = false;
                }
            }
        }
        return availability;
    }
}
