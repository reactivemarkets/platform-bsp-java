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

package com.reactivemarkets.toolbox.bsp;

import io.netty.buffer.ByteBuf;

public interface BspHandler {
    /**
     * Called when a connection to the remote service is established.
     * @param client The client.
     */
    void onBspConnnect(BspClient client);

    /**
     * Called when a connection to the remote service is lost.
     * @param client The client.
     */
    void onBspDisconnect(BspClient client);

    /**
     * Called when an error occurs on the asynchronous part of an operation.
     * @param client The client.
     * @param cause The cause.
     */
    void onBspError(BspClient client, Throwable cause);

    /**
     * Called when a message is received from the remote service.
     *
     * @param client The client.
     * @param msg The message.
     */
    void onBspMessage(BspClient client, ByteBuf msg);

    /**
     * Called when no message or keepalive has been received from the remote service for longer
     * than the heartbeat interval.
     *
     * @param client The client.
     */
    void onBspTimeout(BspClient client);
}
