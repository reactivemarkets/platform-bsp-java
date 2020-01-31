/*
 * Copyright (C) 2020 Reactive Markets Limited
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

public final class BspConstants {
    /**
     * The maximum length of the frame.
     * <p>
     * An IPv4 header is 20 bytes if it has no options, and a UDP header is 8 bytes,
     * so the maximum UDP payload size is 1500-28 = 1472.
     */
    public static final int MAX_FRAME_LENGTH = 1472;
    /**
     * The offset of the length field.
     */
    public static final int LENGTH_FIELD_OFFSET = 0;
    /**
     * The length of the length field.
     */
    public static final int LENGTH_FIELD_LENGTH = 4;
    /**
     * The compensation value to add to the value of the length field.
     */
    public static final int LENGTH_ADJUSTMENT = 0;
    /**
     * The number of first bytes to strip out from the decoded frame.
     */
    public static final int INITIAL_BYTES_TO_STRIP = 4;

    /**
     * Maximum message size.
     * This upper-bound for message payloads was chosen as follows:
     * <ul>
     * <li>Max Datagram (1472) - Aeron Header (32) = 1440</li>
     * <li>Round-down to cache-line boundary: (1440 & ~63) = 1408</li>
     * <li>Subtract MPMC queue header: 1408 - 8 = 1400</li>
     * </ul>
     */
    public static final int MAX_MESSAGE_SIZE = 1400;

    private BspConstants() {
    }
}
