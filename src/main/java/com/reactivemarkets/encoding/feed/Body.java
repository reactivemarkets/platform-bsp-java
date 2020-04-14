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

public final class Body {
  private Body() { }
  public static final byte NONE = 0;
  public static final byte FeedRequest = 1;
  public static final byte FeedRequestReject = 2;
  public static final byte MDSnapshotL2 = 3;
  public static final byte PublicTrade = 4;
  public static final byte SessionStatus = 5;

  public static final String[] names = { "NONE", "FeedRequest", "FeedRequestReject", "MDSnapshotL2", "PublicTrade", "SessionStatus", };

  public static String name(int e) { return names[e]; }
}
