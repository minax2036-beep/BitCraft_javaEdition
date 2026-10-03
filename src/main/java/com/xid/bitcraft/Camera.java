package com.xid.bitcraft;

public class Camera {

    // カメラの位置
    private float x = 0.0f;
    private float y = 1.5f;
    private float z = 3.0f;

    // カメラの向き
    private float yaw = 0.0f;
    private float pitch = -15.0f;

    public void rotate(float deltaYaw, float deltaPitch) {

        yaw += deltaYaw;
        pitch += deltaPitch;

        // 上下を見すぎないように制限
        if (pitch > 89.0f) {
            pitch = 89.0f;
        }

        if (pitch < -89.0f) {
            pitch = -89.0f;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
