package life;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Cristian Bastidas
 */
public class LifeGui extends JFrame {

    private HashMap componentMap;
    public static final int ROWS = 50;
    public static final int COLS = 50;
    public boolean[][] matrix;
    private final JPanel container;

    public LifeGui() {
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
        components.forEach((comp) -> {
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

    //Search for a component giving the name
    public Component getComponentByName(String name) {
        if (componentMap.containsKey(name)) {
            return (Component) componentMap.get(name);
        } else {
            return null;
        }
    }

    //Turn boxes off
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
    }

    public void setMatrix(boolean[][] matrix) {
        this.matrix = matrix;
    }

    public boolean[][] getMatrix() {
        return matrix;
    }

    private class BoxBtn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evento) {
            JToggleButton bxButton = (JToggleButton) evento.getSource();

            //Converts the position of the box to int for using with the array
            String[] position = bxButton.getName().split(",");
            int row = Integer.parseInt(position[0]);
            int col = Integer.parseInt(position[1]);
            
            //Changing the state
            matrix[row][col] = bxButton.isSelected();

            //Feedback
            System.out.println(
                    Arrays.toString(position)
                    + " has changed to: "
                    + bxButton.isSelected()
            );
        }
    }
}
