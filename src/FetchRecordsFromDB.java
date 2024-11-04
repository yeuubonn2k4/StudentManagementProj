import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;

public class FetchRecordsFromDB {
    // Method to fetch records from the database
    public static void fetchRecords(DefaultTableModel tableModel, Component parentComponent) {
        try (Connection conn = DatabaseConnectionUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            tableModel.setRowCount(0); // Clear existing data
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("maSV"),
                        rs.getString("hoVaTen"),
                        rs.getString("lop"),
                        rs.getFloat("diemGPA")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parentComponent, "Error fetching records: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fetchRecords2(int page,
                                     Component parentComponent,
                                     JLabel pageLabel,
                                     JButton prevButton,
                                     JButton nextButton,
                                     DefaultTableModel tableModel) {
        fetchTotalRecords(parentComponent);
        if (page < 1 || page > GlobalVariables.totalPages) {
            return; // Do nothing if the page is out of bounds
        }

        try (Connection conn = DatabaseConnectionUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM students LIMIT ? offset ?")) {

            ps.setInt(1, GlobalVariables.recordsPerPage);
            ps.setInt(2, GlobalVariables.recordsPerPage * GlobalVariables.currentPage - GlobalVariables.recordsPerPage);

            ResultSet rs = ps.executeQuery();

            tableModel.setRowCount(0); // Clear the table
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("maSV"),
                        rs.getString("hoVaTen"),
                        rs.getString("lop"),
                        rs.getFloat("diemGPA")
                });
            }

            // Update pagination controls
            pageLabel.setText("Trang: " + page);
            prevButton.setEnabled(page > 1);
            nextButton.setEnabled(page < GlobalVariables.totalPages);
            if (page < GlobalVariables.totalPages) {
                GlobalVariables.currentPage += 1;
            } else {
                GlobalVariables.currentPage = page;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parentComponent, "Lỗi khi lấy danh sách sinh viên: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to fetch the total number of records
    public static void fetchTotalRecords(Component parentComponent) {
        try (Connection conn = DatabaseConnectionUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM students")) {

            if (rs.next()) {
                GlobalVariables.totalRecords = rs.getInt(1);
                GlobalVariables.totalPages = (int) Math.ceil((double) GlobalVariables.totalRecords / GlobalVariables.recordsPerPage);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parentComponent, "Lỗi khi lấy tất số lượng sinh viên: " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
