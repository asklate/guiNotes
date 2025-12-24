package net.askearly.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ContextMenuTextArea extends JTextArea {

    public ContextMenuTextArea(int rows, int columns) {
        super(null, null, rows, columns);

        setLineWrap(true);
        setWrapStyleWord(true);
        setMargin(new Insets(10, 10, 10, 10));
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        // Create popup menu
        JPopupMenu popupMenu = new JPopupMenu();

        // Cut
        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(e -> cut());
        popupMenu.add(cutItem);

        // Copy
        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(e -> copy());
        popupMenu.add(copyItem);

        // Paste
        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(e -> paste());
        popupMenu.add(pasteItem);

        // Add mouse listener to show popup menu
        addMouseListener(new MouseAdapter() {
            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    // Enable/disable menu items based on selection/clipboard
                    cutItem.setEnabled(getSelectedText() != null);
                    copyItem.setEnabled(getSelectedText() != null);

                    // Check if clipboard has text for paste
                    try {
                        boolean canPaste = Toolkit.getDefaultToolkit()
                                .getSystemClipboard()
                                .isDataFlavorAvailable(java.awt.datatransfer.DataFlavor.stringFlavor);
                        pasteItem.setEnabled(canPaste);
                    } catch (Exception ex) {
                        pasteItem.setEnabled(false);
                    }

                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
        });
    }
}
