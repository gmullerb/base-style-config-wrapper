//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.quality.code.config.BackCodeStyleConfig
import all.shared.gradle.quality.code.config.CommonCodeStyleConfig
import all.shared.gradle.quality.code.config.FrontCodeStyleConfig

import groovy.transform.CompileStatic

import org.gradle.api.artifacts.Configuration
import org.gradle.api.resources.TextResourceFactory

@CompileStatic
class BaseStyleConfigWrapperExtension {
  private final BackCodeStyleConfig back
  private final CommonCodeStyleConfig common
  private final FrontCodeStyleConfig front

  private BaseStyleConfigWrapperExtension(
      final CommonCodeStyleConfig common,
      final BackCodeStyleConfig back,
      final FrontCodeStyleConfig front) {
    this.back = back
    this.common = common
    this.front = front
  }

  static final BaseStyleConfigWrapperExtension of(
      final TextResourceFactory factory,
      final Configuration configuration) {
    new BaseStyleConfigWrapperExtension(
      new CommonCodeStyleConfig(
        factory.fromArchiveEntry(configuration, 'common/common-checks.xml'),
        factory.fromArchiveEntry(configuration, 'gradle/gradle-rules.groovy')),
      new BackCodeStyleConfig(
        factory.fromArchiveEntry(configuration, 'back/coding-checks.xml'),
        factory.fromArchiveEntry(configuration, 'back/checks-suppressions.xml'),
        factory.fromArchiveEntry(configuration, 'back/coding-rules.xml')),
      FrontCodeStyleConfig.of(
        factory.fromArchiveEntry(configuration, 'front/.eslintrc.json'),
        factory.fromArchiveEntry(configuration, 'front/.stylelintrc.json')))
  }

  BackCodeStyleConfig getBack() { back }

  CommonCodeStyleConfig getCommon() { common }

  FrontCodeStyleConfig getFront() { front }
}
