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

        Texture stone =
                new Texture(
                        "/textures/blocks/stone.png"
                );

        STONE = new Block(
                "stone",
                stone
        );

        register("stone", STONE);


        Texture dirt =
                new Texture(
                        "/textures/blocks/dirt.png"
                );

        DIRT = new Block(
                "dirt",
                dirt
        );

        register("dirt", DIRT);


        Texture grassTop =
                new Texture(
                        "/textures/blocks/grass_top.png"
                );

        Texture grassSide =
                new Texture(
                        "/textures/blocks/grass_side.png"
                );

        GRASS = new Block(
                "grass",
                grassTop,
                dirt,
                grassSide
        );

        register("grass", GRASS);


        Texture woodTop =
                new Texture(
                        "/textures/blocks/wood_top.png"
                );

        Texture woodSide =
                new Texture(
                        "/textures/blocks/wood_side.png"
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
