package com.xid.bitcraft;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {

    private final int id;

    public Texture(String resourcePath) {

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        STBImage.stbi_set_flip_vertically_on_load(true);

        ByteBuffer imageData = loadResource(resourcePath);

        ByteBuffer image = STBImage.stbi_load_from_memory(
                imageData,
                width,
                height,
                channels,
                4
        );

        if (image == null) {
            throw new RuntimeException(
                    "Texture loading failed: " + resourcePath
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
                resourcePath +
                " (" +
                width.get(0) +
                "x" +
                height.get(0) +
                ")"
        );
    }

    private static ByteBuffer loadResource(String resourcePath) {

        try (InputStream input =
                     Texture.class.getResourceAsStream(resourcePath)) {

            if (input == null) {
                throw new RuntimeException(
                        "Resource not found: " + resourcePath
                );
            }

            ByteArrayOutputStream output =
                    new ByteArrayOutputStream();

            byte[] buffer = new byte[8192];

            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            byte[] data = output.toByteArray();

            ByteBuffer byteBuffer =
                    BufferUtils.createByteBuffer(data.length);

            byteBuffer.put(data);
            byteBuffer.flip();

            return byteBuffer;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load resource: " +
                    resourcePath,
                    e
            );
        }
    }

    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public void delete() {
        GL11.glDeleteTextures(id);
    }
}
