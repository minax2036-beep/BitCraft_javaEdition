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
                GLFW.GLFW_OPENGL_CORE_PROFILE
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

        // 背景色を設定する
        GL11.glClearColor(
                0.2f,
                0.6f,
                0.9f,
                1.0f
        );

        // ゲームループ
        while (!GLFW.glfwWindowShouldClose(window)) {

            // 画面を背景色で消去する
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            // 描画結果を画面に表示する
            GLFW.glfwSwapBuffers(window);

            // キーボードやマウスなどのイベントを処理する
            GLFW.glfwPollEvents();
        }

        // ウィンドウを破棄する
        GLFW.glfwDestroyWindow(window);

        // GLFWを終了する
        GLFW.glfwTerminate();

        System.out.println("BitCraft closed.");
    }
}
