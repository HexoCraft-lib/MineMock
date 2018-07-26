package com.github.hexocraft.lib.plugin;

/*

 Copyright 2018 hexosse

 Licensed under the Apache License, Version 2.0 (the "License")
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

import com.github.hexocraft.lib.assumptions.AssumeImplementedException;
import org.apache.commons.lang.Validate;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.java.JavaPluginUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;


public class MPluginManager implements PluginManager {

    private final Server server;
    private final List<Plugin> plugins = new ArrayList();
    private final Map<String, Plugin> lookupNames = new HashMap();
    private final List<Class<?>> defaultPluginConstructorParams = Arrays.asList(JavaPluginLoader.class, PluginDescriptionFile.class, File.class, File.class);
    private boolean useTimings = false;


    public MPluginManager(Server instance) {
        this.server = instance;
    }


    // Load plugin
    // -------------------------------------------------------------------------


    /**
     * Load plugin from class
     *
     * @param clazz The plugin to load.
     * @param description The {@link PluginDescriptionFile} which contains information about the plugin
     * @param dataFolder The folder which contains plugin data's files
     * @param file The file which contains this plugin
     *
     * @return The loaded plugin.
     *
     * @throws InvalidPluginException If pluigin constructor cannot be instantiated
     */
    @SuppressWarnings("deprecation")
    public JavaPlugin loadPlugin(Class<? extends JavaPlugin> clazz, PluginDescriptionFile description, File dataFolder, File file) throws InvalidPluginException {
        try {

            // Get plugin constructor
            Constructor<? extends JavaPlugin> constructor = clazz.getDeclaredConstructor(defaultPluginConstructorParams.toArray(new Class<?>[0]));
            constructor.setAccessible(true);

            // Arguments which will passed to the new plugin instance
            Object[] arguments = {new JavaPluginLoader(this.server), description, dataFolder, file};

            // Intance the new plugin
            JavaPlugin plugin = constructor.newInstance(arguments);

            //todo: addCommandsFrom(plugin)
            plugins.add(plugin);
            lookupNames.put(plugin.getDescription().getName(), plugin);
            plugin.onLoad();
            return plugin;

        }
        catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new InvalidPluginException("Failed to instantiate plugin", e);
        }
    }


    // Overridden functions from {@link PluginManager}
    // -------------------------------------------------------------------------

    @Override
    public synchronized Plugin getPlugin(String name) {
        return this.lookupNames.get(name.replace(' ', '_'));
    }

    @Override
    public synchronized Plugin[] getPlugins() {
        return this.plugins.toArray(new Plugin[0]);
    }

    @Override
    public boolean isPluginEnabled(String name) {
        Plugin plugin = this.getPlugin(name);
        return this.isPluginEnabled(plugin);
    }

    @Override
    public boolean isPluginEnabled(Plugin plugin) {

        return (plugin != null && this.plugins.contains(plugin)) && plugin.isEnabled();
    }

    @Override
    public void enablePlugin(Plugin plugin) {

        if (!(plugin instanceof JavaPlugin)) {
            this.server.getLogger().log(Level.SEVERE, "Error occurred while enabling plugin");
        }

        if (plugin.isEnabled())
            return;

        JavaPluginUtils.setEnabled((JavaPlugin) plugin, true);

        callEvent(new PluginEnableEvent(plugin));
    }

    @Override
    public void disablePlugins() {
        this.plugins.forEach(this::disablePlugin);
    }

    @Override
    public void disablePlugin(Plugin plugin) {

        Validate.notNull(plugin, "Plugin cannot be null");

        if (!(plugin instanceof JavaPlugin))
            throw new IllegalArgumentException("Not a JavaPlugin");

        if (!plugin.isEnabled())
            return;

        callEvent(new PluginDisableEvent(plugin));

        JavaPluginUtils.setEnabled((JavaPlugin) plugin, false);
    }

    @Override
    public void clearPlugins() {
        synchronized (this) {
            this.disablePlugins();
            this.plugins.clear();
            this.lookupNames.clear();
            HandlerList.unregisterAll();
            //todo: this.fileAssociations.clear()
            //todo: this.permissions.clear()
            //todo: ((Set)this.defaultPerms.get(true)).clear()
            //todo: ((Set)this.defaultPerms.get(false)).clear()
        }
    }


    @Override
    public void callEvent(Event event) {

        Validate.notNull(event, "Event can not be null");

        synchronized (this) {
            fireEvent(event);
        }
    }

    private void fireEvent(Event event) {
        HandlerList handlers = event.getHandlers();
        RegisteredListener[] listeners = handlers.getRegisteredListeners();

        for (RegisteredListener registration : listeners) {
            if (!registration.getPlugin().isEnabled()) {
                continue;
            }

            try {
                registration.callEvent(event);
            }
            catch (AuthorNagException ex) {
                Plugin plugin = registration.getPlugin();

                if (plugin.isNaggable()) {
                    plugin.setNaggable(false);

                    this.server.getLogger().log(Level.SEVERE, String.format(
                        "Nag author(s): '%s' of '%s' about the following: %s",
                        plugin.getDescription().getAuthors(),
                        plugin.getDescription().getFullName(),
                        ex.getMessage()
                    ));
                }
            }
            catch (Exception ex) {
                this.server.getLogger().log(Level.SEVERE, "Could not pass event " + event.getEventName() + " to " + registration.getPlugin().getDescription().getFullName(), ex);
            }
        }
    }

    @Override
    public void registerEvents(Listener listener, Plugin plugin) {

        Validate.notNull(plugin, "Plugin can not be null");
        Validate.notNull(listener, "Listener can not be null");

        if (!(plugin instanceof JavaPlugin))
            throw new IllegalArgumentException("Not a JavaPlugin");

        if (!plugin.isEnabled())
            throw new IllegalPluginAccessException("Plugin attempted to register " + listener + " while not enabled");

        for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : plugin.getPluginLoader().createRegisteredListeners(listener, plugin).entrySet()) {
            getEventListeners(getRegistrationClass(entry.getKey())).registerAll(entry.getValue());
        }
    }

    @Override
    public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin) {
        registerEvent(event, listener, priority, executor, plugin, false);
    }

    @Override
    public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin, boolean ignoreCancelled) {

        Validate.notNull(listener, "Listener cannot be null");
        Validate.notNull(priority, "Priority cannot be null");
        Validate.notNull(executor, "Executor cannot be null");
        Validate.notNull(plugin, "Plugin cannot be null");

        if (!plugin.isEnabled()) {
            throw new IllegalPluginAccessException("Plugin attempted to register " + event + " while not enabled");
        }

        if (useTimings) {
            getEventListeners(event).register(new TimedRegisteredListener(listener, executor, priority, plugin, ignoreCancelled));
        } else {
            getEventListeners(event).register(new RegisteredListener(listener, executor, priority, plugin, ignoreCancelled));
        }
    }

    private HandlerList getEventListeners(Class<? extends Event> type) {
        try {
            Method method = getRegistrationClass(type).getDeclaredMethod("getHandlerList");
            method.setAccessible(true);
            return (HandlerList) method.invoke(null);
        }
        catch (Exception e) {
            throw new IllegalPluginAccessException(e.toString());
        }
    }

    private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
        try {
            clazz.getDeclaredMethod("getHandlerList");
            return clazz;
        }
        catch (NoSuchMethodException e) {
            if (clazz.getSuperclass() != null
                && !clazz.getSuperclass().equals(Event.class)
                && Event.class.isAssignableFrom(clazz.getSuperclass())) {
                return getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
            } else {
                throw new IllegalPluginAccessException("Unable to find handler list for event " + clazz.getName() + ". Static getHandlerList method required!");
            }
        }
    }

    @Override
    public boolean useTimings() {
        return this.useTimings;
    }


    // Not implemented functions
    // -------------------------------------------------------------------------

    @Override
    public void registerInterface(Class<? extends PluginLoader> aClass) {
        throw new AssumeImplementedException();
    }

    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException {
        throw new AssumeImplementedException();
    }

    @Override
    public Plugin[] loadPlugins(File file) {
        throw new AssumeImplementedException();
    }

    @Override
    public Permission getPermission(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public void addPermission(Permission permission) {
        throw new AssumeImplementedException();
    }

    @Override
    public void removePermission(Permission permission) {
        throw new AssumeImplementedException();
    }

    @Override
    public void removePermission(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public Set<Permission> getDefaultPermissions(boolean b) {
        throw new AssumeImplementedException();
    }

    @Override
    public void recalculatePermissionDefaults(Permission permission) {
        throw new AssumeImplementedException();
    }

    @Override
    public void subscribeToPermission(String s, Permissible permissible) {
        throw new AssumeImplementedException();
    }

    @Override
    public void unsubscribeFromPermission(String s, Permissible permissible) {
        throw new AssumeImplementedException();
    }

    @Override
    public Set<Permissible> getPermissionSubscriptions(String s) {
        throw new AssumeImplementedException();
    }

    @Override
    public void subscribeToDefaultPerms(boolean b, Permissible permissible) {
        throw new AssumeImplementedException();
    }

    @Override
    public void unsubscribeFromDefaultPerms(boolean b, Permissible permissible) {
        throw new AssumeImplementedException();
    }

    @Override
    public Set<Permissible> getDefaultPermSubscriptions(boolean b) {
        throw new AssumeImplementedException();
    }

    @Override
    public Set<Permission> getPermissions() {
        throw new AssumeImplementedException();
    }
}
