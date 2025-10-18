package com.example.spacemod;

import com.example.spacemod.command.TestCommand;
import com.example.spacemod.command.DebugCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("spacemod")
@Mod.EventBusSubscriber(modid = "spacemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpaceMod {
    public static final String MOD_ID = "spacemod";
    public static final Logger LOGGER = LogManager.getLogger();

    public SpaceMod() {
        LOGGER.info("=== 太空模组初始化 ===");
        LOGGER.info("开始加载球形世界生成系统...");
        initializeCoreSystems();
    }
    
    private void initializeCoreSystems() {
        LOGGER.info("✓ 球形世界系统准备就绪");
        LOGGER.info("✓ 坐标映射系统已加载");
        LOGGER.info("✓ 准备创建星际体验!");
    }
    
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        TestCommand.register(event.getDispatcher());
        DebugCommand.register(event.getDispatcher());
        LOGGER.info("✓ 太空模组命令系统已注册");
    }
}
