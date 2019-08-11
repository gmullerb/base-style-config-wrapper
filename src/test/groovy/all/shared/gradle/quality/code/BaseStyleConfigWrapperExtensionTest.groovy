//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.quality.code.config.JavaCodeStyleConfig
import all.shared.gradle.quality.code.config.CommonCodeStyleConfig
import all.shared.gradle.quality.code.config.JsCodeStyleConfig

import groovy.transform.CompileStatic

import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.resources.TextResource
import org.gradle.api.resources.TextResourceFactory

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

import static org.junit.jupiter.api.Assertions.assertAll
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

import static org.mockito.ArgumentMatchers.eq
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock

@CompileStatic
final class BaseStyleConfigWrapperExtensionTest {
  @Test
  void shouldBuildACodeStyleConfig() {
    final TextResourceFactory mockFactory = mock(TextResourceFactory)
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
      .fromArchiveEntry(eq(mockConfiguration), eq('groovy/groovy-rules.groovy'))
    doReturn(mockResource)
      .when(mockFactory)
      .fromArchiveEntry(eq(mockConfiguration), eq('java/coding-checks.xml'))
    doReturn(mockResource)
      .when(mockFactory)
      .fromArchiveEntry(eq(mockConfiguration), eq('java/checks-suppressions.xml'))
    doReturn(mockResource)
      .when(mockFactory)
      .fromArchiveEntry(eq(mockConfiguration), eq('java/coding-rules.xml'))
    doReturn(mockResource)
      .when(mockFactory)
      .fromArchiveEntry(eq(mockConfiguration), eq('js/.eslintrc.json'))
    doReturn(mockResource)
      .when(mockFactory)
      .fromArchiveEntry(eq(mockConfiguration), eq('js/.typescript-eslintrc.json'))
    final File mockFile = mock(File)
    doReturn(mockFile)
      .when(mockResource)
      .asFile()
    doReturn('file')
      .when(mockFile)
      .getPath()

    final BaseStyleConfigWrapperExtension config = BaseStyleConfigWrapperExtension.of(mockFactory, mockConfiguration)

    assertTrue(config.autoComplement)
    assertAll([
    {
      final CommonCodeStyleConfig result = config.getCommon()

      assertNotNull(result)
    } as Executable,
    {
      final JavaCodeStyleConfig result = config.getJava()

      assertNotNull(result)
    } as Executable,
    {
      final JsCodeStyleConfig result = config.getJs()

      assertNotNull(result)
    } as Executable])
  }
}
