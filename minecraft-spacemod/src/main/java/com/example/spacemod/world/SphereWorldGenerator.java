package com.example.spacemod.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import org.jetbrains.annotations.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SphereWorldGenerator extends ChunkGenerator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int SPHERE_RADIUS = 1000; // 球体半径
    private static final int SURFACE_LEVEL = 64;   // 海平面高度

    public SphereWorldGenerator(Biome biome) {
        // 这里需要传入适当的参数，具体根据构造函数调整
        super(null, null, null); // 这些参数需要根据实际情况提供
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types heightmap, LevelHeightAccessor level, RandomState random) {
        // 计算球体表面高度
        double distance = Math.sqrt(x * x + z * z);
        if (distance <= SPHERE_RADIUS) {
            double height = Math.sqrt(SPHERE_RADIUS * SPHERE_RADIUS - distance * distance);
            return SURFACE_LEVEL + (int) height;
        }
        return SURFACE_LEVEL;
    }

    @Override
    public void buildSurface(ChunkPos chunkPos, ChunkAccess chunkAccess) {
        // 这里将实现球体表面生成逻辑
        LOGGER.info("生成球形世界区块: " + chunkPos);
        // 具体的方块生成逻辑将在后续实现
    }
}
EOFcat > src/main/java/com/example/spacemod/world/SphereWorldGenerator.java << 'EOF'
package com.example.spacemod.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import org.jetbrains.annotations.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SphereWorldGenerator extends ChunkGenerator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int SPHERE_RADIUS = 1000; // 球体半径
    private static final int SURFACE_LEVEL = 64;   // 海平面高度

    public SphereWorldGenerator(Biome biome) {
        // 这里需要传入适当的参数，具体根据构造函数调整
        super(null, null, null); // 这些参数需要根据实际情况提供
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.@NotNull Types heightmap, LevelHeightAccessor level, RandomState random) {
        // 计算球体表面高度
        double distance = Math.sqrt(x * x + z * z);
        if (distance <= SPHERE_RADIUS) {
            double height = Math.sqrt(SPHERE_RADIUS * SPHERE_RADIUS - distance * distance);
            return SURFACE_LEVEL + (int) height;
        }
        return SURFACE_LEVEL;
    }

    @Override
    public void buildSurface(ChunkPos chunkPos, ChunkAccess chunkAccess) {
        // 这里将实现球体表面生成逻辑
        LOGGER.info("生成球形世界区块: " + chunkPos);
        // 具体的方块生成逻辑将在后续实现
    }
}
