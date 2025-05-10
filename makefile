usage:
	@echo '==============================================================================================================='
	@echo 'usage                : 显示本菜单'
	@echo 'clean                : 清理项目构建产物'
	@echo 'clean-buildsrc       : 清理项目构建逻辑'
	@echo 'compile              : 编译项目'
	@echo 'install              : 安装到本地maven仓库'
	@echo 'publish              : 发布代码到maven中央仓库'
	@echo 'setup-gradle-wrapper : 初始化 gradle wrapper'
	@echo 'add-license-header   : 为源文件添加许可证头'
	@echo 'test                 : 执行单元测试'
	@echo 'check                : 检查代码风格'
	@echo 'push-to-vcs          : 提交文件'
	@echo '==============================================================================================================='

clean:
	gradlew -q "clean"

clean-buildsrc:
	gradlew -q -p $(CURDIR)/buildSrc/ "clean"

compile:
	gradlew "classes"

install: add-license-header
	gradlew --no-parallel -x "test" -x "check" "publishToMavenLocal"

publish: install
	gradlew --no-parallel -x "test" -x "check" "publishToMavenCentralPortal"

setup-gradle-wrapper:
	gradle "wrapper"

add-license-header:
	gradlew -q "addLicenseHeader"

test:
	gradlew "test"

check:
	gradlew "check"

push-to-vcs: add-license-header
	gradlew -q "pushToVcs"

.PHONY: \
	usage \
	clean clean-buildsrc compile publish install \
	check test \
	setup-gradle-wrapper \
	add-license-header \
	push-to-vcs
