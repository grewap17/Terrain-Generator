package islandGen.islandElevation;

import adt.Polygon;
import adt.TerrainType;

public abstract class AltitudeAssigner {
    protected double maxElevatedDistanceFromPeak;
    protected double mesh_width;
    protected double mesh_height;

    public AltitudeAssigner(double mesh_width_input, double mesh_height_input){
        this.mesh_width = mesh_width_input;
        this.mesh_height = mesh_height_input;
        maxElevatedDistanceFromPeak = Math.min(mesh_width, mesh_height) * 0.4;
    }

    public void assignPolygonAltitude(Polygon polygon) {
        if (polygon.getTileTerrain() == TerrainType.OCEAN) {
            polygon.assignAltitude(0);  //Do not assign ocean a high altitude
            return;
        }

        double polygonDistanceFromPeak = calculatePolygonDistanceFromPeak(polygon);

        polygon.assignAltitude(calculatePolygonAltitude(polygonDistanceFromPeak));
    }

    public abstract double calculatePolygonDistanceFromPeak(Polygon polygon);

    protected abstract double calculatePolygonAltitude(double polygonDistanceFromPeak);
}
