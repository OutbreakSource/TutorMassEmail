import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class chart extends ApplicationFrame {

    public static void main(String[] args) throws IOException {
        //createChart("Student", new int[]{1, 2, 3});
        //saveImage(new File("yeett.png"),lol);
        //parseGroup();
        FileReader reader = new FileReader(".properties");
        Properties properties = new Properties();
        properties.load(reader);

        String OpferR = properties.getProperty("OpferR");
        String SlitR = properties.getProperty("SlitR");

        //outputFile
        String outputFile = properties.getProperty("outputFile");
        String output2File = properties.getProperty("output2File");

        String GPCSV = properties.getProperty("GPCSV");
        String GPCSVR = properties.getProperty("GPCSVR");
        String GPCSV2 = properties.getProperty("GPCSV2");
        String GPCSVR2 = properties.getProperty("GPCSVR2");
        String extra = properties.getProperty("extra");

        makeem(GPCSV2, outputFile);
        makeem(GPCSVR2, outputFile);
    }

    public chart(String applicationTitle, String chartTitle, double[] values) {
        super(applicationTitle);
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "",
                "Completion",
                createDataset(values),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        rangeAxis.setRange(0, 100);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        CategoryItemRenderer renderer = ((CategoryPlot)rangeAxis.getPlot()).getRenderer();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
                TextAnchor.TOP_CENTER);
        renderer.setBasePositiveItemLabelPosition(position);
        setContentPane(chartPanel);
    }


    private static CategoryDataset createDataset(double[] values) {
        final String percentCount = "Course Completion";
        final String actualG = "Grade";
        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();
            dataset.addValue(values[0], percentCount, "");
            dataset.addValue(values[1], actualG, "");
        return dataset;
    }

    public static chart createChart(String name, double[] values, String className) {
        chart chart = new chart(name, name + ": Progress (7/15/21) FINAL WEEK\nfor "+ className, values);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
        return chart;
    }

    public static void saveImage(String name, chart chart, String path) throws IOException {
        Rectangle rec = chart.getBounds();
        BufferedImage img = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        chart.paint(g);
        try {
            //Path always has to have the "//" at the end of it to enter the file
            name = name.replaceAll("-", " ");
            name = name.replaceAll("'", "");
            name = name.replaceAll("\\.", " ");

            ImageIO.write(img, "png", new File(path + name + " .png"));
        } catch (IOException ex) {
            System.out.println(name);
            ex.printStackTrace();
        }
    }

    public static void makeem(String path, String savePath) throws IOException {

        Scanner sc = new Scanner(new File(path));
        sc.useDelimiter(",");
        while (sc.hasNext()) {
            String line1 = sc.nextLine();
            line1 = line1.trim();
            line1 = line1.replace("\"", "");
            String[] line = line1.split(",");
            String name = line[1] + ", " + line[2];
            String className = line[3];
            final boolean b = className.contains("Math") || className.contains("Geometry")
                    || className.contains("Algebra") || className.contains("Precalculus") || Double.parseDouble(line[5]) != 100;
            if (line1.contains("N/A")) {
                line[line.length-1] = String.valueOf(0);
                variables(savePath, line, name, className, b);
            } else {
                variables(savePath, line, name, className, b);
            }

        }
    }

    private static void variables(String savePath, String[] line, String name, String className, boolean b) throws IOException {
        double one = Double.parseDouble(line[5]);
        double two = Double.parseDouble(line[8]);
        double[] data = {one, two};

        if(b){
            saveImage(name, createChart(name, data, className), savePath);
            System.out.println(name);
        }
        else {
            System.out.println("YEET:\n" + name + className);
        }
    }
}



