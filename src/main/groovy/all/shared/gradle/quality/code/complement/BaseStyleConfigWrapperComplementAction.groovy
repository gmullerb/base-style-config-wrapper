//  Copyright (c) 2019 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.complement

import all.shared.gradle.quality.code.BaseStyleConfigWrapperExtension

import groovy.transform.CompileStatic

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CodeNarcExtension
import org.gradle.api.plugins.quality.PmdExtension

@CompileStatic
class BaseStyleConfigWrapperComplementAction implements Action<Project> {
  public static final String CHECKSTYLE_VERSION_PROPERTY = 'CHECKSTYLE_VERSION'
  public static final String PMD_VERSION_PROPERTY = 'PMD_VERSION'
  public static final String CODENARC_VERSION_PROPERTY = 'CODENARC_VERSION'

  private final BaseStyleConfigWrapperExtension extension

  BaseStyleConfigWrapperComplementAction(final BaseStyleConfigWrapperExtension extension) {
    this.extension = extension
  }

  void execute(final Project project) {
    final BaseStyleConfigWrapperBackComplementer complementer = new BaseStyleConfigWrapperBackComplementer(project, extension.autoComplement)
    complementer.complementCodeQualityTool(
      'checkstyle',
      CHECKSTYLE_VERSION_PROPERTY)
      { final CheckstyleExtension codeQualityExtension -> extension.java.complement(codeQualityExtension) }
    complementer.complementCodeQualityTool(
      'pmd',
      PMD_VERSION_PROPERTY)
      { final PmdExtension codeQualityExtension -> extension.java.complement(codeQualityExtension) }
    complementer.complementCodeQualityTool(
      'codenarc',
      CODENARC_VERSION_PROPERTY)
      { final CodeNarcExtension codeQualityExtension -> extension.groovy.complement(codeQualityExtension) }
  }
}
