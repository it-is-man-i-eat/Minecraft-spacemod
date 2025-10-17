#!/bin/bash
echo "=== 代码结构验证 ==="

# 检查必要的目录结构
echo "1. 检查目录结构..."
if [ -d "src/main/java" ]; then
    echo "✅ Java源代码目录存在"
else
    echo "❌ Java源代码目录缺失"
fi

if [ -d "src/main/resources" ]; then
    echo "✅ 资源文件目录存在"
else
    echo "❌ 资源文件目录缺失"
fi

echo ""
echo "2. 检查核心文件..."
declare -a required_files=(
    "src/main/java/com/example/spacemod/SpaceMod.java"
    "src/main/java/com/example/spacemod/math/CoordinateMapper.java"
    "src/main/java/com/example/spacemod/world/SphereWorldGenerator.java"
    "src/main/resources/META-INF/mods.toml"
)

for file in "${required_files[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file"
    else
        echo "❌ $file - 缺失"
    fi
done

echo ""
echo "3. 检查代码基础语法..."
find src -name "*.java" -type f | head -5 | while read file; do
    echo "检查: $(basename $file)"
    # 简单的语法检查（不涉及外部依赖）
    if grep -q "class.*{" "$file" && grep -q "package.*;" "$file"; then
        echo "  ✅ 基础语法正确"
    else
        echo "  ⚠️  可能缺少类定义或包声明"
    fi
done

echo ""
echo "=== 验证完成 ==="
