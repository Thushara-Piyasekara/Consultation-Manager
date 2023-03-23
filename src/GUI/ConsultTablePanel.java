package GUI;

import Console.Consultation;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ConsultTablePanel extends JPanel
{
    JTable myTable;
    static JFrame viewerFrame;
    ConsultationTableModel myTableModel;
    static JLabel wrongPass;

    public ConsultTablePanel(ArrayList<Consultation> consultationList)
    {
        myTableModel = new ConsultationTableModel(consultationList);
        myTable = new JTable(myTableModel);

        myTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        myTable.getTableHeader().setBackground(Color.green);
        myTable.setFont(new Font("Arial", Font.PLAIN, 15));

        myTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(myTable);
        scrollPane.setPreferredSize(new Dimension(1260, 380));
        this.add(scrollPane, BorderLayout.CENTER);
        rowClickAction(consultationList);
    }

    public void updateTable()
    {
        myTableModel.fireTableDataChanged();
    }

    public void rowClickAction(ArrayList<Consultation> consultationList)
    {
        myTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    System.out.println(myTable.getValueAt(myTable.getSelectedRow(), 0).toString());

                    String clickedConId = myTable.getValueAt(myTable.getSelectedRow(), 0).toString();

                    for (Consultation consultation : consultationList)
                    {
                        if (consultation.getConsultationId().equals(clickedConId) && !bothUnavailable(consultation))
                        {
                            decryptor(consultation);
                            break;
                        }
                    }
                }
            }
        });
    }


    public void decryptor(Consultation consultation)
    {

        JFrame passwordFrame = new JFrame("Password Verification");
        passwordFrame.setVisible(true);
        passwordFrame.setSize(800, 200);
        passwordFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        JLabel info = new JLabel("Please enter the password to decrypt Consultation notes and Images", SwingConstants.CENTER);
        wrongPass = new JLabel();
        info.setFont(new Font("Arial", Font.BOLD, 20));
        info.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel passLbl = new JLabel("Password :");
        JPasswordField passField = new JPasswordField(20);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(passLbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        centerPanel.add(wrongPass, gbc);

        JButton decryptButton = new JButton("Decrypt Data");

        decryptButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String passIn = new String(passField.getPassword());
                imageNoteViewer(passIn, consultation);
                passwordFrame.setVisible(false);
            }
        });

        passwordFrame.add(info, BorderLayout.NORTH);
        passwordFrame.add(centerPanel, BorderLayout.CENTER);
        passwordFrame.add(decryptButton, BorderLayout.SOUTH);
    }

    public void imageNoteViewer(String password, Consultation consultation)
    {

        String decryptedNote = Encryptor.decryptNote(consultation.getNotes(), password);

        viewerFrame = new JFrame("Confidential Information");
        viewerFrame.setLayout(new GridLayout(4, 1));
        viewerFrame.setVisible(true);
        viewerFrame.setSize(500, 800);
        viewerFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JLabel imageLbl = new JLabel("Image :");
        JLabel imgOut = new JLabel();
        imgOut.setSize(400, 300);

        if (consultation.getEncryptedImage() != null)
        {

            byte[] decryptedImgByteArr = Encryptor.decryptByteArr(consultation.getEncryptedImage(), password);
            InputStream is = new ByteArrayInputStream(decryptedImgByteArr);
            BufferedImage newBi = null;
            try
            {
                newBi = ImageIO.read(is);
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            Image dimg = newBi.getScaledInstance(imgOut.getWidth(), imgOut.getHeight(), Image.SCALE_SMOOTH);
            imgOut.setIcon(new ImageIcon(dimg));
        } else
        {
            imgOut.setText("No Image Given");
        }

        JLabel noteLbl = new JLabel("Notes :");
        JLabel noteOut = new JLabel();

        if (!decryptedNote.equals(""))
        {
            noteOut.setText(decryptedNote);
        } else
        {
            noteOut.setText("No Note Available");
        }

        viewerFrame.add(imageLbl);
        viewerFrame.add(imgOut);
        viewerFrame.add(noteLbl);
        viewerFrame.add(noteOut);
    }

    public static void wrongPassVisible()
    {
        viewerFrame.setVisible(false);
        wrongPass.setText("Wrong Password Please try Again!");
    }

    public static boolean bothUnavailable(Consultation consultation)
    {
        if (consultation.getEncryptedImage() == null && consultation.getNotes().equals(""))
        {
            return true;
        }
        return false;
    }
}
