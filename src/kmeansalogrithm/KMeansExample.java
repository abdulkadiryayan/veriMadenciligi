//02195076051 ABDULKAD�R YAYAN
package kmeansalogrithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeansExample {
    public static void main(String[] args) {
        // Örnek veri oluştur
        List<Point> data = generateRandomData(100);

        // K-Means algoritmasını uygula
        int k = 3; // Küme sayısı
        List<Centroid> centroids = initializeCentroids(data, k);
        kMeans(data, centroids);

        // Sonuçları görüntüle
        for (int i = 0; i < k; i++) {
            System.out.println("Küme " + (i + 1) + " merkezi: " + centroids.get(i));
        }
        for (Point point : data) {
            System.out.println("Veri noktası: " + point + " - Küme: " + point.getCluster());
        }
    }

    static List<Point> generateRandomData(int numPoints) {
        List<Point> dataPoints = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numPoints; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            dataPoints.add(new Point(x, y));
        }
        return dataPoints;
    }

    static List<Centroid> initializeCentroids(List<Point> dataPoints, int k) {
        List<Centroid> centroids = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            Point randomPoint = dataPoints.get(random.nextInt(dataPoints.size()));
            centroids.add(new Centroid(randomPoint.getX(), randomPoint.getY()));
        }
        return centroids;
    }

    static void kMeans(List<Point> dataPoints, List<Centroid> centroids) {
        int maxIterations = 100;
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            // Her veri noktasını en yakın merkeze atama
            for (Point dataPoint : dataPoints) {
                double minDistance = Double.MAX_VALUE;
                int cluster = -1;
                for (int i = 0; i < centroids.size(); i++) {
                    double distance = dataPoint.calculateDistance(centroids.get(i));
                    if (distance < minDistance) {
                        minDistance = distance;
                        cluster = i;
                    }
                }
                dataPoint.setCluster(cluster);
            }

            // Yeni merkezleri hesapla
            for (int i = 0; i < centroids.size(); i++) {
                double sumX = 0, sumY = 0;
                int count = 0;
                for (Point dataPoint : dataPoints) {
                    if (dataPoint.getCluster() == i) {
                        sumX += dataPoint.getX();
                        sumY += dataPoint.getY();
                        count++;
                    }
                }
                if (count > 0) {
                    centroids.get(i).updateCentroid(sumX / count, sumY / count);
                }
            }
        }
    }
}

class Point {
    private double x;
    private double y;
    private int cluster;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }

    public double calculateDistance(Centroid centroid) {
        return Math.sqrt(Math.pow(x - centroid.getX(), 2) + Math.pow(y - centroid.getY(), 2));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

class Centroid {
    private double x;
    private double y;

    public Centroid(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void updateCentroid(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
