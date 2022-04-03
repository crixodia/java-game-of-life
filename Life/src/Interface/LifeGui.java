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
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Cristian Bastidas
 */
public class LifeGui extends JFrame {

    public Color backgroudColor = Color.CYAN;
    public Color gridColor = Color.GRAY;
    public Color cellColor = Color.BLUE;

    private HashMap componentMap;

    // GRID ROWS
    public static final int ROWS = 50;

    // GRID COLUMNS
    public static final int COLS = 50;

    public boolean[][] matrix;
    public int population;
    public int generation;

    ArrayList<File> imagesList = new ArrayList<>();

    private final JPanel container;

    /**
     * Grid's GUI
     */
    public LifeGui() {
        this.generation = 0;
        this.matrix = new boolean[ROWS][COLS];
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(720, 720);
        setResizable(false);
        setTitle("Game of Life - Grid view");
        setLocationRelativeTo(null);

        setBackground(Color.black);
        setLayout(new GridLayout());

        container = new JPanel(new GridLayout(ROWS, COLS));
        add(container);

        //To get the position of the boxes
        int counterCol = 0;
        int counterRow = 0;
        int counter = 0;

        for (int i = 0; i < 2500; i++) {
            //Updates column id
            counterCol = i % COLS == 0 ? 0 : counterCol + 1;

            JToggleButton bxButton = new JToggleButton();
            BoxBtn listener = new BoxBtn();

            //Set the name according to the array for the game
            bxButton.setName(Integer.toString(counterRow) + ',' + Integer.toString(counterCol));

            bxButton.setBackground(backgroudColor);
            bxButton.setBorder(BorderFactory.createLineBorder(gridColor));

            bxButton.addActionListener(listener);

            container.add(bxButton);

            //Updates the row id
            if (counter == 49) {
                counter = 0;
                counterRow++;
            } else {
                counter++;
            }
        }
        this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("eye.png")).getImage());
        createComponentMap();
    }

    //Creates a HashMap with the components created in the form
    private void createComponentMap() {
        componentMap = new HashMap<String, Component>();
        List<Component> components = getAllComponents(this);
        components.forEach((Component comp) -> {
            componentMap.put(comp.getName(), comp);
        });
    }

    //Get form's component
    private List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container) {
                compList.addAll(getAllComponents((Container) comp));
            }
        }
        return compList;
    }

    /**
     * Search for a component. You must give its name
     *
     * @param name component's name
     * @return a swing component
     */
    public Component getComponentByName(String name) {
        if (componentMap.containsKey(name)) {
            return (Component) componentMap.get(name);
        } else {
            return null;
        }
    }

    /**
     * Clears the grid | Before, All boxes are unselected
     */
    public void clear() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JToggleButton bxButton = (JToggleButton) getComponentByName(i + "," + j);
                if (bxButton != null) {
                    bxButton.setSelected(false);
                    bxButton.setBackground(backgroudColor);
                    bxButton.setBorder(BorderFactory.createLineBorder(gridColor));
                    this.matrix[i][j] = false;
                }
            }
        }
        imagesList = new ArrayList<>();
        this.generation = 0;
    }

    // Updates boolean values from the matrix
    private void reload() {
        this.population = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JToggleButton bxButton = (JToggleButton) getComponentByName(i + "," + j);
                if (bxButton != null) {
                    bxButton.setSelected(this.matrix[i][j]);
                    if (this.matrix[i][j]) {
                        this.population++;
                        bxButton.setBackground(cellColor);
                    } else {
                        bxButton.setBackground(backgroudColor);
                    }
                }
            }
        }
    }

    // Allows toroid grid
    private int rcCheck(int i, int rc) {
        int r = i;
        if (i < 0) {
            r = 49;
        } else if (i >= rc) {
            r = 0;
        }
        return r;
    }

    /**
     * Changes the grid's state TODO: This method will be used for step by step
     * feature
     */
    public void changeState() {
        this.generation++;
        boolean[][] newMatrix = new boolean[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int trueCounter = 0;
                for (int k = 0; k < 8; k++) {
                    int i_ = 0, j_ = 0;
                    switch (k) {
                        case 0:
                            i_ = rcCheck(i - 1, ROWS);
                            j_ = rcCheck(j - 1, COLS);
                            break;
                        case 1:
                            i_ = rcCheck(i - 1, ROWS);
                            j_ = j;
                            break;
                        case 2:
                            i_ = rcCheck(i - 1, ROWS);
                            j_ = rcCheck(j + 1, COLS);
                            break;
                        case 3:
                            i_ = i;
                            j_ = rcCheck(j - 1, COLS);
                            break;
                        case 4:
                            i_ = i;
                            j_ = rcCheck(j + 1, COLS);
                            break;
                        case 5:
                            i_ = rcCheck(i + 1, ROWS);
                            j_ = rcCheck(j - 1, COLS);
                            break;
                        case 6:
                            i_ = rcCheck(i + 1, ROWS);
                            j_ = j;
                            break;
                        case 7:
                            i_ = rcCheck(i + 1, ROWS);
                            j_ = rcCheck(j + 1, COLS);
                            break;
                    }
                    if (this.matrix[i_][j_]) {
                        trueCounter++;
                    }
                }
                if (this.matrix[i][j] && (trueCounter == 2 || trueCounter == 3)) {
                    newMatrix[i][j] = true;
                } else if (!this.matrix[i][j] && trueCounter == 3) {
                    newMatrix[i][j] = true;
                }
            }
        }
        this.setMatrix(newMatrix);
    }

    /**
     * Setting Boolean values and reloading the grid
     *
     * @param matrix a matrix duh!
     */
    public void setMatrix(boolean[][] matrix) {
        this.matrix = matrix;
        reload();
    }

    /**
     * Gets the grid as a Boolean matrix
     *
     * @return the state cells matrix
     */
    public boolean[][] getMatrix() {
        return matrix;
    }

    /**
     * Get current populations (living cells)
     *
     * @return the number of living cells
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Generates the animation
     *
     * @param output the gif's filename
     * @param bck background color
     * @param grid grid color
     * @param state cell color
     * @throws IOException
     */
    public void genImage(File output, Color bck, Color grid, Color state) throws IOException {
        int width = 501, height = 501;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(bck);
        g2d.fillRect(0, 0, width, height);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                g2d.setColor(grid);
                g2d.drawPolygon(new int[]{j * 10, j * 10 + 10, j * 10 + 10, j * 10}, new int[]{i * 10, i * 10, i * 10 + 10, i * 10 + 10}, 4);
                if (matrix[i][j]) {
                    g2d.setColor(state);
                    g2d.fillPolygon(new int[]{j * 10 + 1, j * 10 + 10, j * 10 + 10, j * 10 + 1}, new int[]{i * 10 + 1, i * 10 + 1, i * 10 + 10, i * 10 + 10}, 4);
                }
            }
        }
        g2d.dispose();

        File file = new File(output, this.generation + ".png");
        try {
            imagesList.add(file);
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    /**
     * Creates a list of all temporary images paths
     *
     * @return a list with all paths
     */
    public File[] getImagesList() {
        File[] ret = new File[imagesList.size()];
        for (int i = 0; i < imagesList.size(); i++) {
            ret[i] = imagesList.get(i);
        }
        return ret;
    }

    private class BoxBtn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evento) {
            JToggleButton bxButton = (JToggleButton) evento.getSource();

            //Converts the position of the box to int for using with the matrix
            String[] position = bxButton.getName().split(",");
            int row = Integer.parseInt(position[0]);
            int col = Integer.parseInt(position[1]);

            matrix[row][col] = bxButton.isSelected();

            if (matrix[row][col]) {
                bxButton.setBackground(cellColor);
            } else {
                bxButton.setBackground(backgroudColor);
            }
        }
    }
}
