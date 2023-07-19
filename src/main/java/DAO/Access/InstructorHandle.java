package DAO.Access;

import DAO.JDBCDriver;
import DAO.MySQLSupport;
import Model.Block;
import Model.Instructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstructorHandle  extends AbsSQLAccess<Instructor>{


    @Override
    public Boolean INSERT(Instructor item) {
        return null;
    }

    @Override
    public List<Instructor> SELECT(String sql) throws SQLException {

        List<Instructor> a = new ArrayList<>();
        final ResultSet resultSet = JDBCDriver.ExecQuery(sql);
        while (resultSet.next()){
            Instructor b = new Instructor();
            b.setID_NUMBER(resultSet.getInt("ID_NUMBER"));
            b.setName(resultSet.getString("name"));
            b.setBirthday(resultSet.getDate("birthday"));
            b.setGender(resultSet.getBoolean("gender"));
            b.setPassword(resultSet.getString("passwrod "));
            a.add(b);
            }
        return a;

    }

    @Override
    public Boolean UPDATE(Instructor item) {
        Boolean result = false;
        String  sql= "UPDATE `instructor` SET `ID_NUMBER`='"+item.getID_NUMBER() +"',`name`='"+item.getName() +"',`birthday`='"+item.getBirthday() +"',`Gender`='"+item.getGender() +"',`password`='"+item.getPassword() +"' WHERE id="+item.getID_NUMBER();

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
        return null;
    }
}
