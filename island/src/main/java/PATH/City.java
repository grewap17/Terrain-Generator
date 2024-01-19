package PATH;

import adt.Mesh;
import adt.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class City {

    public List<Node> PlotCity1(Mesh iMesh, Structs.Mesh.Builder result, int Cities, List<Node> StartList) {
        Structs.Property ColorOfCity = Structs.Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();

        List<Node> CityList= new ArrayList<>();
        Random Thicnkess = new Random();
        int Captial=25;
        int Town=15;
        int Hamlet=8;
        Structs.Property thickness = Structs.Property.newBuilder().setKey("CityThickness").setValue(Integer.toString(1)).build();
        int randomCity;

        for (int i=0; i<StartList.size();i++) {

                Node start= new Node(StartList.get(i).x(),StartList.get(i).y());


            randomCity= Thicnkess.nextInt(2);
                if(i==0){
                     thickness = Structs.Property.newBuilder().setKey("CityThickness").setValue(Integer.toString(Captial)).build();
                    ColorOfCity=Structs.Property.newBuilder().setKey("rgb_color").setValue("255,0,0").build();
                }
              else  if(randomCity==0){
                     thickness = Structs.Property.newBuilder().setKey("CityThickness").setValue(Integer.toString(Town)).build();
                    ColorOfCity=Structs.Property.newBuilder().setKey("rgb_color").setValue("240,159,159").build();
                }

           else     if(randomCity==1){
                     thickness = Structs.Property.newBuilder().setKey("CityThickness").setValue(Integer.toString(Hamlet)).build();
                    ColorOfCity=Structs.Property.newBuilder().setKey("rgb_color").setValue("153,255,204").build();
                }

                Structs.Vertex CityVertex = Structs.Vertex.newBuilder().setX(start.x()).setY(start.y()).addProperties(ColorOfCity).addProperties(thickness).build();
                result.addVertices(CityVertex);

                CityList.add(start);



        }
        return CityList;
    }
    public List<Node> PlotCity2(Mesh iMesh, Structs.Mesh.Builder result, int Cities, List<Node> EndList) {
        Structs.Property ColorOfCity = Structs.Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();
        List<Node> CityList= new ArrayList<>();
        Random Thicnkess = new Random();
        int CaptialThick=25;
        int Town=15;
        int Hamlet=8;
        Structs.Property thickness = Structs.Property.newBuilder().setKey("CityThickness").setValue(Integer.toString(1)).build();
        int randomCity;

        for (int i=0; i<EndList.size();i++) {

            Node start= new Node(EndList.get(i).x(),EndList.get(i).y());


            randomCity= Thicnkess.nextInt(2);
            if(randomCity==0){
                thickness = Structs.Property.newBuilder().setKey("CityThickness").setValue(Integer.toString(Town)).build();
                ColorOfCity=Structs.Property.newBuilder().setKey("rgb_color").setValue("240,159,159").build();
            }
            else if(randomCity==1){
                thickness = Structs.Property.newBuilder().setKey("CityThickness").setValue(Integer.toString(Hamlet)).build();
                ColorOfCity=Structs.Property.newBuilder().setKey("rgb_color").setValue("153,255,204").build();
            }

            Structs.Vertex CityVertex = Structs.Vertex.newBuilder().setX(start.x()).setY(start.y()).addProperties(ColorOfCity).addProperties(thickness).build();
            result.addVertices(CityVertex);

            CityList.add(start);



        }
        return CityList;
    }
}