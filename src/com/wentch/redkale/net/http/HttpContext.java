/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wentch.redkale.net.http;

import com.wentch.redkale.convert.json.*;
import com.wentch.redkale.net.*;
import com.wentch.redkale.util.*;
import com.wentch.redkale.watch.*;
import java.net.*;
import java.nio.*;
import java.nio.charset.*;
import java.security.*;
import java.util.concurrent.*;
import java.util.logging.*;

/**
 *
 * @author zhangjx
 */
public final class HttpContext extends Context {

    protected final String contextPath;

    protected final JsonFactory jsonFactory;

    protected final SecureRandom random = new SecureRandom();

    public HttpContext(long serverStartTime, Logger logger, ExecutorService executor, ObjectPool<ByteBuffer> bufferPool,
            ObjectPool<Response> responsePool, int maxbody, Charset charset, InetSocketAddress address,
            PrepareServlet prepare, WatchFactory watch, int readTimeoutSecond, int writeTimeoutSecond, String contextPath) {
        super(serverStartTime, logger, executor, bufferPool, responsePool, maxbody, charset,
                address, prepare, watch, readTimeoutSecond, writeTimeoutSecond);
        this.contextPath = contextPath;
        this.jsonFactory = JsonFactory.root();
        random.setSeed(Math.abs(System.nanoTime()));
    }

    public String getContextPath() {
        return this.contextPath;
    }

    protected String createSessionid() {
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return new String(Utility.binToHex(bytes));
    }

    protected WatchFactory getWatchFactory() {
        return watch;
    }

    protected ExecutorService getExecutor() {
        return executor;
    }

    protected ObjectPool<Response> getResponsePool() {
        return responsePool;
    }

    public JsonConvert getJsonConvert() {
        return jsonFactory.getConvert();
    }
}