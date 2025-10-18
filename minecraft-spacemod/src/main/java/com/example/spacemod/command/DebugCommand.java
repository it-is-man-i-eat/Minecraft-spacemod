package com.example.spacemod.command;

import com.example.spacemod.math.CoordinateMapper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

/**
 * 调试命令 - 用于查看坐标映射信息和测试球形世界系统
 */
public class DebugCommand {
    
    /**
     * 注册调试命令
     * @param dispatcher 命令分发器
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("spacedebug")
            .executes(DebugCommand::showCoordinateInfo)
            .then(Commands.literal("curvature")
                .executes(DebugCommand::showCurvatureInfo)
            )
            .then(Commands.literal("test")
                .executes(DebugCommand::runTest)
            )
        );
    }
    
    /**
     * 显示基础坐标信息
     */
    private static int showCoordinateInfo(CommandContext<CommandSourceStack> context) {
        Vec3 position = context.getSource().getPosition();
        String info = CoordinateMapper.getCurvatureInfo(position);
        
        Vec3 mappedPosition = CoordinateMapper.mapToSpherical(position);
        String mappingInfo = String.format("坐标映射:\n原坐标: (%.1f, %.1f, %.1f)\n球面坐标: (%.1f, %.1f, %.1f)",
            position.x, position.y, position.z,
            mappedPosition.x, mappedPosition.y, mappedPosition.z);
        
        context.getSource().sendSuccess(() -> 
            Component.literal("=== 太空模组调试信息 ===\n" + info + "\n\n" + mappingInfo), 
            false
        );
        
        return 1;
    }
    
    /**
     * 显示曲率信息
     */
    private static int showCurvatureInfo(CommandContext<CommandSourceStack> context) {
        Vec3 position = context.getSource().getPosition();
        
        StringBuilder curvatureInfo = new StringBuilder();
        curvatureInfo.append("=== 球形世界曲率信息 ===\n");
        curvatureInfo.append("球体半径: ").append(CoordinateMapper.getEarthRadius()).append("\n");
        curvatureInfo.append("过渡区域: ").append(CoordinateMapper.TRANSITION_START)
                     .append(" 到 ").append(CoordinateMapper.TRANSITION_END).append("\n");
        
        // 测试几个关键点的映射
        double[] testDistances = {0, 500, 800, 1000, 1200, 1500};
        curvatureInfo.append("\n关键点测试:\n");
        
        for (double dist : testDistances) {
            Vec3 testPos = new Vec3(dist, 64, 0);
            Vec3 mappedPos = CoordinateMapper.mapToSpherical(testPos);
            curvatureInfo.append(String.format("距离 %.0f: (%.0f,64,0) → (%.1f,%.1f,%.1f)\n", 
                dist, dist, mappedPos.x, mappedPos.y, mappedPos.z));
        }
        
        context.getSource().sendSuccess(() -> 
            Component.literal(curvatureInfo.toString()), 
            false
        );
        
        return 1;
    }
    
    /**
     * 运行测试
     */
    private static int runTest(CommandContext<CommandSourceStack> context) {
        Vec3 position = context.getSource().getPosition();
        boolean inSphericalWorld = CoordinateMapper.isInSphericalWorld(position);
        
        String testResult = String.format("=== 球形世界测试 ===\n" +
            "当前位置: (%.1f, %.1f, %.1f)\n" +
            "球形世界范围内: %s\n" +
            "球体半径: %.1f\n" +
            "状态: %s",
            position.x, position.y, position.z,
            inSphericalWorld ? "是" : "否",
            CoordinateMapper.getEarthRadius(),
            inSphericalWorld ? "✅ 正常" : "⚠️ 边界外");
        
        context.getSource().sendSuccess(() -> 
            Component.literal(testResult), 
            false
        );
        
        return 1;
    }
}
