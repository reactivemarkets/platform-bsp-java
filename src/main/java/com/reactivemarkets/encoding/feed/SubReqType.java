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

package com.reactivemarkets.encoding.feed;

public final class SubReqType {
  private SubReqType() { }
  public static final short Subscribe = 1;
  public static final short Unsubscribe = 2;

  public static final String[] names = { "Subscribe", "Unsubscribe", };

  public static String name(int e) { return names[e - Subscribe]; }
}
