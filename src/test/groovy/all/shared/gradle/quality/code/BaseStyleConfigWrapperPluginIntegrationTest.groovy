//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.quality.code.complement.BaseStyleConfigWrapperComplementAction
import all.shared.gradle.testfixtures.SpyProjectFactory

import groovy.transform.CompileStatic

import org.gradle.api.Project

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.never
import static org.mockito.Mockito.verify

/**
 * This is an integration test (almost an E2E) due to it will hit the real repository of 'base-style-config'
 * access to the internet is required in order to run it.
 */
@CompileStatic
final class BaseStyleConfigWrapperPluginIntegrationTest {
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

  @Test
  void shouldAddProjectAfterEvaluateAction() {
    final BaseStyleConfigWrapperPlugin plugin = new BaseStyleConfigWrapperPlugin()

    plugin.apply(testProject)

    verify(testProject)
      .afterEvaluate(any(BaseStyleConfigWrapperComplementAction))
  }

  @Test
  void shouldNotAddProjectAfterEvaluateActionWhenExtensionAlreadyDefined() {
    testProject.extensions.add(BaseStyleConfigWrapperPlugin.EXTENSION_NAME, 'someValue')
    final BaseStyleConfigWrapperPlugin plugin = new BaseStyleConfigWrapperPlugin()

    plugin.apply(testProject)

    verify(testProject, never())
      .afterEvaluate(any(BaseStyleConfigWrapperComplementAction))
  }
}
