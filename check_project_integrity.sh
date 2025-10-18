#!/bin/bash
echo "🔍 Minecraft太空模组项目完整性检查"
echo "=========================================="

# 检查基本结构
echo ""
echo "📁 1. 项目基础结构:"
if [ -f "build.gradle" ]; then
    echo "   ✅ build.gradle 存在"
else
    echo "   ❌ build.gradle 缺失 - 这是关键构建文件!"
fi

if [ -f "gradlew" ]; then
    echo "   ✅ gradlew 存在"
else
    echo "   ❌ gradlew 缺失 - 无法构建项目!"
fi

if [ -d "src" ]; then
    echo "   ✅ src/ 目录存在"
else
    echo "   ❌ src/ 目录缺失 - 没有源代码!"
fi

# 检查核心Java文件
echo ""
echo "📝 2. 核心Java文件:"
declare -A core_files=(
    ["主模组类"]="src/main/java/com/example/spacemod/SpaceMod.java"
    ["世界生成器"]="src/main/java/com/example/spacemod/world/SphereWorldGenerator.java"
    ["坐标映射系统"]="src/main/java/com/example/spacemod/math/CoordinateMapper.java"
    ["测试命令"]="src/main/java/com/example/spacemod/command/TestCommand.java"
    ["调试命令"]="src/main/java/com/example/spacemod/command/DebugCommand.java"
)

missing_count=0
for desc in "${!core_files[@]}"; do
    file="${core_files[$desc]}"
    if [ -f "$file" ]; then
        lines=$(wc -l < "$file")
        echo "   ✅ $desc: $file ($lines 行)"
    else
        echo "   ❌ $desc: $file - 缺失!"
        ((missing_count++))
    fi
done

# 检查配置文件
echo ""
echo "⚙️ 3. 配置文件:"
if [ -f "src/main/resources/META-INF/mods.toml" ]; then
    echo "   ✅ mods.toml 存在"
    # 检查关键配置
    if grep -q "modId=" src/main/resources/META-INF/mods.toml; then
        echo "   ✅ mods.toml 包含模组ID配置"
    else
        echo "   ⚠️  mods.toml 可能配置不完整"
    fi
else
    echo "   ❌ mods.toml 缺失 - 模组无法加载!"
    ((missing_count++))
fi

# 检查包结构
echo ""
echo "📦 4. 包结构检查:"
if [ -d "src/main/java/com/example/spacemod" ]; then
    echo "   ✅ 主包结构正确"
    find src/main/java/com/example/spacemod -type d | while read dir; do
        if [ "$dir" != "src/main/java/com/example/spacemod" ]; then
            echo "   📁 $(basename "$dir")/ 子包"
        fi
    done
else
    echo "   ❌ 包结构不正确"
    ((missing_count++))
fi

# 总结报告
echo ""
echo "=========================================="
echo "📊 检查总结:"
if [ $missing_count -eq 0 ]; then
    echo "🎉 所有核心文件都存在！项目结构完整！"
    echo "🚀 可以尝试构建: ./gradlew build"
else
    echo "⚠️  发现 $missing_count 个缺失的文件"
    echo "�� 需要修复缺失的文件后才能构建"
fi

# 检查文件总览
echo ""
echo "📈 项目统计:"
echo "   Java文件: $(find src -name "*.java" -type f | wc -l) 个"
echo "   配置文件: $(find src -name "*.toml" -type f | wc -l) 个"
echo "   总代码行: $(find src -name "*.java" -type f -exec wc -l {} + | tail -1 | awk '{print $1}') 行"
