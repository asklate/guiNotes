package net.askearly.model;

import java.io.File;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class NoteTableModel extends AbstractTableModel {

    private List<Note> dataList;
    private final String[] columnNames = {"ID", "Title", "Content", "Filename"};

    public NoteTableModel(List<Note> dataList) {
        this.dataList = dataList;
    }

    public void setDataList(List<Note> dataList) {
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
        Note note = dataList.get(row);
        switch (col) {
            case 0: return note.getId();
            case 1: return note.getTitle();
            case 2: return note.getContent();
            case 3: return note.getFilename() == null ? "" :  new File(note.getFilename()).toPath().getFileName().toString();
            default: return null;
        }
    }

    // Optional: Make cells editable
    @Override
    public boolean isCellEditable(int row, int col) {
        return false; // All cells are editable
    }

    // Optional: Update the underlying data when a user edits a cell
    @Override
    public void setValueAt(Object value, int row, int col) {
        Note note = dataList.get(row);
        switch (col) {
            case 0: note.setId((long) value); break;
            case 1: note.setTitle((String) value); break;
            case 2: note.setContent((String) value); break;
            case 3: note.setFilename((String) value); break;
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
