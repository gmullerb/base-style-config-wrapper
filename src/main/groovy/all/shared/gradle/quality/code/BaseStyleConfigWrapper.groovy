//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import groovy.transform.CompileStatic

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

@CompileStatic
class BaseStyleConfigWrapper {
  public static final String QUALITY_CONFIG_MAVEN_REPO_URL = 'https://dl.bintray.com/gmullerb/all.shared.quality'
  public static final String QUALITY_CONFIG_MAVEN_COORDINATES = 'all.shared.quality:base-style-config'
  public static final String BASE_STYLE_CONFIG_VERSION_PROPERTY = 'BASE_STYLE_CONFIG_VERSION'

  private BaseStyleConfigWrapper() { }

  static final boolean addExtension(final Project project, final String extensionName) {
    if (project.extensions.findByName(extensionName) == null) {
      final Configuration configuration = project.configurations.maybeCreate(extensionName)
      project.repositories.maven { MavenArtifactRepository mavenRepo -> mavenRepo.url = QUALITY_CONFIG_MAVEN_REPO_URL }
      project.dependencies.add(extensionName,
        "$QUALITY_CONFIG_MAVEN_COORDINATES:${project.findProperty(BASE_STYLE_CONFIG_VERSION_PROPERTY) ?: '+'}")
      project.extensions.add(extensionName, BaseStyleConfigWrapperExtension.of(project.resources.text, configuration))
      project.logger.debug('Added base-style-config-wrapper extension')
      true
    }
    else {
      project.logger.error('Couldn\'t add base-style-config-wrapper extension')
      false
    }
  }
}
