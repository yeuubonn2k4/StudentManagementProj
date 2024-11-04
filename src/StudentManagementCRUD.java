import enums.ButtonEnum;
import enums.ColumnEnum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;

public class StudentManagementCRUD extends JFrame {

    // UI Components
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton createButton, readButton, updateButton, deleteButton, deleteAllButton;

    // Constructor
    public StudentManagementCRUD() {
        // Set up the JFrame
        setTitle("Quản lý sinh viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Set up the table model and JTable
        String[] columnNames = {ColumnEnum.MA_SV.getColumnName(),
                ColumnEnum.HO_VA_TEN.getColumnName(), ColumnEnum.LOP.getColumnName(),
                ColumnEnum.DIEM_GPA.getColumnName()};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Set up the buttons
        createButton = new JButton(ButtonEnum.CREATE_BUTTON.getButtonValue());
        readButton = new JButton(ButtonEnum.READ_BUTTON.getButtonValue());
        updateButton = new JButton(ButtonEnum.UPDATE_BUTTON.getButtonValue());
        deleteButton = new JButton(ButtonEnum.DELETE_ONE_RECORD_BUTTON.getButtonValue());
        deleteAllButton = new JButton(ButtonEnum.DELETE_ALL_BUTTON.getButtonValue());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(deleteAllButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button action listeners
        createButton.addActionListener(e -> CreateDialog.openCreateDialog( this, tableModel, this));
        readButton.addActionListener(e -> {
            FetchRecordsFromDB.fetchRecords(tableModel, this);
            JOptionPane.showMessageDialog(this, "Hiển thị tất cả thông tin sinh viên thành công.");
        });
        updateButton.addActionListener(e -> UpdateDialog.openUpdateDialog(table, tableModel, this, this
                ));
        deleteButton.addActionListener(e -> DeleteRecordAction.deleteRecord(table, this, tableModel));
        deleteAllButton.addActionListener(e -> DeleteRecordAction.deleteAllRecords(tableModel, this));

        // Fetch records at startup
//        FetchRecordsFromDB.fetchRecords(
//                tableModel,this);

        setVisible(true);
    }
}
