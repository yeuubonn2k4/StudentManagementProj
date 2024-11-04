import enums.FieldEnum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDialog {
    // Method to open a dialog for updating a selected record
    public static void openUpdateDialog(JTable table, DefaultTableModel tableModel, Component parentComponent, Frame frame) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parentComponent, "Hãy chọn dữ liệu sinh viên.");
            return;
        }

        String maSV = (String) tableModel.getValueAt(selectedRow, 0);
        JDialog dialog = new JDialog(frame, "Cập nhật thông tin sinh viên", true);
        dialog.setSize(300, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel labelMaSV = new JLabel(FieldEnum.MA_SV.getFieldName());
        JTextField fieldMaSV = new JTextField((String) tableModel.getValueAt(selectedRow, 0));
        JLabel labelHoVaTen = new JLabel(FieldEnum.HO_VA_TEN.getFieldName());
        JTextField fieldHoVaTen = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        JLabel labelLop = new JLabel(FieldEnum.LOP.getFieldName());
        JTextField fieldLop = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        JLabel labelDiemGPA = new JLabel(FieldEnum.DIEM_GPA.getFieldName());
        JTextField fieldDiemGPA = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());

        dialog.add(labelMaSV);
        dialog.add(fieldMaSV);
        dialog.add(labelHoVaTen);
        dialog.add(fieldHoVaTen);
        dialog.add(labelLop);
        dialog.add(fieldLop);
        dialog.add(labelDiemGPA);
        dialog.add(fieldDiemGPA);

        JButton updateButton = new JButton("Cập nhật");
        JButton cancelButton = new JButton("Quay lại");

        updateButton.addActionListener(e -> {
            if (!FieldValidation.validateFields(parentComponent, fieldMaSV.getText(), fieldHoVaTen.getText(), fieldLop.getText())) {
                return;
            }
            try (Connection conn = DatabaseConnectionUtils.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "UPDATE SinhVien SET maSV = ?, hoVaTen = ?, lop = ?, diemGPA = ? WHERE maSV = ?")) {
                ps.setString(1, fieldMaSV.getText());
                ps.setString(2, fieldHoVaTen.getText());
                ps.setString(3, fieldLop.getText());
                ps.setDouble(4, Double.parseDouble(fieldDiemGPA.getText()));
                ps.setString(5, maSV);
                ps.executeUpdate();
                FetchRecordsFromDB.fetchRecords(
                        tableModel, parentComponent);
                JOptionPane.showMessageDialog(parentComponent, "Cập nhật dữ liệu sinh viên thành công!");
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
        dialog.add(updateButton);
        dialog.add(cancelButton);

        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
    }
}
