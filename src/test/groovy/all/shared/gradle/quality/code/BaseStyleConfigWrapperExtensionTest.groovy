//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.quality.code.config.JavaCodeStyleConfig
import all.shared.gradle.quality.code.config.CommonCodeStyleConfig
import all.shared.gradle.quality.code.config.JsCodeStyleConfig
import all.shared.gradle.testfixtures.SpyProjectFactory

import groovy.transform.CompileStatic

import java.nio.file.Paths
import java.nio.file.NoSuchFileException

import org.gradle.api.Project
import org.gradle.api.resources.TextResource
import org.gradle.api.resources.TextResourceFactory

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

import org.mockito.ArgumentMatchers

import static org.junit.jupiter.api.Assertions.assertAll
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue

import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock

@CompileStatic
final class BaseStyleConfigWrapperExtensionTest {
  private final URI unzippedUri = ClassLoader.getSystemClassLoader()
    .getResource('base_style_config/unzipped/checks-suppressions.xml')
    .toURI()
    .resolve('../unzipped')

  @Test
  void shouldFindFile() {
    final File result = BaseStyleConfigWrapperExtension.findFile(
      Paths.get(unzippedUri),
      'checks-suppressions.xml'
    )

    assertNotNull(result)
  }

  @Test
  void shouldNotFindFileWhenIncorrectMd5() {
    assertThrows(NoSuchFileException, {
      BaseStyleConfigWrapperExtension.findFile(
        Paths.get(unzippedUri),
        'checks-suppressions-2.xml'
      )
    } as Executable)
  }

  @Test
  void shouldNotFindFileWhenNoMd5() {
    assertThrows(NoSuchFileException, {
      BaseStyleConfigWrapperExtension.findFile(
        Paths.get(unzippedUri),
        'checks-suppressions-3.xml'
      )
    } as Executable)
  }

  @Test
  void shouldNotFindFileWhenNoFound() {
    assertThrows(NoSuchFileException, {
      BaseStyleConfigWrapperExtension.findFile(
        Paths.get(unzippedUri),
        'checks-suppressions-4.xml'
      )
    } as Executable)
  }

  @Test
  void shouldCopy() {
    final Project spyProject = SpyProjectFactory.build()
    final File file = spyProject.file('chktest.txt')
    file.text = new File(ClassLoader.getSystemClassLoader()
        .getResource('base_style_config/unzipped/checks-suppressions.xml')
        .toURI())
      .text

    BaseStyleConfigWrapperExtension.copy(
      spyProject.resources.text.fromFile(file),
      Paths.get(spyProject.projectDir.path + '/checktest.txt')
    )

    assertTrue(spyProject.file(spyProject.projectDir.path + '/checktest.txt')
      .exists())
    assertTrue(spyProject.file(spyProject.projectDir.path + '/checktest.txt.md5')
      .exists())
  }

  @Test
  void shouldBuildACodeStyleConfig() {
    final TextResourceFactory mockFactory = mock(TextResourceFactory)
    final TextResource mockResource = mock(TextResource)
    doReturn(mockResource)
      .when(mockFactory)
      .fromUri(ArgumentMatchers.<Object>argThat { arg -> arg == 'jar:conf!/common/common-checks.xml' })
    doReturn(mockResource)
      .when(mockFactory)
      .fromUri(ArgumentMatchers.<Object>argThat { arg -> arg == 'jar:conf!/groovy/groovy-rules.groovy' })
    doReturn(mockResource)
      .when(mockFactory)
      .fromUri(ArgumentMatchers.<Object>argThat { arg -> arg == 'jar:conf!/java/coding-checks.xml' })
    doReturn(mockResource)
      .when(mockFactory)
      .fromUri(ArgumentMatchers.<Object>argThat { arg -> arg == 'jar:conf!/java/checks-suppressions.xml' })
    doReturn(mockResource)
      .when(mockFactory)
      .fromUri(ArgumentMatchers.<Object>argThat { arg -> arg == 'jar:conf!/java/coding-rules.xml' })
    doReturn(mockResource)
      .when(mockFactory)
      .fromUri(ArgumentMatchers.<Object>argThat { arg -> arg == 'jar:conf!/js/.eslintrc.json' })
    doReturn(mockResource)
      .when(mockFactory)
      .fromUri(ArgumentMatchers.<Object>argThat { arg -> arg == 'jar:conf!/js/.typescript-eslintrc.json' })
    final File mockFile = mock(File)
    doReturn(mockFile)
      .when(mockResource)
      .asFile()
    doReturn('file')
      .when(mockFile)
      .getPath()

    final BaseStyleConfigWrapperExtension config = BaseStyleConfigWrapperExtension.of(mockFactory, 'conf')

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
