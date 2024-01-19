# Terrain-Generator

## How to run the product

Run `mvn install`. This should create `generator.jar` in the `generator` directory, `island.jar` in the `island` directory, and `visualizer.jar` in the `visualizer` directory. 

### Generating a mesh, grid or irregular. Create an irregular mesh for best effect

```
java -jar generator/generator.jar -k grid -h 1080 -w 1920 -p 1000 -s 20 -o input.mesh 
java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 1000 -s 20 -o input.mesh
```

One can run the generator with `-help` as option to see the different command line arguments that are available

### Generating an island mesh:

| Feature | Options | Default |
|:--:|---------------|------|
| --mode | lagoon , regular | regular |
| --shape | circle , rectangle | circle |
| --altitude | steep, flat, plateau | flat |
| --lakes | 0,1,2,3,4 ....| 0|
| --aquifers | 0,1,2,3,4 .... | 0|
| --soil | quick, slow, normal | normal|
| --biome | rocky, arctic, grassland | grassland|
| -city | 0,1,2,3,4 .... | 0|

```
java -jar island/island.jar -i input.mesh -o lagoon.mesh --mode lagoon -city 6

java -jar island/island.jar -i input.mesh -o volcano.mesh --mode regular --shape circle --altitude steep --lakes 0 --aquifers 2 --soil quick --biome rocky -city 4

java -jar island/island.jar -i input.mesh -o arctic.mesh --mode regular --shape circle --altitude plateau --lakes 2 --aquifers 1 --soil slow --biome arctic -city 6

java -jar island/island.jar -i input.mesh -o rectangle.mesh --mode regular --shape rectangle --altitude flat --lakes 5 --aquifers 3 --soil normal --biome grassland -city 6

java -jar island/island.jar -i input.mesh -o rectangle.mesh --mode regular --shape rectangle --altitude default --lakes 5 --aquifers 3 --soil quick --biome grassland -city 6
```

### Visualizing a mesh, (use -x for debug mode)

```
java -jar visualizer/visualizer.jar -i lagoon.mesh -o lagoon.svg
java -jar visualizer/visualizer.jar -i volcano.mesh -o volcano.svg
java -jar visualizer/visualizer.jar -i arctic.mesh -o arctic.svg
java -jar visualizer/visualizer.jar -i rectangle.mesh -o rectangle.svg
```

## To viualize the SVG file:

  - Open it with a web browser
  - Convert it into something else with tool slike `rsvg-convert`

