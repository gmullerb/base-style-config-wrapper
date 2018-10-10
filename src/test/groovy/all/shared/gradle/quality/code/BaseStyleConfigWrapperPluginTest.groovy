//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.quality.code.config.CodeStyleConfig

import groovy.transform.CompileStatic

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull

@CompileStatic
class BaseStyleConfigWrapperPluginTest {
  @Test
  void shouldApplyPluginWithTask() {
    final BaseStyleConfigWrapperPlugin plugin = new BaseStyleConfigWrapperPlugin()
    final Project testProject = ProjectBuilder.builder().build()

    plugin.apply(testProject)

    assertNotNull(testProject.properties[CodeStyleConfig.CODE_STYLE_EXTENSION])
  }

  @Test
  void shouldNotApplyPluginWhenExtensionNameNotAvailable() {
    final BaseStyleConfigWrapperPlugin plugin = new BaseStyleConfigWrapperPlugin()
    final Project testProject = ProjectBuilder.builder().build()
    testProject.extensions.add(CodeStyleConfig.CODE_STYLE_EXTENSION, 'someValue')

    plugin.apply(testProject)

    assertFalse(testProject.properties[CodeStyleConfig.CODE_STYLE_EXTENSION] instanceof CodeStyleConfig)
  }
}
