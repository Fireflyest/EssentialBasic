package com.fireflyest.basic.util;

import java.util.*;

import com.fireflyest.basic.bean.Tpa;
public class TpaUtils {

	private static final Map<UUID, Tpa> tpas = new HashMap<>();

	private TpaUtils() {
	}

	public static void addTpa(UUID tper, UUID receiver, boolean tphere) {
		Tpa tpa = new Tpa(tper, receiver, tphere, new Date().getTime() + 1000 * 60);
		tpas.put(receiver, tpa);
	}
	
	public static void removeTpa(UUID receiver) {
		tpas.remove(receiver);
	}
	
	public static boolean containsReceiver(UUID receiver) {
		return tpas.containsKey(receiver);
	}
	
	public static Tpa getTpa(UUID receiver) {
		return tpas.get(receiver);
	}

}
