# Base Style Configuration Wrapper

[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](/LICENSE.txt) 
[![Download](https://api.bintray.com/packages/gmullerb/all.shared.gradle/base-style-config-wrapper/images/download.svg)](https://bintray.com/gmullerb/all.shared.gradle/base-style-config-wrapper/_latestVersion) 
[![coverage report](https://gitlab.com/gmullerb/base-style-config-wrapper/badges/master/coverage.svg)](https://gitlab.com/gmullerb/base-style-config-wrapper/commits/master)

**A Gradle plugin that offers a wrapper for [base-style-config](https://github.com/gmullerb/base-style-config) configurations**.

This project is licensed under the terms of the [MIT license](/LICENSE.txt).
__________________

## Quick Start

1 . Apply the plugin:

```gradle
 plugins {
   id 'all.shared.gradle.base-style-config-wrapper' version '2.0.0'
 }
```

2 . Set the [base-style-config](https://github.com/gmullerb/base-style-config) version:

`gradle.properties`:

```properties
 BASE_STYLE_CONFIG_VERSION=2.0.1
```

3 . Provide the versions of Checkstyle, PMD and Codenarc:

* `base-style-config-wrapper` will be automatically configure Checkstyle, PMD and Codenarc plugins if they are applied to the project:

`gradle.properties`:

```properties
  CHECKSTYLE_VERSION=8.18
  PMD_VERSION=6.13.0
  CODENARC_VERSION=1.3

```

4 . Optionally, configure Checkstyle, PMD, Codenarc, ESlint and/or Stylelint tasks individually:

Checkstyle/PMD/Codenarc:

`build.gradle`:

```gradle
  task someCheckStyleTask (type: Checkstyle) {
    config = baseStyleConfig.java.checkstyleConfig
    ..
  }
```

ESlint:

`build.gradle`:

```gradle
  task assessSomeESLint(type: NpmTask) {
    args = ['run', 'someESlintTask', baseStyleConfig.js.eslintNpmConfigArg]
  }
```

and

`package.json`:

```json
  "scripts": {
    "someESlintTask": "eslint --config ${npm_config_eslintConfigFile}",
  },
```

or Typescript ESlint:

`build.gradle`:

```gradle
  task assessSomeESLint(type: NpmTask) {
    args = ['run', 'someESlintTask', baseStyleConfig.js.tsEslintNpmConfigArg]
  }
```

and

`package.json`:

```json
  "scripts": {
    "someESlintTask": "eslint --config ${npm_config_tsEslintConfigFile}",
  },
```

5 . Jump to [Features](#Features), for customization or digging on How it works.
__________________

## Goals

* Have a wrapper for [base-style-config](https://github.com/gmullerb/base-style-config) configurations in order to make a quick and easy use of it in Gradle.
* Automatically configure Checkstyle, PMD and Codenarc plugins if they are applied to the project.
  * Setting Tool version.
  * Setting Rules to use with tool.

## Features

1 . Complements the Gradle project with **`baseStyleConfig`** extension that provides the following fields:

* `common` with the following:
  * `checkstyleConfig`: a value which wraps [Common Checkstyle's set - common-checks.xml](https://github.com/gmullerb/base-style-config/tree/master/src/config/common/common-checks.xml).
  * `checkstyleConfigFile`: a value which point to the file of `checkstyleConfig`.
* `back` with the following:
  * `checkstyleConfig`: a value which wraps [Checkstyle's set - coding-checks.xml](https://github.com/gmullerb/base-style-config/tree/master/src/config/java/coding-checks.xml).
  * `checkstyleConfigFile`: a value which point to the file of `checkstyleConfig`.
  * `checkstyleSuppressionConfig`: a value which wraps [Checkstyle's suppressions - checks-suppressions.xml](https://github.com/gmullerb/base-style-config/tree/master/src/config/java/checks-suppressions.xml).
  * `checkstyleSuppressionConfigFile`: a value which point to the file of `checkstyleSuppressionConfig`.
  * `pmdConfig`: value which wraps [PMD's set - coding-rules.xml](https://github.com/gmullerb/base-style-config/tree/master/src/config/java/coding-rules.xml).
  * `pmdConfigFile`: a value which point to the file of `pmdConfig`.
  * `complement(checkstyleExtension)`, complements the specified [Checkstyle's extension](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.CheckstyleExtension.html):
    * Sets the `config` property to [Checkstyle's set - coding-checks.xml](https://github.com/gmullerb/base-style-config/tree/master/src/config/java/coding-checks.xml).
    * Adds `suppressionFile` property to the `configProperties` pointing to [Checkstyle's suppressions - checks-suppressions.xml](https://github.com/gmullerb/base-style-config/tree/master/src/config/java/checks-suppressions.xml).
  * `complement(checkstyleTask)`, complements the specified [Checkstyle's task](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.Checkstyle.html):
    * Sets the `config` property to [Checkstyle's set - coding-checks.xml](https://github.com/gmullerb/base-style-config/tree/master/src/config/java/coding-checks.xml).
    * Adds `suppressionFile` property to the `configProperties` pointing to [Checkstyle's suppressions - checks-suppressions.xml](https://github.com/gmullerb/base-style-config/tree/master/src/config/java/checks-suppressions.xml).
  * `complement(pmdExtension)`, complements the specified [PMD's extension](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.PmdExtension.html):
    * Sets the `ruleSetConfig` property to [PMD's set - coding-rules.xml](https://github.com/gmullerb/base-style-config/tree/master/src/config/java/coding-rules.xml).
    * Sets the `ruleSets` property to an empty `Set` (required with new PMD version).
  * `complement(pmdTask)`, complements the specified [PMD's task](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.Pmd.html):
    * Sets the `ruleSetConfig` property to [PMD's set - coding-rules.xml](https://github.com/gmullerb/base-style-config/tree/master/src/config/java/coding-rules.xml).
    * Sets the `ruleSets` property to an empty `Set` (required with new PMD version).
  * `codenarcConfig`: a value which wraps [Codenarc's set - gradle-rules.groovy](https://github.com/gmullerb/base-style-config/tree/master/src/config/groovy/groovy-rules.groovy).
  * `codenarcConfigFile`: a value which point to the file of `codenarcConfig`.
  * `complement(codenarcExtension)`: sets the `config` property of the specified [Codenarc's extension](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.CodeNarcExtension.html) to point to [Codenarc's set - gradle-rules.groovy](https://github.com/gmullerb/base-style-config/tree/master/src/config/groovy/groovy-rules.groovy).
  * `complement(codenarcTask)`: sets the `config` property of the specified [Codenarc's task](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.CodeNarc.html) to point to [Codenarc's set - gradle-rules.groovy](https://github.com/gmullerb/base-style-config/tree/master/src/config/groovy/groovy-rules.groovy).
* `front` with the following values:
  * `eslintConfig`: a value which wraps [ESLint's set - .eslintrc.json](https://github.com/gmullerb/base-style-config/tree/master/src/config/js/.eslintrc.json).
  * `eslintConfigFile`: value which point to the file of `eslintConfig`.
  * `eslintNpmConfigArg`: an array that can be use directly with [`NpmTask`](https://github.com/srs/gradle-node-plugin) `args`, it contains a string, `"--eslintConfigFile=eslintConfigFile"`, which points to `eslintConfigFile`.
  * `tsEslintConfig`: a value which wraps [ESLint's Typescript set - .typescript-eslintrc.json](https://github.com/gmullerb/base-style-config/tree/master/src/config/js/.typescript-eslintrc.json).
  * `tsEslintConfigFile`: value which point to the file of `tsEslintConfig`.
  * `tsEslintNpmConfigArg`: an array that can be use directly with [`NpmTask`](https://github.com/srs/gradle-node-plugin) `args`, it contains a string, `"--tsEslintConfigFile=tsEslintConfigFile"`, which points to `tsEslintConfigFile`.
* `autoComplement`: indicates if plugins Checkstyle, PMD and Codenarc plugins should be automatically complemented with [Base Style rules](https://github.com/gmullerb/base-style-config).

2 . Additionally, It allows to configure the with BASE_STYLE_CONFIG_VERSION:

Use `BASE_STYLE_CONFIG_VERSION` to set the [Base Style Configuration](https://github.com/gmullerb/base-style-config) version to use:

* If not set last version will be used.

`gradle.properties`:

```properties
 BASE_STYLE_CONFIG_VERSION=2.0.1
```

3 . And allows to configure versions of Checkstyle, PMD and Codenarc plugins using `CHECKSTYLE_VERSION`, `PMD_VERSION` and `CODENARC_VERSION` properties, respectively:

`gradle.properties`:

```properties
  CHECKSTYLE_VERSION=8.18
  PMD_VERSION=6.13.0
  CODENARC_VERSION=1.3

```
* If not set, Versions defined by Gradle will be used.
* At the present, should be set in order to be compatible with the set of rules defined by [Base Style Configuration](https://github.com/gmullerb/base-style-config)
__________________

## Using/Configuration

### Prerequisites

* None

### Gradle configuration

Apply the plugin [1]:

```gradle
 plugins {
   id 'all.shared.gradle.base-style-config-wrapper' version '2.0.0'
 }
```

> [1] Alternatively, use the [project-style-checker Gradle plugin](https://github.com/gmullerb/project-style-checker), which wraps this plugin.

### Common checkstyle configuration

1 . Configure plugin:

```gradle
  checkstyle {
    config = baseStyleConfig.common.checkstyleConfig
  }
```

or

```gradle
  task someCheckStyleTask (type: Checkstyle) {
    config = baseStyleConfig.common.checkstyleConfig
    ..
  }
```

2 . Define a Checkstyle task to check "all" files in the project tree, e.g.: [file-lister project - build.gradle file - assessCommon task](https://github.com/gmullerb/file-lister/blob/master/build.gradle).

### Java

#### Checkstyle/PMD plugin

This automatically done by `base-style-config-wrapper` if `autoComplement` is set to `true`(which is the default), if not:

```gradle
  checkstyle {
    config = baseStyleConfig.java.checkstyleConfig
    configProperties.suppressionFile = baseStyleConfig.java.checkstyleSuppressionConfigFile.path
  }

  pmd {
    ruleSets = [] // required with new PMD version
    ruleSetConfig = baseStyleConfig.java.pmdConfig
  }
```

  or (short way, exactly the same):

```gradle
  baseStyleConfig.java.complement(checkstyle)
  baseStyleConfig.java.complement(pmd)
```

#### Checkstyle/PMD task

```gradle
  checkstyleMain {
    config = baseStyleConfig.java.checkstyleConfig
    configProperties.suppressionFile = baseStyleConfig.java.checkstyleSuppressionConfigFile.path
  }

  pmdMain {
    ruleSets = [] // required with new PMD version
    ruleSetConfig = baseStyleConfig.java.pmdConfig
  }
```

  or (short way, exactly the same):

```gradle
  baseStyleConfig.java.complement(checkstyleMain)
  baseStyleConfig.java.complement(pmdMain)
```

> If using both, PMD should be run after Checkstyle, since Checkstyle is "lighter".  
> A complete example in [basecode project - back project](https://github.com/gmullerb/basecode/tree/master/back).

### Groovy

#### Codenarc plugin

This automatically done by `base-style-config-wrapper` if `autoComplement` is set to `true`(which is the default), if not:

```gradle
  codenarc {
    config = baseStyleConfig.groovy.codenarcConfig
  }
```

  or (short way, exactly the same):

```groovy
  baseStyleConfig.groovy.complement(codenarc)
```

#### Codenarc task

```gradle
  codenarcMain {
    config = baseStyleConfig.groovy.codenarcConfig
  }
```

  or (short way, exactly the same):

```gradle
  baseStyleConfig.groovy.complement(codenarcMain)
```

> Gradle and Groovy have almost the same set of rules, define in the same file, see [gradle-rules.groovy](https://github.com/gmullerb/base-style-config/blob/master/src/config/groovy/groovy-rules.groovy).  
> A complete example in [file-lister project - build.gradle file](https://github.com/gmullerb/file-lister/blob/master/build.gradle).

### Js configuration

ESLint:

1 . Add a config parameter to the respective ESLint script task, e.g.:
- eslint script task: `someEslintTask`.
- config parameter: `eslintConfigFile`

```json
  "scripts": {
    "someESlintTask": "eslint --config ${npm_config_eslintConfigFile} ..",
  },
```

2 . Set the config parameter in the respective gradle NpmTask task:

```gradle
  task assessSomeESLint(type: NpmTask) {
    // NpmTask task settings
    args = ['run', 'someESlintTask', baseStyleConfig.js.eslintNpmConfigArg]
    // gradle task settings
    inputs.property('configFile', baseStyleConfig.js.eslintNpmConfigArg)
    inputs.files fileLister.obtainFullFileTree("$MAIN_FOLDER", [includes: ['*.js', '*.mjs']])
  }
```

Same for Typescript ESLint:

`package.json`:

```json
  "scripts": {
    "someEslintTask": "eslint --config ${npm_config_tsEslintConfigFile} ..",
  },
```

`build.gradle`:

```gradle
  task assessSomeEslint(type: NpmTask) {
    // NpmTask task settings
    args = ['run', 'someEslintTask', baseStyleConfig.js.tsEslintNpmConfigArg]
    // gradle task settings
    inputs.property('configFile', baseStyleConfig.js.tsEslintNpmConfigArg)
    inputs.files fileLister.obtainFullFileTree("$MAIN_FOLDER", [includes: ['*.ts']])
  }
```

> A complete example in [basecode project - front project](https://github.com/gmullerb/basecode/tree/master/front).

### Gradle configuration

1 . Configure plugin:

```gradle
  codenarc {
    config = baseStyleConfig.groovy.codenarcConfig
  }
```

2 . Define a Codenarc task to check all gradle files in the project tree, e.g.: [file-lister project - build.gradle file - assessGradle task](https://github.com/gmullerb/file-lister/blob/master/build.gradle).

### Code Quality Tools - Version Compatibility

3 . Use `BASE_STYLE_CONFIG_VERSION` to establish the version of [Base Style Configuration](https://github.com/gmullerb/base-style-config) to be used:

* If not set, then last version will be used.

[`gradle.properties`](gradle.properties):

```properties
 BASE_STYLE_CONFIG_VERSION=2.0.1
```

4 . Use `CHECKSTYLE_VERSION` to establish the version of [Checkstyle](http://checkstyle.sourceforge.net) to be used:

* If not set, Gradle's default version will be used.
  * But, should be set in order to be compatible with the selected, `BASE_STYLE_CONFIG_VERSION`, i.e. with the selected set of rules defined by [Base Style Configuration](https://github.com/gmullerb/base-style-config).

[`gradle.properties`](gradle.properties):

```properties
 CHECKSTYLE_VERSION=8.18
```

5 . Use `PMD_VERSION` to establish the version of [PMD]() to be used:

* If not set, Gradle's default version will be used.
  * But, should be set in order to be compatible with the selected, `BASE_STYLE_CONFIG_VERSION`, i.e. with the selected set of rules defined by [Base Style Configuration](https://github.com/gmullerb/base-style-config).

[`gradle.properties`](gradle.properties):

```properties
 CHECKSTYLE_VERSION=8.18
```

6 . Use `CODENARC_VERSION` to establish the version of [CodeNarc](http://codenarc.sourceforge.net) to be used:

* If not set, Gradle's default version will be used.
  * But, should be set in order to be compatible with the selected, `BASE_STYLE_CONFIG_VERSION`, i.e. with the selected set of rules defined by [Base Style Configuration](https://github.com/gmullerb/base-style-config).

[`gradle.properties`](gradle.properties):

```properties
 CODENARC_VERSION=1.3
```

### Base Style Config - Version Compatibility

[Versions Compatibility Table](VERSIONS_COMPATIBILITY.md)
__________________

## Extending/Developing

### Prerequisites

* [Java](http://www.oracle.com/technetwork/java/javase/downloads).
* [Git](https://git-scm.com/downloads) (only if you are going to clone the project).

### Getting it

Clone or download the project[1], in the desired folder execute:

```sh
git clone https://github.com/gmullerb/base-style-config-wrapper
```

> [1] [Cloning a repository](https://help.github.com/articles/cloning-a-repository/)

### Set up

* **No need**, only download and run (It's Gradle! Yes!).

#### Make it your own

  1. Remove the Git Origin: `git remote remove origin`.
  2. Add your Git origin: `git remote add origin https://gitlab.com/yourUser/yourRepo.git`.
  3. Remove the License for 'All rights reserved' projects, or Modify the License for your needs.
  4. Change the `all.shared.gradle.qrc-file-generator.properties` file name to your plugin Id, e.g. `some.pluginId.properties`.

### Building it

* To build it:
  * `gradlew`: this will run default task, or
  * `gradlew build`.

* To assess files:
  * `gradlew assessCommon`: will check common style of files.
  * `gradlew assessGradle`: will check code style of Gradle's.
  * `gradlew codenarcMain`: will check code style of Groovy's source files.
  * `gradlew codenarcTest`: will check code style of Groovy's test files.
  * `assemble` task depends on these four tasks.

* To test code: `gradlew test`
  * This task is finalized with a Jacoco Report.

* To get all the tasks for the project: `gradlew tasks --all`

### Folders structure

```
  /src
    /main
      /groovy
    /test
      /groovy
```

- `src/main/groovy`: Source code files.
  - [`BaseStyleConfigWrapper`](src/main/groovy/all/shared/gradle/quality/code/BaseStyleConfigWrapper.groovy) is where all the magic of wrapping [base-style-config](https://github.com/gmullerb/base-style-config) happens.
  - [`BaseStyleConfigWrapperBackComplementer`](src/main/groovy/all/shared/gradle/quality/code/complement/BaseStyleConfigWrapperBackComplementer.groovy) is where all the magic for auto complementation of plugins happens.
- `src/test/groovy`: Test code files[1].

> [1] Tests are done with [JUnit](http://junit.org) and [Mockito](http://javadoc.io/page/org.mockito/mockito-core/latest/org/mockito/Mockito.html).

### Convention over Configuration

All `all.shared.gradle` plugins define:

* _PluginName_**Plugin**: which contains the class implements `Plugin` interface.
* _PluginName_**Extension**: which represent the extension of the plugin.
* If Tasks are define, then their names will be _TaskName_**Task**.
* If Actions are define, then their names will be _ActionName_**Action**.

All `all.shared.gradle` plugins have two **`static`** members:

* `String EXTENSION_NAME`: This will have the name of the extension that the plugin add.
  * if the plugin does not add an extension the this field will not exist.

* `String TASK_NAME`: This will have the name of the **unique** task that the plugin add.
  * if the plugin does not add a task or add more than one task, then this field will not exist.

* `boolean complement(final ..)`: will apply the plugin and return true if successful, false otherwise.
  * this methods is **exactly equivalent to the instance `apply` method**, but without instantiate the class if not required.

Both may be useful when applying the plugin when creating custom plugins.

All `all.shared.gradle` plugins "silently" fail when the extension can not be added.

## Documentation

* [`CHANGELOG.md`](CHANGELOG.md): add information of notable changes for each version here, chronologically ordered [1].

> [1] [Keep a Changelog](http://keepachangelog.com)

## License

[MIT License](/LICENSE.txt)
__________________

## Remember

* Use code style verification tools => Encourages Best Practices, Efficiency, Readability and Learnability.
* Start testing early => Encourages Reliability and Maintainability.
* Code Review everything => Encourages Functional suitability, Performance Efficiency and Teamwork.

## Additional words

Don't forget:

* **Love what you do**.
* **Learn everyday**.
* **Learn yourself**.
* **Share your knowledge**.
* **Learn from the past, dream on the future, live and enjoy the present to the max!**.

At life:

* Let's act, not complain.
* Be flexible.

At work:

* Let's give solutions, not questions.
* Aim to simplicity not intellectualism.
