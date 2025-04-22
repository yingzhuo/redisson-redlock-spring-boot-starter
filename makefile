usage:
	@echo "=============================================================="
	@echo "usage                 =>  显示菜单"
	@echo "setup-gradle-wrapper  =>  初始化GradleWrapper"
	@echo "compile               =>  编译"
	@echo "clean                 =>  清理"
	@echo "publish               =>  发布"
	@echo "install               =>  本地安装"
	@echo "github                =>  提交源代码"
	@echo "=============================================================="

clean:
	@$(CURDIR)/gradlew --project-dir $(CURDIR) --quiet "clean"

setup-gradle-wrapper:
	@gradle --project-dir $(CURDIR) "wrapper"

compile:
	@$(CURDIR)/gradlew --project-dir $(CURDIR) classes

install:
	@$(CURDIR)/gradlew --project-dir $(CURDIR) -Dorg.gradle.parallel=false -x "test" -x "check" "publishToMavenLocal"

publish: install
	@$(CURDIR)/gradlew --project-dir $(CURDIR) -Dorg.gradle.parallel=false -x "test" -x "check" "publishToMavenCentralPortal"

github: clean
	@git status
	@git add .
	@git commit -m "$(shell /bin/date "+%F %T")"
	@git push

.PHONY: usage setup-gradle-wrapper compile publish install clean github
