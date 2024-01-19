package export;

import PATH.City;
import PATH.FIndPath;
import PATH.MakePath;
import Path.BFS;
import adt.Polygon;
import adt.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import configuration.Configuration;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Exporter {

    public Exporter() {}

    public Structs.Mesh run(Mesh mesh,Configuration configuration) {
        Structs.Mesh.Builder result = Structs.Mesh.newBuilder();
        Map<Vertex, Integer> vertices = registerVertices(mesh, result);
        Map<PairOfVertex, Integer> segments = registerSegments(mesh, result, vertices);
        registerPolygons(mesh, result, segments, vertices);

        int Cities=configuration.numCities();
        Graph g;

        FIndPath path = new FIndPath();
        g=path.MakeAPath(mesh,result);

        BFS FindPath=new BFS();

        List<Node> AllsourceCitites=new ArrayList<>();
        List<Node> AllDestCitites=new ArrayList<>();
        int startCity=RandomCity(g);
        Node startNode=g.nodeMap.get(startCity);
        AllsourceCitites.add(startNode);

        for(int i=0; i< configuration.numCities();i++){
            Node sourceCity=g.nodeMap.get(startCity);
            Node destinationCity= g.nodeMap.get(RandomCity(g));
            AllDestCitites.add(destinationCity);
            List<Node> shortestPath=(FindPath.FindThePath(g,sourceCity,destinationCity));

            MakePath makepath=new MakePath();
            List<Structs.Vertex> verticesList = result.getVerticesList();
            makepath.ColorPath(result,shortestPath);}

        City citys = new City();
        citys.PlotCity1(mesh, result, Cities,AllsourceCitites);

        City cityss = new City();
        cityss.PlotCity2(mesh, result, Cities,AllDestCitites);






        registerPolygons(mesh,result,segments,vertices);

        return result.build();
    }

    private void registerPolygons(Mesh mesh, Structs.Mesh.Builder result,
                                  Map<PairOfVertex, Integer> segments, Map<Vertex, Integer> vertices) {
        Map<Polygon, Integer> polygons = buildPolygonRegistry(mesh);
        for(Polygon p: mesh) {
            int centroidIdx = vertices.get(p.centroid());
            List<Integer> segmentIdxs = new ArrayList<>();
            for(PairOfVertex pov: p.hull()) {
                segmentIdxs.add(segments.get(pov));
            }
            List<Integer> neigbhoursIdx = new ArrayList<>();
            for(Polygon n: p.neighbours()){
                neigbhoursIdx.add(polygons.get(n));
            }
            Structs.Property colorProperty = getColorProperty(p.getColor());

            Structs.Polygon exported = Structs.Polygon.newBuilder()
                    .setCentroidIdx(centroidIdx)
                    .addAllSegmentIdxs(segmentIdxs)
                    .addAllNeighborIdxs(neigbhoursIdx)
                    .addProperties(colorProperty)
                    .build();
            result.addPolygons(exported);
        }
    }

    private Map<PairOfVertex, Integer> registerSegments(Mesh mesh, Structs.Mesh.Builder result,
                                                        Map<Vertex, Integer> vertices ) {
        Map<PairOfVertex, Integer> segments = buildSegmentRegistry(mesh);
        Structs.Segment[] exported = new Structs.Segment[segments.size()];

        for(PairOfVertex key: segments.keySet()){

            Vertex[] contents = key.contents();

//            Structs.Property colors = key.getSegment_type().getColorProperty2();

            Structs.Segment s = Structs.Segment.newBuilder()
                    .setV1Idx(vertices.get(contents[0]))
                    .setV2Idx(vertices.get(contents[1])).build();
            exported[segments.get(key)] = s;
        }
        result.addAllSegments(List.of(exported));
        return segments;
    }
    private Map<Vertex, Integer>  registerVertices(Mesh mesh, Structs.Mesh.Builder result) {
        Map<Vertex, Integer> vertices = buildVertexRegistry(mesh);
        Structs.Vertex[] exported = new Structs.Vertex[vertices.size()];
        for(Vertex key: vertices.keySet()){
            Structs.Vertex v = Structs.Vertex.newBuilder().setX(key.x()).setY(key.y()).build();
            exported[vertices.get(key)] = v;
        }
        result.addAllVertices(List.of(exported));
        return vertices;
    }

    private Map<Vertex, Integer> buildVertexRegistry(Mesh mesh) {
        // Creating the set of all vertices
        Set<Vertex> vertices = new HashSet<>();
        for (Polygon p: mesh) {
            for(Vertex v: p) {
                vertices.add(v);
            }
            vertices.add(p.centroid());
        }
        // building the inverse structure (vertex -> index)
        Map<Vertex, Integer> registry = new HashMap<>();
        int counter = 0;
        for(Vertex v: vertices) {
            registry.put(v, counter++);
        }
        return registry;
    }

    private int RandomCity(Graph graph){
        Set<Integer> keys = graph.nodeMap.keySet();
        Integer[] keyArray = keys.toArray(new Integer[0]);
        Random rand = new Random();
        int randomIndex = rand.nextInt(keyArray.length);
        int randomKey = keyArray[randomIndex];
        return randomKey;

    }


    public static Map<PairOfVertex, Integer> buildSegmentRegistry(Mesh mesh) {
        Set<PairOfVertex> segments = new HashSet<>();
        for(Polygon p: mesh){
            segments.addAll(p.hull());
        }
        Map<PairOfVertex, Integer> registry = new HashMap<>();
        int counter = 0;
        for(PairOfVertex pov: segments) {
            registry.put(pov, counter++);
        }
        return registry;
    }

    private Map<Polygon, Integer> buildPolygonRegistry(Mesh mesh) {
        Map<Polygon, Integer> registry = new HashMap<>();
        int counter = 0;
        for(Polygon p: mesh) {
            registry.put(p, counter++);
        }
        return registry;
    }

    private Structs.Property getColorProperty(Color color){
        String color_code = color.getRed()+","+color.getGreen()+","+color.getBlue();
        Structs.Property color_property = Structs.Property.newBuilder()
                .setKey("rgb_color")
                .setValue(color_code).build();
        return color_property;
    }

}

