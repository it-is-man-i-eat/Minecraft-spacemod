package com.example.spacemod.math;

import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 坐标系映射系统 - 将平面坐标映射到球面坐标
 * 保持原版XYZ坐标兼容性，同时实现球形世界效果
 */
public class CoordinateMapper {
    private static final Logger LOGGER = LogManager.getLogger();
    
    // 球体参数
    public static final double EARTH_RADIUS = 1000.0; // 球体半径
    public static final double TRANSITION_START = 800.0; // 过渡区开始
    public static final double TRANSITION_END = 1200.0; // 过渡区结束
    
    /**
     * 将平面坐标映射到球面坐标
     * @param originalPos 原版平面坐标
     * @return 映射后的球面坐标
     */
    public static Vec3 mapToSpherical(Vec3 originalPos) {
        double x = originalPos.x;
        double z = originalPos.z;
        double y = originalPos.y;
        
        // 计算到原点的距离
        double distance = Math.sqrt(x * x + z * z);
        
        // 如果在过渡区内，应用球形映射
        if (distance > TRANSITION_START) {
            double transitionFactor = calculateTransitionFactor(distance);
            
            // 计算经纬度
            double longitude = Math.atan2(z, x); // 经度 (-π 到 π)
            double latitude = Math.atan2(y, distance); // 纬度 (-π/2 到 π/2)
            
            // 球面坐标计算
            double newX = EARTH_RADIUS * Math.cos(latitude) * Math.cos(longitude);
            double newZ = EARTH_RADIUS * Math.cos(latitude) * Math.sin(longitude);
            double newY = EARTH_RADIUS * Math.sin(latitude);
            
            // 应用过渡效果
            newX = lerp(x, newX, transitionFactor);
            newZ = lerp(z, newZ, transitionFactor);
            newY = lerp(y, newY, transitionFactor);
            
            return new Vec3(newX, newY, newZ);
        }
        
        // 在中心区域保持原坐标
        return originalPos;
    }
    
    /**
     * 计算过渡因子 (0到1之间)
     * @param distance 到原点的距离
     * @return 过渡因子
     */
    private static double calculateTransitionFactor(double distance) {
        if (distance <= TRANSITION_START) return 0.0;
        if (distance >= TRANSITION_END) return 1.0;
        return (distance - TRANSITION_START) / (TRANSITION_END - TRANSITION_START);
    }
    
    /**
     * 线性插值
     * @param a 起始值
     * @param b 结束值
     * @param factor 插值因子 (0-1)
     * @return 插值结果
     */
    private static double lerp(double a, double b, double factor) {
        return a + (b - a) * factor;
    }
    
    /**
     * 获取当前位置的曲率信息（用于调试）
     * @param position 当前位置
     * @return 曲率信息字符串
     */
    public static String getCurvatureInfo(Vec3 position) {
        double distance = Math.sqrt(position.x * position.x + position.z * position.z);
        double transitionFactor = calculateTransitionFactor(distance);
        
        String status;
        if (distance <= TRANSITION_START) {
            status = "平面区域";
        } else if (distance >= TRANSITION_END) {
            status = "球面区域";
        } else {
            status = "过渡区域";
        }
        
        return String.format("位置: (%.1f, %.1f, %.1f)\n距离原点: %.1f\n区域: %s\n过渡因子: %.2f", 
            position.x, position.y, position.z, distance, status, transitionFactor);
    }
    
    /**
     * 检查坐标是否在球形世界范围内
     * @param position 坐标
     * @return 是否在范围内
     */
    public static boolean isInSphericalWorld(Vec3 position) {
        double distance = Math.sqrt(position.x * position.x + position.z * position.z);
        return distance <= TRANSITION_END + 100; // 额外100格缓冲
    }
    
    /**
     * 获取球体半径
     * @return 球体半径
     */
    public static double getEarthRadius() {
        return EARTH_RADIUS;
    }
}
