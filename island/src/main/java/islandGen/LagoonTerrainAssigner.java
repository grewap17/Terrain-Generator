package islandGen;

import adt.Polygon;
import adt.TerrainType;

public class LagoonTerrainAssigner implements TerrainAssigner {

    public void assignPolygonTerrain(Polygon polygon, double mesh_width, double mesh_height) {
        /* Design choice: the hard coded numbers below are neccesarry because the user does NOT choose
        the dimensions of the lagoon circle. The size of the lagoon is determined by the dimensions of
        the mesh. */
        double lagoon_circle_radius = Math.min(mesh_width,mesh_height)*0.8/4; //lagoon takes ~50% of island
        double distanceOfCentroidToFromCenter = Math.sqrt(Math.pow((polygon.centroid().x() - mesh_width/2), 2) + Math.pow((polygon.centroid().y() - mesh_height/2),2));

        if (distanceOfCentroidToFromCenter < lagoon_circle_radius){
            polygon.assignTileTerrain(TerrainType.LAGOON);

        }
    }
}