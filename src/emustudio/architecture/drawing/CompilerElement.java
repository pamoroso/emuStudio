/*
 * CompilerElement.java
 *
 * KISS, YAGNI
 *
 *  Copyright (C) 2010-2011 Peter Jakubčo <pjakubco@gmail.com>
 * 
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package emustudio.architecture.drawing;

import java.awt.Color;
import java.awt.Point;
import java.util.Properties;

/**
 * This class represents a compiler element. It is used in the abstract schema
 * editor. It corresponds to a compiler object that will be used in the emulated
 * computer.
 *
 * @author vbmacher
 */
public class CompilerElement extends Element {
    private final static Color BACK_COLOR = new Color(0xeeefff);

    /**
     * Create new Compiler element object.
     *
     * @param settings settings of virtual computer
     */
    public CompilerElement(Properties settings) {
        super(settings, "compiler", BACK_COLOR);
    }

    /**
     * Create new Compiler element object.
     *
     * @param pluginName name of the compiler
     * @param location the point where the compiler is located in the schema
     */
    public CompilerElement(String pluginName, Point location) {
        super(pluginName, location, BACK_COLOR);
    }

    /**
     * Get string representing the type of the plugin.
     *
     * @return "compiler" text
     */
    @Override
    protected String getPluginType() {
        return "Compiler";
    }

}
