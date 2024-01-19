package islandGen.islandElevation;

import adt.Polygon;
import adt.TerrainType;
import islandGen.TerrainAssigner;

public class Biomes {

    public void assignPolygonTerrain(Polygon polygon, String biome, String altitude) {
        double humidity = polygon.getHumidity();
        double temperature = polygon.getTemperature();

        if (polygon.getTileTerrain() == TerrainType.LAND || polygon.getTileTerrain() == TerrainType.FOREST) {
            if (biome.equals("rocky") ) {

                if(altitude.equals("steep")){
                if (temperature>=-10 && temperature<=0) {
                    polygon.assignTileTerrain(TerrainType.VOLCANO_PEAK);
                }
                else if (temperature <= 25) {
                    polygon.assignTileTerrain(TerrainType.ROCK);
                } else {
                    polygon.assignTileTerrain(TerrainType.LAND);
                }
                }
                else if (altitude.equals("plateau")){
                    if (temperature>=-10 && temperature<=5) {
                        polygon.assignTileTerrain(TerrainType.VOLCANO_PEAK);
                    }

                    else if (temperature <= 25) {
                        polygon.assignTileTerrain(TerrainType.ROCK);
                    } else {
                        polygon.assignTileTerrain(TerrainType.LAND);
                    } }
                else {
                    polygon.assignTileTerrain(TerrainType.ROCK);

                }
            }

            if (biome.equals("arctic")) {
                if(altitude.equals("steep")){
                    polygon.assignTileTerrain(TerrainType.SNOW);
                }
                else if(altitude.equals("plateau")){
                    if (temperature<=30 && temperature>=25) {
                        polygon.assignTileTerrain(TerrainType.LAND);
                    }
                    else {
                        polygon.assignTileTerrain(TerrainType.SNOW);
                    }
                }else {
                    polygon.assignTileTerrain(TerrainType.SNOW);

                }

            }
        }
    }}

