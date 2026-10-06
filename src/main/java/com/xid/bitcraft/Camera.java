package com.xid.bitcraft;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private float x = 0.0f;
    private float y = 1.5f;
    private float z = 3.0f;

    private float yaw = 0.0f;
    private float pitch = -15.0f;

    private final Vector3f position =
            new Vector3f();

    private final Vector3f front =
            new Vector3f();

    private final Vector3f up =
            new Vector3f(0.0f, 1.0f, 0.0f);

    private final Vector3f center =
            new Vector3f();

    private final Matrix4f viewMatrix =
            new Matrix4f();

    public Camera() {
        update();
    }

    public void rotate(
            float deltaYaw,
            float deltaPitch
    ) {

        yaw += deltaYaw;
        pitch += deltaPitch;

        if (pitch > 89.0f) {
            pitch = 89.0f;
        }

        if (pitch < -89.0f) {
            pitch = -89.0f;
        }

        update();
    }

    public void setPosition(
            float x,
            float y,
            float z
    ) {

        this.x = x;
        this.y = y;
        this.z = z;

        update();
    }

    private void update() {

        /*
         * カメラ位置
         */
        position.set(
                x,
                y,
                z
        );

        /*
         * yaw / pitchから
         * カメラの正面方向を計算
         */
        float yawRadians =
                (float) Math.toRadians(yaw);

        float pitchRadians =
                (float) Math.toRadians(pitch);

        front.set(
                (float) (
                        Math.cos(pitchRadians)
                                * Math.sin(yawRadians)
                ),

                (float) Math.sin(pitchRadians),

                (float) (
                        -Math.cos(pitchRadians)
                                * Math.cos(yawRadians)
                )
        );

        /*
         * カメラが見る場所
         */
        center.set(position)
                .add(front);

        /*
         * JOMLでView Matrixを作成
         */
        viewMatrix.identity();

        viewMatrix.lookAt(
                position,
                center,
                up
        );
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getFront() {
        return front;
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
