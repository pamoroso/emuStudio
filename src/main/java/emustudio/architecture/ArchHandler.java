/*
 * ArchHandler.java
 * 
 * Created on Friday, 28.1.2008 22:31
 * KISS, YAGNI, DRY
 *
 * Copyright (C) 2008-2012, Peter Jakubčo
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
package emustudio.architecture;

import emulib.plugins.Plugin;
import emulib.plugins.SettingsManipulator;
import emulib.plugins.compiler.Compiler;
import emulib.plugins.cpu.CPU;
import emulib.plugins.device.Device;
import emulib.plugins.memory.Memory;
import emustudio.architecture.ArchLoader.PluginInfo;
import emustudio.architecture.drawing.Schema;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class holds actual computer configuration - plugins and settings.
 *
 * @author vbmacher
 */
public class ArchHandler implements SettingsManipulator {
    private final static Logger logger = LoggerFactory.getLogger(ArchHandler.class);

    private Computer computer;
    private Properties settings;
    private Schema schema;
    private Map<Long, String> pluginNames;

    private static final String EMPTY_STRING = "";

    /**
     * Creates new virtual computer architecture and initializes all plug-ins.
     * 
     * @param arch         Virtual computer, handling the structure of plug-ins
     * @param settings     Architecture settings (Properties)
     * @param schema       Abstract schema of the architecture
     * @param pluginNames  Names of all plug-ins
     * @param auto         If the emuStudio is runned in automatization mode
     * @param nogui        If the "--nogui" parameter was given to emuStudio
     *  
     * @throws PluginInitializationException if initialization of the architecture failed.
     */
    public ArchHandler(Computer arch, Properties settings,
            Schema schema, Collection<PluginInfo> plugins, boolean auto,
            boolean nogui) throws PluginInitializationException {
        this.computer = arch;
        this.settings = settings;
        this.schema = schema;
        this.pluginNames = new HashMap<Long, String>();
        
        for (PluginInfo plugin : plugins) {
            pluginNames.put(plugin.pluginId, plugin.pluginSettingsName);
        }

        if (initialize(auto, nogui) == false) {
            throw new PluginInitializationException("Initialization of plugins failed");
        }
    }

    /**
     * Initialize all plugins. The method is called by
     * constructor. Also provides necessary connections.
     * 
     * @return true If the initialization succeeded, false otherwise
     */
    private boolean initialize(boolean auto, boolean nogui) {
        if (auto) {
           // Set "auto" setting to "true" to all plugins
           writeSettingToAll("auto", "true");
        }
        if (nogui) {
           writeSettingToAll("nogui", "true");
        }

        return computer.initialize(this);
    }

    /**
     * Method destroys current architecture
     */
    public void destroy() {
        computer.destroy();
        pluginNames.clear();
    }

    /**
     * Get schema of this virtual architecture
     * 
     * @return Abstract schema
     */
    public Schema getSchema() {
        return schema;
    }

    /**
     * Return the name of the computer (the configuration name).
     *
     * @return name of the virtual computer
     */
    public String getComputerName() {
        return (schema == null) ? "unknown" : schema.getConfigName();
    }

    /**
     * Return Computer object
     *
     * @return virtual computer structure
     */
    public Computer getComputer() {
        return computer;
    }

    /**
     * Method reads value of specified setting from Properties for 
     * specified plugin. Setting has to be fully specified.
     * 
     * @param pluginID  identification number of a plugin
     * @param settingName  name of wanted setting
     * @return setting value if exists, or null if not
     */
    @Override
    public String readSetting(long pluginID, String settingName) {
        Plugin plug = computer.getPlugin(pluginID);
        
        if (plug == null)
            return null;

        String prop = pluginNames.get(pluginID);

        if ((prop == null) || prop.equals(EMPTY_STRING))
            return null;
        
        if ((settingName != null) && (!settingName.equals(EMPTY_STRING)))
            prop += "." + settingName;

        return settings.getProperty(prop, null);
    }

    /**
     * Get device name (file name without extension)
     * 
     * @param index  Index of the device
     * @return device file name without extension, or null
     *         if device is unknown
     */
    public String getDeviceName(int index) {
        return settings.getProperty("device" + index, null);
    }

    /**
     * Get compiler file name, without file extension.
     * 
     * @return compiler name or null
     */
    public String getCompilerName() {
        return settings.getProperty("compiler", null);
    }

    /**
     * Get CPU file name, without file extension.
     * 
     * @return CPU name or null
     */
    public String getCPUName() {
        return settings.getProperty("cpu", null);
    }

    /**
     * Get memory file name, without file extension.
     * 
     * @return memory name or null
     */
    public String getMemoryName() {
        return settings.getProperty("memory", null);
    }

    /**
     * Method writes a value of specified setting to Properties for 
     * specified plugin. Setting has to be fully specified.
     * 
     * @param pluginID  plugin ID, identification of a plugin
     * @param settingName name of wanted setting
     * @param val new value of the setting
     */
    @Override
    public void writeSetting(long pluginID, String settingName, String val) {
        if (settingName == null || settingName.equals(EMPTY_STRING)) {
            return;
        }

        Plugin plug = computer.getPlugin(pluginID);
        if (plug == null) {
            return;
        }

        String prop = EMPTY_STRING;
        if (plug instanceof Device) {
            // search for device
            prop = pluginNames.get(pluginID);
        } else if (plug instanceof CPU) {
            prop = "cpu";
        } else if (plug instanceof Memory) {
            prop = "memory";
        } else if (plug instanceof Compiler) {
            prop = "compiler";
        }

        if (prop.equals(EMPTY_STRING)) {
            return;
        }
        prop += "." + settingName;

        settings.setProperty(prop, val);
        try {
        ArchLoader.writeConfig(schema.getConfigName(), settings);
        } catch (WriteConfigurationException e) {
            logger.error(new StringBuilder().append("[pluginID=").append(pluginID).append("; ").append(settingName)
                    .append("=").append(val).append("]").append(" Could not write setting").toString(),e);
        }
    }

    /**
     * Method removes value of specified setting from Properties for 
     * specified plugin. Setting has to be fully specified.
     * 
     * @param pluginID    plugin ID, identification of a plugin
     * @param settingName name of wanted setting
     */
    @Override
    public void removeSetting(long pluginID, String settingName) {
        if (settingName == null || settingName.equals(EMPTY_STRING)) {
            return;
        }

        Plugin plug = computer.getPlugin(pluginID);
        if (plug == null) {
            return;
        }

        String prop = EMPTY_STRING;

        if (plug instanceof Device) {
            // search for device
            prop = pluginNames.get(pluginID);
        } else if (plug instanceof CPU) {
            prop = "cpu";
        } else if (plug instanceof Memory) {
            prop = "memory";
        } else if (plug instanceof Compiler) {
            prop = "compiler";
        }

        if (prop.equals(EMPTY_STRING)) {
            return;
        }
        prop += "." + settingName;

        settings.remove(prop);
        try {
        ArchLoader.writeConfig(schema.getConfigName(), settings);
        } catch (WriteConfigurationException e) {
            logger.error(new StringBuilder().append("[pluginID=").append(pluginID).append("; ").append(settingName)
                    .append("] Could not remove setting.").toString(), e);
        }
    }

    /**
     * This method is used only by the emuStudio to set some common settings
     * that should be set for all plugins.
     *
     * @param settingName name of the setting
     * @param val value of the setting
     */
    public void writeSettingToAll(String settingName, String val) {
        long id;
        Set<Long> set = pluginNames.keySet();
        for (Iterator e = set.iterator(); e.hasNext();) {
            id = (Long)e.next();
            this.writeSetting(id, settingName, val);
        }
    }

}
