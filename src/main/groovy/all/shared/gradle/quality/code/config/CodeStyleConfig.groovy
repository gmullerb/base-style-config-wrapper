//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.config

import groovy.transform.CompileStatic

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.resources.TextResourceFactory

@CompileStatic
class CodeStyleConfig {
  public static final String CODE_STYLE_EXTENSION = 'baseStyleConfig'

  private final BackCodeStyleConfig back
  private final CommonCodeStyleConfig common
  private final FrontCodeStyleConfig front

  private CodeStyleConfig(
      final CommonCodeStyleConfig common,
      final BackCodeStyleConfig back,
      final FrontCodeStyleConfig front) {
    this.back = back
    this.common = common
    this.front = front
  }

  static final CodeStyleConfig of(final Project project) {
    final TextResourceFactory factory = project.resources.text
    final Configuration configuration = project.configurations.getByName(CODE_STYLE_EXTENSION)
    new CodeStyleConfig(
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
