/*
 * Copyright (c) 2010 - 2012 Novus Partners, Inc. <http://novus.com>
 *
 * Module:        salat-util
 * Class:         BestAvailableConstructorSpec.scala
 * Last modified: 2012-04-28 20:34:21 EDT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project:      http://github.com/novus/salat
 * Wiki:         http://github.com/novus/salat/wiki
 * Mailing list: http://groups.google.com/group/scala-salat
 */

package com.novus.salat.util

import org.specs2.mutable.Specification

import com.novus.salat.util._
import com.novus.salat.util.model._

class BestAvailableConstructorSpec extends Specification with Logging {

  "BestAvailableConstructor" should {
    "find a constructor with args when one is present" in {
      val clazz = classOf[OneConstructorWithArgs]
      BestAvailableConstructor(clazz) must_== clazz.getConstructors.head
    }
    "discard an empty constructor in favour of one with args" in {
      val clazz = classOf[OneConstructorWithArgsOneEmpty]
      BestAvailableConstructor(clazz) must_== clazz.getConstructors.filter(_.getParameterTypes.nonEmpty).head
    }
    "throw an exception if more than one constructor with args is present" in {
      val clazz = classOf[TwoConstructorsWithArgs]
      BestAvailableConstructor(clazz) must throwA[TooManyConstructorsWithArgs[TwoConstructorsWithArgs]]
    }
    "allow an empty constructor if no better alternative exists" in {
      val clazz = classOf[OnlyEmptyConstructor]
      BestAvailableConstructor(clazz) must_== clazz.getConstructors.head
    }
  }

}