/*
 *
 * Copyright 2022-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package buildlogic.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

import javax.inject.Inject

class LicensePlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		project.extensions.add(AddLicenseHeaderTask.TASK_NAME, new PluginConfiguration())
		project.getTasks()
			.register(AddLicenseHeaderTask.TASK_NAME, AddLicenseHeaderTask, project)
	}

	static class AddLicenseHeaderTask extends DefaultTask {
		public static final String TASK_NAME = 'addLicenseHeader'

		final Project project

		@Inject
		AddLicenseHeaderTask(Project project) {
			this.project = project
			this.group = 'license'
			this.description = 'Adds license header to the source files'
		}

		@TaskAction
		void execute() {
			var header = getJavaHeader()

			project.fileTree(project.rootDir) {
				include(
					'**/*.java',
					'**/*.groovy',
					'**/*.kt',
					'**/*.scala',
				)
			}.each { file ->
				var content = file.text
				var changed = false

				if (!content.startsWith(header)) {
					content = header + content
					changed = true
				}

				if (!content.endsWith('\n')) {
					content = content + '\n'
					changed = true
				}

				if (changed) {
					file.text = content
				}
			}
		}

		private String getJavaHeader() {
			var config = project.extensions.getByName(TASK_NAME) as PluginConfiguration
			var header = config.javaHeader

			if (header.isBlank()) {
				var msg = 'java license header text is blank'
				logger.error(msg)
				throw new GradleException(msg)
			}

			if (!header.endsWith('\n')) {
				header += '\n'
			}
			return header
		}
	}

	static class PluginConfiguration {
		String javaHeader = ''
	}

}
