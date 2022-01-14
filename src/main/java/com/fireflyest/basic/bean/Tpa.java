package com.fireflyest.basic.bean;

import java.util.UUID;

/**
 * @author Fireflyest
 * 2022/1/8 19:29
 */

public class Tpa {

    private UUID tpaer;

    private UUID receiver;

    private boolean tphere;

    private long time;

    public Tpa(UUID tpaer, UUID receiver, boolean tphere, long time) {
        this.tpaer = tpaer;
        this.receiver = receiver;
        this.tphere = tphere;
        this.time = time;
    }

    public Tpa() {
    }

    public UUID getTpaer() {
        return tpaer;
    }

    public void setTpaer(UUID tpaer) {
        this.tpaer = tpaer;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public void setReceiver(UUID receiver) {
        this.receiver = receiver;
    }

    public boolean isTphere() {
        return tphere;
    }

    public void setTphere(boolean tphere) {
        this.tphere = tphere;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
