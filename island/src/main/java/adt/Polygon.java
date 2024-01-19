package adt;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Polygon implements Cropable<Polygon>, Iterable<Vertex> {
    private final List<Vertex> hull;

    private final Set<Polygon> neighbors;

    private TerrainType tile_type;
    private double moisture = 0.0;
    private double altitude = 0.0;
    private double temperature = 0.0;
    private double humidity = 0.0;
    public Polygon() {
        this(new ArrayList<>());
    }

    private Polygon(List<Vertex> hull) {
        this.hull = hull;
        this.neighbors = new HashSet<>();
    }

    public void add(Vertex v) {
        this.hull.add(v);
    }

    public void registerAsNeighbour(Polygon p) {
        this.neighbors.add(p);
    }

    public Set<Polygon> neighbours() {
        return this.neighbors;
    }

    public Polygon crop(float maxX, float maxY) {
        List<Vertex> cropped = new ArrayList<>();
        for (Vertex v: this.hull){
            cropped.add(v.crop(maxX, maxY));
        }
        return new Polygon(cropped);
    }

    public Vertex centroid() {
        float xs = 0.0f, ys = 0.0f;
        for (Vertex v: this.hull) {
            xs += v.x();
            ys += v.y();
        }
        return new Vertex(xs/this.hull.size(), ys/this.hull.size());
    }

    public List<PairOfVertex> hull() {
        List<PairOfVertex> result = new ArrayList<>();
        Iterator<Vertex> it = this.hull.iterator();
        Vertex start = it.next();
        Vertex current = start;
        while(it.hasNext()) {
            Vertex next = it.next();
            result.add(new PairOfVertex(current, next));
            current = next;
        }
        result.add(new PairOfVertex(current, start));
        return result;
    }
    public Color getColor(){
        Color color = this.tile_type.getColor();
        if (this.tile_type == TerrainType.LAND){
            int green = color.getGreen();
            green -= this.moisture/2;
            green -= this.altitude/2;
            color = new Color(color.getRed(),green,color.getBlue());
        }
        else if (this.tile_type == TerrainType.ROCK){
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            red -= this.altitude;
            green -= this.altitude;
            blue -= this.altitude;
            color = new Color(red,green,blue);
        }
        else if (this.tile_type == TerrainType.SNOW) {
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            red += this.altitude*2.5;
            if (red > 255) red = 255;
            color = new Color(red,green,blue);
        }

        return color;
    }

    public void assignTileTerrain(TerrainType terrain){
        this.tile_type = terrain;
    }

    public TerrainType getTileTerrain(){
        return this.tile_type;
    }
    public void assignMoisture(double assigned_moisture){
        this.moisture = assigned_moisture;
        if (this.moisture>100) this.moisture = 100; //moisture is max 100
        else if (this.moisture<0) this.moisture = 0; //moisture is min 0
    }
    public double getMoisture(){ return this.moisture; }

    public void calculateTemperature(){
        this.temperature = -0.4*this.altitude+30;
        if (this.temperature>30) this.temperature = 30; //temp is max 30
        else if (this.temperature<-10) this.temperature = -10; //temp is min -10
    }
    public double getTemperature(){
        calculateTemperature();
        return this.temperature;
    }

    public void calculateHumidity(){
        this.humidity = this.moisture;
        if (this.humidity>100) this.humidity = 100; //humid is max 100
        else if (this.humidity<0) this.humidity = 0; //humid is min 0
    }
    public double getHumidity(){
        calculateHumidity();
        return this.humidity;
    }

    public void assignAltitude(double assigned_moisture){
        this.altitude = assigned_moisture;
        if (this.altitude>100) this.altitude = 100; //altitude is max 100
        else if (this.altitude<0) this.altitude = 0; //altitude is min 0
    }
    public double getAltitude(){ return this.altitude; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polygon polygon = (Polygon) o;

        if (!Objects.equals(this.tile_type, polygon.tile_type)) return false;
        if ((Math.abs(this.moisture - polygon.moisture)>0.01)) return false;
        if ((Math.abs(this.altitude - polygon.altitude)>0.01)) return false;
        return hull.equals(polygon.hull);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hull);
    }

    @Override
    public Iterator<Vertex> iterator() {
        return this.hull.iterator();
    }

    @Override
    public String toString() {
        return "Polygon(" +centroid()+ "," + hull + ", "+
                this.neighbors.size() + "," +
                this.tile_type + "," +
                this.moisture + "," +
                this.altitude + ")";
    }
}
