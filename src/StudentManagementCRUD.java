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
    private JButton createButton, readButton, updateButton, deleteButton, prevButton, nextButton, deleteAllButton;
    private TableColumn hiddenColumn; // To store the hidden column

    private JLabel pageLabel;


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
        prevButton = new JButton(ButtonEnum.PREV_BUTTON.getButtonValue());
        nextButton = new JButton(ButtonEnum.NEXT_BUTTON.getButtonValue());
        deleteAllButton = new JButton(ButtonEnum.DELETE_ALL_BUTTON.getButtonValue());
        pageLabel = new JLabel("Trang: 1");

        JPanel buttonPanel = new JPanel();
        JPanel paginationPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(deleteAllButton);

        paginationPanel.add(prevButton);
        paginationPanel.add(pageLabel);
        paginationPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);
        add(paginationPanel, BorderLayout.NORTH);

        // Button action listeners
        createButton.addActionListener(e -> CreateDialog.openCreateDialog(GlobalVariables.currentPage, GlobalVariables.totalPages,
                GlobalVariables.recordsPerPage, pageLabel, prevButton, nextButton, this, tableModel, this));
        readButton.addActionListener(e -> FetchRecordsFromDB.fetchRecords2(GlobalVariables.currentPage,
                this, pageLabel, prevButton, nextButton, tableModel));
        updateButton.addActionListener(e -> UpdateDialog.openUpdateDialog(pageLabel,
                prevButton, nextButton, table, tableModel, this, this
                ));
        deleteButton.addActionListener(e -> DeleteRecordAction.deleteRecord(pageLabel,
                prevButton, nextButton, table, this, tableModel));
        prevButton.addActionListener(e -> FetchRecordsFromDB.fetchRecords2(GlobalVariables.currentPage - 1,
                this, pageLabel, prevButton, nextButton, tableModel));
        nextButton.addActionListener(e -> FetchRecordsFromDB.fetchRecords2(GlobalVariables.currentPage + 1,
                this, pageLabel, prevButton, nextButton, tableModel));
        deleteAllButton.addActionListener(e -> DeleteRecordAction.deleteAllRecords(tableModel, this, pageLabel, prevButton, nextButton));

        // Fetch records at startup
        FetchRecordsFromDB.fetchTotalRecords(this);
        FetchRecordsFromDB.fetchRecords2(GlobalVariables.currentPage,
                this, pageLabel, prevButton, nextButton, tableModel);

        setVisible(true);
    }
}
