//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.resources.TextResource

@CompileStatic
class CommonCodeStyleConfig {
  final TextResource checkstyleConfig

  CommonCodeStyleConfig(final TextResource checkstyleConfig) {
    this.checkstyleConfig = checkstyleConfig
  }

  File getCheckstyleConfigFile() { checkstyleConfig.asFile() }
}
