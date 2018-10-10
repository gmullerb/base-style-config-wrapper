//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.Project
import org.gradle.api.resources.TextResource
import org.gradle.api.plugins.quality.CodeNarc
import org.gradle.api.plugins.quality.CodeNarcExtension
import org.gradle.testfixtures.ProjectBuilder

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

import static org.junit.jupiter.api.Assertions.assertAll
import static org.junit.jupiter.api.Assertions.assertEquals

import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock

@CompileStatic
class CommonCodeStyleConfigTest {
  @Test
  void shouldGetCheckstyleConfig() {
    final TextResource mockCheckstyleConfig = mock(TextResource)
    final CommonCodeStyleConfig config = new CommonCodeStyleConfig(mockCheckstyleConfig, null)

    final TextResource result = config.getCheckstyleConfig()

    assertEquals(mockCheckstyleConfig, result)
  }

  @Test
  void shouldGetCheckstyleConfigFile() {
    final TextResource mockCheckstyleConfig = mock(TextResource)
    final CommonCodeStyleConfig config = new CommonCodeStyleConfig(mockCheckstyleConfig, null)
    final File mockFile = mock(File)
    doReturn(mockFile)
      .when(mockCheckstyleConfig)
      .asFile()

    final File result = config.getCheckstyleConfigFile()

    assertEquals(mockFile, result)
  }

  @Test
  void shouldGetCodenarcConfig() {
    final TextResource mockCodenarcConfig = mock(TextResource)
    final CommonCodeStyleConfig config = new CommonCodeStyleConfig(null, mockCodenarcConfig)

    final TextResource result = config.getCodenarcConfig()

    assertEquals(mockCodenarcConfig, result)
  }

  @Test
  void shouldGetCodenarcConfigFile() {
    final TextResource mockCodenarcConfig = mock(TextResource)
    final CommonCodeStyleConfig config = new CommonCodeStyleConfig(null, mockCodenarcConfig)
    final File mockFile = mock(File)
    doReturn(mockFile)
      .when(mockCodenarcConfig)
      .asFile()

    final File result = config.getCodenarcConfigFile()

    assertEquals(mockFile, result)
  }

  @Test
  void shouldComplement() {
    final TextResource mockCodenarcConfig = mock(TextResource)
    final CommonCodeStyleConfig config = new CommonCodeStyleConfig(null, mockCodenarcConfig)
    final Project testProject = ProjectBuilder.builder().build()

    assertAll([
    {
      final CodeNarcExtension extension = new CodeNarcExtension(testProject)

      config.complement(extension)

      assertEquals(mockCodenarcConfig, extension.config)
    } as Executable,
    {
      final CodeNarc task = testProject.tasks.create('shouldComplement', CodeNarc)

      config.complement(task)

      assertEquals(mockCodenarcConfig, task.config)
    } as Executable])
  }
}
