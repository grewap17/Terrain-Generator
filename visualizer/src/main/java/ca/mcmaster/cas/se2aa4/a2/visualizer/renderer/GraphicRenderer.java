package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.ColorProperty;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class GraphicRenderer implements Renderer {

    private static final int THICKNESS = 3;

    private void drawCity(Mesh aMesh, Graphics2D canvas){
        List<Vertex> vertices = aMesh.getVerticesList();
        Stroke stroke;
        Optional<Color> fill;
        for (Structs.Vertex v : vertices){
            for (Structs.Property prop : v.getPropertiesList()){
                if (prop.getKey().equals("CityThickness")){
                    canvas.setStroke(stroke = new BasicStroke(Float.parseFloat(prop.getValue())));


                    fill=new ColorProperty().extract(v.getPropertiesList());
                    canvas.setColor((fill=new ColorProperty().extract(v.getPropertiesList())).get());

                    Ellipse2D.Double circle = new Ellipse2D.Double(v.getX(),v.getY(), 1, 1);

                    canvas.fill(circle);
                    canvas.draw(circle);
                }
            }
        }
    }
    private void drawPath(Mesh aMesh, Graphics2D canvas){
        List<Structs.Segment> segments = aMesh.getSegmentsList();
        List<Vertex> vertices = aMesh.getVerticesList();
        Stroke stroke;
        Optional<Color> fill;
        for (Structs.Segment s : segments){
            for (Structs.Property prop : s.getPropertiesList()){
                if (prop.getKey().equals("WidthOfRoad")){
                    canvas.setStroke(stroke = new BasicStroke(Float.parseFloat(prop.getValue())));


                    canvas.setColor((fill = new ColorProperty().extract(s.getPropertiesList())).get());

                    Line2D.Double Line = new Line2D.Double(vertices.get(s.getV1Idx()).getX(), vertices.get(s.getV1Idx()).getY(), vertices.get(s.getV2Idx()).getX(), vertices.get(s.getV2Idx()).getY());
                    canvas.fill(Line);
                    canvas.draw(Line);
                }
            }
        }
    }
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.2f);
        canvas.setStroke(stroke);
        drawPolygons(aMesh,canvas);
        drawCity(aMesh, canvas);
        drawPath(aMesh, canvas);
    }

    private void drawPolygons(Mesh aMesh, Graphics2D canvas) {
        for(Structs.Polygon p: aMesh.getPolygonsList()){
            drawAPolygon(p, aMesh, canvas);
        }
    }

    private void drawAPolygon(Structs.Polygon p, Mesh aMesh, Graphics2D canvas) {
        Hull hull = new Hull();
        for(Integer segmentIdx: p.getSegmentIdxsList()) {
            hull.add(aMesh.getSegments(segmentIdx), aMesh);
        }
        Path2D path = new Path2D.Float();
        Iterator<Vertex> vertices = hull.iterator();
        Vertex current = vertices.next();
        path.moveTo(current.getX(), current.getY());
        while (vertices.hasNext()) {
            current = vertices.next();
            path.lineTo(current.getX(), current.getY());
        }

        path.closePath();
        canvas.draw(path);
        Optional<Color> fill = new ColorProperty().extract(p.getPropertiesList());
        if(fill.isPresent()) {
            Color old = canvas.getColor();
            canvas.setColor(fill.get());
            canvas.fill(path);
            canvas.setColor(old);
        }
    }

}
