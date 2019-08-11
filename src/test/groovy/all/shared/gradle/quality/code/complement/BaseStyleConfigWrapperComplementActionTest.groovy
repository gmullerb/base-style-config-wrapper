//  Copyright (c) 2019 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.complement

import all.shared.gradle.quality.code.BaseStyleConfigWrapperExtension
import all.shared.gradle.quality.code.config.JavaCodeStyleConfig
import all.shared.gradle.testfixtures.SpyProjectFactory

import groovy.transform.CompileStatic

import org.gradle.api.Project
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CodeNarcExtension
import org.gradle.api.plugins.quality.PmdExtension
import org.gradle.api.resources.TextResource

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.never
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify

@CompileStatic
final class BaseStyleConfigWrapperComplementActionTest {
  private final Project spyProject = SpyProjectFactory.build()
  private final TextResource checkstyleConfig = mock(TextResource)
  private final TextResource checkstyleSuppressionConfig = mock(TextResource)
  private final TextResource pmdConfig = mock(TextResource)
  private final TextResource codenarcConfig = mock(TextResource)
  private final JavaCodeStyleConfig spyBackConfig = spy(new JavaCodeStyleConfig(
    checkstyleConfig,
    checkstyleSuppressionConfig,
    pmdConfig,
    codenarcConfig))
  private final BaseStyleConfigWrapperExtension extension = new BaseStyleConfigWrapperExtension(null, spyBackConfig, null) { }
  private final BaseStyleConfigWrapperComplementAction action = new BaseStyleConfigWrapperComplementAction(extension)

  @Test
  void shouldComplementCheckstyleExtension() {
    spyProject.extensions.add(BaseStyleConfigWrapperComplementAction.CHECKSTYLE_VERSION_PROPERTY, '1.0')
    final CheckstyleExtension extension = new CheckstyleExtension(spyProject)
    spyProject.extensions.add('checkstyle', extension)
    doReturn(mock(File))
      .when(checkstyleSuppressionConfig)
      .asFile()

    action.execute(spyProject)

    assertEquals('1.0', extension.toolVersion)
    assertEquals(checkstyleConfig, extension.config)
  }

  @Test
  void shouldComplementPmdExtension() {
    spyProject.extensions.add(BaseStyleConfigWrapperComplementAction.PMD_VERSION_PROPERTY, '1.0')
    final PmdExtension extension = new PmdExtension(spyProject)
    spyProject.extensions.add('pmd', extension)

    action.execute(spyProject)

    assertEquals('1.0', extension.toolVersion)
    assertEquals(pmdConfig, extension.ruleSetConfig)
  }

  @Test
  void shouldComplementCodenarcExtension() {
    spyProject.extensions.add(BaseStyleConfigWrapperComplementAction.CODENARC_VERSION_PROPERTY, '1.0')
    final CodeNarcExtension extension = new CodeNarcExtension(spyProject)
    spyProject.extensions.add('codenarc', extension)

    action.execute(spyProject)

    assertEquals('1.0', extension.toolVersion)
    assertEquals(codenarcConfig, extension.config)
  }

  @Test
  void shouldNotComplementExtensionsWhenAutoComplementFalse() {
    final CheckstyleExtension checkStylExtension = new CheckstyleExtension(spyProject)
    spyProject.extensions.add('checkstyle', checkStylExtension)
    final PmdExtension pmdExtension = new PmdExtension(spyProject)
    spyProject.extensions.add('pmd', pmdExtension)
    final CodeNarcExtension codenarcExtension = new CodeNarcExtension(spyProject)
    spyProject.extensions.add('codenarc', codenarcExtension)
    extension.autoComplement = false

    action.execute(spyProject)

    assertNull(checkStylExtension.config)
    assertNull(pmdExtension.ruleSetConfig)
    assertNull(codenarcExtension.config)
  }

  @Test
  void shouldNotComplementExtensionsWhenExtensionIsNotPresent() {
    action.execute(spyProject)

    verify(spyBackConfig, never())
      .complement(any(CheckstyleExtension))
    verify(spyBackConfig, never())
      .complement(any(PmdExtension))
    verify(spyBackConfig, never())
      .complement(any(CodeNarcExtension))
  }
}
