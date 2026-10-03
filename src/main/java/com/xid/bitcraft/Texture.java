package com.xid.bitcraft;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {

    private final int id;

    public Texture(String path) {

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        STBImage.stbi_set_flip_vertically_on_load(true);

        ByteBuffer image = STBImage.stbi_load(
                path,
                width,
                height,
                channels,
                4
        );

        if (image == null) {
            throw new RuntimeException(
                    "Texture loading failed: " + path
            );
        }

        id = GL11.glGenTextures();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D,
                GL11.GL_TEXTURE_MIN_FILTER,
                GL11.GL_NEAREST
        );

        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D,
                GL11.GL_TEXTURE_MAG_FILTER,
                GL11.GL_NEAREST
        );

        GL11.glTexImage2D(
                GL11.GL_TEXTURE_2D,
                0,
                GL11.GL_RGBA,
                width.get(0),
                height.get(0),
                0,
                GL11.GL_RGBA,
                GL11.GL_UNSIGNED_BYTE,
                image
        );

        STBImage.stbi_image_free(image);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        System.out.println(
                "Loaded texture: " +
                path +
                " (" +
                width.get(0) +
                "x" +
                height.get(0) +
                ")"
        );
    }

    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public void delete() {
        GL11.glDeleteTextures(id);
    }
}
