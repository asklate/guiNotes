package net.askearly.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class JournalTableModel extends AbstractTableModel {

    private List<Journal> dataList;
    private final String[] columnNames = {"ID", "Title", "Content", "Filename"};

    public JournalTableModel(List<Journal> dataList) {
        this.dataList = dataList;
    }

    public void setDataList(List<Journal> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getRowCount() {
        return dataList.size(); // Returns the number of rows in the table
    }

    @Override
    public int getColumnCount() {
        return columnNames.length; // Returns the number of columns
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col]; // Returns the column header name
    }

    @Override
    public Object getValueAt(int row, int col) {
        Journal journal = dataList.get(row);
        return switch (col) {
            case 0 -> journal.getId();
            case 1 -> journal.getContent();
            case 2 -> journal.getCreatedDt();
            default -> null;
        };
    }

    // Optional: Make cells editable
    @Override
    public boolean isCellEditable(int row, int col) {
        return false; // All cells are NOT editable
    }

    // Optional: Update the underlying data when a user edits a cell
    @Override
    public void setValueAt(Object value, int row, int col) {
        Journal journal = dataList.get(row);
        switch (col) {
            case 0: journal.setId((long) value); break;
            case 1: journal.setContent((String) value); break;
            case 2: journal.setCreatedDt((String) value); break;
        }
        fireTableCellUpdated(row, col); // Notify the JTable that data changed
    }

    // Optional: Ensure correct cell rendering (e.g., boolean as checkbox)
    @Override
    public Class getColumnClass(int columnIndex) {
        if (dataList.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
}
