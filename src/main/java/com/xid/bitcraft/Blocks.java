package com.xid.bitcraft;

import java.util.HashMap;
import java.util.Map;

public class Blocks {

    private static final Map<String, Block> BLOCKS =
            new HashMap<>();

    public static Block STONE;
    public static Block DIRT;
    public static Block GRASS;
    public static Block WOOD;

    public static void register(
            String id,
            Block block
    ) {
        BLOCKS.put(id, block);
    }

    public static Block get(String id) {
        return BLOCKS.get(id);
    }

    public static void init() {

        // 石
        Texture stone =
                new Texture(
                        "src/main/resources/textures/blocks/stone.png"
                );

        STONE = new Block(
                "stone",
                stone
        );

        register("stone", STONE);

        // 土
        Texture dirt =
                new Texture(
                        "src/main/resources/textures/blocks/dirt.png"
                );

        DIRT = new Block(
                "dirt",
                dirt
        );

        register("dirt", DIRT);

        // 草
        Texture grassTop =
                new Texture(
                        "src/main/resources/textures/blocks/grass_top.png"
                );

        Texture grassSide =
                new Texture(
                        "src/main/resources/textures/blocks/grass_side.png"
                );

        GRASS = new Block(
                "grass",
                grassTop,
                dirt,
                grassSide
        );

        register("grass", GRASS);

        // 木
        Texture woodTop =
                new Texture(
                        "src/main/resources/textures/blocks/wood_top.png"
                );

        Texture woodSide =
                new Texture(
                        "src/main/resources/textures/blocks/wood_side.png"
                );

        WOOD = new Block(
                "wood",
                woodTop,
                woodTop,
                woodSide
        );

        register("wood", WOOD);
    }
}
