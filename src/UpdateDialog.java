import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDialog {
    // Method to open a dialog for updating a selected record
    public static void openUpdateDialog(JLabel pageLabel,
                                        JButton prevButton, JButton nextButton,
                                        JTable table, DefaultTableModel tableModel, Component parentComponent, Frame frame) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parentComponent, "Hãy chọn dữ liệu sinh viên.");
            return;
        }

        String maSV = (String) tableModel.getValueAt(selectedRow, 0);
        JDialog dialog = new JDialog(frame, "Cập nhật thông tin sinh viên", true);
        dialog.setSize(300, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel labelMaSV = new JLabel("Mã Sinh viên: ");
        JTextField fieldMaSV = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        JLabel labelHoVaTen = new JLabel("Họ và tên: ");
        JTextField fieldHoVaTen = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        JLabel labelLop = new JLabel("Lớp: ");
        JTextField fieldLop = new JTextField((String) tableModel.getValueAt(selectedRow, 3));
        JLabel labelDiemGPA = new JLabel("Điểm GPA: ");
        JTextField fieldDiemGPA = new JTextField(tableModel.getValueAt(selectedRow, 4).toString());

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
            try (Connection conn = DatabaseConnectionUtils.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "UPDATE students SET maSV = ?, hoVaTen = ?, lop = ?, diemGPA = ? WHERE maSV = ?")) {
                ps.setString(1, fieldMaSV.getText());
                ps.setString(2, fieldHoVaTen.getText());
                ps.setString(3, fieldLop.getText());
                ps.setFloat(4, Float.parseFloat(fieldDiemGPA.getText()));
                ps.setString(5, maSV);
                ps.executeUpdate();
                FetchRecordsFromDB.fetchRecords2(GlobalVariables.currentPage,
                        parentComponent, pageLabel, prevButton, nextButton,
                        tableModel);
                JOptionPane.showMessageDialog(parentComponent, "Cập nhật dữ liệu sinh viên thành công!");
                dialog.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentComponent, "Lỗi khi cập nhật sinh viên: " + ex.getMessage());
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
