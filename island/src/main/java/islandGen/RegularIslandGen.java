package islandGen;

import adt.*;
import configuration.Configuration;
import islandGen.islandElevation.*;

public class RegularIslandGen implements IslandGen {
    public void islandEnricher(Mesh iMesh,String args[]) {
        double mesh_width = iMesh.getWidth();
        double mesh_height = iMesh.getHeight();
        Configuration config = new Configuration(args);

        buildIslandBase(iMesh, config.shape(), mesh_width, mesh_height);
        assignAltitudes(iMesh, config.altitude(), mesh_width, mesh_height);

        int soilAbsorptionLvl = calculateSoilAbsorptionLvl(config.soil());

        buildLakes(iMesh, config.numLakes(), soilAbsorptionLvl);
        buildAquifers(iMesh, config.numAquifers(), soilAbsorptionLvl);

        buildBiomes(iMesh, config.biome(), config.altitude());
    }

    private void buildIslandBase(Mesh iMesh, String baseShape, double mesh_width, double mesh_height){
        TerrainAssigner baseShapeTerrainAssigner;
        if (baseShape.equals("rectangle")) {baseShapeTerrainAssigner = new RectangleTerrain();}
        else {baseShapeTerrainAssigner = new CircleTerrain();}

        //Build the base island in the correct shape. Assign each polygon a base terrain.
        for (Polygon polygon : iMesh) {
            baseShapeTerrainAssigner.assignPolygonTerrain(polygon, mesh_width, mesh_height);
        }
    }

    private void assignAltitudes(Mesh iMesh, String altitude, double mesh_width, double mesh_height){
        AltitudeAssigner altitudeAssigner;
        if (altitude.equals("steep")) { altitudeAssigner = new SteepAltitudeAssigner(mesh_width,mesh_height); }
        else if (altitude.equals("plateau")){ altitudeAssigner = new LinearPlateauAltitudeAssigner(mesh_width,mesh_height); }
        else { altitudeAssigner = new FlatAltitudeAssigner(mesh_width,mesh_height); }
        //If no altitude is specified, make the island flattish

        for (Polygon polygon : iMesh) {
            altitudeAssigner.assignPolygonAltitude(polygon);
        }
    }

    private void buildBiomes(Mesh iMesh, String biomeType, String altitude){
        Biomes islandBiomes = new Biomes();
        for (Polygon polygon : iMesh){
            islandBiomes.assignPolygonTerrain(polygon,biomeType, altitude);
        }
    }

    private void buildLakes(Mesh iMesh, int totalNumLakes, int soilAbsorptionLvl){
        LakesAssigner islandLakes = new LakesAssigner();
        int currentNumberOfLakes = 0;
        int soilAbsorptionLvlLake = soilAbsorptionLvl*2;

        for (Polygon polygon : iMesh){
            if (currentNumberOfLakes < totalNumLakes){
                currentNumberOfLakes = islandLakes.assignPolygonTerrain(polygon,currentNumberOfLakes);
            }
            if (currentNumberOfLakes == totalNumLakes){
                break;
            }
        }
        for (Polygon polygon : iMesh){
            islandLakes.SurroundLakeGreener(polygon, (double) soilAbsorptionLvlLake);
        }
        for (Polygon polygon : iMesh){
            islandLakes.NeighbourLakeMoisture(polygon, (double)soilAbsorptionLvlLake);
        }
    }

    private void buildAquifers(Mesh iMesh, int totalNumAquifers, int soilAbsorptionLvl){
        int currentNumberOfAqufiers = 0;
        AquifersAssigner islandAquifiers = new AquifersAssigner();

        for (Polygon polygon : iMesh){
            if (currentNumberOfAqufiers < totalNumAquifers){
                currentNumberOfAqufiers = islandAquifiers.AssignAquifers(polygon,currentNumberOfAqufiers, (double) soilAbsorptionLvl);
            }
            if (currentNumberOfAqufiers == totalNumAquifers){
                break;
            }
        }
        //surround aquifers with moist terrain
        for (Polygon polygon : iMesh){
            islandAquifiers.NeighbourAquiferMoisture(polygon);
        }
    }

    private int calculateSoilAbsorptionLvl(String absorptionProfile){
        int soilAbsorptionLvl=0;

        if(absorptionProfile.equals("quick")){
            soilAbsorptionLvl=10;

        } else if (absorptionProfile.equals("slow")) {
            soilAbsorptionLvl=-10;
        }

        return soilAbsorptionLvl;
    }
}

