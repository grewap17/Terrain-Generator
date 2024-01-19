package adt;

import adt.neighbours.Neighborhood;

import java.util.*;

public class Mesh implements Iterable<Polygon> {
    /*
    Design Choice: This mesh adt is kept seperate from the adt in the generator sub-module.
    This is because keeping the adt that generator uses and the adt that island uses independent
    of each other allows both sub-projects to grow independently of each other. Also, this allows
    unused functions to be deleted to optimize runtime.
     */

    private Set<Polygon> polygons;
    private int width, height;
    public Mesh() {
        this.polygons = new HashSet<>();
    }
    public Mesh(int width, int height) {
        this.width = width;
        this.height = height;
        this.polygons = new HashSet<>();
    }

    public void register(Polygon p) {
        this.polygons.add(p.crop(width, height));
    }

    @Override
    public Iterator<Polygon> iterator() {
        return this.polygons.iterator();
    }

    public void populateNeighbours(Neighborhood neighbourhood) {
        neighbourhood.build(this.polygons);
        for(Polygon p: this) {
            for(Polygon n: neighbourhood.neighbors(p)) {
                p.registerAsNeighbour(n);
            }
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mesh mesh = (Mesh) o;
        if (mesh.getWidth() != this.getWidth()) return false;
        if (mesh.getHeight() != this.getHeight()) return false;
        return mesh.polygons.equals(this.polygons);
    }
    @Override
    public String toString() {
        return "Mesh(" +width+"x"+height+","+polygons+")";
    }

    public int getWidth(){
        int copy_of_width = Integer.valueOf(width);
        return copy_of_width;
    }
    public int getHeight(){
        int copy_of_height = Integer.valueOf(height);
        return copy_of_height;
    }
}

