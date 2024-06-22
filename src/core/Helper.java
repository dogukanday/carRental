package core;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String str) {
        String msg;
        String title;
        optionPaneTR();

        switch (str) {
            case "error":
                msg = "Bir hata oluştu!";
                title = "Hata";
                break;
            case "fill":
                msg = "Lütfen tüm alanları doldurunuz";
                title = "Hata";
                break;
            case "success":
                msg = "Başarılı!";
                title = "Başarılı";

                break;
            case "invalid":
                msg = "Kullanıcı adı veya şifre yanlış!";
                title = "HATA !";
                break;
            default:
                msg = "Bir hata yaşandı!";
                title = "HATA!!";
                break;
        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean isFieldListEmpty(JTextField[] field) {
        for (JTextField f : field) {
            if (isFieldEmpty(f)) return true;
        }
        return false;
    }

    public static int getLocationPoint(String type, Dimension size) {
        int point = 0;
        switch (type) {
            case "x":
                point = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - size.getWidth()) / 2;
                break;
            case "y":
                point = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - size.getHeight()) / 2;
                break;
            default:
                return 0;
        }
        return point;
    }

    public static int getTableSelectedRow(JTable table, int id) {
        return Integer.parseInt(table.getValueAt(table.getSelectedRow(), id).toString());
    }

    public static boolean confirm(String str){
        optionPaneTR();
        String msg;
        if(str.equals("sure")){
            msg = "Bu islemi yapmak istediginizde emin misiniz?";
        }else{
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null, msg, "Uyarı", JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void optionPaneTR(){
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
        UIManager.put("OptionPane.okButtonText", "Tamam");
    }
}
