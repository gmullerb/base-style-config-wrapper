//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.resources.TextResource

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock

@CompileStatic
class CommonCodeStyleConfigTest {
  @Test
  void shouldGetCheckstyleConfig() {
    final TextResource mockCheckstyleConfig = mock(TextResource)
    final CommonCodeStyleConfig config = new CommonCodeStyleConfig(mockCheckstyleConfig)

    final TextResource result = config.getCheckstyleConfig()

    assertEquals(mockCheckstyleConfig, result)
  }

  @Test
  void shouldGetCheckstyleConfigFile() {
    final TextResource mockCheckstyleConfig = mock(TextResource)
    final CommonCodeStyleConfig config = new CommonCodeStyleConfig(mockCheckstyleConfig)
    final File mockFile = mock(File)
    doReturn(mockFile)
      .when(mockCheckstyleConfig)
      .asFile()

    final File result = config.getCheckstyleConfigFile()

    assertEquals(mockFile, result)
  }
}
