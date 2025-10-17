#!/bin/bash
echo "🌍 Minecraft太空模组 - 项目结构"
echo "======================================"

echo ""
echo "📁 根目录"
find . -maxdepth 1 -type f -name "*.gradle" -o -name "*.md" -o -name "*.sh" | grep -v ".git" | while read file; do
    echo "  ├── 📄 $(basename $file)"
done

echo ""
echo "📁 src/main/java/com/example/spacemod"
find src/main/java/com/example/spacemod -name "*.java" -type f | while read file; do
    filename=$(basename "$file")
    dirname=$(dirname "$file" | sed 's|src/main/java/com/example/spacemod/||')
    if [ "$dirname" = "com/example/spacemod" ]; then
        echo "  ├── 🎮 $filename"
    else
        subdir=$(echo "$dirname" | sed 's|com/example/spacemod/||')
        echo "  ├── 📁 $subdir/"
        echo "  │   └── 🎮 $filename"
    fi
done

echo ""
echo "📁 src/main/resources"
find src/main/resources -type f | while read file; do
    rel_path=$(echo "$file" | sed 's|src/main/resources/||')
    echo "  ├── ⚙️ $rel_path"
done

echo ""
echo "======================================"
echo "文件总数: $(find . -name "*.java" -o -name "*.toml" -o -name "*.md" -o -name "*.sh" | grep -v ".git" | wc -l)"
echo "Java类: $(find src -name "*.java" -type f | wc -l)"
