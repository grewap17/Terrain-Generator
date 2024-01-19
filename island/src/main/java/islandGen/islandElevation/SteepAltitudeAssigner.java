package islandGen.islandElevation;

import adt.Polygon;

public class SteepAltitudeAssigner extends AltitudeAssigner{

    public SteepAltitudeAssigner(double mesh_width_input, double mesh_height_input){
        super(mesh_width_input,mesh_height_input);
    }

    public double calculatePolygonDistanceFromPeak(Polygon polygon){
        double xAxisDistanceFromCenter = polygon.centroid().x() - mesh_width / 2;
        double yAxisDistanceFromCenter = polygon.centroid().y() - mesh_height / 2;
        double centroidDistanceFromCenter = Math.sqrt(Math.pow(xAxisDistanceFromCenter, 2) + Math.pow(yAxisDistanceFromCenter, 2));

        return centroidDistanceFromCenter;
    }

    protected double calculatePolygonAltitude(double polygonDistanceFromPeak) {
        /* Hard coded numbers are actually the steepness function.
        Use a steepness function to calculate altitude based on distance from a peak.
        The steepness function for this steep mountain is EXTREMELY high close to the peak, and
        quickly descending terrain around the peak */
        return -1000*(polygonDistanceFromPeak / maxElevatedDistanceFromPeak)/6+100;
    }
}
