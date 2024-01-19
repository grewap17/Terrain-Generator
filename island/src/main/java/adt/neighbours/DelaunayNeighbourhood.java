package adt.neighbours;

import adt.Polygon;
import adt.Vertex;
import adt.neighbours.PrecisionModel;
import adt.neighbours.ExtractTriangles;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DelaunayNeighbourhood extends Neighborhood {

    @Override
    protected void computeRelations() {
        Set<Coordinate> sites = new HashSet<>();
        for(Vertex v: this.registeredCentroids()){
            sites.add(new Coordinate(v.x(), v.y()));
        }
        DelaunayTriangulationBuilder builder = new DelaunayTriangulationBuilder();
        builder.setSites(sites);
        Geometry triangles = builder.getTriangles(PrecisionModel.FACTORY);
        ExtractTriangles visitor = new ExtractTriangles();
        triangles.apply(visitor);
        store(visitor.getLinks());
    }

    private void store(Map<Vertex, Set<Vertex>> links) {
        for(Vertex v: links.keySet()){
            Polygon current = this.polygonAt(v);
            Set<Polygon> linked = links.get(v).stream()
                    .map(this::polygonAt).collect(Collectors.toSet());
            register(current, linked);
        }
    }
}
