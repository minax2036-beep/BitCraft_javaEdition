package com.xid.bitcraft;

import org.lwjgl.opengl.GL11;

public class BlockRenderer {

    /**
     * ブロック1個を描画する
     *
     * ブロックの中心を (0, 0, 0) として、
     * 1×1×1 の立方体を描画する。
     */
    public static void renderBlock() {

        float size = 0.5f;

        GL11.glBegin(GL11.GL_QUADS);

        // 前面
        GL11.glColor3f(0.3f, 0.8f, 0.3f);

        GL11.glVertex3f(-size, -size, size);
        GL11.glVertex3f(size, -size, size);
        GL11.glVertex3f(size, size, size);
        GL11.glVertex3f(-size, size, size);

        // 背面
        GL11.glColor3f(0.2f, 0.6f, 0.2f);

        GL11.glVertex3f(size, -size, -size);
        GL11.glVertex3f(-size, -size, -size);
        GL11.glVertex3f(-size, size, -size);
        GL11.glVertex3f(size, size, -size);

        // 左面
        GL11.glColor3f(0.25f, 0.7f, 0.25f);

        GL11.glVertex3f(-size, -size, -size);
        GL11.glVertex3f(-size, -size, size);
        GL11.glVertex3f(-size, size, size);
        GL11.glVertex3f(-size, size, -size);

        // 右面
        GL11.glColor3f(0.35f, 0.9f, 0.35f);

        GL11.glVertex3f(size, -size, size);
        GL11.glVertex3f(size, -size, -size);
        GL11.glVertex3f(size, size, -size);
        GL11.glVertex3f(size, size, size);

        // 上面
        GL11.glColor3f(0.45f, 1.0f, 0.45f);

        GL11.glVertex3f(-size, size, size);
        GL11.glVertex3f(size, size, size);
        GL11.glVertex3f(size, size, -size);
        GL11.glVertex3f(-size, size, -size);

        // 底面
        GL11.glColor3f(0.15f, 0.5f, 0.15f);

        GL11.glVertex3f(-size, -size, -size);
        GL11.glVertex3f(size, -size, -size);
        GL11.glVertex3f(size, -size, size);
        GL11.glVertex3f(-size, -size, size);

        GL11.glEnd();
    }
}
