//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.resources.TextResource
import org.gradle.api.plugins.quality.CodeNarc
import org.gradle.api.plugins.quality.CodeNarcExtension

@CompileStatic
class CommonCodeStyleConfig {
  private final TextResource codenarcConfig
  private final TextResource checkstyleConfig

  CommonCodeStyleConfig(
      final TextResource checkstyleConfig,
      final TextResource codenarcConfig) {
    this.codenarcConfig = codenarcConfig
    this.checkstyleConfig = checkstyleConfig
  }

  TextResource getCodenarcConfig() { codenarcConfig }

  File getCodenarcConfigFile() { codenarcConfig.asFile() }

  TextResource getCheckstyleConfig() { checkstyleConfig }

  File getCheckstyleConfigFile() { checkstyleConfig.asFile() }

  void complement(final CodeNarcExtension extension) {
    extension.config = codenarcConfig
  }

  void complement(final CodeNarc task) {
    task.config = codenarcConfig
  }
}
