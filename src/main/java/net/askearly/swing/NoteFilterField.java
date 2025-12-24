package net.askearly.swing;

import net.askearly.model.NoteTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.regex.PatternSyntaxException;

public class NoteFilterField extends JTextField {

    public NoteFilterField(JTable table, NoteTableModel model) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                newFilter(sorter);
            }
            public void insertUpdate(DocumentEvent e) {
                newFilter(sorter);
            }
            public void removeUpdate(DocumentEvent e) {
                newFilter(sorter);
            }
        });
    }

    private void newFilter(TableRowSorter<TableModel> sorter) {
        if (getText().trim().isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }

        RowFilter<TableModel, Object> rf;

        try {
            rf = RowFilter.regexFilter("(?i)" + getText());
        } catch (PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }
}
