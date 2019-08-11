//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.resources.TextResource
import org.gradle.api.plugins.quality.CodeNarc
import org.gradle.api.plugins.quality.CodeNarcExtension

@CompileStatic
class GroovyCodeStyleConfig {
  final TextResource codenarcConfig

  GroovyCodeStyleConfig(final TextResource codenarcConfig) {
    this.codenarcConfig = codenarcConfig
  }

  File getCodenarcConfigFile() { codenarcConfig.asFile() }

  void complement(final CodeNarcExtension extension) {
    extension.config = codenarcConfig
  }

  void complement(final CodeNarc task) {
    task.config = codenarcConfig
  }

}
