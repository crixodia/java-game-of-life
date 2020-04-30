/*
 * Copyright (C) 2020 gabri
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
package life;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Cristian Bastidas
 */
public class LifeFile {

    public static boolean[][] loadFile() {
        return null;
    }

    //Saves a file with the information of tru boxes from the grid
    public static void saveFile(String path, boolean[][] matrix) {
        String content = "";
        for (int i = 0; i < LifeGui.ROWS; i++) {
            for (int j = 0; j < LifeGui.COLS; j++) {
                if (matrix[i][j]) {
                    content += i + ":" + j + ";\n"; //Formatting ROWS:COLS;
                }
            }
        }
        //System.out.println(content);
        try {
            FileWriter writer = new FileWriter(path, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
