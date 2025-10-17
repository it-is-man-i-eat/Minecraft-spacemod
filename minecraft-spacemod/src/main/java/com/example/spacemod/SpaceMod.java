package com.example.spacemod;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SpaceMod.MOD_ID)
public class SpaceMod {
    public static final String MOD_ID = "spacemod";
    private static final Logger LOGGER = LogManager.getLogger();

    public SpaceMod() {
        initializeCoreSystems();
    }

    private void initializeCoreSystems() {
        LOGGER.info("Initializing SpaceMod...");
    }
}