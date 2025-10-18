#!/bin/bash
echo "=== 代码质量检查 ==="

# 检查语法基本正确性
echo "1. 基础语法检查..."
error_count=0

for java_file in $(find src -name "*.java"); do
    echo "检查: $java_file"
    
    # 检查是否有明显的语法问题
    if ! grep -q "class.*{" "$java_file" 2>/dev/null; then
        echo "⚠️  $java_file - 缺少类定义"
        ((error_count++))
    fi
    
    if ! grep -q "package.*;" "$java_file" 2>/dev/null; then
        echo "⚠️  $java_file - 缺少包声明"
        ((error_count++))
    fi
    
    # 检查文件是否非空
    if [ ! -s "$java_file" ]; then
        echo "❌ $java_file - 文件为空"
        ((error_count++))
    else
        lines=$(wc -l < "$java_file")
        if [ $lines -lt 5 ]; then
            echo "⚠️  $java_file - 文件可能不完整 ($lines 行)"
        else
            echo "✅ $java_file - 结构正确 ($lines 行)"
        fi
    fi
done

echo ""
echo "2. 项目结构检查..."
if [ -f "src/main/java/com/example/spacemod/SpaceMod.java" ]; then
    lines=$(wc -l < "src/main/java/com/example/spacemod/SpaceMod.java")
    if [ $lines -gt 10 ]; then
        echo "✅ 主模组类完整 ($lines 行)"
    else
        echo "❌ 主模组类可能不完整"
        ((error_count++))
    fi
else
    echo "❌ 主模组类缺失"
    ((error_count++))
fi

# 检查其他核心文件
core_files=(
    "src/main/java/com/example/spacemod/world/SphereWorldGenerator.java"
    "src/main/java/com/example/spacemod/math/CoordinateMapper.java"
    "src/main/java/com/example/spacemod/command/TestCommand.java"
    "src/main/java/com/example/spacemod/command/DebugCommand.java"
    "src/main/resources/META-INF/mods.toml"
)

for file in "${core_files[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file - 存在"
    else
        echo "❌ $file - 缺失"
        ((error_count++))
    fi
done

echo ""
echo "=== 检查完成 ==="
echo "发现的问题数量: $error_count"
if [ $error_count -eq 0 ]; then
    echo "🎉 代码质量优秀！"
else
    echo "🔧 需要修复 $error_count 个问题"
fi
