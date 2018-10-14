//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

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

    assertNotNull(testProject.properties[BaseStyleConfigWrapperPlugin.EXTENSION_NAME])
  }

  @Test
  void shouldNotApplyPluginWhenExtensionNameNotAvailable() {
    final BaseStyleConfigWrapperPlugin plugin = new BaseStyleConfigWrapperPlugin()
    final Project testProject = ProjectBuilder.builder().build()
    testProject.extensions.add(BaseStyleConfigWrapperPlugin.EXTENSION_NAME, 'someValue')

    plugin.apply(testProject)

    assertFalse(testProject.properties[BaseStyleConfigWrapperPlugin.EXTENSION_NAME] instanceof BaseStyleConfigWrapperExtension)
  }
}
