//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import groovy.transform.CompileStatic

import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.Dependency

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

import static org.mockito.Matchers.eq
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify

@CompileStatic
class BaseStyleConfigWrapperTest {
  private final Project spyProject = spy(ProjectBuilder.builder().build())
  private final Logger mockLogger = mock(Logger)

  @BeforeEach
  void beforeEachTest() {
    doReturn(mockLogger)
      .when(spyProject)
      .getLogger()
  }

  @Test
  void shouldAddExtension() {
    BaseStyleConfigWrapper.addExtension(spyProject, 'extensionName')

    assertTrue(spyProject.properties['extensionName'] instanceof BaseStyleConfigWrapperExtension)
    assertEquals(
      BaseStyleConfigWrapper.QUALITY_CONFIG_MAVEN_REPO_URL,
      ((MavenArtifactRepository) spyProject.repositories.getByName('maven')).getUrl().toString())
    final Configuration configuration = spyProject.configurations.getByName('extensionName')
    assertTrue(configuration.allDependencies
      .collect { Dependency dependency -> "$dependency.group:$dependency.name:$dependency.version" }
      .contains("$BaseStyleConfigWrapper.QUALITY_CONFIG_MAVEN_COORDINATES:+"))
    verify(mockLogger).debug(eq('Added base-style-config-wrapper extension'))
  }

  @Test
  void shouldAddExtensionWithSpecificVersion() {
    spyProject.extensions.add(BaseStyleConfigWrapper.BASE_STYLE_CONFIG_VERSION_PROPERTY, '1.0.4')

    BaseStyleConfigWrapper.addExtension(spyProject, 'extensionName')

    final Configuration configuration = spyProject.configurations.getByName('extensionName')
    assertTrue(configuration.allDependencies
      .collect { Dependency dependency -> "$dependency.group:$dependency.name:$dependency.version" }
      .contains("$BaseStyleConfigWrapper.QUALITY_CONFIG_MAVEN_COORDINATES:1.0.4"))
  }

  @Test
  void shouldNotAddExtensionWhenExtensionNameNotAvailable() {
    spyProject.extensions.add('extensionName', 'someValue')

    BaseStyleConfigWrapper.addExtension(spyProject, 'extensionName')

    verify(mockLogger).error(eq('Couldn\'t add base-style-config-wrapper extension'))
  }
}
