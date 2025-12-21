package net.askearly.utils;

import javax.swing.*;
import java.io.*;

public class FileCopyWorker extends SwingWorker<Void, Void> {

    private final File source;
    private final File destination;
    private final JProgressBar progressBar;
    private final JFrame frame;

    public FileCopyWorker(File source, File destination, JProgressBar progressBar, JFrame frame) {
        this.source = source;
        this.destination = destination;
        this.progressBar = progressBar;
        this.frame = frame;
        // Add a listener to the worker's progress property to update the JProgressBar
        addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((Integer) evt.getNewValue());
            }
        });
    }

    @Override
    protected Void doInBackground() throws Exception {
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(destination)) {

            long totalBytes = source.length();
            long bytesCopied = 0;
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                bytesCopied += bytesRead;
                // Calculate and set progress (0-100)
                int progress = (int) ((bytesCopied * 100) / totalBytes);
                setProgress(progress);
            }
        }
        return null;
    }

    @Override
    protected void done() {
        frame.dispose();
    }
}
