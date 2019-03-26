//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.testfixtures.SpyProjectFactory

import groovy.transform.CompileStatic

import org.gradle.api.Project

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull

@CompileStatic
class BaseStyleConfigWrapperPluginTest {
  private final Project testProject = SpyProjectFactory.build()

  @Test
  void shouldApplyPluginWithTask() {
    final BaseStyleConfigWrapperPlugin plugin = new BaseStyleConfigWrapperPlugin()

    plugin.apply(testProject)

    assertNotNull(testProject.properties[BaseStyleConfigWrapperPlugin.EXTENSION_NAME])
  }

  @Test
  void shouldNotApplyPluginWhenExtensionNameNotAvailable() {
    final BaseStyleConfigWrapperPlugin plugin = new BaseStyleConfigWrapperPlugin()
    testProject.extensions.add(BaseStyleConfigWrapperPlugin.EXTENSION_NAME, 'someValue')

    plugin.apply(testProject)

    assertFalse(testProject.properties[BaseStyleConfigWrapperPlugin.EXTENSION_NAME] instanceof BaseStyleConfigWrapperExtension)
  }
}
