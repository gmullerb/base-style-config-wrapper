//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.testfixtures.SpyProjectFactory

import groovy.transform.CompileStatic

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.Files
import java.nio.file.FileVisitResult
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor

import org.gradle.api.Project

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertTrue

import static org.mockito.ArgumentMatchers.eq
import static org.mockito.Mockito.verify

@CompileStatic
final class BaseStyleConfigWrapperTest {
  private final Project spyProject = SpyProjectFactory.build()
  private final URI versionUri = ClassLoader.getSystemClassLoader()
    .getResource('base_style_config/maven-metadata.xml')
    .toURI()
    .resolve('../base_style_config')

  @Test
  void shouldComposeConfigUrl() {
    final String result = BaseStyleConfigWrapper.composeConfigUrl('@from@', '#version#')

    assertEquals('@from@/#version#/base-style-config-#version#.jar', result)
  }

  @Test
  void shouldFindConfigVersion() {
    final String result = BaseStyleConfigWrapper.findConfigVersion(versionUri.toString())

    assertEquals('3.0.0', result)
  }

  @Test
  void shouldNotFindConfigVersionWhenFileIsBad() {
    final String result = BaseStyleConfigWrapper.findConfigVersion(versionUri.resolve('base_style_config/error')
      .toString())

    assertNull(result)
  }

  @Test
  void shouldNotFindConfigVersionWhenFileIsNotFound() {
    final String result = BaseStyleConfigWrapper.findConfigVersion(versionUri.resolve('./')
      .toString())

    assertNull(result)
  }

  @Test
  void shouldAddExtensionFromFolder() {
    final String dest = spyProject.mkdir(spyProject.buildDir.toString() + '/extensionName/3.0.0')
    Files.walkFileTree(
      Paths.get(ClassLoader.getSystemClassLoader()
        .getResource('base_style_config/unzipped/3.0.0/checks-suppressions.xml')
        .toURI()
        .resolve('../3.0.0')),
      new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
        throws IOException {
        Files.copy(file, Paths.get("$dest/${file.fileName.toString()}"))
        return FileVisitResult.CONTINUE
      }
    })
    spyProject.extensions.add('versionProperty', '3.0.0')

    final BaseStyleConfigWrapperExtension result = BaseStyleConfigWrapper.addExtension(
      spyProject,
      'extensionName',
      'versionProperty',
      null
    )

    assertNotNull(result)
    assertTrue(spyProject.properties['extensionName'] instanceof BaseStyleConfigWrapperExtension)
    verify(spyProject.logger)
      .debug(eq('Added base-style-config-wrapper extension from Local'))
  }

  @Test
  void shouldAddExtensionWithoutSpecificVersion() {
    final BaseStyleConfigWrapperExtension result = BaseStyleConfigWrapper.addExtension(
      spyProject,
      'extensionName',
      'versionProperty',
      versionUri.toString()
    )

    assertNotNull(result)
    assertTrue(spyProject.properties['extensionName'] instanceof BaseStyleConfigWrapperExtension)
    verify(spyProject.logger)
      .debug(eq('Added base-style-config-wrapper extension from Web'))
  }

  @Test
  void shouldAddExtensionWithSpecificVersion() {
    spyProject.extensions.add('versionProperty', '2.0.0')

    final BaseStyleConfigWrapperExtension result = BaseStyleConfigWrapper.addExtension(
      spyProject,
      'extensionName',
      'versionProperty',
      versionUri.toString()
    )

    assertNotNull(result)
  }

  @Test
  void shouldNotAddExtensionWhenExtensionNameNotAvailable() {
    spyProject.extensions.add('extensionName', 'someValue')

    final BaseStyleConfigWrapperExtension result = BaseStyleConfigWrapper.addExtension(
      spyProject,
      'extensionName',
      null,
      null
    )

    assertNull(result)
    verify(spyProject.logger)
      .error(eq('Couldn\'t add base-style-config-wrapper extension'))
  }

  @Test
  void shouldNotAddExtensionWhenVersionNotFound() {
    final BaseStyleConfigWrapperExtension result = BaseStyleConfigWrapper.addExtension(
      spyProject,
      'extensionName',
      'versionProperty',
      versionUri.resolve('./')
      .toString()
    )

    assertNull(result)
    verify(spyProject.logger)
      .error(eq('Couldn\'t detect base-style-config version, set versionProperty property'))
    verify(spyProject.logger)
      .error(eq('Couldn\'t add base-style-config-wrapper extension'))
  }

  @Test
  void shouldAddExtensionAndNotSave() {
    spyProject.extensions.add('versionProperty', '2.0.0')

    final BaseStyleConfigWrapperExtension result = BaseStyleConfigWrapper.addExtension(
      spyProject,
      'extension*Namea',
      'versionProperty',
      versionUri.toString()
    )

    assertNotNull(result)
  }

}
