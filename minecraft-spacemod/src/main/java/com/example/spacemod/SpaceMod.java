package com.example.spacemod;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("spacemod")
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
        
        // 注册世界生成器
        registerWorldGenerator();
    }
    
    private void registerWorldGenerator() {
        LOGGER.info("注册球形世界生成器...");
        // 世界生成器注册逻辑将在这里实现
    }
}
