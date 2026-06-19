package com.example.examplemod;

import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.lang.reflect.Field;
import java.util.function.Predicate;

@Mod("tpmod")
public class TpMod {

    public TpMod(IEventBus modBus) {
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandNode<CommandSourceStack> tpNode = event.getDispatcher().getRoot().getChild("tp");
        if (tpNode == null) return;

        try {
            // 反射获取 Brigadier 内部的权限字段
            Field field = CommandNode.class.getDeclaredField("requirement");
            field.setAccessible(true);
            // 强制把权限要求改成谁都能用
            field.set(tpNode, (Predicate<CommandSourceStack>) source -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
