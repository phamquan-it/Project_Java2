package com.score.management;

import GUI.Login;
import GUI.MainGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) throws AWTException {
        new Login();
      //  new MainGUI();
        if (!SystemTray.isSupported()) {
            System.err.println("System tray feature is not supported");
            return;
        }

       if(SystemTray.isSupported()){
            SystemTray systemTray = SystemTray.getSystemTray();
            TrayIcon trayIcon = new TrayIcon(new ImageIcon("./icon.png").getImage());
            trayIcon.setImageAutoSize(true);
           MenuItem aboutItem = new MenuItem("Giới thiệu");

           MenuItem exitItem = new MenuItem("Thoát");
           aboutItem.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent actionEvent) {
                   trayIcon.displayMessage("Thông báo", "Cấm dùng chùa nha! hehe",TrayIcon.MessageType.INFO);
               }
           });

           Timer timer = new Timer(1000, new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   LocalDateTime now = LocalDateTime.now();

                   // Định dạng theo 12 giờ
                   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

                   // Chuyển đổi sang định dạng 12 giờ
                   String formattedDateTime = now.format(formatter);

                   // In ra màn hình
                   trayIcon.displayMessage("Quản lý điểm học sinh", "Bạn cần kích hoạt QLĐHS_PRO để sử dụng các năng tiện ích nhất",TrayIcon.MessageType.INFO);
               }
           });
           timer.setRepeats(false);
           timer.start();
           exitItem.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent actionEvent) {
                   System.exit(1);
               }
           });
           final PopupMenu popup = new PopupMenu();
           //Add components to pop-up menu
           popup.add(aboutItem);
           popup.addSeparator();
           popup.add(exitItem);

           trayIcon.setPopupMenu(popup);

           try {
               systemTray.add(trayIcon);
           } catch (AWTException e) {
               System.out.println("TrayIcon could not be added.");
           }
       }else System.out.println("SystemTray is not supported");
    }

}