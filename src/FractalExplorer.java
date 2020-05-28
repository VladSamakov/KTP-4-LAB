import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay display;
    private JButton button;
    private JFrame frame;
    private FractalGenerator generator;
    private Rectangle2D.Double range;


    public static void main(String args[]) {
        FractalExplorer explorer = new FractalExplorer(800);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }



    public FractalExplorer(int ScreenSize) {
        displaySize = ScreenSize;

        range = new Rectangle2D.Double();
        generator = new Mandelbrot();
        generator.getInitialRange(range);
    }

    public void createAndShowGUI() {
        frame = new JFrame();

        button = new JButton("reset");
        button.addActionListener(new actionListener());
        frame.getContentPane().add(button, BorderLayout.SOUTH);

        display = new JImageDisplay(displaySize, displaySize);
        display.addMouseListener(new MouseListener());
        frame.getContentPane().add(display, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
    }

    private void drawFractal() {
        for (int x = 0; x < displaySize; x++)
        {
            for (int y = 0; y < displaySize; y++)
            {
                double xCoord = FractalGenerator.getCoord
                        (range.x, range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord
                        (range.y, range.y + range.height, displaySize, y);


                int numIter = generator.numIterations(xCoord, yCoord);
                if (numIter == -1) display.drawPixel(x, y, 0);
                else {
                    float hue = 0.7f + (float) numIter / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    display.drawPixel(x, y, rgbColor);
                }
            }
        }
        display.repaint();
    }

    private class actionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            generator.getInitialRange(range);
            drawFractal();
        }
    }

    private class MouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            double xCoord = generator.getCoord(range.x, range.x + range.width, displaySize,x);
            double yCoord = generator.getCoord(range.y, range.y + range.height, displaySize,y);
            generator.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

}
