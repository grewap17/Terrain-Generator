package islandGen.islandElevation;

import adt.Polygon;

import java.awt.geom.Line2D;

public class LinearPlateauAltitudeAssigner extends AltitudeAssigner{
    public LinearPlateauAltitudeAssigner(double mesh_width_input, double mesh_height_input){
        super(mesh_width_input,mesh_height_input);
    }

    public double calculatePolygonDistanceFromPeak(Polygon polygon){
        Line2D measuringline = new Line2D.Double(mesh_width,0,0,mesh_height);
        double centroidDistanceFromLine = measuringline.ptLineDist(polygon.centroid().x(),polygon.centroid().y());

        return centroidDistanceFromLine;
    }

    protected double calculatePolygonAltitude(double polygonDistanceFromPeak) {
        /* Hard coded numbers are actually the steepness function.
        Use a steepness function to calculate altitude based on distance from a peak.
        The steepness function for this plateau is high close to the peak, and
        slowly descending terrain around the peak */
        return -280*(polygonDistanceFromPeak / maxElevatedDistanceFromPeak)/3+70;
    }
}
