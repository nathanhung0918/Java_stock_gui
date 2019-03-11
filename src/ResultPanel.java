import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ResultPanel extends JPanel {

	int padding = 10;
	int labelPadding = 65;
	private int numberYDivisions = 10;//cut y axis to parts
	private int pointWidth = 4;//size of my point
	double[] data;
	String[] date;
	double maxN;
	double minN;
	//prepare data
    public ResultPanel(String[] date,String[] display) {
    	this.date = date;
    	this.data = new double[display.length];
        for(int i=0;i<display.length;i++){
        	this.data[i] = Double.parseDouble(display[i]);//transfer string to double
        }
        setMax(data);
        setMin(data);
        for(int i = 0; i < data.length / 2; i++) {//reverse data(double)
            double temp = data[i];
            data[i] = data[data.length - i - 1];
            data[data.length - i - 1] = temp;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (data.length - 1);//distance of x0 and x1
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxN - minN);//distance of y
        Point[] mPoint = new Point[data.length];//set my points
        for(int i=0;i<mPoint.length;i++) {
        	int x = (int)(i*xScale + padding + labelPadding);//point can only be double
        	int y = (int) ((int)(maxN - data[i]) * yScale + padding);
        	mPoint[i] = new Point(x,y);
        }

        // fill white background
        g2.setColor(Color.BLACK);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);
        // create y label and scale lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (data.length > 0) {
                g2.setColor(Color.GRAY);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);// division line
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((minN + (maxN - minN) * ((i * 1.0) / numberYDivisions)) ))  + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth + 1, y0 + (metrics.getHeight() / 2) - 3);//draw label
            }
        }
        // same for x axis
        for (int i = 0; i < data.length; i++) {
            if (data.length > 1) {//keep the label part empty
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (data.length - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if(data.length>=5) {//only show 5 date
                if ((i % (data.length/5)) == 0) {
                    g2.setColor(Color.GRAY);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = date[i];
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }}
                else //data not enough to 5
                	{
                        g2.setColor(Color.GRAY);
                        g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                        g2.setColor(Color.BLACK);
                        String xLabel = date[i];
                        FontMetrics metrics = g2.getFontMetrics();
                        int labelWidth = metrics.stringWidth(xLabel);
                        g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                    }
                	
            }
        }
        Stroke oldStroke = g2.getStroke();
        g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(2f));
        for (int i = 0; i < mPoint.length - 1; i++) {//draw my lines
            int x1 = mPoint[i].x;
            int y1 = mPoint[i].y;
            int x2 = mPoint[i+1].x;//first end point be the next start point
            int y2 = mPoint[i+1].y;
            g2.drawLine(x1, y1, x2, y2);
        }
        g2.setStroke(oldStroke);
        g2.setColor(Color.GRAY);
        for (int i = 0; i < mPoint.length; i++) {//plot my points
            int x = mPoint[i].x - 4 / 2;
            int y = mPoint[i].y - 4 / 2;
            g2.fillOval(x, y, 4, 4);
        }
    }
    private void setMax(double[] data) {
		double maxPrice = 0.0;
		for(int i=0;i<data.length;i++) {
			maxPrice = Math.max(maxPrice,data[i]);
		}
		maxN = maxPrice;
	}
	private void setMin(double[] data) {
		double minPrice = 1000000000.0;
		for(int i=0;i<data.length;i++) {
			minPrice = Math.min(minPrice,data[i]);
		}
		minN = minPrice;
	}



   
}