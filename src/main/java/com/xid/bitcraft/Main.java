package com.xid.bitcraft;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Main {

    private static Camera camera;

    public static void main(String[] args) {

        System.out.println("BitCraft starting...");

        // GLFW初期化
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException(
                    "GLFW initialization failed."
            );
        }

        // OpenGL 3.3
        GLFW.glfwWindowHint(
                GLFW.GLFW_CONTEXT_VERSION_MAJOR,
                3
        );

        GLFW.glfwWindowHint(
                GLFW.GLFW_CONTEXT_VERSION_MINOR,
                3
        );

        // 今はOpenGLの固定機能を使うのでCompatibility Profile
        GLFW.glfwWindowHint(
                GLFW.GLFW_OPENGL_PROFILE,
                GLFW.GLFW_OPENGL_COMPAT_PROFILE
        );

        // ウィンドウ設定
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

        // ウィンドウ作成
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

        // ウィンドウ装飾を有効化
        GLFW.glfwSetWindowAttrib(
                window,
                GLFW.GLFW_DECORATED,
                GLFW.GLFW_TRUE
        );

        GLFW.glfwRestoreWindow(window);

        // OpenGLコンテキストを現在のスレッドに設定
        GLFW.glfwMakeContextCurrent(window);

        // VSync
        GLFW.glfwSwapInterval(1);

        // ウィンドウ表示
        GLFW.glfwShowWindow(window);

        // OpenGL機能を初期化
        GL.createCapabilities();

        // 深度テスト
        GL11.glEnable(
                GL11.GL_DEPTH_TEST
        );

        // テクスチャ
        GL11.glEnable(
                GL11.GL_TEXTURE_2D
        );

        // 背景色
        GL11.glClearColor(
                0.2f,
                0.6f,
                0.9f,
                1.0f
        );

        // カメラ
        camera = new Camera();

        // ブロック登録
        Blocks.init();

        // ESCで終了
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

        // メインループ
        while (!GLFW.glfwWindowShouldClose(window)) {

            /*
             * ========================================
             * 入力
             * ========================================
             */

            // カメラ回転速度
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

            /*
             * ========================================
             * 画面クリア
             * ========================================
             */

            GL11.glClear(
                    GL11.GL_COLOR_BUFFER_BIT
                            | GL11.GL_DEPTH_BUFFER_BIT
            );

            /*
             * ========================================
             * ウィンドウサイズ取得
             * ========================================
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

            if (framebufferHeight == 0) {
                framebufferHeight = 1;
            }

            /*
             * ========================================
             * Viewport
             * ========================================
             */

            GL11.glViewport(
                    0,
                    0,
                    framebufferWidth,
                    framebufferHeight
            );

            /*
             * ========================================
             * Projection
             * ========================================
             */

            GL11.glMatrixMode(
                    GL11.GL_PROJECTION
            );

            GL11.glLoadIdentity();

            float aspect =
                    (float) framebufferWidth
                            / (float) framebufferHeight;

            float fov = 70.0f;
            float near = 0.1f;
            float far = 100.0f;

            float top =
                    (float) Math.tan(
                            Math.toRadians(
                                    fov / 2.0
                            )
                    ) * near;

            float bottom = -top;

            float right =
                    top * aspect;

            float left =
                    -right;

            GL11.glFrustum(
                    left,
                    right,
                    bottom,
                    top,
                    near,
                    far
            );

            /*
             * ========================================
             * Camera / View
             * ========================================
             */

            GL11.glMatrixMode(
                    GL11.GL_MODELVIEW
            );

            GL11.glLoadIdentity();

            /*
             * 上下を見る
             */
            GL11.glRotatef(
                    -camera.getPitch(),
                    1.0f,
                    0.0f,
                    0.0f
            );

            /*
             * 左右を見る
             */
            GL11.glRotatef(
                    -camera.getYaw(),
                    0.0f,
                    1.0f,
                    0.0f
            );

            /*
             * カメラ位置を反映
             */
            GL11.glTranslatef(
                    -camera.getX(),
                    -camera.getY(),
                    -camera.getZ()
            );

            /*
             * ========================================
             * ブロック描画
             * ========================================
             */

            Block block = Blocks.STONE;

            BlockRenderer.renderBlock(
                    block.getTopTexture(),
                    block.getBottomTexture(),
                    block.getSideTexture()
            );

            /*
             * ========================================
             * 画面更新
             * ========================================
             */

            GLFW.glfwSwapBuffers(window);

            /*
             * イベント処理
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
