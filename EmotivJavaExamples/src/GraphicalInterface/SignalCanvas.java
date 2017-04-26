package GraphicalInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class SignalCanvas extends JPanel {

    private static final ArrayList<ArrayList<Double>> allData = new ArrayList<>();
    private final int xOffset = 1;
    private final int yOffset = 35;
    private static final int datalength = 880;
    private final int numberOfDataChannels = 14;
    private final ArrayList<Color> colors;

    public SignalCanvas() {
        colors = new ArrayList<>();
        colors.add(Color.BLACK);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        colors.add(Color.ORANGE);
        colors.add(Color.PINK);
        colors.add(Color.BLACK);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.RED);

        for (int i = 0; i < numberOfDataChannels; i++) {
            allData.add(new ArrayList<Double>());
        }
        this.setBackground(Color.WHITE);

    }

    public void start() {
        Thread st = new Thread(new repainter());
        st.start();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        for (int i = 0; i < allData.size() - 1; i++) {
           //updateData(i, allData.get(i));
            drawData(allData.get(i), g, i, colors.get(i));
        }

    }

    private void paint() {
        this.repaint();
    }

    public static void updateData(int index, double dataItem) {
        if (!allData.isEmpty()) {
            dataItem = (dataItem - 3500) / 30;
            allData.get(index).add(dataItem);

            if (allData.get(index).size() > datalength) {
                allData.get(index).remove(0);
            }
        }
    }

    public void drawData(ArrayList<Double> data, Graphics g, int lineNumber, Color newColor) {
        g.setColor(newColor);
        if (data.size() > 1) {
            for (int i = 1; i < data.size(); i++) {
                g.drawLine(i * xOffset, data.get(i - 1).intValue() + lineNumber * yOffset, (i + 1) * xOffset, data.get(i).intValue() + lineNumber * yOffset);
            }
        }
    }

    class repainter implements Runnable {

        @Override
        public void run() {
            while (true) {
                paint();
            }
        }

    }

}
