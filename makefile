usage:
	@echo "=============================================================="
	@echo "usage     =>  显示菜单"
	@echo "wrapper   =>  初始化GradleWrapper"
	@echo "compile   =>  编译"
	@echo "clean     =>  清理"
	@echo "build     =>  打包"
	@echo "publish   =>  发布"
	@echo "install   =>  本地安装"
	@echo "github    =>  提交源代码"
	@echo "=============================================================="

wrapper:
	@gradle wrapper

compile:
	@$(CURDIR)/gradlew --project-dir $(CURDIR) classes

install:
	@$(CURDIR)/gradlew --project-dir $(CURDIR) -Dorg.gradle.parallel=false -x "test" -x "check" "publishToMavenLocal"

publish: install
	@$(CURDIR)/gradlew --project-dir $(CURDIR) -Dorg.gradle.parallel=false -x "test" -x "check" "publishToMavenCentralPortal"

clean:
	@gradlew clean -q

github: clean
	@git status
	@git add .
	@git commit -m "$(shell /bin/date "+%F %T")"
	@git push

.PHONY: usage compile build publish install clean github
