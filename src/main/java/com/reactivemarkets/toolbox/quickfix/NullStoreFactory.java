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

import quickfix.MessageStore;
import quickfix.MessageStoreFactory;
import quickfix.RuntimeError;
import quickfix.SessionID;
import quickfix.SystemTime;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public final class NullStoreFactory implements MessageStoreFactory {
    @Override
    public MessageStore create(final SessionID sessionID) {
        try {
            return new NullStore();
        } catch (final IOException e) {
            throw new RuntimeError(e);
        }
    }

    private static final class NullStore implements MessageStore {
        private int nextSenderMsgSeqNum;
        private int nextTargetMsgSeqNum;
        private Calendar creationTime = SystemTime.getUtcCalendar();

        private NullStore() throws IOException {
            reset();
        }

        @Override
        public void get(final int startSequence, final int endSequence, final Collection<String> messages)
            throws IOException {
            // No messages.
        }

        @Override
        public Date getCreationTime() throws IOException {
            return creationTime.getTime();
        }

        @Override
        public int getNextSenderMsgSeqNum() throws IOException {
            return nextSenderMsgSeqNum;
        }

        @Override
        public void setNextSenderMsgSeqNum(final int next) throws IOException {
            nextSenderMsgSeqNum = next;
        }

        @Override
        public int getNextTargetMsgSeqNum() throws IOException {
            return nextTargetMsgSeqNum;
        }

        @Override
        public void setNextTargetMsgSeqNum(final int next) throws IOException {
            nextTargetMsgSeqNum = next;
        }

        @Override
        public void incrNextSenderMsgSeqNum() throws IOException {
            setNextSenderMsgSeqNum(getNextSenderMsgSeqNum() + 1);
        }

        @Override
        public void incrNextTargetMsgSeqNum() throws IOException {
            setNextTargetMsgSeqNum(getNextTargetMsgSeqNum() + 1);
        }

        @Override
        public void reset() throws IOException {
            setNextSenderMsgSeqNum(1);
            setNextTargetMsgSeqNum(1);
            creationTime = SystemTime.getUtcCalendar();
        }

        @Override
        public boolean set(final int sequence, final String message) throws IOException {
            return true;
        }

        @Override
        public void refresh() throws IOException {
        }
    }
}
