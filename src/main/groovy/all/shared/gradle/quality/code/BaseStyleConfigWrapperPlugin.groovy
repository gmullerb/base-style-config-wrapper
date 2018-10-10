//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import groovy.transform.CompileStatic

import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
class BaseStyleConfigWrapperPlugin implements Plugin<Project> {
  void apply(final Project project) {
    BaseStyleConfigWrapper.addExtension(project)
  }
}
