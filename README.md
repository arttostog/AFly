# AFly - simple fly plugin
### Commands
- `/fly [player] [on | off]` - enable or disable fly
- `/fly reload` - reload plugin configuration
### Permissions
- `afly.fly` - allows to use /fly
- `afly.flyothers` - allows to use /fly [player] [on | off]
- `afly.reload` - allows to use /fly [reload]
- `afly.*` - allows to use all commands
### Configuration
```yml
 #Plugin prefix in messages
 Prefix: '[§aAFly§r] '

 #Plugin messages
 Fly_enabled_message: §aFly enabled!
 Fly_disabled_message: §cFly disabled!
 Fly_enabled_message_by: §aFly enabled by %sender%!
 Fly_disabled_message_by: §cFly disabled by %sender%!
 Fly_enabled_message_for: §aFly enabled for %target%!
 Fly_disabled_message_for: §cFly disabled for %target%!
 Plugin_reload: Reloading...
 Plugin_reload_done: Done!
 Error: '§cAn error has occurred! Usage: /<command> [player] [on | off]'
```