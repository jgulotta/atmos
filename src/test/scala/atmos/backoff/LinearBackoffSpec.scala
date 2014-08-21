/* LinearBackoffSpec.scala
 * 
 * Copyright (c) 2013-2014 bizo.com
 * Copyright (c) 2013-2014 zman.io
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
package atmos.backoff

import scala.concurrent.duration._
import org.scalatest._

/**
 * Test suite for [[atmos.backoff.LinearBackoff]].
 */
class LinearBackoffSpec extends FlatSpec with Matchers {

  val thrown = new RuntimeException

  "LinearBackoff" should "scale its backoff by multiplying by the number of failed attempts" in {
    for {
      backoff <- 1L to 100L map (100.millis * _)
      policy = LinearBackoff(backoff)
      attempt <- 1 to 10
    } policy.nextBackoff(attempt, thrown) shouldEqual scale(backoff.toNanos, attempt).nanos
  }
  
  /** Scales a number by multiplying by the number of failed attempts. */
  def scale(n: Long, attempts: Int): Long = n * attempts

}