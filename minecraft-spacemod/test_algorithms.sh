#!/bin/bash
echo "=== 算法逻辑验证 ==="

# 创建简单的Java测试程序来验证坐标映射算法
cat > TestCoordinateMapper.java << 'JAVAEOF'
public class TestCoordinateMapper {
    // 复制CoordinateMapper的核心算法进行测试
    public static final double EARTH_RADIUS = 1000.0;
    public static final double TRANSITION_START = 800.0;
    public static final double TRANSITION_END = 1200.0;
    
    public static double calculateTransitionFactor(double distance) {
        if (distance <= TRANSITION_START) return 0.0;
        if (distance >= TRANSITION_END) return 1.0;
        return (distance - TRANSITION_START) / (TRANSITION_END - TRANSITION_START);
    }
    
    public static double lerp(double a, double b, double factor) {
        return a + (b - a) * factor;
    }
    
    public static void testCoordinateMapping() {
        System.out.println("测试坐标映射算法:");
        
        double[] testDistances = {500, 800, 1000, 1200, 1500};
        for (double dist : testDistances) {
            double factor = calculateTransitionFactor(dist);
            System.out.printf("距离: %.1f -> 过渡因子: %.3f\n", dist, factor);
        }
        
        System.out.println("\n测试线性插值:");
        double result = lerp(10, 20, 0.5);
        System.out.printf("lerp(10, 20, 0.5) = %.1f (期望: 15.0)\n", result);
    }
    
    public static void main(String[] args) {
        testCoordinateMapping();
    }
}
JAVAEOF

# 编译并运行测试
javac TestCoordinateMapper.java
java TestCoordinateMapper

# 清理
rm TestCoordinateMapper.java TestCoordinateMapper.class

echo ""
echo "=== 算法测试完成 ==="
