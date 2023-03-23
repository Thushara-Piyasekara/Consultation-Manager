package GUI;

import Console.Consultation;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ConsultationTableModel extends AbstractTableModel
{
    private String[] columnNames = {"Consultation ID","Patient Name", "Patient ID", "Doctor Name", "Consultation Time","Number of hours"};
    private ArrayList<Consultation> consultationList;

    public ConsultationTableModel(ArrayList<Consultation> consultationList)
    {
        this.consultationList = consultationList;
    }

    @Override
    public int getRowCount()
    {
        return consultationList.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Object temp = null;
        switch (columnIndex)
        {
            case 0 -> temp = consultationList.get(rowIndex).getConsultationId();
            case 1 -> temp = consultationList.get(rowIndex).getPatient().getFullName();
            case 2 -> temp = consultationList.get(rowIndex).getPatient().getPatientId();
            case 3 -> temp = consultationList.get(rowIndex).getDoctor().getFullName();
            case 4 -> temp = consultationList.get(rowIndex).getStartTimeAsString();
            case 5 -> temp = consultationList.get(rowIndex).getNumOfHours();
        }
        return temp;
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }
}
