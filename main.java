import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;

public class SelectionSortAnimate extends JPanel {

    private JButton startButton;
    private Timer timer = null;
    private JButton resetButton;

    Integer[] list;
    int currentIndex;

    public SelectionSortAnimate(int numOfBars) {
        list = initList(numOfBars);
        currentIndex = numOfBars - 1;

        timer = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSortingDone()) {
                    ((Timer) e.getSource()).stop();
                    startButton.setEnabled(false);
                } else {
                    sortOnlyOneItem();
                }
                repaint();
            }
        });

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numOfBars = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of bars:"));
                list = initList(numOfBars);
                currentIndex = numOfBars - 1;
                repaint();
                startButton.setEnabled(true);
            }
        });

        add(startButton);
        add(resetButton);
    }

    public boolean isSortingDone() {
        return currentIndex == 0;
    }

    public Integer[] initList(int numOfBars) {
        Integer[] nums = new Integer[numOfBars];
        for (int i = 1; i <= nums.length; i++) {
            nums[i - 1] = i;
        }
        Collections.shuffle(Arrays.asList(nums));
        return nums;
    }

    public void drawItem(Graphics g, int item, int index) {
        int maxItem = Collections.max(Arrays.asList(list));
        int height = item * (getHeight() - 50) / maxItem;
        int y = getHeight() - height;
        int x = index * getWidth() / list.length;
        g.fillRect(x, y, getWidth() / list.length, height);
    }

    public void sortOnlyOneItem() {
        int currentMax = list[0];
        int currentMaxIndex = 0;

        for (int j = 1; j <= currentIndex; j++) {
            if (list[j] > currentMax) {
                currentMax = list[j];
                currentMaxIndex = j;
            }
        }

        if (currentMaxIndex != currentIndex) {
            int temp = list[currentIndex];
            list[currentIndex] = list[currentMaxIndex];
            list[currentMaxIndex] = temp;
        }
        currentIndex--;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < list.length; i++) {
            drawItem(g, list[i], i);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int numOfBars = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of bars:"));
                JFrame frame = new JFrame("Selection Sort Visualization");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                SelectionSortAnimate panel = new SelectionSortAnimate(numOfBars);
                frame.add(panel);
                frame.setSize(new Dimension(1200, 800));
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the frame to full screen
                frame.setVisible(true);
            }
        });
    }
}
