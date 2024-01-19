package islandGen;

import adt.Polygon;
import adt.TerrainType;

import java.awt.geom.Line2D;
import java.util.Random;

public class ArcticTerrainAssigner implements TerrainAssigner{
    /*
        =========================================================
        Move this stuff to a biome class instead of this elevation class
        =========================================================
         */

    public void assignPolygonTerrain(Polygon polygon, double mesh_width, double mesh_height) {
        //Design choice: the code goes over the island and generates an arctic environment from the top left of the island
        double island_circle_radius = Math.min(mesh_width, mesh_height)*0.6; //island takes ~80% of mesh
        Line2D measuringline = new Line2D.Double(mesh_width*0.5,0,0,mesh_height);
        double distanceOfCentroidToFromLine = measuringline.ptLineDist(polygon.centroid().x(),polygon.centroid().y());

        if (polygon.getTileTerrain() == TerrainType.OCEAN) { return; } //Arctic island does not go over ocean

        if (distanceOfCentroidToFromLine <= island_circle_radius * 0.6) {
            polygon.assignTileTerrain(TerrainType.SNOW);  //Slush Ground Level
        }
        else if (distanceOfCentroidToFromLine <= island_circle_radius*0.7) {
            Random random = new Random();
            int IceOdds = random.nextInt(3) + 1;
            if (IceOdds == 1) {
                polygon.assignTileTerrain(TerrainType.SNOW); //Slush Ground Level
            } else {
                polygon.assignTileTerrain(TerrainType.FOREST);
            }
        }
    }
}
