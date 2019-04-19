//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.resources.TextResource

@CompileStatic
class FrontCodeStyleConfig {
  final TextResource eslintConfig
  final String eslintNpmConfigArg
  final TextResource stylelintConfig
  final String stylelintNpmConfigArg

  protected FrontCodeStyleConfig(
      final TextResource eslintConfig,
      final String eslintNpmConfigArg,
      final TextResource stylelintConfig,
      final String stylelintNpmConfigArg) {
    this.eslintConfig = eslintConfig
    this.eslintNpmConfigArg = eslintNpmConfigArg
    this.stylelintConfig = stylelintConfig
    this.stylelintNpmConfigArg = stylelintNpmConfigArg
  }

  static final FrontCodeStyleConfig of(
      final TextResource eslintConfig,
      final TextResource stylelintConfig) {
    new FrontCodeStyleConfig(
      eslintConfig,
      "--eslintConfigFile=${eslintConfig.asFile().path}",
      stylelintConfig,
      "--stylelintConfigFile=${stylelintConfig.asFile().path}")
  }

  File getEslintConfigFile() { eslintConfig.asFile() }

  File getStylelintConfigFile() { stylelintConfig.asFile() }
}
