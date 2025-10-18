package com.example.spacemod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class TestCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("spacetest")
            .executes(TestCommand::executeTest)
        );
    }
    
    private static int executeTest(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> 
            Component.literal("✅ 太空模组测试命令正常！球形世界系统已加载。"), 
            false
        );
        return 1;
    }
}
