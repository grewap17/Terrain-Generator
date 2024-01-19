package islandGen;

import adt.Polygon;
import adt.TerrainType;

import java.util.Random;

public class AquifersAssigner {

    public int AssignAquifers(Polygon polygon, int NumberOfAquifiers, Double SoilAbsoprtion){
        Random random = new Random();
        int LakeOdds = random.nextInt(5) + 1;
        if(LakeOdds == 1 && polygon.getTileTerrain() != TerrainType.OCEAN && polygon.getTileTerrain() != TerrainType.BEACH
                && polygon.getTileTerrain() != TerrainType.VOLCANO_PEAK && polygon.getAltitude() < 80
                && polygon.getTileTerrain() != TerrainType.LAKE && polygon.getTileTerrain() != TerrainType.LAGOON) {


            polygon.assignMoisture(50+SoilAbsoprtion);

            return NumberOfAquifiers = NumberOfAquifiers + 1;
        }
        return NumberOfAquifiers;
    }

    public void NeighbourAquiferMoisture(Polygon polygon){
        if (polygon.getMoisture() == 70){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND && neighbour.getMoisture() < 50){
                    neighbour.assignMoisture(45);
                    NeighbourMoisture(neighbour);
                }
            }
        }
        if (polygon.getMoisture() == 60){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND && neighbour.getMoisture() < 50){
                    neighbour.assignMoisture(25);
                    NeighbourMoisture(neighbour);
                }
            }
        }
        if (polygon.getMoisture() == 40){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND && neighbour.getMoisture() < 50){
                    neighbour.assignMoisture(15);
                    NeighbourMoisture(neighbour);
                }
            }
        }
    }

    public void NeighbourMoisture(Polygon polygon){
        if (polygon.getMoisture() == 45){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND && neighbour.getMoisture() < 35){
                    neighbour.assignMoisture(25);
                }
            }
        }

        if (polygon.getMoisture() == 25){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND && neighbour.getMoisture() < 35){
                    neighbour.assignMoisture(15);
                }
            }
        }

            if (polygon.getMoisture() == 15){
        for (Polygon neighbour : polygon.neighbours()){
            if (neighbour.getTileTerrain() == TerrainType.LAND && neighbour.getMoisture() < 35){
                neighbour.assignMoisture(5);
            }
        }
    }
}
}


