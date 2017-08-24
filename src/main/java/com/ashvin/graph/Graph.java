package com.ashvin.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by ashvin<ashvinsharma97@gmail.com></> on 24-08-2017.
 * Creates a graph on the ping to the internal server on LNMIIT campus
 */
public class Graph extends JFrame {
    public Graph() {
        super("XY Line Chart");
        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createChartPanel() {
        String objectTitle = "first graph";
        String xAxis = "x";
        String yAxis = "y";
        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createXYLineChart(objectTitle, xAxis, yAxis, dataset);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.DARK_GRAY);

        //sets grid colors Range is Y and Domain is X
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        File imageFile = new File("XYLineChart_1366x768.png");
        int width = 1366;
        int height = 768;

        try { ChartUtilities.saveChartAsPNG(imageFile, chart, width, height); } catch (IOException ex) {
            System.err.println(ex);
        }

        return new ChartPanel(chart);
    }

    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Object 1");

        series1.add(1.0, 2.0);
        series1.add(2.0, 3.0);
        series1.add(3.0, 2.5);
        series1.add(3.5, 2.8);
        series1.add(4.2, 6.0);
        dataset.addSeries(series1);

        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Graph().setVisible(true));
    }
}