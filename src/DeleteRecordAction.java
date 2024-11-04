import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;

public class DeleteRecordAction {

    // Method to delete a selected record
    public static void deleteRecord(JTable table, Component parentComponent, DefaultTableModel tableModel) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parentComponent, "Hãy chọn dữ liệu sinh viên để xóa.");
            return;
        }

        String maSV = (String) tableModel.getValueAt(selectedRow, 0);
        String hoVaTen = (String) tableModel.getValueAt(selectedRow, 1);
        String lop = (String) tableModel.getValueAt(selectedRow, 2);
        Double diemGPA = (Double) tableModel.getValueAt(selectedRow, 3);

        int confirmation = JOptionPane.showConfirmDialog(parentComponent,
                "Bạn có muốn xóa dữ liệu này không? \n"
                + "Mã Sinh viên: " + maSV + "\n"
                        + "Họ và tên: " + hoVaTen + "\n"
                        + "Lớp: " + lop + "\n"
                        + "Điểm GPA: " + diemGPA + "\n"
                ,
                "Xóa sinh viên",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnectionUtils.getConnection();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM SinhVien WHERE maSV = ?")) {
                ps.setString(1, maSV);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(parentComponent, "Xóa dữ liệu thành công!");
                FetchRecordsFromDB.fetchRecords(
                        tableModel, parentComponent);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentComponent, "Error deleting record: " + ex.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void deleteAllRecords(DefaultTableModel tableModel, Component parentComponent
    ) {
        int confirmation = JOptionPane.showConfirmDialog(parentComponent,
                "Bạn có chắc muốn xóa tất cả dữ liệu?",
                "Xóa tất cả dữ liệu sinh viên",
                JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnectionUtils.getConnection();
                 Statement stmt = conn.createStatement();
            ) {
                // Delete all records from the database
                stmt.executeUpdate("DELETE FROM SinhVien");

                // Clear the table model
                tableModel.setRowCount(0);

                GlobalVariables.totalRecords = 0;
                GlobalVariables.totalPages = 0;
                GlobalVariables.currentPage = 1;
                JOptionPane.showMessageDialog(parentComponent, "Xóa tất cả dữ liệu thành công.");

                FetchRecordsFromDB.fetchRecords(tableModel, parentComponent);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parentComponent, "Lỗi xảy ra khi xóa tất cả dữ liệu: " + ex.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
