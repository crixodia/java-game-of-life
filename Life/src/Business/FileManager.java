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
package Business;

import Interface.LifeGui;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Cristian Bastidas
 */
public class FileManager {

    private final static Pattern POSITION_FORMAT = Pattern.compile("[0-9]+:[0-9]+;");

    /**
     * This function loads a file with the grid's values
     *
     * @param path Directory's path for loading a file with values
     * @param frame A JFrame
     * @return A matrix[ROWS][COLS] where rows and columns are gotten from
     * LifeGui
     */
    public static boolean[][] loadFile(String path, JFrame frame) {
        boolean[][] matrix = new boolean[LifeGui.ROWS][LifeGui.COLS];
        try {
            try ( FileReader reader = new FileReader(path)) {
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] position;
                    //Is the correct format?
                    Matcher posMatcher = POSITION_FORMAT.matcher(line);
                    if (posMatcher.find()) {
                        position = line.replaceAll("\n", "").replace(";", "").split(":");
                        matrix[Integer.parseInt(position[0])][Integer.parseInt(position[1])] = true;
                    } else {
                        JOptionPane.showMessageDialog(
                                frame,
                                "File corrupted",
                                "LifeGUI",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    frame, e.getMessage(),
                    "LifeGUI",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return matrix;
    }

    /**
     * This function save a file with the grid's values
     *
     * @param path Directory's path for saving a file with values
     * @param matrix Grid's matrix with values
     * @param frame A frame
     */
    public static void saveFile(String path, boolean[][] matrix, JFrame frame) {
        String content = "";
        for (int i = 0; i < LifeGui.ROWS; i++) {
            for (int j = 0; j < LifeGui.COLS; j++) {
                if (matrix[i][j]) {
                    content += i + ":" + j + ";\n"; //Formatting ROWS:COLS;
                }
            }
        }
        try {
            try ( FileWriter writer = new FileWriter(path, true)) {
                writer.write(content);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "LifeGUI", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean[][] transformFile(String path) {
        boolean[][] matrix = new boolean[LifeGui.ROWS][LifeGui.COLS];
        return matrix;
    }

}
