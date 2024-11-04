import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;

public class DeleteRecordAction {

    // Method to delete a selected record
    public static void deleteRecord(JLabel pageLabel,
                                    JButton prevButton, JButton nextButton, JTable table, Component parentComponent, DefaultTableModel tableModel) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parentComponent, "Hãy chọn dữ liệu sinh viên để xóa.");
            return;
        }

        String maSV = (String) tableModel.getValueAt(selectedRow, 0);
        int confirmation = JOptionPane.showConfirmDialog(parentComponent,
                "Bạn có muốn xóa dữ liệu này không?",
                "Yes",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnectionUtils.getConnection();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE maSV = ?")) {
                ps.setString(1, maSV);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(parentComponent, "Xóa dữ liệu thành công!");
                FetchRecordsFromDB.fetchRecords2(GlobalVariables.currentPage,
                         parentComponent, pageLabel, prevButton, nextButton, tableModel);
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

    public static void deleteAllRecords(DefaultTableModel tableModel, Component parentComponent, JLabel pageLabel,
        JButton prevButton, JButton nextButton
    ) {
        int confirmation = JOptionPane.showConfirmDialog(parentComponent,
                "Bạn có chắc muốn xóa tất cả dữ liệu?",
                "Yes",
                JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnectionUtils.getConnection();
                 Statement stmt = conn.createStatement();
            ) {
                // Delete all records from the database
                stmt.executeUpdate("DELETE FROM students");

                // Clear the table model
                tableModel.setRowCount(0);

                GlobalVariables.totalRecords = 0;
                GlobalVariables.totalPages = 0;
                GlobalVariables.currentPage = 1;

                pageLabel.setText("Page: " + 1);
                prevButton.setEnabled(false);
                nextButton.setEnabled(false);

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
