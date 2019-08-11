//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.resources.TextResource

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock

@CompileStatic
class JsCodeStyleConfigTest {
  private final TextResource mockEslintConfig = mock(TextResource)
  private final TextResource mockTsEslintConfig = mock(TextResource)
  private final File mockEslintConfigFile = mock(File)
  private final File mockTsEslintConfigFile = mock(File)

  @BeforeEach
  void beforeEachTest() {
    doReturn(mockEslintConfigFile)
      .when(mockEslintConfig)
      .asFile()
    doReturn(mockTsEslintConfigFile)
      .when(mockTsEslintConfig)
      .asFile()
  }

  @Nested
  class EslintTest {
    @Test
    void shouldGetEslintConfig() {
      final JsCodeStyleConfig config = JsCodeStyleConfig.of(mockEslintConfig, mockTsEslintConfig)

      final TextResource result = config.getEslintConfig()

      assertEquals(mockEslintConfig, result)
    }

    @Test
    void shouldGetEslintConfigAsField() {
      final JsCodeStyleConfig config = JsCodeStyleConfig.of(mockEslintConfig, mockTsEslintConfig)

      final TextResource result = config.eslintConfig

      assertEquals(mockEslintConfig, result)
    }

    @Test
    void shouldGetEslintConfigFile() {
      final JsCodeStyleConfig config = JsCodeStyleConfig.of(mockEslintConfig, mockTsEslintConfig)

      final File result = config.getEslintConfigFile()

      assertEquals(mockEslintConfigFile, result)
    }

    @Test
    void shouldGetEslintNpmConfigArgs() {
      doReturn('file')
        .when(mockEslintConfigFile)
        .getPath()
      final JsCodeStyleConfig config = JsCodeStyleConfig.of(mockEslintConfig, mockTsEslintConfig)

      final String result = config.getEslintNpmConfigArg()

      assertEquals('--eslintConfigFile=file', result)
    }
  }

  @Nested
  class TsEslintTest {
    @Test
    void shouldGetTsEslintConfig() {
      final JsCodeStyleConfig config = JsCodeStyleConfig.of(mockEslintConfig, mockTsEslintConfig)

      final TextResource result = config.getTsEslintConfig()

      assertEquals(mockTsEslintConfig, result)
    }

    @Test
    void shouldGetTsEslintConfigFile() {
      final JsCodeStyleConfig config = JsCodeStyleConfig.of(mockEslintConfig, mockTsEslintConfig)

      final File result = config.getTsEslintConfigFile()

      assertEquals(mockTsEslintConfigFile, result)
    }

    @Test
    void shouldGetTsEslintNpmConfigArgs() {
      doReturn('file')
        .when(mockTsEslintConfigFile)
        .getPath()
      final JsCodeStyleConfig config = JsCodeStyleConfig.of(mockEslintConfig, mockTsEslintConfig)

      final String result = config.getTsEslintNpmConfigArg()

      assertEquals('--tsEslintConfigFile=file', result)
    }
  }
}
