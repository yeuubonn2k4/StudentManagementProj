import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateDialog {
    // Method to open a dialog for creating a new record
    public static void openCreateDialog(
            int page, int totalPage, int recordPerPage, JLabel pageLabel,
            JButton prevButton, JButton nextButton,
            Frame frame, DefaultTableModel tableModel, Component parentComponent) {
        JDialog dialog = new JDialog(frame, "Thêm sinh viên", true);
        dialog.setSize(300, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel labelMaSV = new JLabel("MaSV:");
        JTextField fieldMaSV = new JTextField();
        JLabel labelHoVaTen = new JLabel("Ho va ten:");
        JTextField fieldHoVaTen = new JTextField();
        JLabel labelLop = new JLabel("Lop:");
        JTextField fieldLop = new JTextField();
        JLabel labelDiemGPA = new JLabel("Diem GPA:");
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
            try (Connection conn = DatabaseConnectionUtils.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "INSERT INTO students (maSV, hoVaTen, lop, diemGPA) VALUES (?, ?, ?, ?)")) {
                ps.setString(1, fieldMaSV.getText());
                ps.setString(2, fieldHoVaTen.getText());
                ps.setString(3, fieldLop.getText());
                ps.setFloat(4, Float.parseFloat(fieldDiemGPA.getText()));
                ps.executeUpdate();
                FetchRecordsFromDB.fetchRecords2(GlobalVariables.currentPage,
                         parentComponent, pageLabel,
                        prevButton, nextButton,
                        tableModel);
                JOptionPane.showMessageDialog(parentComponent, "Tạo dữ liệu sinh viên thành công!");
                dialog.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentComponent, "Lỗi tạo dữ liệu sinh viên: " + ex.getMessage());
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
