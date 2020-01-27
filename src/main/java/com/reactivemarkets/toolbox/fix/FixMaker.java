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

package com.reactivemarkets.toolbox.fix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.fix44.MarketDataRequest;
import quickfix.fix44.MessageCracker;

public final class FixMaker extends MessageCracker implements Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixMaker.class);

    /**
     * This method is called when quickfix creates a new session. A session
     * comes into and remains in existence for the life of the application.
     * Sessions exist whether or not a counter party is connected to it. As soon
     * as a session is created, you can begin sending messages to it. If no one
     * is logged on, the messages will be sent at the time a connection is
     * established with the counterparty.
     *
     * @param sessionId
     */
    @Override
    public void onCreate(final SessionID sessionId) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(sessionId + ": onCreate");
        }
    }

    /**
     * This callback notifies you when a valid logon has been established with a
     * counter party. This is called when a connection has been established and
     * the FIX logon process has completed with both parties exchanging valid
     * logon messages.
     *
     * @param sessionId QuickFIX session ID
     */
    @Override
    public void onLogon(final SessionID sessionId) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(sessionId + ": onLogon");
        }
    }

    /**
     * This callback notifies you when an FIX session is no longer online. This
     * could happen during a normal logout exchange or because of a forced
     * termination or a loss of network connection.
     *
     * @param sessionId QuickFIX session ID
     */
    @Override
    public void onLogout(final SessionID sessionId) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(sessionId + ": onLogout");
        }
    }

    /**
     * This callback provides you with a peek at the administrative messages
     * that are being sent from your FIX engine to the counter party. This is
     * normally not useful for an application however it is provided for any
     * logging you may wish to do. You may add fields in an adminstrative
     * message before it is sent.
     *
     * @param message   QuickFIX message
     * @param sessionId QuickFIX session ID
     */
    @Override
    public void toAdmin(final Message message, final SessionID sessionId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(sessionId + ": toAdmin: " + message);
        }
    }

    /**
     * This callback notifies you when an administrative message is sent from a
     * counterparty to your FIX engine. This can be usefull for doing extra
     * validation on logon messages such as for checking passwords. Throwing a
     * RejectLogon exception will disconnect the counterparty.
     *
     * @param message   QuickFIX message
     * @param sessionId QuickFIX session ID
     * @throws FieldNotFound
     * @throws IncorrectDataFormat
     * @throws IncorrectTagValue
     * @throws RejectLogon         causes a logon reject
     */
    @Override
    public void fromAdmin(final Message message, final SessionID sessionId) throws FieldNotFound,
        IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(sessionId + ": fromAdmin: " + message);
        }
        try {
            crack(message, sessionId);
        } catch (final UnsupportedMessageType e) {
            LOGGER.error(sessionId + ": fromAdmin: " + e.getMessage(), e);
        }
    }

    /**
     * This is a callback for application messages that you are sending to a
     * counterparty. If you throw a DoNotSend exception in this function, the
     * application will not send the message. This is mostly useful if the
     * application has been asked to resend a message such as an order that is
     * no longer relevant for the current market. Messages that are being resent
     * are marked with the PossDupFlag in the header set to true; If a DoNotSend
     * exception is thrown and the flag is set to true, a sequence reset will be
     * sent in place of the message. If it is set to false, the message will
     * simply not be sent. You may add fields before an application message
     * before it is sent out.
     *
     * @param message   QuickFIX message
     * @param sessionId QuickFIX session ID
     * @throws DoNotSend This exception aborts message transmission
     */
    @Override
    public void toApp(final Message message, final SessionID sessionId) throws DoNotSend {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(sessionId + ": toApp: " + message);
        }
    }

    /**
     * This callback receives messages for the application. This is one of the
     * core entry points for your FIX application. Every application level
     * request will come through here. If, for example, your application is a
     * sell-side OMS, this is where you will get your new order requests. If you
     * were a buy side, you would get your execution reports here. If a
     * FieldNotFound exception is thrown, the counterparty will receive a reject
     * indicating a conditionally required field is missing. The Message class
     * will throw this exception when trying to retrieve a missing field, so you
     * will rarely need the throw this explicitly. You can also throw an
     * UnsupportedMessageType exception. This will result in the counterparty
     * getting a business reject informing them your application cannot process
     * those types of messages. An IncorrectTagValue can also be thrown if a
     * field contains a value that is out of range or you do not support.
     *
     * @param message   QuickFIX message
     * @param sessionId QuickFIX session ID
     * @throws FieldNotFound
     * @throws IncorrectDataFormat
     * @throws IncorrectTagValue
     * @throws UnsupportedMessageType
     */
    @Override
    public void fromApp(final Message message, final SessionID sessionId) throws FieldNotFound,
        IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(sessionId + ": fromApp: " + message);
        }
        crack(message, sessionId);
    }

    @Override
    public void onMessage(final MarketDataRequest mdr, final SessionID sessionId)
        throws FieldNotFound, IncorrectTagValue {
        LOGGER.info(sessionId + ": MarketDataRequest: " + mdr);
    }
}
