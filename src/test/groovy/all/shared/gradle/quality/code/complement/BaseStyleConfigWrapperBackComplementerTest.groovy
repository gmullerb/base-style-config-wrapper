//  Copyright (c) 2019 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt
package all.shared.gradle.quality.code.complement

import all.shared.gradle.testfixtures.SpyProjectFactory

import groovy.transform.CompileStatic

import org.gradle.api.Project
import org.gradle.api.plugins.quality.CodeQualityExtension

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull

import static org.mockito.Matchers.any
import static org.mockito.Matchers.anyString
import static org.mockito.Matchers.eq
import static org.mockito.Mockito.never
import static org.mockito.Mockito.verify

@CompileStatic
final class BaseStyleConfigWrapperBackComplementerTest {
  private final Project spyProject = SpyProjectFactory.build()

  @Test
  void shouldEstablishVersion() {
    final BaseStyleConfigWrapperBackComplementer complementer = new BaseStyleConfigWrapperBackComplementer(spyProject, false)
    spyProject.extensions.add('versionProperty', '1.0')
    final CodeQualityExtension codeQualityExtension = new CodeQualityExtension() { }

    complementer.establishCodeQualityToolVersion(codeQualityExtension, 'versionProperty')

    assertEquals('1.0', codeQualityExtension.toolVersion)
    verify(spyProject.logger)
      .debug(eq('base-style-config-wrapper set {} to {} version'), eq(codeQualityExtension), eq('1.0'))
  }

  @Test
  void shouldNotEstablishVersionWhenPropertyNotfound() {
    final BaseStyleConfigWrapperBackComplementer complementer = new BaseStyleConfigWrapperBackComplementer(spyProject, false)
    final CodeQualityExtension codeQualityExtension = new CodeQualityExtension() { }

    complementer.establishCodeQualityToolVersion(codeQualityExtension, 'versionProperty')

    assertNull(codeQualityExtension.toolVersion)
    verify(spyProject.logger, never())
      .debug(anyString(), any(CodeQualityExtension), anyString())
  }
}
