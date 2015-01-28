/*
 * Copyright (C) 2009-2015 Peter Jakubčo
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
package net.sf.emustudio.ram.abstracttape.impl;

import emulib.annotations.PLUGIN_TYPE;
import emulib.annotations.PluginType;
import emulib.emustudio.SettingsManager;
import emulib.plugins.PluginInitializationException;
import emulib.plugins.device.AbstractDevice;
import emulib.runtime.AlreadyRegisteredException;
import emulib.runtime.ContextPool;
import emulib.runtime.InvalidContextException;
import net.sf.emustudio.ram.abstracttape.AbstractTapeContext;
import net.sf.emustudio.ram.abstracttape.gui.SettingsDialog;
import net.sf.emustudio.ram.abstracttape.gui.TapeDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

@PluginType(
        type = PLUGIN_TYPE.CPU,
        title = "Abstract tape",
        copyright = "\u00A9 Copyright 2008-2015, Peter Jakubčo",
        description = "Abstract tape device is used by abstract machines such as RAM or Turing machine"
)
public class AbstractTape extends AbstractDevice {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTape.class);
    private String guiTitle;
    private AbstractTapeContextImpl context;
    private TapeDialog gui;
    private final ContextPool contextPool;

    boolean nogui;
    boolean auto;

    public AbstractTape(Long pluginID, ContextPool contextPool) {
        super(pluginID);
        this.contextPool = Objects.requireNonNull(contextPool);
        context = new AbstractTapeContextImpl(this);
        try {
            contextPool.register(pluginID, context, AbstractTapeContext.class);
        } catch (AlreadyRegisteredException | InvalidContextException e) {
            LOGGER.error("Could not register Abstract tape context", e);
        }
    }

    @Override
    public String getVersion() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("net.sf.emustudio.ram.abstracttape.version");
            return bundle.getString("version");
        } catch (MissingResourceException e) {
            return "(unknown)";
        }
    }

    @Override
    public void initialize(SettingsManager settings) throws PluginInitializationException {
        super.initialize(settings);
        this.settings = settings;

        nogui = Boolean.parseBoolean(settings.readSetting(pluginID, SettingsManager.NO_GUI));
        auto = Boolean.parseBoolean(settings.readSetting(pluginID, SettingsManager.AUTO));

        // show GUI at startup?
        String s = settings.readSetting(pluginID, "showAtStartup");
        if (!nogui && s != null && s.toLowerCase().equals("true")) {
            showGUI();
        }
        context.setVerbose(auto);
    }

    @Override
    public void showGUI() {
        if (!nogui) {
            if (gui == null) {
                gui = new TapeDialog(this, context, settings, pluginID);
            }
            gui.setVisible(true);
        }
    }

    @Override
    public String getTitle() {
        return (guiTitle == null) ? super.getTitle() : guiTitle;
    }


    public void setGUITitle(String title) {
        this.guiTitle = title;
        if (gui != null) {
            gui.setTitle(title);
            context.setVerbose(auto);
        }
    }

    @Override
    public void destroy() {
        if (gui != null) {
            gui.dispose();
        }
        gui = null;
        context = null;
        settings = null;
    }

    @Override
    public void reset() {
        context.reset();
    }

    @Override
    public void showSettings() {
        if (!nogui) {
            new SettingsDialog(settings, pluginID, gui).setVisible(true);
        }
    }

    @Override
    public boolean isShowSettingsSupported() {
        return !nogui;
    }
}
