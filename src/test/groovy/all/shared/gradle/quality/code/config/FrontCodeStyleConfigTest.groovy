//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.resources.TextResource

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock

@CompileStatic
class FrontCodeStyleConfigTest {
  private final TextResource mockEslintConfig = mock(TextResource)
  private final TextResource mockStylelintConfig = mock(TextResource)
  private final File mockEslintConfigFile = mock(File)
  private final File mockStylelintConfigFile = mock(File)

  @BeforeEach
  void beforeEachTest() {
    doReturn(mockEslintConfigFile)
      .when(mockEslintConfig)
      .asFile()
    doReturn(mockStylelintConfigFile)
      .when(mockStylelintConfig)
      .asFile()
  }

  @Test
  void shouldGetEslintConfig() {
    final FrontCodeStyleConfig config = FrontCodeStyleConfig.of(mockEslintConfig, mockStylelintConfig)

    final TextResource result = config.getEslintConfig()

    assertEquals(mockEslintConfig, result)
  }

  @Test
  void shouldGetEslintConfig1() {
    final FrontCodeStyleConfig config = FrontCodeStyleConfig.of(mockEslintConfig, mockStylelintConfig)

    final TextResource result = config.eslintConfig

    assertEquals(mockEslintConfig, result)
  }

  @Test
  void shouldGetEslintConfigFile() {
    final FrontCodeStyleConfig config = FrontCodeStyleConfig.of(mockEslintConfig, mockStylelintConfig)

    final File result = config.getEslintConfigFile()

    assertEquals(mockEslintConfigFile, result)
  }

  @Test
  void shouldGetEslintNpmConfigArgs() {
    doReturn('file')
      .when(mockEslintConfigFile)
      .getPath()
    final FrontCodeStyleConfig config = FrontCodeStyleConfig.of(mockEslintConfig, mockStylelintConfig)

    final String result = config.getEslintNpmConfigArg()

    assertEquals('--eslintConfigFile=file', result)
  }

  @Test
  void shouldGetStylelintConfig() {
    final FrontCodeStyleConfig config = FrontCodeStyleConfig.of(mockEslintConfig, mockStylelintConfig)

    final TextResource result = config.getStylelintConfig()

    assertEquals(mockStylelintConfig, result)
  }

  @Test
  void shouldGetStylelintConfigFile() {
    final FrontCodeStyleConfig config = FrontCodeStyleConfig.of(mockEslintConfig, mockStylelintConfig)

    final File result = config.getStylelintConfigFile()

    assertEquals(mockStylelintConfigFile, result)
  }

  @Test
  void shouldGetStylelintNpmConfigArgs() {
    doReturn('file')
      .when(mockStylelintConfigFile)
      .getPath()
    final FrontCodeStyleConfig config = FrontCodeStyleConfig.of(mockEslintConfig, mockStylelintConfig)

    final String result = config.getStylelintNpmConfigArg()

    assertEquals('--stylelintConfigFile=file', result)
  }
}
