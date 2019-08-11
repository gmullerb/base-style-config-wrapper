//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.quality.code.config.JavaCodeStyleConfig
import all.shared.gradle.quality.code.config.CommonCodeStyleConfig
import all.shared.gradle.quality.code.config.FrontCodeStyleConfig

import groovy.transform.CompileStatic

import org.gradle.api.artifacts.Configuration
import org.gradle.api.resources.TextResourceFactory

@CompileStatic
class BaseStyleConfigWrapperExtension {
  final JavaCodeStyleConfig java
  final CommonCodeStyleConfig common
  final FrontCodeStyleConfig js

  boolean autoComplement = true

  protected BaseStyleConfigWrapperExtension(
      final CommonCodeStyleConfig common,
      final JavaCodeStyleConfig java,
      final FrontCodeStyleConfig js) {
    this.java = java
    this.common = common
    this.js = js
  }

  static final BaseStyleConfigWrapperExtension of(
      final TextResourceFactory factory,
      final Configuration configuration) {
    new BaseStyleConfigWrapperExtension(
      new CommonCodeStyleConfig(
        factory.fromArchiveEntry(configuration, 'common/common-checks.xml')),
      new JavaCodeStyleConfig(
        factory.fromArchiveEntry(configuration, 'java/coding-checks.xml'),
        factory.fromArchiveEntry(configuration, 'java/checks-suppressions.xml'),
        factory.fromArchiveEntry(configuration, 'java/coding-rules.xml'),
        factory.fromArchiveEntry(configuration, 'groovy/groovy-rules.groovy')),
      FrontCodeStyleConfig.of(
        factory.fromArchiveEntry(configuration, 'js/.eslintrc.json'),
        factory.fromArchiveEntry(configuration, 'js/.typescript-eslintrc.json')))
  }
}
