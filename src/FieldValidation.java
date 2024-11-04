import javax.swing.*;
import java.awt.*;

public class FieldValidation {
    public static boolean validateFields(Component parentComponent, String maSV, String hoVaTen, String lop) {
        if (maSV.isEmpty()) {
            JOptionPane.showMessageDialog(parentComponent, "Mã sinh viên không được để trống!");
            return false;
        }
        if (hoVaTen.isEmpty()) {
            JOptionPane.showMessageDialog(parentComponent, "Họ và tên sinh viên không được để trống!");
            return false;
        }
        if (lop.isEmpty()) {
            JOptionPane.showMessageDialog(parentComponent, "Tên lớp không được để trống!");
            return false;
        }
        return true;
    }
}
