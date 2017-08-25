package com.ashvin.graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYSeriesLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by ashvin<ashvinsharma97@gmail.com></> on 24-08-2017.
 * Creates a graph on the ping to the internal server on LNMIIT campus
 */
public class Graph extends JFrame {
    private Graph() {
//        Details on the GUI
        super("First Chart in JAVA");
        String objectTitle = "first graph";
        String xAxis = "x";
        String yAxis = "y";

//        Call to the data set and initializing the chart. ChartFactory is the utility which contains standard templates to create some basic designs
        XYDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createXYLineChart(objectTitle, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, true, true, false);

//        Added Chart to the panel and disable zoom out on left-drag mouse
//        Overriding methods to enable panning without CTRL and enabling drag zooming on SHIFT-drag
        ChartPanel chartPanel = new ChartPanel(chart, false) {
            @Override
            public void restoreAutoBounds() {
                // Do nothing
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int mods = e.getModifiers();
                int panMask = MouseEvent.BUTTON1_MASK;

                if (mods == MouseEvent.BUTTON1_MASK + MouseEvent.SHIFT_MASK) {
                    panMask = 255; //The pan test will match nothing and the zoom rectangle will be activated.
                }
                try {
                    Field mask = ChartPanel.class.getDeclaredField("panMask");
                    mask.setAccessible(true);
                    mask.set(this, panMask);
                } catch (Exception ex) { ex.printStackTrace(); }
                super.mousePressed(e);
            }
        };

//        Enabled Scroll zooming
        chartPanel.setMouseWheelEnabled(true);

//      Adding colors and visual preferences
        add(chartPanel, BorderLayout.CENTER);
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setLegendItemToolTipGenerator(new StandardXYSeriesLabelGenerator(StandardXYSeriesLabelGenerator.DEFAULT_LABEL_FORMAT));
        plot.setDomainZeroBaselineVisible(true);
        plot.setDomainZeroBaselinePaint(Color.ORANGE);
        plot.setRangeZeroBaselineVisible(true);
        plot.setRangeZeroBaselinePaint(Color.ORANGE);
        plot.setDomainPannable(true);
        plot.setRangePannable(true);

//        show points on the chart
//        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.DARK_GRAY);

//        sets grid colors Range is Y and Domain is X
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        File imageFile = new File("XYLineChart_1366x768.png");
        int width = 1366;
        int height = 768;

        try { ChartUtilities.saveChartAsPNG(imageFile, chart, width, height); } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries sineSeries = new XYSeries("cos(x)sin(x)");
//        XYSeries exponential = new XYSeries("e^(x)");

        for (double i = -(2 * Math.PI); i < 2 * Math.PI; i = i + 0.01) {
            sineSeries.add(i, Math.exp(-i) * Math.cos(i) * Math.sin(i));
//            exponential.add(i, Math.exp(i));
        }

        dataset.addSeries(sineSeries);
//        dataset.addSeries(exponential);

        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Graph().setVisible(true));
        ArrayList<String> cmdList = new ArrayList<>();

//        cmdList.add("ping");
//        cmdList.add("172.22.2.26");
//        execution(cmdList);
    }

    /*private static void execution(ArrayList<String> cmdList) {
       try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("result.txt"))) {
            ProcessBuilder pb = new ProcessBuilder(cmdList);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(
                    process.getErrorStream()));

            String s;
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a dd/MM/YYYY");

            //noinspection UnusedAssignment
            s = input.readLine();
            while ((s = input.readLine()) != null) {
                System.out.println(s);
                if (s.contains("time=")) {
                    int index = s.indexOf("time=");
                    char i = s.charAt(index+5);
                    System.out.println(i);
                }

                bufferedWriter.write(sdf.format(System.currentTimeMillis()) + " ");
                bufferedWriter.write(s + "\n");
            }
            System.out.println("Errors: ");
            while ((s = error.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}