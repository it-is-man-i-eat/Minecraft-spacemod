#!/bin/bash
echo "=== 项目结构验证 ==="

echo "1. 检查文件结构..."
find src -name "*.java" -type f | wc -l | xargs echo "Java文件数量:"
find src -name "*.toml" -type f | wc -l | xargs echo "配置文件数量:"

echo ""
echo "2. 检查核心类..."
declare -a required_files=(
    "src/main/java/com/example/spacemod/SpaceMod.java"
    "src/main/java/com/example/spacemod/world/SphereWorldGenerator.java"
    "src/main/java/com/example/spacemod/math/CoordinateMapper.java"
    "src/main/java/com/example/spacemod/command/TestCommand.java"
    "src/main/java/com/example/spacemod/command/DebugCommand.java"
    "src/main/resources/META-INF/mods.toml"
)

for file in "${required_files[@]}"; do
    if [ -f "$file" ]; then
        lines=$(wc -l < "$file")
        echo "✅ $file ($lines 行)"
    else
        echo "❌ $file - 缺失"
    fi
done

echo ""
echo "3. 检查依赖配置..."
if [ -f "src/main/resources/META-INF/mods.toml" ]; then
    echo "✅ mods.toml 配置存在"
    grep -c "modId=" src/main/resources/META-INF/mods.toml | xargs echo "模组配置项:"
else
    echo "❌ mods.toml 缺失"
fi

echo ""
echo "=== 验证完成 ==="
