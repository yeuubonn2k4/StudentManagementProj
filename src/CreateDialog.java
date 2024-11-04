import enums.FieldEnum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateDialog {
    // Method to open a dialog for creating a new record
    public static void openCreateDialog(
            Frame frame, DefaultTableModel tableModel, Component parentComponent) {
        JDialog dialog = new JDialog(frame, "Thêm sinh viên", true);
        dialog.setSize(300, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel labelMaSV = new JLabel(FieldEnum.MA_SV.getFieldName());
        JTextField fieldMaSV = new JTextField();
        JLabel labelHoVaTen = new JLabel(FieldEnum.HO_VA_TEN.getFieldName());
        JTextField fieldHoVaTen = new JTextField();
        JLabel labelLop = new JLabel(FieldEnum.LOP.getFieldName());
        JTextField fieldLop = new JTextField();
        JLabel labelDiemGPA = new JLabel(FieldEnum.DIEM_GPA.getFieldName());
        JTextField fieldDiemGPA = new JTextField();

        dialog.add(labelMaSV);
        dialog.add(fieldMaSV);
        dialog.add(labelHoVaTen);
        dialog.add(fieldHoVaTen);
        dialog.add(labelLop);
        dialog.add(fieldLop);
        dialog.add(labelDiemGPA);
        dialog.add(fieldDiemGPA);

        JButton addButton = new JButton("Thêm");
        JButton cancelButton = new JButton("Quay lại");

        addButton.addActionListener(e -> {
            if (!FieldValidation.validateFields(parentComponent, fieldMaSV.getText(), fieldHoVaTen.getText(), fieldLop.getText())) {
                return;
            }
            try (Connection conn = DatabaseConnectionUtils.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "INSERT INTO SinhVien (maSV, hoVaTen, lop, diemGPA) VALUES (?, ?, ?, ?)")) {
                ps.setString(1, fieldMaSV.getText());
                ps.setString(2, fieldHoVaTen.getText());
                ps.setString(3, fieldLop.getText());
                ps.setDouble(4, Double.parseDouble(fieldDiemGPA.getText()));
                ps.executeUpdate();
                FetchRecordsFromDB.fetchRecords(tableModel, parentComponent);
                JOptionPane.showMessageDialog(parentComponent, "Tạo dữ liệu sinh viên thành công!");
                dialog.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                if (ex.getMessage().contains("Duplicate entry")) {
                    JOptionPane.showMessageDialog(parentComponent, "Mã sinh viên " + fieldMaSV.getText() + " đã tồn tại!");
                } else {
                    JOptionPane.showMessageDialog(parentComponent, "Lỗi tạo dữ liệu sinh viên: " + ex.getMessage());
                }
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (InstantiationException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.add(addButton);
        dialog.add(cancelButton);

        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
    }
}
