package DAO.Access;

import DAO.JDBCDriver;
import Model.Student;
import Model.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectHandle extends AbsSQLAccess<Subject> {

    @Override
    public Boolean INSERT(Subject item) {
        Boolean result = false;
        String sql;
        if(item.getID() == -1){sql = "INSERT INTO `subject`( `Name`, `Credits`, `grant_id`) VALUES ('"+item.getName()+"',"+item.getCredits()+","+ item.getGrandID()+")";}
        else {sql = "INSERT INTO `subject`(`Subject_code`, `Name`, `Credits`, `grant_id`) VALUES ("+item.getID()+",'"+item.getName()+"',"+item.getCredits()+","+ item.getGrandID()+")";}

        System.out.println(sql);
        try {
            boolean a = JDBCDriver.SetQuery(sql);
            System.out.println("them du lieu thành công:"+a);
            result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Subject> SELECT(String sql) throws SQLException {
        List<Subject> a = new ArrayList<>();
        final ResultSet resultSet = JDBCDriver.ExecQuery(sql);
        while (resultSet.next()){
            Subject b = new Subject();
            b.setID(resultSet.getInt("Subject_code"));
            b.setName(resultSet.getString("Name"));
            b.setCredits(resultSet.getInt("Credits"));
            b.setGrandID(resultSet.getInt("grant_id"));
            a.add(b);
        }
        JDBCDriver.DestroyConnection();
        return a;
    }

    @Override
    public Boolean UPDATE(Subject item) {
        Boolean result = false;
        String  sql= "UPDATE `subject` SET `Subject_code`="+item.getID()+",`Name`='"+item.getName()+"',`Credits`="+item.getCredits()+",`grant_id`="+item.getGrandID()+" WHERE `Subject_code`="+item.getID();
        System.out.println(sql);
        try {
            boolean a =JDBCDriver.SetQuery(sql);
            if (a)System.out.println("Cập nhật dữ liệu thành công");
            else System.out.println("Cập nhật dữ liệu không thành công");
            result = true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Boolean DELETE(int id) {
        try {
            JDBCDriver.SetQuery("CALL deleteFromSubject("+id+")");
            return JDBCDriver.SetQuery("DELETE FROM `subject` WHERE `Subject_code` = "+id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
