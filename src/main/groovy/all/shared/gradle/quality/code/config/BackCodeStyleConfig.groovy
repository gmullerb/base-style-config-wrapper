//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.resources.TextResource
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CodeNarc
import org.gradle.api.plugins.quality.CodeNarcExtension
import org.gradle.api.plugins.quality.Pmd
import org.gradle.api.plugins.quality.PmdExtension

@CompileStatic
class BackCodeStyleConfig {
  final TextResource checkstyleConfig
  final TextResource checkstyleSuppressionConfig
  final TextResource pmdConfig
  final TextResource codenarcConfig

  BackCodeStyleConfig(
      final TextResource checkstyleConfig,
      final TextResource checkstyleSuppressionConfig,
      final TextResource pmdConfig,
      final TextResource codenarcConfig) {
    this.checkstyleConfig = checkstyleConfig
    this.checkstyleSuppressionConfig = checkstyleSuppressionConfig
    this.pmdConfig = pmdConfig
    this.codenarcConfig = codenarcConfig
  }

  File getCheckstyleConfigFile() { checkstyleConfig.asFile() }

  File getCheckstyleSuppressionConfigFile() { checkstyleSuppressionConfig.asFile() }

  File getPmdConfigFile() { pmdConfig.asFile() }

  File getCodenarcConfigFile() { codenarcConfig.asFile() }

  void complement(final CheckstyleExtension extension) {
    extension.config = checkstyleConfig
    extension.configProperties['suppressionFile'] = checkstyleSuppressionConfig.asFile().path
  }

  void complement(final Checkstyle task) {
    task.config = checkstyleConfig
    task.configProperties['suppressionFile'] = checkstyleSuppressionConfig.asFile().path
  }

  void complement(final PmdExtension extension) {
    extension.ruleSets = [] // required with new PMD version
    extension.ruleSetConfig = pmdConfig
  }

  void complement(final Pmd task) {
    task.ruleSets = [] // required with new PMD version
    task.ruleSetConfig = pmdConfig
  }

  void complement(final CodeNarcExtension extension) {
    extension.config = codenarcConfig
  }

  void complement(final CodeNarc task) {
    task.config = codenarcConfig
  }

}
