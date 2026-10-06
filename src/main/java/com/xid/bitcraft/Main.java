package com.xid.bitcraft;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Main {

    private static Camera camera;

    public static void main(String[] args) {

        System.out.println("BitCraft starting...");

        /*
         * ========================================
         * GLFW初期化
         * ========================================
         */

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException(
                    "GLFW initialization failed."
            );
        }

        /*
         * ========================================
         * OpenGL設定
         * ========================================
         */

        GLFW.glfwWindowHint(
                GLFW.GLFW_CONTEXT_VERSION_MAJOR,
                3
        );

        GLFW.glfwWindowHint(
                GLFW.GLFW_CONTEXT_VERSION_MINOR,
                3
        );

        /*
         * 今は固定機能パイプラインを使うため
         * Compatibility Profileを使用
         */
        GLFW.glfwWindowHint(
                GLFW.GLFW_OPENGL_PROFILE,
                GLFW.GLFW_OPENGL_COMPAT_PROFILE
        );

        /*
         * ========================================
         * ウィンドウ設定
         * ========================================
         */

        GLFW.glfwWindowHint(
                GLFW.GLFW_DECORATED,
                GLFW.GLFW_TRUE
        );

        GLFW.glfwWindowHint(
                GLFW.GLFW_RESIZABLE,
                GLFW.GLFW_TRUE
        );

        GLFW.glfwWindowHint(
                GLFW.GLFW_MAXIMIZED,
                GLFW.GLFW_FALSE
        );

        /*
         * ========================================
         * ウィンドウ作成
         * ========================================
         */

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

        /*
         * ========================================
         * OpenGLコンテキスト
         * ========================================
         */

        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        /*
         * ========================================
         * OpenGL基本設定
         * ========================================
         */

        GL11.glEnable(
                GL11.GL_DEPTH_TEST
        );

        GL11.glEnable(
                GL11.GL_TEXTURE_2D
        );

        GL11.glClearColor(
                0.2f,
                0.6f,
                0.9f,
                1.0f
        );

        /*
         * ========================================
         * Camera
         * ========================================
         */

        camera = new Camera();

        /*
         * ========================================
         * ブロック登録
         * ========================================
         */

        Blocks.init();

        /*
         * ========================================
         * ESCキー
         * ========================================
         */

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

        /*
         * ========================================
         * メインループ
         * ========================================
         */

        while (!GLFW.glfwWindowShouldClose(window)) {

            /*
             * ====================================
             * 入力
             * ====================================
             */

            float cameraSpeed = 1.0f;

            /*
             * 左
             */
            if (GLFW.glfwGetKey(
                    window,
                    GLFW.GLFW_KEY_LEFT
            ) == GLFW.GLFW_PRESS) {

                camera.rotate(
                        -cameraSpeed,
                        0.0f
                );
            }

            /*
             * 右
             */
            if (GLFW.glfwGetKey(
                    window,
                    GLFW.GLFW_KEY_RIGHT
            ) == GLFW.GLFW_PRESS) {

                camera.rotate(
                        cameraSpeed,
                        0.0f
                );
            }

            /*
             * 上
             */
            if (GLFW.glfwGetKey(
                    window,
                    GLFW.GLFW_KEY_UP
            ) == GLFW.GLFW_PRESS) {

                camera.rotate(
                        0.0f,
                        cameraSpeed
                );
            }

            /*
             * 下
             */
            if (GLFW.glfwGetKey(
                    window,
                    GLFW.GLFW_KEY_DOWN
            ) == GLFW.GLFW_PRESS) {

                camera.rotate(
                        0.0f,
                        -cameraSpeed
                );
            }

            /*
             * ====================================
             * 画面クリア
             * ====================================
             */

            GL11.glClear(
                    GL11.GL_COLOR_BUFFER_BIT
                            | GL11.GL_DEPTH_BUFFER_BIT
            );

            /*
             * ====================================
             * framebufferサイズ取得
             * ====================================
             */

            int[] width = new int[1];
            int[] height = new int[1];

            GLFW.glfwGetFramebufferSize(
                    window,
                    width,
                    height
            );

            int framebufferWidth = width[0];
            int framebufferHeight = height[0];

            if (framebufferWidth <= 0) {
                framebufferWidth = 1;
            }

            if (framebufferHeight <= 0) {
                framebufferHeight = 1;
            }

            /*
             * ====================================
             * Viewport
             * ====================================
             */

            GL11.glViewport(
                    0,
                    0,
                    framebufferWidth,
                    framebufferHeight
            );

            /*
             * ====================================
             * Projection Matrix
             * ====================================
             */

            float aspect =
                    (float) framebufferWidth
                            / (float) framebufferHeight;

            float fov = 70.0f;

            float near = 0.1f;

            float far = 100.0f;

            Matrix4f projection =
                    new Matrix4f();

            projection.perspective(
                    (float) Math.toRadians(fov),
                    aspect,
                    near,
                    far
            );

            /*
             * OpenGLにProjection Matrixを渡す
             */

            FloatBuffer projectionBuffer =
                    BufferUtils.createFloatBuffer(16);

            projection.get(
                    projectionBuffer
            );

            GL11.glMatrixMode(
                    GL11.GL_PROJECTION
            );

            GL11.glLoadMatrixf(
                    projectionBuffer
            );

            /*
             * ====================================
             * Camera View Matrix
             * ====================================
             */

            FloatBuffer viewBuffer =
                    BufferUtils.createFloatBuffer(16);

            camera.getViewMatrix().get(
                    viewBuffer
            );

            GL11.glMatrixMode(
                    GL11.GL_MODELVIEW
            );

            GL11.glLoadMatrixf(
                    viewBuffer
            );

            /*
             * ====================================
             * ブロック描画
             * ====================================
             */

            Block block = Blocks.STONE;

            BlockRenderer.renderBlock(
                    block.getTopTexture(),
                    block.getBottomTexture(),
                    block.getSideTexture()
            );

            /*
             * ====================================
             * 画面更新
             * ====================================
             */

            GLFW.glfwSwapBuffers(window);

            /*
             * ====================================
             * イベント処理
             * ====================================
             */

            GLFW.glfwPollEvents();
        }

        /*
         * ========================================
         * 終了処理
         * ========================================
         */

        GLFW.glfwDestroyWindow(window);

        GLFW.glfwTerminate();

        System.out.println(
                "BitCraft closed."
        );
    }
}
