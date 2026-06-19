package com.example.examplemod;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.ArrayList;
import java.util.List;

@Mod("tpmod")
public class TpMod {

    public TpMod(IEventBus modBus) {
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        // 获取原版 /tp 节点
        CommandNode<CommandSourceStack> original = dispatcher.getRoot().getChild("tp");
        if (original == null) return;

        // 移除原版节点
        dispatcher.getRoot().getChildren().remove(original);

        // 创建一个带新权限的根节点副本（requires -> true）
        CommandNode<CommandSourceStack> newTp = copyNode(original, source -> true);

        // 注册回去
        dispatcher.getRoot().addChild(newTp);
    }

    /**
     * 递归复制命令节点树，允许替换权限条件
     */
    private static CommandNode<CommandSourceStack> copyNode(
            CommandNode<CommandSourceStack> node,
            com.mojang.brigadier.Command<CommandSourceStack> newRequirement
    ) {
        CommandNode<CommandSourceStack> copy = new CommandNode<>(
                node.getCommand(),
                newRequirement,
                node.getRequirement(),
                node.getRedirect(),
                node.getRedirectModifier(),
                node.isFork()
        );
        // 递归复制所有子节点
        for (CommandNode<CommandSourceStack> child : node.getChildren()) {
            copy.addChild(copyNode(child, newRequirement));
        }
        return copy;
    }
}
