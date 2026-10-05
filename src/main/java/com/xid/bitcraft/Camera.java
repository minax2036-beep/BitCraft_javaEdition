package com.xid.bitcraft;

public class Camera {

    private float x = 0.0f;
    private float y = 1.5f;
    private float z = 3.0f;

    private float yaw = 0.0f;
    private float pitch = -15.0f;

    public void rotate(float deltaYaw, float deltaPitch) {

        yaw += deltaYaw;
        pitch += deltaPitch;

        if (pitch > 89.0f) {
            pitch = 89.0f;
        }

        if (pitch < -89.0f) {
            pitch = -89.0f;
        }

        System.out.println(
                "Camera: yaw=" +
                yaw +
                ", pitch=" +
                pitch
        );
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
