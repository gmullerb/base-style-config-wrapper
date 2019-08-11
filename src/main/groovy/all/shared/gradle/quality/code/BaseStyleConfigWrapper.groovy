//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import groovy.transform.CompileStatic
import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.NodeChild

import java.nio.file.Path

import org.gradle.api.Project

@CompileStatic
class BaseStyleConfigWrapper {
  private BaseStyleConfigWrapper() { }

  static final BaseStyleConfigWrapperExtension addExtension(final Project project, final String extensionName,
      final String qualityVersionProperty, final String qualityUri) {
    if (project.extensions.findByName(extensionName) == null) {
      final String qualityVersion = project.findProperty(qualityVersionProperty) ?: findConfigVersion(qualityUri)
      if (qualityVersion) {
        final Path configPath = project.buildDir.toPath()
          .resolve("$extensionName/$qualityVersion")
        BaseStyleConfigWrapperExtension extension
        try  {
          extension = BaseStyleConfigWrapperExtension.of(project.resources.text, configPath)
          project.logger.debug('Added base-style-config-wrapper extension from Local')
        }
        catch (any) {
          extension = BaseStyleConfigWrapperExtension.of(project.resources.text, composeConfigUrl(qualityUri, qualityVersion))
          extension.save(configPath)
          project.logger.debug('Added base-style-config-wrapper extension from Web')
        }
        project.extensions.add(extensionName, extension)
        return extension
      }
      project.logger.error("Couldn't detect base-style-config version, set $qualityVersionProperty property")
    }
    project.logger.error('Couldn\'t add base-style-config-wrapper extension')
  }

  protected static final String findConfigVersion(final String qualityUri) {
    try {
      final GPathResult metadataNode = new XmlSlurper()
        .parse("$qualityUri/maven-metadata.xml")
      final GPathResult versionNode = metadataNode.children().find { NodeChild node ->
        node.name() == 'version'
      }
      return versionNode.empty
        ? null
        : versionNode.text()
    }
    catch (any) {
      return null
    }
  }

  protected static final String composeConfigUrl(final String qualityUri, final String qualityVersion) {
    return "$qualityUri/$qualityVersion/base-style-config-${qualityVersion}.jar"
  }
}
