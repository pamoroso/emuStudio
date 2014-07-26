/*
 * TokenZ80.java
 *
 * Created on Sobota, 2008, august 9, 15:20
 *
 * Copyright (C) 2008-2012 Peter Jakubčo
 * KISS, YAGNI, DRY
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package net.sf.emustudio.zilogZ80.assembler.impl;

import emulib.plugins.compiler.Token;
import java_cup.runtime.Symbol;

public class TokenZ80 extends Symbol implements Token, SymZ80 {
    public final static int ERROR_DECIMAL_SIZE = 0xA01;
    public final static int ERROR_UNCLOSED_CHAR = 0xA02;
    public final static int ERROR_UNCLOSED_STRING = 0xA03;
    public final static int ERROR_HEXA_FORMAT = 0xA04;
    public final static int ERROR_UNKNOWN_TOKEN = 0xA05;
    
    private String text;
    private int lineNumber;
    private int columnNumber;
    private int offset;
    private int length;
    private int type;
    private boolean initial;

    public TokenZ80(int ID, int type, String text, Object value, int lineNumber,
            int columnNumber, int charBegin, int charEnd, boolean initial) {
        super(ID, value);
        this.text = text;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.offset = charBegin;
        this.length = charEnd - charBegin;
        this.initial = initial;
        this.type = type;
    }

    @Override
    public int getID() {
        return super.sym;
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getErrorString() {
        switch (super.sym) {
            case ERROR_DECIMAL_SIZE:
                return "Lieral has too big size (max. is 65535)";
            case ERROR_UNCLOSED_CHAR:
                return "Char isn't closed with single quote (')";
            case ERROR_UNCLOSED_STRING:
                return "String isn't closed with single quote (')";
            case ERROR_UNKNOWN_TOKEN:
                return "Unknown token";
        }
        return "";
    }

    @Override
    public int getLine() {
        return lineNumber;
    }

    @Override
    public int getColumn() {
        return columnNumber;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public boolean isInitialLexicalState() {
        return initial;
    }
}
