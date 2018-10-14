//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.quality.code.config.BackCodeStyleConfig
import all.shared.gradle.quality.code.config.CommonCodeStyleConfig
import all.shared.gradle.quality.code.config.FrontCodeStyleConfig

import groovy.transform.CompileStatic

import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer
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
class BaseStyleConfigWrapperExtensionTest {
  @Test
  void shouldBuildACodeStyleConfig() {
    final TextResourceFactory mockFactory =  mock(TextResourceFactory)
    final ConfigurationContainer mockContainer = mock(ConfigurationContainer)
    final Configuration mockConfiguration = mock(Configuration)
    doReturn(mockConfiguration)
      .when(mockContainer)
      .getByName(eq(BaseStyleConfigWrapperPlugin.EXTENSION_NAME))
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

    final BaseStyleConfigWrapperExtension config = BaseStyleConfigWrapperExtension.of(mockFactory, mockConfiguration)

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
