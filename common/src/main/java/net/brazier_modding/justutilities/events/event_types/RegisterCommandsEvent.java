package net.brazier_modding.justutilities.events.event_types;

import com.mojang.brigadier.CommandDispatcher;
import net.brazier_modding.justutilities.events.ReuseableEventType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class RegisterCommandsEvent extends ReuseableEventType<RegisterCommandsEvent> {

	private CommandDispatcher<CommandSourceStack> dispatcher;
	private Commands.CommandSelection environment;

	public RegisterCommandsEvent(CommandDispatcher<CommandSourceStack> dispatcher, Commands.CommandSelection environment) {
		this.dispatcher = dispatcher;
		this.environment = environment;
	}

	public CommandDispatcher<CommandSourceStack> getDispatcher() {
		return dispatcher;
	}

	public Commands.CommandSelection getEnvironment() {
		return environment;
	}

	@Override
	protected void resetObject(){
		super.resetObject();
		this.dispatcher = null;
		this.environment = null;
	}
}
