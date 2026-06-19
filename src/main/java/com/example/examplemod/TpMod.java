package com.example.examplemod;

import net.minecraft.server.commands.TpCommand;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod("tpmod")
public class TpMod {

    public TpMod(IEventBus modBus) {
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            TpCommand.register(event.getDispatcher().getRoot())
                .requires(source -> true)
        );
    }
}
