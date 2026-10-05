package com.xid.bitcraft;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Main {

    private static Camera camera;

    public static void main(String[] args) {

        System.out.println("BitCraft starting...");

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException(
                    "GLFW initialization failed."
            );
        }

        // =========================
        // ウィンドウ設定
        // =========================

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

        // タイトルバー・×ボタン
        GLFW.glfwWindowHint(
                GLFW.GLFW_DECORATED,
                GLFW.GLFW_TRUE
        );

        // ウィンドウサイズ変更可能
        GLFW.glfwWindowHint(
                GLFW.GLFW_RESIZABLE,
                GLFW.GLFW_TRUE
        );

        // 最大化しない
        GLFW.glfwWindowHint(
                GLFW.GLFW_MAXIMIZED,
                GLFW.GLFW_FALSE
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

        GLFW.glfwSetWindowAttrib(
                window,
                GLFW.GLFW_DECORATED,
                GLFW.GLFW_TRUE
        );

        GLFW.glfwRestoreWindow(window);

        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        // =========================
        // OpenGL設定
        // =========================

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

        Blocks.init();

        // =========================
        // ESCで終了
        // =========================

        GLFW.glfwSetKeyCallback(
                window,
                (windowHandle, key, scancode, action, mods) -> {

                    if (key == GLFW.GLFW_KEY_ESCAPE
                            && action == GLFW.GLFW_PRESS) {

                        GLFW.glfwSetWindowShouldClose(
                                windowHandle,
                                true
                        );
                    }
                }
        );

        // =========================
        // メインループ
        // =========================

        while (!GLFW.glfwWindowShouldClose(window)) {

            // =========================
            // カメラ操作テスト
            // =========================

            float cameraSpeed = 1.0f;

            // 左
            if (GLFW.glfwGetKey(
                    window,
                    GLFW.GLFW_KEY_LEFT
            ) == GLFW.GLFW_PRESS) {

                camera.rotate(
                        -cameraSpeed,
                        0.0f
                );
            }

            // 右
            if (GLFW.glfwGetKey(
                    window,
                    GLFW.GLFW_KEY_RIGHT
            ) == GLFW.GLFW_PRESS) {

                camera.rotate(
                        cameraSpeed,
                        0.0f
                );
            }

            // 上
            if (GLFW.glfwGetKey(
                    window,
                    GLFW.GLFW_KEY_UP
            ) == GLFW.GLFW_PRESS) {

                camera.rotate(
                        0.0f,
                        cameraSpeed
                );
            }

            // 下
            if (GLFW.glfwGetKey(
                    window,
                    GLFW.GLFW_KEY_DOWN
            ) == GLFW.GLFW_PRESS) {

                camera.rotate(
                        0.0f,
                        -cameraSpeed
                );
            }

            // =========================
            // 画面クリア
            // =========================

            GL11.glClear(
                    GL11.GL_COLOR_BUFFER_BIT |
                    GL11.GL_DEPTH_BUFFER_BIT
            );

            // =========================
            // 透視投影
            // =========================

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

            // =========================
            // モデルビュー
            // =========================

            GL11.glMatrixMode(
                    GL11.GL_MODELVIEW
            );

            GL11.glLoadIdentity();

            // =========================
            // カメラ回転
            // =========================

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

            // =========================
            // カメラ位置
            // =========================

            GL11.glTranslatef(
                    -camera.getX(),
                    -camera.getY(),
                    -camera.getZ()
            );

            // =========================
            // ブロック描画
            // =========================

            Block block = Blocks.STONE;

            BlockRenderer.renderBlock(
                    block.getTopTexture(),
                    block.getBottomTexture(),
                    block.getSideTexture()
            );

            // =========================
            // 表示
            // =========================

            GLFW.glfwSwapBuffers(window);

            GLFW.glfwPollEvents();
        }

        // =========================
        // 終了処理
        // =========================

        GLFW.glfwDestroyWindow(window);

        GLFW.glfwTerminate();

        System.out.println(
                "BitCraft closed."
        );
    }
}
