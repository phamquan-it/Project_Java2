package Controllers.Authenlication;

import Controllers.Validation;
import DAO.Access.InstructorHandle;
import DAO.JDBCDriver;
import GUI.MainGUI;
import Model.Instructor;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authenlication {
    //Session Login
    public static Instructor insLogin = new Instructor();
    public static Instructor InstructorAccess;
    public static Boolean Login(String email, String password) throws SQLException {
        if(!Validation.isEmail(email)){
            JOptionPane.showMessageDialog( null,"Địa chỉ email không hợp lệ"); return false;
        }
        if(!Validation.isStrongPassword(password)){
            JOptionPane.showMessageDialog( null,"Mật khẩu không hợp lệ");
            System.out.println("Password is not valid"); return false;
        }
        ResultSet resultSet =JDBCDriver.ExecQuery("SELECT * FROM instructor WHERE Email = "+"'"+email+"'");

        while (resultSet.next()){
            insLogin.setEmail(resultSet.getString("Email"));
            insLogin.setPassword(resultSet.getString("Password"));
            insLogin.setName(resultSet.getString("name"));
            insLogin.setID_NUMBER(resultSet.getInt("ID_NUMBER"));
            if(email.equals(insLogin.getEmail()) && password.equals(insLogin.getPassword())){
                new MainGUI();
                return true;
            }else {
                JOptionPane.showMessageDialog( null,"Thông tin tài khoản hoặc mật khẩu không chính xác");
                return false;
            }
        }
        JOptionPane.showMessageDialog( null,"Email không tồn tại");
        return false;
        //do something
    }
    public static void Register(String email,String password, String fullname){
        if (!Validation.isFullName(fullname)){
            JOptionPane.showMessageDialog( null,"Tên không hợp lệ");
            return;
        }
        if(!Validation.isEmail(email)){
            JOptionPane.showMessageDialog( null,"Địa chỉ email không hợp lệ");
            return;
        }
        if(!Validation.isStrongPassword(password)){
            JOptionPane.showMessageDialog( null,"Mật khẩu không hợp lệ");
            return;
        }
        try {
            if(Validation.hasEmail(email)){
                JOptionPane.showMessageDialog( null,"Địa chỉ email đã tồn  tại");
                return;}
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
          Boolean result =  JDBCDriver.SetQuery("INSERT INTO `instructor`(`name`, `password`, `Email`,`Phone`,`birthday`) VALUES ('"+fullname+"','"+password+"','"+email+"','01234567','1999-01-01')");
          if(result){
              JOptionPane.showMessageDialog( null,"Đăng ký tài khoản thành công");
          }else {
              JOptionPane.showMessageDialog( null,"Đăng ký tài khoản không thành công");
          }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void ForgotPassword(){
        
    }
}
