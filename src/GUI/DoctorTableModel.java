package GUI;

import Console.Doctor;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class DoctorTableModel extends AbstractTableModel
{
    private String[] columnNames = {"Name", "Surname", "Date of Birth",
            "Medical License Number", "Mobile Number", "Specialisation", "Availability"};
    private ArrayList<Doctor> docList;

    static boolean availabiliy;

    public DoctorTableModel(ArrayList<Doctor> personList)
    {
        this.docList = personList;
    }

    @Override
    public int getRowCount()
    {
        return docList.size();
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
            case 0 -> temp = docList.get(rowIndex).getName();
            case 1 -> temp = docList.get(rowIndex).getSurname();
            case 2 -> temp = docList.get(rowIndex).getDateOfBirthAsString();
            case 3 -> temp = docList.get(rowIndex).getMedicalLicenceNum();
            case 4 -> temp = docList.get(rowIndex).getMobileNumber();
            case 5 -> temp = docList.get(rowIndex).getSpecialisation();
            case 6 ->
            {
                if (DoctorTablePanel.checkAvailability(docList.get(rowIndex)))
                    temp = "Available";
                else
                    temp = "Unavailable";
            }
        }
        return temp;
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }
}
