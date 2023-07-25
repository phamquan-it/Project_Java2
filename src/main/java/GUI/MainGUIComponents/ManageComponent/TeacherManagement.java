package GUI.MainGUIComponents.ManageComponent;

import DAO.Access.InstructorHandle;
import Model.Instructor;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TeacherManagement extends JInternalFrame{
    private List<Instructor> a = new ArrayList<>();
    public TeacherManagement(){

        //----------------------------------------------------
        DefaultTableModel modelTeacherManage = new DefaultTableModel();
        modelTeacherManage.addColumn("Chọn");
        modelTeacherManage.addColumn("Mã giáo viên");
        modelTeacherManage.addColumn("Họ và tên");
        modelTeacherManage.addColumn("Giới tính");
        modelTeacherManage.addColumn("Ngày sinh");
        modelTeacherManage.addColumn("Password");
        modelTeacherManage.addColumn("Email");
        modelTeacherManage.addColumn("Phone");
        table1.setModel(modelTeacherManage);
        //----------------------------------------------------

        refreshTable();
        //----------------------------------------------------
        setBorder(new LineBorder(new Color(168, 167, 167, 226),1));
        setContentPane(TeacherManagentPanel);
        setVisible(true);
        //----------------------------------------------------
        insertButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Instructor instructor = new Instructor();

                instructor.setName(instName.getText());
                instructor.setPassword(insPassword.getText());
                if(insGender.getSelectedIndex() ==0)
                    instructor.setGender(false);
                else {
                    instructor.setGender(true);
                }

                String dateString = insBirthday.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(dateString, formatter);
                instructor.setBirthday(date);
                instructor.setEmail(insEmail.getText());
                instructor.setPhone(insPhone.getText());
                new InstructorHandle().INSERT(instructor);
                System.out.println("them du lieu thanh cong");
            }
        });
<<<<<<< HEAD



=======
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int clickedRow = table1.rowAtPoint(e.getPoint());
                String id = String.valueOf( table1.getValueAt(clickedRow, 1));
                String Name =String.valueOf( table1.getValueAt(clickedRow,2));
                insID.setText(id);
                instName.setText(Name);
                if (Boolean.parseBoolean(String.valueOf( table1.getValueAt(clickedRow, 3)))){
                    insGender.setSelectedIndex(0);
                }else {
                    insGender.setSelectedIndex(1);
                }
                insBirthday.setText(String.valueOf( table1.getValueAt(clickedRow,4)));
                insPassword.setText(String.valueOf( table1.getValueAt(clickedRow,5)));
                insEmail.setText(String.valueOf( table1.getValueAt(clickedRow,6)));
                insPhone.setText(String.valueOf( table1.getValueAt(clickedRow,7)));
            }
        });
>>>>>>> 9af30d8a4e2529d1ca9f6229ed794a3e974f2c0d
    }
    public void refreshTable() {
        DefaultTableModel modelScoreManage = (DefaultTableModel) table1.getModel();
        modelScoreManage.setRowCount(0); // Clear existing data in the table

        InstructorHandle instuctorHandle = new InstructorHandle();
        this.a = null;
        try {
            a = instuctorHandle.SELECT("SELECT * FROM `instructor`");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Iterator<Instructor> instructorIterator = a.iterator();
        while (instructorIterator.hasNext()){
            Instructor instructor = instructorIterator.next();
            modelScoreManage.addRow(new Object[]{true,instructor.getID_NUMBER(),instructor.getName(),instructor.getGender(),instructor.getBirthday(),instructor.getPassword(),instructor.getEmail(),instructor.getPhone()});
        }

    }
    private JPanel TeacherManagentPanel;
    private JButton insertButton;
    private JButton sửaButton;
    private JButton xóaButton;
    private JButton tảiLạiButton;
    private JButton chọnẢnhButton;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JComboBox comboBox1;
    private JComboBox insGender;
<<<<<<< HEAD
    private JTextField ID;
=======
    private JTextField insID;
>>>>>>> 9af30d8a4e2529d1ca9f6229ed794a3e974f2c0d
    private JTextField instName;
    private JTextField insBirthday;
    private JTextField insEmail;
    private JTextField insPhone;
    private JPasswordField insPassword;
    private JTable table1;
}
