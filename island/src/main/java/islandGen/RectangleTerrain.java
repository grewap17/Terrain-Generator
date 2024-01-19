package islandGen;

import adt.Polygon;
import adt.TerrainType;

public class RectangleTerrain implements TerrainAssigner {

        public void assignPolygonTerrain(Polygon polygon, double mesh_width, double mesh_height) {
            double farLeftX=mesh_width/8;
            double CloseX=mesh_width*0.875;
            double topY=mesh_height/8;
            double bottomY=mesh_height*0.875;

            if (polygon.centroid().x() > farLeftX && polygon.centroid().x() < CloseX &&  polygon.centroid().y() > topY && polygon.centroid().y() < bottomY ){
                if (checkForNeighbouringTerrain(polygon,TerrainType.OCEAN)){
                    polygon.assignTileTerrain(TerrainType.BEACH);

                } else {
                    polygon.assignTileTerrain(TerrainType.LAND);

                }
            } else  {
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

