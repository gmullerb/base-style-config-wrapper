//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.resources.ResourceHandler
import org.gradle.api.resources.TextResource
import org.gradle.api.resources.TextResourceFactory

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

import static org.junit.jupiter.api.Assertions.assertAll
import static org.junit.jupiter.api.Assertions.assertNotNull

import static org.mockito.Matchers.eq
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock

@CompileStatic
class CodeStyleConfigTest {
  @Test
  void shouldBuildACodeStyleConfig() {
    final Project mockProject = mock(Project)
    final ResourceHandler mockHandler =  mock(ResourceHandler)
    doReturn(mockHandler)
      .when(mockProject)
      .getResources()
    final TextResourceFactory mockFactory =  mock(TextResourceFactory)
    doReturn(mockFactory)
      .when(mockHandler)
      .getText()
    final ConfigurationContainer mockContainer = mock(ConfigurationContainer)
    doReturn(mockContainer)
      .when(mockProject)
      .getConfigurations()
    final Configuration mockConfiguration = mock(Configuration)
    doReturn(mockConfiguration)
      .when(mockContainer)
      .getByName(eq(CodeStyleConfig.CODE_STYLE_EXTENSION))
    final TextResource mockResource = mock(TextResource)
    doReturn(mockResource)
      .when(mockFactory)
      .fromArchiveEntry(eq(mockConfiguration), eq('common/common-checks.xml'))
    doReturn(mockResource)
      .when(mockFactory)
      .fromArchiveEntry(eq(mockConfiguration), eq('gradle/gradle-rules.groovy'))
    doReturn(mockResource)
      .when(mockFactory)
      .fromArchiveEntry(eq(mockConfiguration), eq('back/coding-checks.xml'))
    doReturn(mockResource)
      .when(mockFactory)
      .fromArchiveEntry(eq(mockConfiguration), eq('back/checks-suppressions.xml'))
    doReturn(mockResource)
      .when(mockFactory)
      .fromArchiveEntry(eq(mockConfiguration), eq('back/coding-rules.xml'))
    doReturn(mockResource)
      .when(mockFactory)
      .fromArchiveEntry(eq(mockConfiguration), eq('front/.eslintrc.json'))
    doReturn(mockResource)
      .when(mockFactory)
      .fromArchiveEntry(eq(mockConfiguration), eq('front/.stylelintrc.json'))
    final File mockFile = mock(File)
    doReturn(mockFile)
      .when(mockResource)
      .asFile()
    doReturn('file')
      .when(mockFile)
      .getPath()

    final CodeStyleConfig config = CodeStyleConfig.of(mockProject)

    assertAll([
    {
      final CommonCodeStyleConfig result = config.getCommon()

      assertNotNull(result)
    } as Executable,
    {
      final BackCodeStyleConfig result = config.getBack()

      assertNotNull(result)
    } as Executable,
    {
      final FrontCodeStyleConfig result = config.getFront()

      assertNotNull(result)
    } as Executable])
  }
}
