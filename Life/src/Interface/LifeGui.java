/*
 * Copyright (C) 2020 Gabriel Bastidas
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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Cristian Bastidas
 */
public class LifeGui extends JFrame {

    private HashMap componentMap;

    /**
     * Grid's rows
     */
    public static final int ROWS = 50;

    /**
     * Grid's columns
     */
    public static final int COLS = 50;

    /**
     * Values to select grid's boxes
     */
    public boolean[][] matrix;
    public int population;
    public int generation;

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
            bxButton.setName(
                    Integer.toString(counterRow)
                    + ','
                    + Integer.toString(counterCol)
            );

            bxButton.setBackground(Color.darkGray);
            bxButton.setBorder(BorderFactory.createLineBorder(Color.gray));

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
        createComponentMap();
    }

    //Creates a HashMap wuit the components created in the form
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
     * @param name Component's name
     * @return A swing component
     */
    public Component getComponentByName(String name) {
        if (componentMap.containsKey(name)) {
            return (Component) componentMap.get(name);
        } else {
            return null;
        }
    }

    /**
     * Clear the grid | Before, All boxes are unselected
     */
    public void clear() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JToggleButton bxButton = (JToggleButton) getComponentByName(i + "," + j);
                if (bxButton != null) {
                    bxButton.setSelected(false);
                    this.matrix[i][j] = false;
                }
            }
        }
        this.generation = 0;
    }

    //Updates boolean values from the matrix
    private void reload() {
        this.population = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JToggleButton bxButton = (JToggleButton) getComponentByName(i + "," + j);
                if (bxButton != null) {
                    bxButton.setSelected(this.matrix[i][j]);
                    if (this.matrix[i][j]) {
                        this.population++;
                    }
                }
            }
        }
    }

    //Allows toroid grid
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
                    //System.out.println("A cell has resurrected");
                } else if (!this.matrix[i][j] && trueCounter == 3) {
                    newMatrix[i][j] = true;
                    //System.out.println("A cell has died");
                }
            }
        }
        //System.out.println("Matrix has changed");
        this.setMatrix(newMatrix);
    }

    /**
     * Setting boolean values and reloading the grid
     *
     * @param matrix a matrix duh!
     */
    public void setMatrix(boolean[][] matrix) {
        this.matrix = matrix;
        reload();
    }

    public boolean[][] getMatrix() {
        return matrix;
    }

    public int getPopulation() {
        return population;
    }

    private class BoxBtn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evento) {
            JToggleButton bxButton = (JToggleButton) evento.getSource();

            //Converts the position of the box to int for using with the matrix
            String[] position = bxButton.getName().split(",");
            int row = Integer.parseInt(position[0]);
            int col = Integer.parseInt(position[1]);

            //Changing the state
            matrix[row][col] = bxButton.isSelected();

            //Feedback | Uncomment if you have problems
            /*System.out.println(
                    Arrays.toString(position)
                    + " has changed to: "
                    + bxButton.isSelected()
            );*/
        }
    }
}
