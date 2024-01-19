package adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum TerrainType {
    OCEAN,
    LAND,
    LAGOON,
    BEACH,
    VOLCANO_PEAK,
    ROCK,
    FOREST,
    SNOW,
    LAKE;

    private static final Map<TerrainType, Color> colorMap = new HashMap<>();

    static {
        Color ocean_color = new Color(10,48,110); //dark blue
        colorMap.put(OCEAN, ocean_color);

        Color land_color = new Color(0,102,0); //green
        colorMap.put(LAND, land_color);

        Color lagoon_color = new Color(27,101,133); //blue
        colorMap.put(LAGOON, lagoon_color);

        Color beach_color = new Color(255,255,150); //yellow
        colorMap.put(BEACH, beach_color);

        Color volcanic_peak_color = new Color(255,102,0); //orange
        colorMap.put(VOLCANO_PEAK, volcanic_peak_color);

        Color rock_color = new Color(153,153,153); //light grey
        colorMap.put(ROCK, rock_color);

        Color forest_color = new Color(27,84,27); //dark green
        colorMap.put(FOREST, forest_color);

        Color snow_color = new Color(100,255,255); //light blue
        colorMap.put(SNOW, snow_color);

        Color lake_color = new Color(0,128,255); //lake blue
        colorMap.put(LAKE, lake_color);
    }
    public Color getColor() {
        return colorMap.get(this);
    }
}
