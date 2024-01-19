
import java.io.IOException;

import islandGen.IslandGen;
import islandGen.LagoonIslandGen;
import islandGen.RegularIslandGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import configuration.Configuration;
import export.Exporter;
import adt.*;
import importer.Importer;


public class Main {

    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);

        Structs.Mesh input_mesh_io = new MeshFactory().read(config.inputMesh());
        Mesh islandMesh = new Importer().run(input_mesh_io);

        IslandGen island = (config.modeIsLagoon()? new LagoonIslandGen(): new RegularIslandGen());
        island.islandEnricher(islandMesh,args);

        Structs.Mesh mesh_io = new Exporter().run(islandMesh,config);
        new MeshFactory().write(mesh_io, config.outputMesh());
    }
}
