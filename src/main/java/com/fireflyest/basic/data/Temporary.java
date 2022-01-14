package com.fireflyest.basic.data;

import org.bukkit.Location;

import java.util.*;

/**
 * @author Fireflyest
 * 2022/1/6 12:15
 */

public class Temporary {

    private static final Map<String, Location> back = new HashMap<>();
    private static final Map<String, Integer>level = new HashMap<>();
    private static final Map<String, Long>mute = new HashMap<>();
    private static final Set<String>dnd = new HashSet<>();

    private Temporary() {
    }

    public static void addMute(String name, long time) {
        mute.put(name, new Date().getTime() + time);
    }

    public static void removeMute(String name) {
        mute.remove(name);
    }

    public static boolean isMute(String name) {
        return mute.containsKey(name) && mute.get(name) > new Date().getTime();
    }

    public static void addDnd(String name) {
        dnd.add(name);
    }

    public static void removeDnd(String name) {
        dnd.remove(name);
    }

    public static boolean isDnd(String name) {
        return dnd.contains(name);
    }

    public static void putBack(String name, Location loc) {
        back.put(name, loc);
    }

    public static Location getBack(String name) {
        return back.get(name);
    }

    public static void putLevel(String name, int value) {
        level.put(name, value);
    }

    public static int getLevel(String name) {
        return level.getOrDefault(name, 0);
    }

}
