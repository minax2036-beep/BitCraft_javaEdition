package com.xid.bitcraft;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Main {

    public static void main(String[] args) {

        System.out.println("BitCraft starting...");

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("GLFW initialization failed.");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);

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

        GLFW.glfwMakeContextCurrent(window);

        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        GL11.glClearColor(
                0.2f,
                0.6f,
                0.9f,
                1.0f
        );

        while (!GLFW.glfwWindowShouldClose(window)) {

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            GLFW.glfwSwapBuffers(window);

            GLFW.glfwPollEvents();
        }

        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();

        System.out.println("BitCraft closed.");
    }
}
