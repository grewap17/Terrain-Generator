package islandGen;

import adt.Polygon;
import adt.TerrainType;
import java.util.Random;

public class LakesAssigner {

    public int assignPolygonTerrain(Polygon polygon, int Lakes) {
        Random random = new Random();
        int LakeOdds = random.nextInt(5) + 1;
        if(LakeOdds == 1 && polygon.getTileTerrain() != TerrainType.OCEAN && polygon.getTileTerrain() != TerrainType.BEACH&& polygon.getTileTerrain() != TerrainType.VOLCANO_PEAK && polygon.getAltitude() < 80
                && polygon.getTileTerrain() != TerrainType.LAKE && polygon.getTileTerrain() != TerrainType.LAGOON
                && !CheckNeighbours(polygon,TerrainType.LAKE)) {

            TerrainType OldTerrain = polygon.getTileTerrain();
            polygon.assignTileTerrain(TerrainType.LAKE);
            polygon.assignMoisture(100);
            AssignLakeNeighbours(polygon, OldTerrain);

            return Lakes = Lakes + 1;
        }
        return Lakes;
    }

    private void AssignLakeNeighbours(Polygon polygon, TerrainType OldTerrain){
        Random random = new Random();
        for (Polygon neighbour: polygon.neighbours()){
            int NeighbourLakeOdds = random.nextInt(2) + 1;
            if(NeighbourLakeOdds == 1 && neighbour.getTileTerrain() != TerrainType.OCEAN && neighbour.getTileTerrain() != TerrainType.BEACH
                    && neighbour.getTileTerrain() != TerrainType.VOLCANO_PEAK && neighbour.getAltitude() < 80
                    && neighbour.getTileTerrain() != TerrainType.LAGOON && polygon.getTileTerrain() == TerrainType.LAKE
                    && neighbour.getTileTerrain() == OldTerrain){

                TerrainType oldTerrain = neighbour.getTileTerrain();
                neighbour.assignTileTerrain(TerrainType.LAKE);
                neighbour.assignMoisture(100);

                int NeighbourNeighbourLakeOdds = random.nextInt(4)+1;
                if (NeighbourNeighbourLakeOdds == 1) {
                    AssignLakeNeighbours(neighbour, oldTerrain);
                }
            }
        }
    }

    private boolean CheckNeighbours(Polygon polygon, TerrainType search){
        for (Polygon neighbour: polygon.neighbours()){
            if (neighbour.getTileTerrain() == search){
                return true;
            }
        }
        return false;
    }

    public void SurroundLakeGreener(Polygon polygon,Double SoilAbsorption){
        if (polygon.getTileTerrain() == TerrainType.LAKE){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND){
                    neighbour.assignMoisture(60+(1.5*SoilAbsorption));
                }
            }
        }
    }

    public void NeighbourLakeMoisture(Polygon polygon, Double SoilAbsorb){
        if (polygon.getMoisture() == 90){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND && neighbour.getMoisture() < 90){
                    neighbour.assignMoisture(60);
                    Neighbour2LakeMoisture(neighbour, SoilAbsorb);
                }
            }
        }
        if (polygon.getMoisture() == 30){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND && neighbour.getMoisture() < 30){
                    neighbour.assignMoisture(15);
                    Neighbour2LakeMoisture(neighbour, SoilAbsorb);
                }
            }
        }
        if (polygon.getMoisture() == 15 && SoilAbsorb > 0){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND && neighbour.getMoisture() < 50){
                    neighbour.assignMoisture(15);
                    Neighbour2LakeMoisture(neighbour, SoilAbsorb);
                }
            }
        }
    }

    public void Neighbour2LakeMoisture(Polygon polygon, double SoilAbsorb){
        if (polygon.getMoisture() == 60){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND && neighbour.getMoisture() < 60){
                    neighbour.assignMoisture(45);
                }
            }
        }

        if (polygon.getMoisture() == 45){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND && neighbour.getMoisture() < 60){
                    neighbour.assignMoisture(15);
                }
            }
        }

        if (polygon.getMoisture() == 15 && SoilAbsorb > 0){
            for (Polygon neighbour : polygon.neighbours()){
                if (neighbour.getTileTerrain() == TerrainType.LAND && neighbour.getMoisture() < 20){
                    neighbour.assignMoisture(5);
                }
            }
        }

    }
}