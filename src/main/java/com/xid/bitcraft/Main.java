package com.xid.bitcraft;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Main {

    public static void main(String[] args) {

        System.out.println("BitCraft starting...");

        // GLFWを初期化する
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("GLFW initialization failed.");
        }

        // OpenGLのバージョンを指定する
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(
                GLFW.GLFW_OPENGL_PROFILE,
                GLFW.GLFW_OPENGL_COMPAT_PROFILE
        );

        // ウィンドウを作る
        long window = GLFW.glfwCreateWindow(
                1280,
                720,
                "BitCraft",
                0,
                0
        );

        if (window == 0) {
            GLFW.glfwTerminate();
            throw new IllegalStateException("Window creation failed.");
        }

        // OpenGLの描画対象をこのウィンドウにする
        GLFW.glfwMakeContextCurrent(window);

        // 垂直同期を有効にする
        GLFW.glfwSwapInterval(1);

        // ウィンドウを表示する
        GLFW.glfwShowWindow(window);

        // OpenGLの機能をLWJGLから使えるようにする
        GL.createCapabilities();

        // 深度テストを有効にする
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // 背景色
        GL11.glClearColor(
                0.2f,
                0.6f,
                0.9f,
                1.0f
        );

        // 3D表示用の設定
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        GL11.glOrtho(
                -2.0,
                2.0,
                -2.0,
                2.0,
                -10.0,
                10.0
        );

        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        // ゲームループ
        while (!GLFW.glfwWindowShouldClose(window)) {

            // 画面と深度バッファを消去
            GL11.glClear(
                    GL11.GL_COLOR_BUFFER_BIT |
                    GL11.GL_DEPTH_BUFFER_BIT
            );

            // カメラを少し引く
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -1.5f);

            // ブロックを描画
            BlockRenderer.renderBlock();

            // 描画結果を表示
            GLFW.glfwSwapBuffers(window);

            // イベント処理
            GLFW.glfwPollEvents();
        }

        // ウィンドウを破棄
        GLFW.glfwDestroyWindow(window);

        // GLFWを終了
        GLFW.glfwTerminate();

        System.out.println("BitCraft closed.");
    }
}
