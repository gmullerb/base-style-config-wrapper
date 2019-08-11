//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import all.shared.gradle.testfixtures.SpyProjectFactory

import groovy.transform.CompileStatic

import org.gradle.api.Project
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.Pmd
import org.gradle.api.plugins.quality.PmdExtension
import org.gradle.api.resources.TextResource

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

import static org.junit.jupiter.api.Assertions.assertAll
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock

@CompileStatic
class JavaCodeStyleConfigTest {

  @Nested
  class CheckstyleTest {
    @Test
    void shouldGetCheckstyleConfig() {
      final TextResource mockCheckstyleConfig = mock(TextResource)
      final JavaCodeStyleConfig config = new JavaCodeStyleConfig(mockCheckstyleConfig, null, null)

      final TextResource result = config.getCheckstyleConfig()

      assertEquals(mockCheckstyleConfig, result)
    }

    @Test
    void shouldGetCheckstyleConfigFile() {
      final TextResource mockCheckstyleConfig = mock(TextResource)
      final JavaCodeStyleConfig config = new JavaCodeStyleConfig(mockCheckstyleConfig, null, null)
      final File mockFile = mock(File)
      doReturn(mockFile)
        .when(mockCheckstyleConfig)
        .asFile()

      final File result = config.getCheckstyleConfigFile()

      assertEquals(mockFile, result)
    }

    @Test
    void shouldGetCheckstyleSuppressionConfig() {
      final TextResource mockCheckstyleSuppressionConfig = mock(TextResource)
      final JavaCodeStyleConfig config = new JavaCodeStyleConfig(null, mockCheckstyleSuppressionConfig, null)

      final TextResource result = config.getCheckstyleSuppressionConfig()

      assertEquals(mockCheckstyleSuppressionConfig, result)
    }

    @Test
    void shouldGetCheckstyleSuppressionConfigFile() {
      final TextResource mockCheckstyleSuppressionConfig = mock(TextResource)
      final JavaCodeStyleConfig config = new JavaCodeStyleConfig(null, mockCheckstyleSuppressionConfig, null)
      final File mockFile = mock(File)
      doReturn(mockFile)
        .when(mockCheckstyleSuppressionConfig)
        .asFile()

      final File result = config.getCheckstyleSuppressionConfigFile()

      assertEquals(mockFile, result)
    }

    @Test
    void shouldComplement() {
      final TextResource mockCheckstyleConfig = mock(TextResource)
      final TextResource mockCheckstyleSuppressionConfig = mock(TextResource)
      final JavaCodeStyleConfig config = new JavaCodeStyleConfig(mockCheckstyleConfig, mockCheckstyleSuppressionConfig, null)
      final File mockFile = mock(File)
      doReturn(mockFile)
        .when(mockCheckstyleSuppressionConfig)
        .asFile()
      doReturn('theFile')
        .when(mockFile)
        .getPath()
      final Project testProject = SpyProjectFactory.build()

      assertAll([
      {
        final CheckstyleExtension extension = new CheckstyleExtension(testProject)

        config.complement(extension)

        assertEquals(mockCheckstyleConfig, extension.config)
        assertEquals('theFile', extension.configProperties['suppressionFile'])
      } as Executable,
      {
        final Checkstyle task = testProject.tasks.create('shouldComplementCheckstyle', Checkstyle)

        config.complement(task)

        assertEquals(mockCheckstyleConfig, task.config)
        assertEquals('theFile', task.configProperties['suppressionFile'])
      } as Executable])
    }
  }

  @Nested
  class PmdTest {
    @Test
    void shouldGetPmdConfig() {
      final TextResource mockPmdConfig = mock(TextResource)
      final JavaCodeStyleConfig config = new JavaCodeStyleConfig(null, null, mockPmdConfig)

      final TextResource result = config.getPmdConfig()

      assertEquals(mockPmdConfig, result)
    }

    @Test
    void shouldGetPmdConfigFile() {
      final TextResource mockPmdConfig = mock(TextResource)
      final JavaCodeStyleConfig config = new JavaCodeStyleConfig(null, null, mockPmdConfig)
      final File mockFile = mock(File)
      doReturn(mockFile)
        .when(mockPmdConfig)
        .asFile()

      final File result = config.getPmdConfigFile()

      assertEquals(mockFile, result)
    }

    @Test
    void shouldComplement() {
      final TextResource mockPmdConfig = mock(TextResource)
      final JavaCodeStyleConfig config = new JavaCodeStyleConfig(null, null, mockPmdConfig)
      final Project testProject = SpyProjectFactory.build()

      assertAll([
      {
        final PmdExtension extension = new PmdExtension(testProject)

        config.complement(extension)

        assertTrue(extension.ruleSets.isEmpty())
        assertEquals(mockPmdConfig, extension.ruleSetConfig)
      } as Executable,
      {
        final Pmd task = testProject.tasks.create('shouldComplementPmd', Pmd)

        config.complement(task)

        assertTrue(task.ruleSets.isEmpty())
        assertEquals(mockPmdConfig, task.ruleSetConfig)
      } as Executable])
    }
  }
}
