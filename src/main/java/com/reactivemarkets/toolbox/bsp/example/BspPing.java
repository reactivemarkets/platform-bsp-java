/*
 * Copyright (C) 2019 Reactive Markets Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.reactivemarkets.toolbox.bsp.example;

import com.reactivemarkets.toolbox.bsp.BspClient;
import com.reactivemarkets.toolbox.bsp.BspConfig;
import com.reactivemarkets.toolbox.bsp.BspHandler;
import io.netty.channel.EventLoopGroup;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.reactivemarkets.toolbox.bsp.BspFactory.newBspClient;
import static com.reactivemarkets.toolbox.bsp.BspFactory.newBspEventLoopGroup;

public final class BspPing implements BspHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BspPing.class);

    private static final int HB_INT = 5;
    private static final String HOST = "localhost";
    private static final int PORT = 8280;

    @Override
    public void onBspConnnect(final BspClient client) {
        LOGGER.info("onBspConnnect");
    }

    @Override
    public void onBspDisconnect(final BspClient client) {
        LOGGER.info("onBspDisconnect");
        client.reconnect(1, TimeUnit.SECONDS);
    }

    @Override
    public void onBspError(final BspClient client, final Throwable cause) {
        LOGGER.error("onBspError: " + cause.getMessage());
        client.reconnect(1, TimeUnit.SECONDS);
    }

    @Override
    public void onBspMessage(final BspClient client, final String msg) {
        LOGGER.info("onBspMessage: " + msg);
    }

    @Override
    public void onBspTimeout(final BspClient client) {
        LOGGER.warn("onBspTimeout");
        client.reconnect(1, TimeUnit.SECONDS);
    }

    public static void main(final String[] args) throws Exception {

        // Run with the following VM options:
        // --add-opens java.base/jdk.internal.misc=ALL-UNNAMED
        // -Dio.netty.tryReflectionSetAccessible=true

        BasicConfigurator.configure();
        final EventLoopGroup group = newBspEventLoopGroup();
        try {
            final BspConfig config = new BspConfig(HOST, PORT, HB_INT);
            final BspClient client = newBspClient(group, new BspPing(), config);
            try {
                int count = 0;
                while (count < 1000) {
                    LOGGER.info("sending message batch");
                    try {
                        for (int j = 0; j < 5; ++j) {
                            client.write("hello");
                            ++count;
                        }
                        client.flush();
                    } catch (final IOException e) {
                        LOGGER.error("write failed: " + e.getMessage());
                    }
                    Thread.sleep(1000);
                }
            } finally {
                client.close().get();
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
