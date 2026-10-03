package com.xid.bitcraft;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Main {

    private static Camera camera;

    private static double lastMouseX;
    private static double lastMouseY;

    private static boolean firstMouse = true;

    public static void main(String[] args) {

        System.out.println("BitCraft starting...");

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException(
                    "GLFW initialization failed."
            );
        }

        GLFW.glfwWindowHint(
                GLFW.GLFW_CONTEXT_VERSION_MAJOR,
                3
        );

        GLFW.glfwWindowHint(
                GLFW.GLFW_CONTEXT_VERSION_MINOR,
                3
        );

        GLFW.glfwWindowHint(
                GLFW.GLFW_OPENGL_PROFILE,
                GLFW.GLFW_OPENGL_COMPAT_PROFILE
        );

        long window = GLFW.glfwCreateWindow(
                1280,
                720,
                "BitCraft",
                0,
                0
        );

        if (window == 0) {
            GLFW.glfwTerminate();

            throw new IllegalStateException(
                    "Window creation failed."
            );
        }

        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glClearColor(
                0.2f,
                0.6f,
                0.9f,
                1.0f
        );

        // =========================
        // カメラ
        // =========================

        camera = new Camera();

        // =========================
        // テクスチャ
        // =========================

        Texture blockTexture =
                new Texture(
                        "src/main/resources/textures/blocks/stone.png"
                );

        // =========================
        // マウス
        // =========================

        GLFW.glfwSetInputMode(
                window,
                GLFW.GLFW_CURSOR,
                GLFW.GLFW_CURSOR_DISABLED
        );

        GLFW.glfwSetCursorPosCallback(
                window,
                (windowHandle, mouseX, mouseY) -> {

                    if (firstMouse) {

                        lastMouseX = mouseX;
                        lastMouseY = mouseY;

                        firstMouse = false;

                        return;
                    }

                    double deltaX =
                            mouseX - lastMouseX;

                    double deltaY =
                            mouseY - lastMouseY;

                    lastMouseX = mouseX;
                    lastMouseY = mouseY;

                    float sensitivity = 0.15f;

                    camera.rotate(
                            (float) deltaX * sensitivity,
                            (float) -deltaY * sensitivity
                    );
                }
        );

        // =========================
        // メインループ
        // =========================

        while (!GLFW.glfwWindowShouldClose(window)) {

            GL11.glClear(
                    GL11.GL_COLOR_BUFFER_BIT |
                    GL11.GL_DEPTH_BUFFER_BIT
            );

            // 透視投影
            GL11.glMatrixMode(
                    GL11.GL_PROJECTION
            );

            GL11.glLoadIdentity();

            float aspect =
                    1280.0f / 720.0f;

            float fov = 70.0f;
            float near = 0.1f;
            float far = 100.0f;

            float top =
                    (float) Math.tan(
                            Math.toRadians(fov / 2.0)
                    ) * near;

            float bottom = -top;

            float right = top * aspect;
            float left = -right;

            GL11.glFrustum(
                    left,
                    right,
                    bottom,
                    top,
                    near,
                    far
            );

            // モデルビュー
            GL11.glMatrixMode(
                    GL11.GL_MODELVIEW
            );

            GL11.glLoadIdentity();

            // カメラ回転
            GL11.glRotatef(
                    -camera.getPitch(),
                    1.0f,
                    0.0f,
                    0.0f
            );

            GL11.glRotatef(
                    -camera.getYaw(),
                    0.0f,
                    1.0f,
                    0.0f
            );

            // カメラ位置
            GL11.glTranslatef(
                    -camera.getX(),
                    -camera.getY(),
                    -camera.getZ()
            );

            // ブロック描画
            BlockRenderer.renderBlock(
                    blockTexture,
                    blockTexture,
                    blockTexture
            );

            GLFW.glfwSwapBuffers(window);

            GLFW.glfwPollEvents();
        }

        // =========================
        // 終了処理
        // =========================

        blockTexture.delete();

        GLFW.glfwDestroyWindow(window);

        GLFW.glfwTerminate();

        System.out.println("BitCraft closed.");
    }
}
