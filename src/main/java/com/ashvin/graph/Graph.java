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
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by ashvin<ashvinsharma97@gmail.com></> on 24-08-2017.
 * Creates a graph on the ping to the internal server on LNMIIT campus
 */
public class Graph extends JFrame {
    private Graph() {
        super("First Chart in JAVA");
        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);
        setSize(1366, 748);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

        try { ChartUtilities.saveChartAsPNG(imageFile, chart, width, height); } catch (IOException e) {
            e.printStackTrace();
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
        ArrayList<String> cmdList = new ArrayList<>();

        cmdList.add("ping");
        cmdList.add("172.22.2.26");
        execution(cmdList);
    }

    private static void execution(ArrayList<String> cmdList) {
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

    }
}