//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.resources.TextResource

@CompileStatic
class FrontCodeStyleConfig {
  final TextResource eslintConfig
  final String eslintNpmConfigArg
  final TextResource tsEslintConfig
  final String tsEslintNpmConfigArg

  protected FrontCodeStyleConfig(
      final TextResource eslintConfig,
      final String eslintNpmConfigArg,
      final TextResource tsEslintConfig,
      final String tsEslintNpmConfigArg) {
    this.eslintConfig = eslintConfig
    this.eslintNpmConfigArg = eslintNpmConfigArg
    this.tsEslintConfig = tsEslintConfig
    this.tsEslintNpmConfigArg = tsEslintNpmConfigArg
  }

  static final FrontCodeStyleConfig of(
      final TextResource eslintConfig,
      final TextResource tsEslintConfig) {
    new FrontCodeStyleConfig(
      eslintConfig,
      "--eslintConfigFile=${eslintConfig.asFile().path}",
      tsEslintConfig,
      "--tsEslintConfigFile=${tsEslintConfig.asFile().path}")
  }

  File getEslintConfigFile() { eslintConfig.asFile() }

  File getTsEslintConfigFile() { tsEslintConfig.asFile() }
}
