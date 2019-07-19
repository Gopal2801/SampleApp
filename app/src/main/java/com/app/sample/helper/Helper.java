package com.app.sample.helper;

import java.util.UUID;

public class Helper {
    public static String createJOBID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}
