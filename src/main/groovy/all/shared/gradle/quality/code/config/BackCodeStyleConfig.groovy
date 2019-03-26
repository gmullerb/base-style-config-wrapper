//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.resources.TextResource
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.Pmd
import org.gradle.api.plugins.quality.PmdExtension

@CompileStatic
class BackCodeStyleConfig {
  final TextResource checkstyleConfig
  final TextResource checkstyleSuppressionConfig
  final TextResource pmdConfig

  BackCodeStyleConfig(
      final TextResource checkstyleConfig,
      final TextResource checkstyleSuppressionConfig,
      final TextResource pmdConfig) {
    this.checkstyleConfig = checkstyleConfig
    this.checkstyleSuppressionConfig = checkstyleSuppressionConfig
    this.pmdConfig = pmdConfig
  }

  File getCheckstyleConfigFile() { checkstyleConfig.asFile() }

  File getCheckstyleSuppressionConfigFile() { checkstyleSuppressionConfig.asFile() }

  File getPmdConfigFile() { pmdConfig.asFile() }

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
}
