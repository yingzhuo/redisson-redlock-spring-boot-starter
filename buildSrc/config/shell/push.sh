#!/usr/bin/env bash

git add .
git commit -m "$(/bin/date "+%F %T")"
git push gitee
git push github
