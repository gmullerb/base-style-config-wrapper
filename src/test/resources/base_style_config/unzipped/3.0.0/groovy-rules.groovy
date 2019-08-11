//  Copyright (c) 2018 Gonzalo Müller Bravo.
//  Licensed under the MIT License (MIT), see LICENSE.txt

final GRADLE_FILES = '*.gradle'
final TEST_FILES = '*Test.*'

ruleset {
  description 'Gradle code rules'

  ruleset('rulesets/basic.xml')

  ruleset('rulesets/braces.xml')

  ruleset('rulesets/convention.xml') {
    CompileStatic(enabled: false)
    CouldBeElvis {
      priority = 0
    }
    MethodParameterTypeRequired {
      doNotApplyToFileNames = GRADLE_FILES
    }
    NoDef {
      doNotApplyToFileNames = GRADLE_FILES
    }
    VariableTypeRequired {
      doNotApplyToFileNames = GRADLE_FILES
    }
    InvertedCondition(enabled: false)
    PublicMethodsBeforeNonPublicMethods(enabled: false)
    StaticMethodsBeforeInstanceMethods(enabled: false)
    TrailingComma(enabled: false)
  }

  ruleset('rulesets/design.xml') {
    PublicInstanceField(enabled: false)
    Instanceof  {
      priority = 0
    }
  }

  ruleset('rulesets/dry.xml') {
    DuplicateNumberLiteral {
      priority = 0
    }
    DuplicateStringLiteral {
      priority = 0
    }
  }

  ruleset('rulesets/formatting.xml') {
    BracesForIfElse {
      validateElse = true
      elseOnSameLineAsClosingBrace = false
    }
    Indentation {
      spacesPerIndentLevel = 2
      doNotApplyToFileNames = GRADLE_FILES
    }
    LineLength {
      length = 144
      priority = 3
    }
    SpaceAfterOpeningBrace {
      ignoreEmptyBlock = true
    }
    SpaceAroundMapEntryColon {
      characterAfterColonRegex = /\s/
    }
    SpaceBeforeClosingBrace {
      ignoreEmptyBlock = true
    }
    ClassEndsWithBlankLine(enabled: false)
    ClassStartsWithBlankLine(enabled: false)
    SpaceAroundOperator(enabled: false)
  }

  ruleset('rulesets/groovyism.xml') {
    GStringExpressionWithinString  {
      priority = 0
    }
  }

  ruleset('rulesets/imports.xml') {
    MisorderedStaticImports {
      comesBefore = false
    }
  }

  ruleset('rulesets/logging.xml')

  ruleset('rulesets/naming.xml') {
    FieldName {
      regex = '[a-z]([A-Z]?[a-z0-9]+)*[A-Z]?'
      staticFinalRegex = '([A-Z](_?[A-Z0-9]+)*)|([a-z]([A-Z]?[a-z0-9]+)*[A-Z]?)'
    }
    FactoryMethodName(enabled: false)
    VariableName(enabled: false)
  }

  ruleset('rulesets/security.xml') {
    UnsafeArrayDeclaration(enabled: false)
    JavaIoPackageAccess(enabled: false)
    FileCreateTempFile(enabled: false)
  }

  ruleset('rulesets/size.xml') {
    ClassSize {
      maxLines = 300
      doNotApplyToFileNames = TEST_FILES
    }
    CyclomaticComplexity {
      maxMethodComplexity = 8
    }
    MethodSize {
      maxLines = 30
      doNotApplyToFileNames = TEST_FILES
    }
    ParameterCount {
      maxParameters = 5
    }
    AbcMetric(enabled: false)
    CrapMetric(enabled: false)
    MethodCount(enabled: false)
  }

  ruleset('rulesets/unnecessary.xml') {
    UnnecessaryGetter {
      doNotApplyToFileNames = TEST_FILES
    }
    UnnecessarySetter {
      doNotApplyToFileNames = TEST_FILES
    }
    UnnecessaryCast(enabled: false)
    UnnecessaryObjectReferences(enabled: false)
    UnnecessaryPublicModifier(enabled: false)
    UnnecessaryReturnKeyword(enabled: false)
  }

  ruleset('rulesets/unused.xml')

  IllegalRegex {
    // Codenarc rule settings
    name = 'CallOnlyOneMethodPerLineForChainedCall'
    description = 'No chained methods call allowed in same line'
    violationMessage = 'Call only one chained method per line: method1(...) NEWLINE .method2(...) NEWLINE .method3(...)'
    // IllegalRegex rule settings
    regex = /[^\(\v]*\(.*\)\h*\..*\(\V*/
  }

  IllegalRegex {
    // Codenarc rule settings
    name = 'NameOfTestsMustStartWithShould'
    description = 'Names of Test methods must begin with "should"'
    violationMessage = 'Name of the Test method must begin with "should"'
    applyToFileNames = TEST_FILES
    // IllegalRegex rule settings
    regex = /(@Test|@org\.junit\.jupiter\.api\.Test|@org\.junit\.Test|@org\.testng\.annotations\.Test)\s+((public|protected)\s+)?void(?!\s+should)/
  }

  IllegalRegex {
    // Codenarc rule settings
    name = 'SpaceBeforeOperator'
    description = 'Must set 1 space before operators'
    violationMessage = 'Set 1 space before operator'
    // IllegalRegex rule settings
    regex = /(?<=[\n\r]).((\h*[^"'\n\r\/\*]*(?<!\s)(\!\=|\=\=|\<\=\>|(?<![\!\=\+\-\/\*\&\|\>\<\^%])\=(?!\=)|\+\=|(?<!\+)\+(?![\=\+])|\-\=|(?<!\-)\-(?![\-\=\>])|(?<!\/)\*\=|(?<!\/)\*\*|(?<![\/\*])\*(?![\*\/\=])|%\=|%(?!\=)|\^\=|\^(?!\=)|\|\||\|\=|(?<!\|)\|(?![\|\=])|\&\&|\&\=|(?<![\.\&])\&(?![\&\=])|\?:))|([^\<"'\n\r\/\*]*(?<!\s)((?<!\-)(\>(?!\>)))))[^\n\r]*/
  }

  IllegalRegex {
    // Codenarc rule settings
    name = 'SpaceAfterOperator'
    description = 'Must set 1 space after operators'
    violationMessage = 'Set 1 space after operator'
    // IllegalRegex rule settings
    regex = /(?<=[\n\r]).\h*[^"'\n\r\/\*]*((\!\=|\=\=|\<\=\>|(?<![\!\=\+\-\/\*\&\|\>\<\^%])\=(?!\=)|\+\=|(?<!\+)\+(?![\=\+])|\-\=|(?<!\-)\-(?![\-\=\>])|(?<!\/)\*\=|(?<!\/)\*\*|(?<![\/\*])\*(?![\*\/\=\.:])|%\=|%(?!\=)|\^\=|\^(?!\=)|\|\||\|\=|(?<!\|)\|(?![\|\=])|\&\&|\&\=|(?<![\&\.])\&(?![\&\=])|\?:|\>\>\>|(?<!\>)\>\>(?!\>)|\<\<\<|(?<!\<)\<\<(?!\<))(?!\s)|\s(\<(?!\<))(?!\s)(?!.*\>))[^\n\r]*/
  }

  IllegalRegex {
    // Codenarc rule settings
    name = 'Exactly1SpaceBeforeOperator'
    description = 'Must set exactly 1 space before operators'
    violationMessage = 'Set exactly 1 space before operator'
    // IllegalRegex rule settings
    regex = /(?<=[\n\r]).((\h*[^"'\n\r\/\*]*(?<=\s\s)((?<=[\n\r])(\!\=|\=\=|\<\=\>|(?<![\!\=\+\-\/\*\&\|\>\<\^%])\=(?!\=)|\+\=|(?<!\+)\+(?![\=\+])|\-\=|(?<!\-)\-(?![\-\=\>])|(?<!\/)\*\=|(?<!\/)\*\*|%\=|%(?!\=)|\^\=|\^(?!\=)|\|\||\|\=|(?<!\|)\|(?![\|\=])|\&\&|\&\=|(?<![\.\&])\&(?![\&\=])|\?:)))|([^\<"'\n\r\/\*]*(?<=\s\s)\>(?!\>)))[^\n\r]*/
  }

  IllegalRegex {
    // Codenarc rule settings
    name = 'Exactly1SpaceAfterOperator'
    description = 'Must set exactly 1 space after operators'
    violationMessage = 'Set exactly 1 space after operator'
    // IllegalRegex rule settings
    regex = /(?<=[\n\r]).\h*[^"'\n\r\/\*]*(((\!\=|\=\=|\<\=\>|(?<![\!\=\+\-\/\*\&\|\>\<\^%])\=(?!\=)|\+\=|(?<!\+)\+(?![\=\+])|\-\=|(?<!\-)\-(?![\-\=\>])|(?<!\/)\*\=|(?<!\/)\*\*|%\=|%(?!\=)|\^\=|\^(?!\=)|\|\||\|\=|(?<!\|)\|(?![\|\=])|\&\&|\&\=|(?<![\&\.])\&(?![\&\=])|\?:|\>\>\>|(?<!\>)\>\>(?!\>)|\<\<\<|(?<!\<)\<\<(?!\<)))(?![\n\r])(?=\s\s)|\s(\<(?=\s=\s))(?!.*\>))[^\n\r]*/
  }

  IllegalRegex {
    // Codenarc rule settings
    name = 'UseMultilineTernaryOperator'
    description = 'Must expand ternary operator in multiple lines'
    violationMessage = 'Expand ternary operator in multiple lines: condition NEWLINE ? expression NEWLINE : expression'
    // IllegalRegex rule settings
    regex = /(?<=[\n\r]).\h*[^"'\n\r\/\*]*([^\s"']+?\h*?(?<!(\<|,|,\h))\?(?![:\>\.])|"[^"\n\r]*?"[^"\n\r]*?(?<!(\<|,|,\h))\?(?![:\>\.])|'[^'\n\r]*?'[^'\n\r]*?(?<!(\<|,|,\h))\?(?![:\>\.]))[^\n\r]*/
  }

  IllegalRegex {
    // Codenarc rule settings
    name = 'UseOnlyDoFamilyMethodsWhenMocking'
    description = 'Should use only "do*"\'s family methods when using Mockito: doAnswer, doCallRealMethod, doNothing, doReturn & doThrow'
    violationMessage = 'Use only "do*"\'s family methods when using Mockito: doAnswer, doCallRealMethod, doNothing, doReturn & doThrow'
    applyToFileNames = TEST_FILES
    // IllegalRegex rule settings
    regex = /Mockito\s*\.\s*when|org\.mockito\.BDDMockito/
  }

  IllegalRegex {
    // Codenarc rule settings
    name = 'UseOnlyMockOrSpyPrefixOnTestFiles'
    description = 'Should use the "mock" or "spy" prefixes only on test\'s files\' code'
    violationMessage = 'Use the "mock" or "spy" prefixes only on test\'s files\' code'
    doNotApplyToFileNames = "$GRADLE_FILES,$TEST_FILES"
    priority = 3
    // IllegalRegex rule settings
    regex = /\b(mock|spy)[A-Z0-9]/
  }

  RequiredRegex {
    // Codenarc rule settings
    name = 'AnnotateClassesWith@CompileStaticOr@TypeChecked'
    description = 'Must annotate Main class, interface or trait with @CompileStatic or @TypeChecked'
    violationMessage = 'Annotate Main class, interface or trait with @CompileStatic or @TypeChecked'
    doNotApplyToFileNames = GRADLE_FILES
    // RequiredRegex rule settings
    regex = /(((?<![\}\/]\h*)@CompileStatic|(?<![\}\/]\h*)@TypeChecked)\s+(public\s+)?(final\s+)?(class|interface|trait)) | (^(((?!(class|interface|trait)).)*)$)/
  }
}
