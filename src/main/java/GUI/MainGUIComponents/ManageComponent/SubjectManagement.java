package GUI.MainGUIComponents.ManageComponent;

import Controllers.Authorization.Authorization;
import Controllers.ExportData;
import Controllers.SortA_Z;
import Controllers.SortZ_A;
import Controllers.Validation;
import DAO.Access.GrantHandle;
import DAO.Access.SubjectHandle;
import Model.Block;
import Model.Subject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class SubjectManagement extends JInternalFrame{
    public List<Subject> a = new ArrayList<>();
    public SubjectManagement(){
        GrantHandle grantHandle = new GrantHandle();
        List<Block> idList = new ArrayList<>();
        try {
            idList = grantHandle.SELECT("SELECT * FROM grants");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Add the IDs to the JComboBox
        for (Block obj : idList) {
            comboBox1.addItem(obj.getID());
        }
        //--------------------------------------
        DefaultTableModel modelScoreManage = new DefaultTableModel();
        modelScoreManage.addColumn("Chọn");
        modelScoreManage.addColumn("Mã Môn Mọc");
        modelScoreManage.addColumn("Tên Môn Học");
        modelScoreManage.addColumn("Tín Chỉ");
        modelScoreManage.addColumn("ID Khối");
        //------------------------------------

        table1.setModel(modelScoreManage);
        refreshTable();


        //---------------------------------------------
        setBorder(new LineBorder(new Color(168, 167, 167, 226),1));
        setContentPane(panel1);
        setVisible(true);
        //-----------------------------------------
        insert.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Authorization.getPermisionForSubject()){
                    JOptionPane.showMessageDialog(null,"Bạn không có quyền truy  cập");
                    return;
                }
                if(!Validation.isFullName(TenMon.getText())){
                    JOptionPane.showMessageDialog(null,"Tên môn học không hợp lệ");
                    return;
                }
                if(!Validation.isNumeric(tinchi.getText())){
                    JOptionPane.showMessageDialog(null,"Tín chỉ không hợp lệ");
                    return;
                }

                String TenMonHoc = TenMon.getText();


                int TinChi = Integer.parseInt(tinchi.getText());
                int Khoi = (int)comboBox1.getSelectedItem();

                Subject subject = new Subject();
                try {
                    if(String.valueOf(mamh.getText()).equals("")){
                        subject.setID(-1);
                    }else {
                        int id = Integer.valueOf(mamh.getText());
                        subject.setID(id);
                    }
                subject.setName(TenMonHoc);
                subject.setCredits(TinChi);
                subject.setGrandID(Khoi);
                SubjectHandle subjectHandle = new SubjectHandle();
                subjectHandle.INSERT(subject);
                JOptionPane.showMessageDialog(null,"Thêm môn học thành công");
                refreshTable();


            }
                catch (NumberFormatException e1){
                    throw new RuntimeException(e1);
                }}
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int clickedRow = table1.rowAtPoint(e.getPoint());
                String id = String.valueOf( table1.getValueAt(clickedRow, 1));
                String maMon = String.valueOf( table1.getValueAt(clickedRow,2));
                String Tinchi  = String.valueOf(table1.getValueAt(clickedRow,3));
                int khoi  = (int)table1.getValueAt(clickedRow,4);
                System.out.println(khoi);
                mamh.setText(id);
                TenMon.setText(maMon);
                tinchi.setText(Tinchi);
                comboBox1.setSelectedItem(khoi);

            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Authorization.getPermisionForSubject()){
                    JOptionPane.showMessageDialog(null,"Bạn không có quyền truy  cập");
                    return;
                }
                if(!Validation.isNumeric(mamh.getText())){
                    JOptionPane.showMessageDialog(null,"ID không hợp lệ");
                    return;
                }
                int id = Integer.valueOf(mamh.getText());
                SubjectHandle subjectHandle = new SubjectHandle();
                subjectHandle.DELETE(id);
                JOptionPane.showMessageDialog(null,"Xóa môn học thành công");
                mamh.setText(null);
                TenMon.setText(null);
                tinchi.setText(null);
             //   comboBox1.setSelectedItem(null);
                refreshTable();
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Authorization.getPermisionForSubject()){
                    JOptionPane.showMessageDialog(null,"Bạn không có quyền truy  cập");
                    return;
                }
                if(!Validation.isNumeric(mamh.getText())){
                    JOptionPane.showMessageDialog(null,"ID không hợp lệ");
                    return;
                }
                if(!Validation.isFullName(TenMon.getText())){
                    JOptionPane.showMessageDialog(null,"Tên môn học không hợp lệ");
                    return;
                }
                if(!Validation.isNumeric(tinchi.getText())){
                    JOptionPane.showMessageDialog(null,"Tín chỉ không hợp lệ");
                    return;
                }
                int id = Integer.valueOf(mamh.getText());
                String TenMonHoc = TenMon.getText();
                int TinChi = Integer.parseInt(tinchi.getText());
                int Khoi = (int)comboBox1.getSelectedItem();

                Subject subject = new Subject();
                subject.setID(id);
                subject.setName(TenMonHoc);
                subject.setCredits(TinChi);
                subject.setGrandID(Khoi);
                SubjectHandle subjectHandle = new SubjectHandle();
                subjectHandle.UPDATE(subject);
                JOptionPane.showMessageDialog(null,"Cập  nhật môn học thành  công");
                refreshTable();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel modelScoreManage = (DefaultTableModel) table1.getModel();
                modelScoreManage.setRowCount(0); // Clear existing data in the table

                SubjectHandle subjectHandle = new SubjectHandle();
                if (searchByNameCheckBox.isSelected()){
                    Iterator<Subject> subjectIterator = a.iterator();
                    while (subjectIterator.hasNext()){
                        Subject subject = subjectIterator.next();
                        if(subject.getName().contains(searchinput.getText())){
                            modelScoreManage.addRow(new Object[]{true,subject.getID(),subject.getName(),subject.getCredits(),subject.getGrandID()});
                        }

                    }
                }else {
                    Iterator<Subject> subjectIterator = a.iterator();
                    while (subjectIterator.hasNext()){
                        Subject subject = subjectIterator.next();
                        if(subject.getID() == Integer.valueOf(searchinput.getText())){
                            modelScoreManage.addRow(new Object[]{true,subject.getID(),subject.getName(),subject.getCredits(),subject.getGrandID()});
                        }
                    }
                }
            }
        });
        aZButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(a, new SortA_Z());
                DefaultTableModel modelScoreManage = (DefaultTableModel) table1.getModel();
                modelScoreManage.setRowCount(0); // Clear existing data in the table
                Iterator<Subject> subjectIterator = a.iterator();
                while (subjectIterator.hasNext()){
                    Subject subject = subjectIterator.next();
                    modelScoreManage.addRow(new Object[]{true,subject.getID(),subject.getName(),subject.getCredits(),subject.getGrandID()});
                }

            }
        });
        zAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(a, new SortZ_A());
                DefaultTableModel modelScoreManage = (DefaultTableModel) table1.getModel();
                modelScoreManage.setRowCount(0); // Clear existing data in the table
                Iterator<Subject> subjectIterator = a.iterator();
                while (subjectIterator.hasNext()){
                    Subject subject = subjectIterator.next();
                    modelScoreManage.addRow(new Object[]{true,subject.getID(),subject.getName(),subject.getCredits(),subject.getGrandID()});
                }
            }
        });
        ExportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ExportData.exportData(table1,1);
            }
        });
        removeFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mamh.setText(null);
                TenMon.setText(null);
                tinchi.setText(null);

            }
        });
    }
    public void refreshTable() {
        DefaultTableModel modelScoreManage = (DefaultTableModel) table1.getModel();
        modelScoreManage.setRowCount(0); // Clear existing data in the table

        SubjectHandle subjectHandle = new SubjectHandle();
        this.a = null;
        try {
            a = subjectHandle.SELECT("SELECT * FROM `subject`");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Iterator<Subject> subjectIterator = a.iterator();
        while (subjectIterator.hasNext()){
            Subject subject = subjectIterator.next();
            modelScoreManage.addRow(new Object[]{true,subject.getID(),subject.getName(),subject.getCredits(),subject.getGrandID()});
        }

    }
    private JPanel panel1;
    private JTextField mamh;
    private JTextField tinchi;
    private JComboBox comboBox1;
    private JButton delete;
    private JButton insert;
    private JButton update;
    private JButton resetButton;
    private JButton removeFormButton;
    private JTable table1;
    private JComboBox comboBox2;
    private JButton zAButton;
    private JButton aZButton;
    private JTextField TenMon;
    private JTextField searchinput;
    private JButton searchButton;
    private JCheckBox searchByNameCheckBox;
    private JButton ExportButton;

    private void createUIComponents() {
        table1 = new JTable() {

            private static final long serialVersionUID = 1L;
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }
        };
    }
}
