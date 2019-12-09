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

package com.reactivemarkets.toolbox.example;

import com.reactivemarkets.encoding.fbs.AssetType;
import com.reactivemarkets.encoding.fbs.Message;
import com.reactivemarkets.toolbox.bsp.BspClient;
import com.reactivemarkets.toolbox.bsp.BspConfig;
import com.reactivemarkets.toolbox.bsp.BspHandler;
import com.reactivemarkets.toolbox.fbs.FbsFactory;
import com.reactivemarkets.toolbox.util.LoggerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import static com.reactivemarkets.toolbox.bsp.BspFactory.newBspClient;
import static com.reactivemarkets.toolbox.bsp.BspFactory.newBspEventLoopGroup;

public final class BspPing implements BspHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BspPing.class);

    private static final int HB_INT = 5;
    private static final String HOST = "localhost";
    private static final int PORT = 8280;

    public static void main(final String[] args) throws Exception {

        LoggerUtil.consoleLogger();
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
                            client.write(newAsset());
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

    private static ByteBuf newAsset() {
        return FbsFactory.newAsset(1, "EUR", "Euro Member Countries, Euro", AssetType.Ccy);
    }

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
    public void onBspMessage(final BspClient client, final ByteBuf msg) {
        LOGGER.info("onBspMessage: " + msg.readableBytes());
        try {
            final ByteBuffer bb = msg.nioBuffer();
            final Message root = Message.getRootAsMessage(bb);
            final long timestamp = root.tts();
            final byte type = root.bodyType();
            assert type == 1;

            LOGGER.info("timestamp: " + timestamp);
            LOGGER.info("type: " + (int) type);
        } finally {
            msg.release();
        }
    }

    @Override
    public void onBspTimeout(final BspClient client) {
        LOGGER.warn("onBspTimeout");
        client.reconnect(1, TimeUnit.SECONDS);
    }
}
