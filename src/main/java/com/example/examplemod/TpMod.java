package com.example.examplemod;

import net.minecraft.commands.CommandSourceStack;
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
        // 直接拿到原版 /tp 命令节点
        var tpNode = event.getDispatcher().getRoot().getChild("tp");
        if (tpNode != null) {
            // 把权限要求改成谁都能用
            tpNode.setRequirement(source -> true);
        }
    }
}
