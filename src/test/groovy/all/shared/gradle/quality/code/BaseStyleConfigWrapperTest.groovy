//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.testfixtures.SpyProjectFactory

import groovy.transform.CompileStatic

import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.Dependency

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

import static org.mockito.Matchers.eq
import static org.mockito.Mockito.verify

@CompileStatic
class BaseStyleConfigWrapperTest {
  private final Project spyProject = SpyProjectFactory.build()

  @Test
  void shouldAddExtension() {
    BaseStyleConfigWrapper.addExtension(spyProject, 'extensionName')

    assertTrue(spyProject.properties['extensionName'] instanceof BaseStyleConfigWrapperExtension)
    assertEquals(
      BaseStyleConfigWrapper.QUALITY_CONFIG_MAVEN_REPO_URL,
      ((MavenArtifactRepository) spyProject.repositories.getByName('maven'))
        .getUrl()
        .toString())
    final Configuration configuration = spyProject.configurations.getByName('extensionName')
    assertTrue(configuration.allDependencies.stream()
      .anyMatch { final Dependency dependency ->
        "$dependency.group:$dependency.name:$dependency.version" == "$BaseStyleConfigWrapper.QUALITY_CONFIG_MAVEN_COORDINATES:+" })
    verify(spyProject.logger)
      .debug(eq('Added base-style-config-wrapper extension'))
  }

  @Test
  void shouldAddExtensionWithSpecificVersion() {
    spyProject.extensions.add(BaseStyleConfigWrapper.BASE_STYLE_CONFIG_VERSION_PROPERTY, '1.0.4')

    BaseStyleConfigWrapper.addExtension(spyProject, 'extensionName')

    final Configuration configuration = spyProject.configurations.getByName('extensionName')
    assertTrue(configuration.allDependencies.stream()
      .anyMatch { final Dependency dependency ->
        "$dependency.group:$dependency.name:$dependency.version" == "$BaseStyleConfigWrapper.QUALITY_CONFIG_MAVEN_COORDINATES:1.0.4" })
  }

  @Test
  void shouldNotAddExtensionWhenExtensionNameNotAvailable() {
    spyProject.extensions.add('extensionName', 'someValue')

    BaseStyleConfigWrapper.addExtension(spyProject, 'extensionName')

    verify(spyProject.logger)
      .error(eq('Couldn\'t add base-style-config-wrapper extension'))
  }
}
