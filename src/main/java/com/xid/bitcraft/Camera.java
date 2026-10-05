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

        // 上を向きすぎない
        if (pitch > 89.0f) {
            pitch = 89.0f;
        }

        // 下を向きすぎない
        if (pitch < -89.0f) {
            pitch = -89.0f;
        }
    }

    // カメラが向いている方向のX成分
    public float getFrontX() {
        return (float) (
                Math.cos(Math.toRadians(pitch))
                * Math.sin(Math.toRadians(yaw))
        );
    }

    // カメラが向いている方向のY成分
    public float getFrontY() {
        return (float) (
                Math.sin(Math.toRadians(pitch))
        );
    }

    // カメラが向いている方向のZ成分
    public float getFrontZ() {
        return (float) (
                -Math.cos(Math.toRadians(pitch))
                * Math.cos(Math.toRadians(yaw))
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

    public void setPosition(
            float x,
            float y,
            float z
    ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
