//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code

import all.shared.gradle.quality.code.config.CommonCodeStyleConfig
import all.shared.gradle.quality.code.config.GroovyCodeStyleConfig
import all.shared.gradle.quality.code.config.JavaCodeStyleConfig
import all.shared.gradle.quality.code.config.JsCodeStyleConfig

import groovy.transform.CompileStatic

import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Path
import java.nio.file.StandardCopyOption

import org.gradle.api.resources.TextResource
import org.gradle.api.resources.TextResourceFactory

@CompileStatic
class BaseStyleConfigWrapperExtension {
  final CommonCodeStyleConfig common
  final GroovyCodeStyleConfig groovy
  final JavaCodeStyleConfig java
  final JsCodeStyleConfig js

  boolean autoComplement = true

  protected BaseStyleConfigWrapperExtension(
      final CommonCodeStyleConfig common,
      final JavaCodeStyleConfig java,
      final GroovyCodeStyleConfig groovy,
      final JsCodeStyleConfig js) {
    this.common = common
    this.java = java
    this.groovy = groovy
    this.js = js
  }

  public boolean save(final Path configPath) {
    try {
      final Path path = Files.createDirectories(configPath)
      copy(common.checkstyleConfig, path.resolve('common-checks.xml'))
      copy(groovy.codenarcConfig, path.resolve('groovy-rules.groovy'))
      copy(java.checkstyleConfig, path.resolve('coding-checks.xml'))
      copy(java.checkstyleSuppressionConfig, path.resolve('checks-suppressions.xml'))
      copy(java.pmdConfig, path.resolve('coding-rules.xml'))
      copy(js.eslintConfig, path.resolve('.eslintrc.json'))
      copy(js.tsEslintConfig, path.resolve('.typescript-eslintrc.json'))
      return true
    }
    catch (Exception e) {
      return false
    }
  }

  static final BaseStyleConfigWrapperExtension of(
      final TextResourceFactory factory,
      final String configuration) {
    new BaseStyleConfigWrapperExtension(
      new CommonCodeStyleConfig(
        factory.fromUri("jar:$configuration!/common/common-checks.xml")),
      new JavaCodeStyleConfig(
        factory.fromUri("jar:$configuration!/java/coding-checks.xml"),
        factory.fromUri("jar:$configuration!/java/checks-suppressions.xml"),
        factory.fromUri("jar:$configuration!/java/coding-rules.xml")),
      new GroovyCodeStyleConfig(
        factory.fromUri("jar:$configuration!/groovy/groovy-rules.groovy")),
      JsCodeStyleConfig.of(
        factory.fromUri("jar:$configuration!/js/.eslintrc.json"),
        factory.fromUri("jar:$configuration!/js/.typescript-eslintrc.json")))
  }

  static final BaseStyleConfigWrapperExtension of(
      final TextResourceFactory factory,
      final Path configPath) throws NoSuchFileException {
    new BaseStyleConfigWrapperExtension(
      new CommonCodeStyleConfig(
        factory.fromFile(findFile(configPath, 'common-checks.xml'))),
      new JavaCodeStyleConfig(
        factory.fromFile(findFile(configPath, 'coding-checks.xml')),
        factory.fromFile(findFile(configPath, 'checks-suppressions.xml')),
        factory.fromFile(findFile(configPath, 'coding-rules.xml'))),
      new GroovyCodeStyleConfig(
        factory.fromFile(findFile(configPath, 'groovy-rules.groovy'))),
      JsCodeStyleConfig.of(
        factory.fromFile(findFile(configPath, '.eslintrc.json')),
        factory.fromFile(findFile(configPath, '.typescript-eslintrc.json')))
    )
  }

  protected static final void copy(final TextResource from, final Path to) {
    final File file = from.asFile()
    Files.copy(from.asFile()
      .toPath(), to, StandardCopyOption.REPLACE_EXISTING)
    final File md5File = new File(to.toString() + '.md5')
    md5File.text = file.text.md5()
  }

  protected static final File findFile(
      final Path configPath,
      final String from) throws NoSuchFileException {
    final File file = configPath.resolve(from)
      .toFile()
    if (file.exists()) {
      final File md5File = new File(file.toString() + '.md5')
      if (md5File.exists() && file.text.md5() == md5File.text) {
        return file
      }
    }
    throw new NoSuchFileException(from)
  }
}
