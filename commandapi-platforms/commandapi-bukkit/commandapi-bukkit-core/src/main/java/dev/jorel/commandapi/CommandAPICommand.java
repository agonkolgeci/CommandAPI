package dev.jorel.commandapi;

import dev.jorel.commandapi.arguments.Argument;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandAPICommand extends AbstractCommandAPICommand<CommandAPICommand, Argument<?>, CommandSender> implements BukkitExecutable<CommandAPICommand> {
	/**
	 * Creates a new command builder
	 *
	 * @param commandName The name of the command to create
	 */
	public CommandAPICommand(String commandName) {
		super(commandName);
	}

	@Override
	public CommandAPICommand instance() {
		return this;
	}

	/**
	 * Registers the command with a given namespace.
	 *
	 * @param namespace The namespace of this command. This cannot be null or empty.
	 *
	 */
	public void register(String namespace) {
		if (CommandAPIBukkit.get().isInvalidNamespace(this.name, namespace)) {
			super.register();
			return;
		}
		super.register(namespace);
	}

	/**
	 * Registers this command with a given {@link JavaPlugin} instance
	 *
	 * @param plugin The plugin instance used to determine this command's namespace
	 */
	public void register(JavaPlugin plugin) {
		super.register(plugin.getName().toLowerCase());
	}
}
