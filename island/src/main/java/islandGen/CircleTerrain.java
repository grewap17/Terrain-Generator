package islandGen;

import adt.Polygon;
import adt.TerrainType;

public class CircleTerrain implements TerrainAssigner {
    /*
    ==============================================================
    Move this class to a shape class later
    ==============================================================
     */

    public void assignPolygonTerrain(Polygon polygon, double mesh_width, double mesh_height) {
        double island_circle_radius = Math.min(mesh_width,mesh_height)*0.8/2; //island takes ~80% of mesh
        double distanceOfCentroidToFromCenter = Math.sqrt(Math.pow((polygon.centroid().x() - mesh_width/2), 2) + Math.pow((polygon.centroid().y() - mesh_height/2),2));

        if(distanceOfCentroidToFromCenter < island_circle_radius){
            if (checkForNeighbouringTerrain(polygon,TerrainType.OCEAN)){
                polygon.assignTileTerrain(TerrainType.BEACH);

            } else {
                polygon.assignTileTerrain(TerrainType.LAND);
            }
        }
        else {
            if (checkForNeighbouringTerrain(polygon,TerrainType.LAND)){
                polygon.assignTileTerrain(TerrainType.BEACH);
            } else {
                polygon.assignTileTerrain(TerrainType.OCEAN);
            }
        }
    }

    public boolean checkForNeighbouringTerrain(Polygon polygon, TerrainType search_terrain){
        for (Polygon neighbour: polygon.neighbours()){
            if (neighbour.getTileTerrain()==search_terrain){
                return true; //polygon is neighboured by search_terrain
            }
        }
        return false;  //polygon is not neighboured by search_terrain
    }
}
