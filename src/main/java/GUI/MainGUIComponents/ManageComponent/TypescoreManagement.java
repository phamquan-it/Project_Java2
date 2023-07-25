package GUI.MainGUIComponents.ManageComponent;

import DAO.Access.TypeScoreHandle;
import Model.TypeScore;
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
import java.util.Iterator;
import java.util.List;

public class TypescoreManagement extends JInternalFrame {
    public List<TypeScore> a = new ArrayList<>();
    public TypescoreManagement(){
        DefaultTableModel modelScoreManage = new DefaultTableModel();
        modelScoreManage.addColumn("Chọn");
        modelScoreManage.addColumn("Mã Loại điểm");
        modelScoreManage.addColumn("Tên Loại điểm");
        table1.setModel(modelScoreManage);

        refreshTable();


        // set layout (phan nay ko duoc code) -----------------------
        setBorder(new LineBorder(new Color(168, 167, 167, 226),1));
        setContentPane(panel1);
        setVisible(true);
        //---------------------------------
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TypeScore typeScore = new TypeScore();
                try{
                    if(String.valueOf(ts_id.getText()).equals("")){
                        typeScore.setID(-1);
                    }else{
                        int id = Integer.valueOf(ts_id.getText());
                        typeScore.setID(id);
                    }
                    String tenloaidiem = name.getText();
                    typeScore.setName(tenloaidiem);
                    TypeScoreHandle typeScoreHandle = new TypeScoreHandle();
                    typeScoreHandle.INSERT(typeScore);
                    refreshTable();
                }catch (NumberFormatException e1){
                    throw new RuntimeException(e1);
                }

            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int clickedRow = table1.rowAtPoint(e.getPoint());
                String id = String.valueOf( table1.getValueAt(clickedRow, 1));
                String ten = String.valueOf( table1.getValueAt(clickedRow,2));
                ts_id.setText(id);
                name.setText(ten);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.valueOf(ts_id.getText());
                TypeScoreHandle typeScoreHandle = new TypeScoreHandle();
                typeScoreHandle.DELETE(id);
                ts_id.setText(null);
                name.setText(null);
                refreshTable();
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TypeScore typeScore = new TypeScore();
                int id = Integer.valueOf(ts_id.getText());
                String tenloaidiem = name.getText();
                typeScore.setID(id);
                typeScore.setName(tenloaidiem);
                TypeScoreHandle typeScoreHandle = new TypeScoreHandle();
                typeScoreHandle.UPDATE(typeScore);
                refreshTable();
            }
        });
    }
    public void refreshTable(){
        TypeScoreHandle TypeScoreHandle = new TypeScoreHandle();
        DefaultTableModel modelScoreManage = (DefaultTableModel) table1.getModel();
        modelScoreManage.setRowCount(0);
        this.a = null;
        try {
            a = TypeScoreHandle.SELECT("SELECT * FROM type_score");
        } catch (SQLException e){ throw  new RuntimeException(e);}
        Iterator<TypeScore> typeScoreIterator = a.iterator();
        while (typeScoreIterator.hasNext()){
            TypeScore typeScore = typeScoreIterator.next();
            modelScoreManage.addRow(new Object[]{true,typeScore.getID(),typeScore.getName()});
        }
    }
    private JPanel panel1;
    private JTable table1;
    private JButton chọnẢnhButton;
    private JButton insert;
    private JButton update;
    private JButton tảiLạiButton;
    private JButton delete;
    private JComboBox comboBox1;
    private JTextField ts_id;
    private JTextField name;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
}
