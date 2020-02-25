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

package com.reactivemarkets.toolbox.example;

import com.reactivemarkets.toolbox.fix.FixMaker;
import com.reactivemarkets.toolbox.fix.FixUtility;
import com.reactivemarkets.toolbox.quickfix.NullStoreFactory;
import com.reactivemarkets.toolbox.quickfix.Slf4jLogFactory;
import com.reactivemarkets.toolbox.util.LoggerUtil;
import quickfix.Application;
import quickfix.DefaultMessageFactory;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;

import java.util.concurrent.atomic.AtomicBoolean;

public final class FixBot {
    private FixBot() {
    }

    public static void main(final String[] args) throws Exception {

        LoggerUtil.consoleLogger();
        final Application app = new FixMaker();
        final MessageStoreFactory storeFactory = new NullStoreFactory();
        final SessionSettings settings = FixUtility.readSettings("fix/FixLocal.conf");
        final LogFactory logFactory = new Slf4jLogFactory();
        final MessageFactory messageFactory = new DefaultMessageFactory();
        final Initiator initiator = new SocketInitiator(app, storeFactory, settings,
            logFactory, messageFactory);

        initiator.start();
        AtomicBoolean stopped = new AtomicBoolean();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                initiator.stop();
                stopped.set(true);
            }
        });
        while (!stopped.get()) {
            Thread.sleep(1000);
        }
    }
}
