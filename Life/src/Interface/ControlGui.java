/*
 * Copyright (C) 2020 Cristian Bastidas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Interface;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import Business.FileManager;
import Business.GifSequenceWriter;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

/**
 *
 * @author Cristian Bastidas
 */
public class ControlGui extends javax.swing.JFrame {

    //Grid of boxes
    LifeGui GameWindow = new LifeGui();

    private final ScheduledExecutorService scheduler;
    final Runnable run;
    ScheduledFuture<?> runHandle;

    public File input, output;

    Color bck, grid, state;
    boolean nftOption = false;

    /**
     * Creates new form ControlGui
     */
    public ControlGui() {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.run = () -> {
            if (nftOption) {
                try {
                    GameWindow.genImage(output, bck, grid, state);
                } catch (IOException ex) {
                    Logger.getLogger(ControlGui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            GameWindow.changeState();
            int genAux = GameWindow.generation, popAux = GameWindow.population;
            lblPopulation.setText(Integer.toString(popAux));
            lblGeneration.setText(Integer.toString(genAux));
        };
        //this.runHandle = scheduler.scheduleAtFixedRate(run, 1, 3, SECONDS);

        initComponents();

        //Our own file type
        FileNameExtensionFilter ff;
        ff = new FileNameExtensionFilter("Java game of life file .jglf", "jglf");

        fileDialog.addChoosableFileFilter(ff);
        fileDialog.setFileFilter(ff);
        fileDialog.setSelectedFile(new File("new.jglf"));   //Suggested filename

        this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("grid.png")).getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileDialog = new javax.swing.JFileChooser();
        btnSave = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnPlayPause = new javax.swing.JToggleButton();
        generation = new javax.swing.JLabel();
        population = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        btnAbout = new javax.swing.JButton();
        btnGithub = new javax.swing.JButton();
        lblPopulation = new javax.swing.JLabel();
        lblGeneration = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        BtnNFT = new javax.swing.JButton();
        CheckLoop = new javax.swing.JCheckBox();

        fileDialog.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        fileDialog.setApproveButtonText("");
        fileDialog.setApproveButtonToolTipText("");
        fileDialog.setDialogTitle("Choose a folder");
        fileDialog.setFileFilter(null);
        fileDialog.setDragEnabled(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Game of Life");
        setAlwaysOnTop(true);
        setName("Control"); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnOpen.setText("Open");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        btnPlayPause.setText("Play");
        btnPlayPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPauseActionPerformed(evt);
            }
        });

        generation.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        generation.setLabelFor(lblGeneration);
        generation.setText("Generation:");
        generation.setToolTipText("");

        population.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        population.setLabelFor(lblPopulation);
        population.setText("Population:");

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnAbout.setText("About");
        btnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutActionPerformed(evt);
            }
        });

        btnGithub.setText("GitHub");
        btnGithub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGithubActionPerformed(evt);
            }
        });

        lblPopulation.setText("0");

        lblGeneration.setText("0");
        lblGeneration.setToolTipText("");

        BtnNFT.setText("GIF Generator");
        BtnNFT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNFTActionPerformed(evt);
            }
        });

        CheckLoop.setText("Loop GIF");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAbout, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGithub, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnPlayPause, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnNFT, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(generation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(population, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGeneration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPopulation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(CheckLoop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnOpen)
                    .addComponent(btnClear))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(btnPlayPause))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(population)
                            .addComponent(lblPopulation))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(generation)
                            .addComponent(lblGeneration)
                            .addComponent(BtnNFT))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CheckLoop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAbout)
                    .addComponent(btnGithub))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        GameWindow.setVisible(true);
        this.toFront();
    }//GEN-LAST:event_formWindowOpened

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        GameWindow.clear();
        nftOption = false;
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
        About aboutWindow = new About(this);
        aboutWindow.setVisible(true);
        this.setEnabled(false);
        aboutWindow.toFront();
    }//GEN-LAST:event_btnAboutActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        //Shows dialog
        int fd = fileDialog.showDialog(this, "Save");
        if (fd == JFileChooser.APPROVE_OPTION) {
            File path = fileDialog.getSelectedFile();
            if (path.exists()) {    //If file exists ask for a confirmation
                int reply = JOptionPane.showConfirmDialog(
                        this,
                        "This file already exists.\n"
                        + "Do you want to overwrite this file?",
                        "LifeGUI",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (reply == JOptionPane.YES_OPTION) {
                    if (path.delete()) {
                        FileManager.saveFile(path.toString(), GameWindow.matrix, this);
                        GameWindow.setTitle("Game of Life @ " + path.toString());
                    } else {
                        JOptionPane.showConfirmDialog(
                                this,
                                "An error has occurred.",
                                "LifeGUI",
                                JOptionPane.ERROR,
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            } else {
                FileManager.saveFile(path.toString(), GameWindow.matrix, this);
                GameWindow.setTitle("Game of Life @ " + path.toString());
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        //Shows dialog
        int fd = fileDialog.showDialog(this, "Open");
        if (fd == JFileChooser.APPROVE_OPTION) {
            File path = fileDialog.getSelectedFile();
            GameWindow.clear();
            GameWindow.setMatrix(FileManager.loadFile(path.toString(), this));
            GameWindow.setTitle("Game of Life @ " + path.toString());
        }
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnGithubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGithubActionPerformed
        try {
            java.awt.Desktop.getDesktop().browse(
                    java.net.URI.create("https://github.com/crixodia/java-game-of-life")
            );
        } catch (IOException ex) {
            Logger.getLogger(ControlGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGithubActionPerformed


    private void btnPlayPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayPauseActionPerformed
        if (btnPlayPause.isSelected()) {
            //Game has started
            btnSave.setEnabled(false);
            btnOpen.setEnabled(false);
            btnClear.setEnabled(false);
            BtnNFT.setEnabled(false);
            CheckLoop.setEnabled(false);

            this.runHandle = scheduler.scheduleAtFixedRate(
                    run,
                    0,
                    500,
                    MILLISECONDS
            );
            btnPlayPause.setText("Stop");
        } else {
            btnSave.setEnabled(true);
            btnOpen.setEnabled(true);
            btnClear.setEnabled(true);
            BtnNFT.setEnabled(true);
            CheckLoop.setEnabled(true);

            //Game has stopped
            runHandle.cancel(true);
            if (nftOption) {
                // create a new BufferedOutputStream with the last argument
                boolean looped = this.CheckLoop.isSelected();
                Thread genGif = new Thread(() -> {
                    ImageOutputStream out;
                    GifSequenceWriter writer;
                    try {
                        out = new FileImageOutputStream(new File(output, "animation.gif"));
                        writer = new GifSequenceWriter(
                                out,
                                ImageIO.read(GameWindow.getImagesList()[0]).getType(),
                                1,
                                looped
                        );
                        for (File imagesList : GameWindow.getImagesList()) {
                            BufferedImage nextImage = ImageIO.read(imagesList);
                            writer.writeToSequence(nextImage);
                        }
                        writer.close();
                        out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ControlGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                genGif.start();
            }
            btnPlayPause.setText("Play");
        }
    }//GEN-LAST:event_btnPlayPauseActionPerformed

    private void BtnNFTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNFTActionPerformed
        GIF nft = new GIF(this);
        nft.setVisible(true);
        this.setEnabled(false);
        nft.toFront();
    }//GEN-LAST:event_BtnNFTActionPerformed

    /**
     * Transforms hash bytes to hash string
     *
     * @param hash hash bytes
     * @return A hash string
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Transforms an array of bytes to a binary string
     *
     * @param input array of bytes
     * @return string of binary numbers based on the input
     */
    public static String convertByteArraysToBinary(byte[] input) {

        StringBuilder result = new StringBuilder();
        for (byte b : input) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                result.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return result.toString();
    }

    /**
     * Start the animation process by setting the file hash into the grid
     *
     * @param inputFile file for hashing
     * @param outputFolder folder which will contain all images
     * @param background background color
     * @param grid grid color
     * @param state active cell colors
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public void startNFT(File inputFile, File outputFolder, Color background, Color grid, Color state) throws IOException, NoSuchAlgorithmException {
        this.nftOption = true;
        this.bck = background;
        this.grid = grid;
        this.state = state;
        this.input = inputFile;
        this.output = outputFolder;

        if (inputFile.exists()) {
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            final byte[] bytes = digest.digest(Files.readAllBytes(inputFile.toPath()));

            String hash = bytesToHex(bytes);
            String binary = convertByteArraysToBinary(hash.getBytes());
            System.out.println(binary);
            boolean[][] matrix = new boolean[50][50];

            int j = 0, k = 0;
            for (int i = 0; i < binary.length(); i++) {
                matrix[k][j] = binary.charAt(i) == '1';
                if (j == 49) {
                    j = 0;
                    k++;
                } else {
                    j++;
                }
            }

            GameWindow.backgroudColor = background;
            GameWindow.gridColor = grid;
            GameWindow.cellColor = state;

            GameWindow.clear();
            GameWindow.setMatrix(matrix);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); //UIManager.getSystemLookAndFeelClassName()
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, e, "Look and feel error", 0);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ControlGui().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnNFT;
    private javax.swing.JCheckBox CheckLoop;
    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnGithub;
    private javax.swing.JButton btnOpen;
    private javax.swing.JToggleButton btnPlayPause;
    private javax.swing.JButton btnSave;
    private javax.swing.JFileChooser fileDialog;
    private javax.swing.JLabel generation;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblGeneration;
    private javax.swing.JLabel lblPopulation;
    private javax.swing.JLabel population;
    // End of variables declaration//GEN-END:variables

}
