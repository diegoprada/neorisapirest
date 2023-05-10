package com.neoris.backend.apirest.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LoggerController {
    public static String formatLoggerRst(String message, String inOut) throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return "{REST} " + inOut + " " + ip.getHostAddress() + " - " + message;
    }
}
