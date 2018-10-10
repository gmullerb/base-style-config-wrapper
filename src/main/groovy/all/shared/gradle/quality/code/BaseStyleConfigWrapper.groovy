//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.quality.code.config.CodeStyleConfig

import groovy.transform.CompileStatic

import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

@CompileStatic
class BaseStyleConfigWrapper {
  public static final String QUALITY_CONFIG_MAVEN_REPO_URL = 'https://dl.bintray.com/gmullerb/all.shared.quality'
  public static final String QUALITY_CONFIG_MAVEN_COORDINATES = 'all.shared.quality:base-style-config'
  public static final String BASE_STYLE_CONFIG_VERSION_PROPERTY = 'BASE_STYLE_CONFIG_VERSION'

  private BaseStyleConfigWrapper() { }

  static void addExtension(final Project project) {
    if (project.extensions.findByName(CodeStyleConfig.CODE_STYLE_EXTENSION) == null) {
      project.repositories.maven { MavenArtifactRepository mavenRepo -> mavenRepo.url = QUALITY_CONFIG_MAVEN_REPO_URL }
      project.configurations.maybeCreate(CodeStyleConfig.CODE_STYLE_EXTENSION)
      project.dependencies.add(CodeStyleConfig.CODE_STYLE_EXTENSION,
        "$QUALITY_CONFIG_MAVEN_COORDINATES:${project.findProperty(BASE_STYLE_CONFIG_VERSION_PROPERTY) ?: '+'}")
      project.extensions.add(CodeStyleConfig.CODE_STYLE_EXTENSION, CodeStyleConfig.of(project))
      project.logger.debug('Added code-style-check extension')
    }
    else {
      project.logger.error('Couldn\'t add code-style-check extension')
    }
  }
}
