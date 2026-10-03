package com.xid.bitcraft;

public class Block {

    private final String id;

    private final Texture topTexture;
    private final Texture bottomTexture;
    private final Texture sideTexture;

    // 6面同じテクスチャ
    public Block(String id, Texture texture) {
        this(
                id,
                texture,
                texture,
                texture
        );
    }

    // 上・下・側面を分ける
    public Block(
            String id,
            Texture topTexture,
            Texture bottomTexture,
            Texture sideTexture
    ) {
        this.id = id;

        this.topTexture = topTexture;
        this.bottomTexture = bottomTexture;
        this.sideTexture = sideTexture;
    }

    public String getId() {
        return id;
    }

    public Texture getTopTexture() {
        return topTexture;
    }

    public Texture getBottomTexture() {
        return bottomTexture;
    }

    public Texture getSideTexture() {
        return sideTexture;
    }
}
