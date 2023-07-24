package GUI.MainGUIComponents.ManageComponent;

import DAO.Access.InstructorHandle;
import DAO.Access.InstructorSubjectHandle;
import DAO.Access.SubjectHandle;
import Model.Instructor;
import Model.InstructorSubject;
import Model.Subject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubjectTeachingTeacher extends JInternalFrame{
    private List<InstructorSubject> a = new ArrayList<>();
    public SubjectTeachingTeacher(){


        //-----------------------------------------------
        DefaultTableModel modelScoreManage = new DefaultTableModel();
        modelScoreManage.addColumn("Chọn");
        modelScoreManage.addColumn("Mã Giáo Viên Bộ Môn");
        modelScoreManage.addColumn("Mã Giáo Viên");
        modelScoreManage.addColumn("Mã Môn Học");
        //-----------------------------------------------

        table1.setModel(modelScoreManage);
        refreshTable();
        //-----------------------------------------------
        setTitle("");
        setContentPane(subjectTeachingTeacherPanel);
        setVisible(true);
        //-----------------------------------------------
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int magv = (int)MaGV.getSelectedItem();
                int mamh = (int)MaMH.getSelectedItem();
                InstructorSubject instructorSubject = new InstructorSubject();
                instructorSubject.setSubject_code(mamh);
                instructorSubject.setID_NUMBER(magv);
                InstructorSubjectHandle instructorSubjectHandle = new InstructorSubjectHandle();
                instructorSubjectHandle.INSERT(instructorSubject);
                refreshTable();
            }
        });
    }

    public void refreshTable() {
        List<Instructor> maGV = null;
        InstructorHandle instructorHandle = new InstructorHandle();
        try {
            maGV = instructorHandle.SELECT("SELECT * FROM `instructor`");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(Instructor obj: maGV){
            MaGV.addItem(obj.getID_NUMBER());
        }

        List<Subject> maMH = null;
        SubjectHandle subjectHandle = new SubjectHandle();
        try {
            maMH = subjectHandle.SELECT("SELECT * FROM `subject`");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(Subject obj1: maMH){
            MaGV.addItem(obj1.getID());
        }



        DefaultTableModel modelScoreManage = (DefaultTableModel) table1.getModel();
        modelScoreManage.setRowCount(0); // Clear existing data in the table

        InstructorSubjectHandle instructorSubjectHandle = new InstructorSubjectHandle();
        this.a = null;
        try {
            a = instructorSubjectHandle.SELECT("SELECT * FROM `instructor_subject`");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Iterator<InstructorSubject> instructorsubjectIterator = a.iterator();
        while (instructorsubjectIterator.hasNext()){
            InstructorSubject instructorSubject = instructorsubjectIterator.next();
            modelScoreManage.addRow(new Object[]{true,instructorSubject.getID_Teach(),instructorSubject.getID_NUMBER(),instructorSubject.getSubject_code()});
        }

    }
    private JPanel subjectTeachingTeacherPanel;
    private JButton tảiLạiButton;
    private JButton xóaButton;
    private JButton sửaButton;
    private JButton insert;
    private JButton chọnẢnhButton;
    private JCheckBox chọnTấtCảCheckBox;
    private JCheckBox bỏChọnCheckBox;
    private JComboBox comboBox1;
    private JTextField MaGVBM;
    private JTable table1;
    private JComboBox MaGV;
    private JComboBox MaMH;
}
