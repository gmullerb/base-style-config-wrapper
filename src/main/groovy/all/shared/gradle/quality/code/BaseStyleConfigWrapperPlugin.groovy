//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.quality.code.complement.BaseStyleConfigWrapperComplementAction

import groovy.transform.CompileStatic

import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
class BaseStyleConfigWrapperPlugin implements Plugin<Project> {
  public static final String EXTENSION_NAME = 'baseStyleConfig'
  public static final String QUALITY_CONFIG_URL = 'https://dl.bintray.com/gmullerb/all.shared.quality/all/shared/quality/base-style-config'
  public static final String BASE_STYLE_CONFIG_VERSION_PROPERTY = 'BASE_STYLE_CONFIG_VERSION'

  static final boolean complement(final Project project) {
    final BaseStyleConfigWrapperExtension extension = BaseStyleConfigWrapper.addExtension(
      project,
      EXTENSION_NAME,
      BASE_STYLE_CONFIG_VERSION_PROPERTY,
      QUALITY_CONFIG_URL)

    if (extension) {
      project.afterEvaluate(new BaseStyleConfigWrapperComplementAction(extension))
    }
  }

  void apply(final Project project) {
    complement(project)
  }
}
