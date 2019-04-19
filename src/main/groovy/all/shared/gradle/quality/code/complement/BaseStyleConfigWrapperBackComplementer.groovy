//  Copyright (c) 2019 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.complement

import groovy.transform.CompileStatic

import java.util.function.Consumer

import org.gradle.api.Project
import org.gradle.api.plugins.quality.CodeQualityExtension

@CompileStatic
class BaseStyleConfigWrapperBackComplementer {
  private final Project project
  private final boolean autoComplement

  BaseStyleConfigWrapperBackComplementer(
      final Project project,
      final boolean autoComplement) {
    this.project = project
    this.autoComplement = autoComplement
  }

  protected final void establishCodeQualityToolVersion(
      final CodeQualityExtension codeQualityExtension,
      final String versionProperty) {
    final String version = project.findProperty(versionProperty)
    if (version) {
      codeQualityExtension.toolVersion = version
      project.logger.debug('base-style-config-wrapper set {} to {} version', codeQualityExtension, version)
    }
  }

  public void complementCodeQualityTool(
      final String codeQualityExtensionName,
      final String versionProperty,
      final Consumer<CodeQualityExtension> complementer) {
    final CodeQualityExtension codeQualityExtension = (CodeQualityExtension) project.extensions.findByName(codeQualityExtensionName)
    if (codeQualityExtension) {
      establishCodeQualityToolVersion(codeQualityExtension, versionProperty)
      if (autoComplement) {
        complementer.accept(codeQualityExtension)
      }
    }
  }
}
