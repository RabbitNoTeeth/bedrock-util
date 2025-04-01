package com.gitee.rabbitnoteeth.bedrock.util;

import com.gitee.rabbitnoteeth.bedrock.util.exception.NetException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.regex.Pattern;

/**
 * net utils
 */
public class NetUtils {

    private final static Pattern PING_SUCCESS_PATTERN = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);

    private NetUtils() {
    }

    public static boolean ping(String ip, int timeout) throws NetException {
        try {
            return InetAddress.getByName(ip).isReachable(timeout);
        } catch (Throwable e) {
            throw new NetException(e);
        }
    }

    /**
     * ping
     *
     * @param ip
     * @param pingTimes
     * @param timeout
     * @return
     */
    public static PingResult ping(String ip, int pingTimes, int timeout) throws NetException {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        String pingCommand = isWin() ? ("ping " + ip + " -n " + pingTimes + " -w " + timeout) : ("ping " + ip + " -c " + pingTimes);
        try {
            Process p = r.exec(pingCommand);
            if (p == null) {
                throw new NetException("failed to execute ping command");
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int packetLossCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                if (!PING_SUCCESS_PATTERN.matcher(line).matches()) {
                    packetLossCount++;
                }
            }
            return new PingResult(packetLossCount, packetLossCount * 100 / pingTimes);
        } catch (NetException e) {
            throw e;
        } catch (Throwable e) {
            throw new NetException("failed to execute ping command", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                //ignore
            }
        }
    }

    public static class PingResult {
        private final int packetLossCount;
        private final int packetLossRate;

        public PingResult(int packetLossCount, int packetLossRate) {
            this.packetLossCount = packetLossCount;
            this.packetLossRate = packetLossRate;
        }

        public int getPacketLossCount() {
            return packetLossCount;
        }

        public int getPacketLossRate() {
            return packetLossRate;
        }

    }

    private static String osName() {
        return System.getProperty("os.name");
    }

    private static boolean isWin() {
        return osName().toUpperCase().contains("WIN");
    }

    private static boolean isLinux() {
        return osName().toUpperCase().contains("LINUX");
    }

}
