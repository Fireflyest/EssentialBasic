package com.fireflyest.basic.bean;

/**
 * @author Fireflyest
 * 2022/1/6 14:26
 */
public class Sign {

    private int id;
    private String command;
    private String world;
    private double x;
    private double y;
    private double z;

    public Sign() {
    }

    public Sign(String command, String world, double x, double y, double z) {
        this.command = command;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

}
