#!/usr/bin/env bash
set -e

# 添加所有文件
git add .

# 检查是否有未提交的更改
if git diff --quiet && git diff --staged --quiet; then
    echo "[INFO] 没有检测到更改"
    echo "[INFO] 跳过提交"
    exit 0
fi

# 暂存所有更改并提交
TIMESTAMP=$(date "+%F %T")
git commit -m "$TIMESTAMP"

# 推送到所有远程仓库
REMOTES=$(git remote)
for remote in $REMOTES; do
    echo "[INFO] 正在推送至远程仓库：$remote"
    git push "$remote" HEAD
done
