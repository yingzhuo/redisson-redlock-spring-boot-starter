usage:
	@echo "=============================================================="
	@echo "usage                 =>  显示菜单"
	@echo "setup-gradle-wrapper  =>  初始化GradleWrapper"
	@echo "compile               =>  编译"
	@echo "clean                 =>  清理"
	@echo "publish               =>  发布"
	@echo "install               =>  本地安装"
	@echo "push-all-codes        =>  提交源代码"
	@echo "=============================================================="

clean:
	gradlew -q "clean"

setup-gradle-wrapper:
	gradle "wrapper"

compile:
	gradlew "classes"

install:
	gradlew -x "test" -x "check" "publishToMavenLocal"

publish: install
	gradlew -x "test" -x "check" "publishToMavenCentralPortal"

push-all-codes: clean
	git status
	git add .
	git commit -m "$(shell /bin/date "+%F %T")"
	git push github
	git push gitee

.PHONY: usage setup-gradle-wrapper compile publish install clean push-all-codes
