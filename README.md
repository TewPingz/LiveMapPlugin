
# LiveMapPlugin

I made this plugin as I saw a request for it and wanted to test my skills to see how I may implement this.
This plugin renders the map by itself and doesn't do anything using the default map system allowing me
to dodge having to deal with the IO garbage.

This plugin was made in under a day and might be unstable. Also, unsure how to will deal with massive amounts of
players. But I utilised a workload queue executor to ensure that executions of a chunk do not take over 2.5ms
in a single tick giving spigot enough breathing room to catch up with itself.

The map is also drawn on an async thread allowing a lot of 
the load from accessing the maps to be passed onto another thread.

[Workload thread](https://www.spigotmc.org/threads/guide-on-workload-distribution-or-how-to-handle-heavy-splittable-tasks.409003/)

[Imgur](https://imgur.com/a/qSyPmaY)