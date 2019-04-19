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

  static final boolean complement(final Project project) {
    final BaseStyleConfigWrapperExtension extension = BaseStyleConfigWrapper.addExtension(project, EXTENSION_NAME)

    if (extension) {
      project.afterEvaluate(new BaseStyleConfigWrapperComplementAction(extension))
    }
  }

  void apply(final Project project) {
    complement(project)
  }
}
