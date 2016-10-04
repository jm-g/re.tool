/*
 *     re.tool - a regular expression generation tool
 *     Copyright (C) 2016  Jean-Marie Gaillourdet
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.gaillourdet

/**
  * Created by jmg on 21.08.16.
  */
package object re_tool {

  def digit(x: Int) = {
    require(x >= 0, ">= 0")
    require(x <= 9, "<= 9x")
    Digit(x)
  }

  def toDigit(c: Char): Digit = {
    digit(Integer.parseInt(c.toString))
  }

  def fromDigit(d: Digit): Char = {
    d.value.toString.charAt(0)
  }


}
