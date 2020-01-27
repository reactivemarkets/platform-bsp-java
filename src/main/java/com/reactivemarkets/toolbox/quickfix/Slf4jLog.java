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

package com.reactivemarkets.toolbox.quickfix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.Log;
import quickfix.SessionID;

final class Slf4jLog implements Log {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4jLog.class);

    private final SessionID sessionId;

    Slf4jLog(final SessionID sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void clear() {
    }

    @Override
    public void onIncoming(final String message) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(sessionId + ": onIncoming: " + message);
        }
    }

    @Override
    public void onOutgoing(final String message) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(sessionId + ": onOutgoing: " + message);
        }
    }

    @Override
    public void onEvent(final String text) {
        LOGGER.info(sessionId + ": onEvent: " + text);
    }

    @Override
    public void onErrorEvent(final String text) {
        LOGGER.error(sessionId + ": onErrorEvent: " + text);
    }
}
