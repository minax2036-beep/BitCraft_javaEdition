package com.xid.bitcraft;

import org.lwjgl.opengl.GL11;

public class BlockRenderer {

    public static void renderBlock(
            Texture topTexture,
            Texture bottomTexture,
            Texture sideTexture
    ) {

        float size = 0.5f;

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        // =========================
        // 前面
        // =========================

        sideTexture.bind();

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(-size, -size, size);

        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(size, -size, size);

        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(size, size, size);

        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(-size, size, size);

        GL11.glEnd();

        // =========================
        // 背面
        // =========================

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(size, -size, -size);

        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(-size, -size, -size);

        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(-size, size, -size);

        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(size, size, -size);

        GL11.glEnd();

        // =========================
        // 左面
        // =========================

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(-size, -size, -size);

        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(-size, -size, size);

        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(-size, size, size);

        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(-size, size, -size);

        GL11.glEnd();

        // =========================
        // 右面
        // =========================

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(size, -size, size);

        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(size, -size, -size);

        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(size, size, -size);

        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(size, size, size);

        GL11.glEnd();

        // =========================
        // 上面
        // =========================

        topTexture.bind();

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(-size, size, size);

        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(size, size, size);

        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(size, size, -size);

        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(-size, size, -size);

        GL11.glEnd();

        // =========================
        // 底面
        // =========================

        bottomTexture.bind();

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(-size, -size, -size);

        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(size, -size, -size);

        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(size, -size, size);

        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(-size, -size, size);

        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
}
