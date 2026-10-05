package com.xid.bitcraft;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Main {

    private static Camera camera;

    private static double lastMouseX;
    private static double lastMouseY;

    private static boolean firstMouse = true;

    private static boolean mouseCaptured = false;

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

        // タイトルバー・×ボタンを表示
        GLFW.glfwWindowHint(
                GLFW.GLFW_DECORATED,
                GLFW.GLFW_TRUE
        );

        // ウィンドウサイズ変更を許可
        GLFW.glfwWindowHint(
                GLFW.GLFW_RESIZABLE,
                GLFW.GLFW_TRUE
        );

        // 最初から最大化しない
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

        // 念のため装飾を有効化
        GLFW.glfwSetWindowAttrib(
                window,
                GLFW.GLFW_DECORATED,
                GLFW.GLFW_TRUE
        );

        // 最大化されていた場合は解除
        GLFW.glfwRestoreWindow(window);

        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        // =========================
        // OpenGL
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
        // マウス移動
        // =========================

        GLFW.glfwSetCursorPosCallback(
                window,
                (windowHandle, mouseX, mouseY) -> {

                    if (!mouseCaptured) {
                        return;
                    }

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

                    float sensitivity = 0.25f;

                    camera.rotate(
                            (float) deltaX * sensitivity,
                            (float) -deltaY * sensitivity
                    );
                }
        );

        // =========================
        // キーボード
        // =========================

        GLFW.glfwSetKeyCallback(
                window,
                (windowHandle, key, scancode, action, mods) -> {

                    if (key == GLFW.GLFW_KEY_ESCAPE
                            && action == GLFW.GLFW_PRESS) {

                        if (mouseCaptured) {

                            // マウスを解放
                            mouseCaptured = false;

                            GLFW.glfwSetInputMode(
                                    windowHandle,
                                    GLFW.GLFW_CURSOR,
                                    GLFW.GLFW_CURSOR_NORMAL
                            );

                            firstMouse = true;

                            System.out.println(
                                    "Mouse released."
                            );

                        } else {

                            // マウスを再捕捉
                            mouseCaptured = true;

                            GLFW.glfwSetInputMode(
                                    windowHandle,
                                    GLFW.GLFW_CURSOR,
                                    GLFW.GLFW_CURSOR_DISABLED
                            );

                            firstMouse = true;

                            System.out.println(
                                    "Mouse captured."
                            );
                        }
                    }
                }
        );

        // =========================
        // マウスクリック
        // =========================

        GLFW.glfwSetMouseButtonCallback(
                window,
                (windowHandle, button, action, mods) -> {

                    if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT
                            && action == GLFW.GLFW_PRESS
                            && !mouseCaptured) {

                        mouseCaptured = true;

                        GLFW.glfwSetInputMode(
                                windowHandle,
                                GLFW.GLFW_CURSOR,
                                GLFW.GLFW_CURSOR_DISABLED
                        );

                        firstMouse = true;

                        System.out.println(
                                "Mouse captured."
                        );
                    }
                }
        );

        // =========================
        // メインループ
        // =========================

        while (!GLFW.glfwWindowShouldClose(window)) {

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
            // 画面表示
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
