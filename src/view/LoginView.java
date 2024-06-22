package view;

import business.UserManager;
import core.Helper;

import entities.User;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends Layout {
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_welcome;
    private JLabel lbl_welcome2;
    private JPanel w_bottom;
    private JTextField fld_username;
    private JPasswordField fld_pass;
    private JButton btn_login;
    private JLabel lbl_username;
    private JLabel lbl_pass;
    private final UserManager userManager;

    public LoginView() {
        this.userManager = new UserManager();
        this.add(container);
        this.guiInit(400, 400, "Login Panel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btn_login.addActionListener(e -> {
            JTextField[] checkFieldsList = {fld_username, fld_pass};
        if (Helper.isFieldListEmpty(checkFieldsList)) {
            Helper.showMessage("fill");
        }else{
            User loginUser = userManager.findByLogin(fld_username.getText(), fld_pass.getText());
            if(loginUser == null){
                Helper.showMessage("invalid");
            }else{
                Helper.showMessage("success");
                new AdminView(loginUser);
                dispose();
            }
        }
        });
    }
}
