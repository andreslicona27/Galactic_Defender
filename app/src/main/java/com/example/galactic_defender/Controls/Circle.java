package com.example.galactic_defender.Controls;

import android.graphics.PointF;

/**
 * Represents a circle in 2D space.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-28-2023]
 */
public class Circle {
    /**
     * Represents the center of position of the circle
     */
    PointF center;

    /**
     * Represents the circle are of the circle
     */
    float radius;

    /**
     * Constructs a Circle object with the specified center and radius.
     *
     * @param center The center point of the circle.
     * @param radius The radius of the circle.
     */
    public Circle(PointF center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Returns the center point of the circle.
     *
     * @return The center point of the circle.
     */
    public PointF getCenter() {
        return center;
    }

    /**
     * Returns the radius of the circle.
     *
     * @return The radius of the circle.
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Checks if the specified coordinates are within the boundaries of the circle.
     *
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return true if the coordinates are inside the circle, false otherwise.
     */
    public boolean contains(float x, float y) {
        float dx = x - center.x;
        float dy = y - center.y;
        return dx * dx + dy * dy <= radius * radius;
    }
}
