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

package com.reactivemarkets.platform.quickfix;

import quickfix.Field;

public class LongField extends Field<Long> {

    private static final long serialVersionUID = 1L;

    public LongField(final int field) {
        super(field, 0L);
    }

    public LongField(final int field, final Long data) {
        super(field, data);
    }

    public LongField(final int field, final long data) {
        super(field, data);
    }

    public final long getValue() {
        return getObject();
    }

    public final void setValue(final Long value) {
        setObject(value);
    }

    public final void setValue(final long value) {
        setObject(value);
    }

    public final boolean valueEquals(final Long value) {
        return getObject().equals(value);
    }

    public final boolean valueEquals(final int value) {
        return getObject().equals(value);
    }
}
