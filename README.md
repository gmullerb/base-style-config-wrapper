# Base Style Configuration Wrapper

[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](/LICENSE.txt) [![Download](https://api.bintray.com/packages/gmullerb/all.shared.gradle/base-style-config-wrapper/images/download.svg?version=1.0.0)](https://bintray.com/gmullerb/all.shared.gradle/base-style-config-wrapper/1.0.0/link)

**This project offers a Gradle wrapper for [base-style-config](https://github.com/gmullerb/base-style-config) configurations**

This project is licensed under the terms of the [MIT license](/LICENSE.txt).

## Goals

Have a wrapper for [base-style-config](https://github.com/gmullerb/base-style-config) configurations in order to make a quick and easy use of it in Gradle.

## Features

Complements the Gradle project with **`baseStyleConfig`** property that provides the following fields:

* `common` with the following:
  * `checkstyleConfig`: a value which wraps [Common Checkstyle's set - common-checks.xml](https://github.com/gmullerb/base-style-config/tree/master/config/common/common-checks.xml).
  * `checkstyleConfigFile`: a value which point to the file of `checkstyleConfig`.
  * `codenarcConfig`: a value which wraps [Codenarc's set - gradle-rules.groovy](https://github.com/gmullerb/base-style-config/tree/master/config/gradle/gradle-rules.groovy).
  * `codenarcConfigFile`: a value which point to the file of `codenarcConfig`.
  * `complement(codenarcExtension)`: sets the `config` property of the specified [Codenarc's extension](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.CodeNarcExtension.html) to point to [Codenarc's set - gradle-rules.groovy](https://github.com/gmullerb/base-style-config/tree/master/config/gradle/gradle-rules.groovy).
  * `complement(codenarcTask)`: sets the `config` property of the specified [Codenarc's task](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.CodeNarc.html) to point to [Codenarc's set - gradle-rules.groovy](https://github.com/gmullerb/base-style-config/tree/master/config/gradle/gradle-rules.groovy).
* `back` with the following:
  * `checkstyleConfig`: a value which wraps [Checkstyle's set - coding-checks.xml](https://github.com/gmullerb/base-style-config/tree/master/config/back/coding-checks.xml).
  * `checkstyleConfigFile`: a value which point to the file of `checkstyleConfig`.
  * `checkstyleSuppressionConfig`: a value which wraps [Checkstyle's suppressions - checks-suppressions.xml](https://github.com/gmullerb/base-style-config/tree/master/config/back/checks-suppressions.xml).
  * `checkstyleSuppressionConfigFile`: a value which point to the file of `checkstyleSuppressionConfig`.
  * `pmdConfig`: value which wraps [PMD's set - coding-rules.xml](https://github.com/gmullerb/base-style-config/tree/master/config/back/coding-rules.xml).
  * `pmdConfigFile`: a value which point to the file of `pmdConfig`.
  * `complement(checkstyleExtension)`, complements the specified [Checkstyle's extension](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.CheckstyleExtension.html):
    * Sets the `config` property to [Checkstyle's set - coding-checks.xml](https://github.com/gmullerb/base-style-config/tree/master/config/back/coding-checks.xml).
    * Adds `suppressionFile` property to the `configProperties` pointing to [Checkstyle's suppressions - checks-suppressions.xml](https://github.com/gmullerb/base-style-config/tree/master/config/back/checks-suppressions.xml).
  * `complement(checkstyleTask)`, complements the specified [Checkstyle's task](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.Checkstyle.html):
    * Sets the `config` property to [Checkstyle's set - coding-checks.xml](https://github.com/gmullerb/base-style-config/tree/master/config/back/coding-checks.xml).
    * Adds `suppressionFile` property to the `configProperties` pointing to [Checkstyle's suppressions - checks-suppressions.xml](https://github.com/gmullerb/base-style-config/tree/master/config/back/checks-suppressions.xml).
  * `complement(pmdExtension)`, complements the specified [PMD's extension](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.PmdExtension.html):
    * Sets the `ruleSetConfig` property to [PMD's set - coding-rules.xml](https://github.com/gmullerb/base-style-config/tree/master/config/back/coding-rules.xml).
    * Sets the `ruleSets` property to an empty `Set` (required with new PMD version).
  * `complement(pmdTask)`, complements the specified [PMD's task](https://docs.gradle.org/current/dsl/org.gradle.api.plugins.quality.Pmd.html):
    * Sets the `ruleSetConfig` property to [PMD's set - coding-rules.xml](https://github.com/gmullerb/base-style-config/tree/master/config/back/coding-rules.xml).
    * Sets the `ruleSets` property to an empty `Set` (required with new PMD version).
* `front` with the following values:
  * `eslintConfig`: a value which wraps [ESLint's set - .eslintrc.json](https://github.com/gmullerb/base-style-config/tree/master/config/front/.eslintrc.json).
  * `eslintConfigFile`: value which point to the file of `eslintConfig`.
  * `eslintNpmConfigArg`: an array that can be use directly with [`NpmTask`](https://github.com/srs/gradle-node-plugin) `args`, it contains a string, `"--eslintConfigFile=eslintConfigFile"`, which points to `eslintConfigFile`.
  * `stylelintConfig`: a value which wraps [StyleLint's set - .stylelintrc.json](https://github.com/gmullerb/base-style-config/tree/master/config/front/.stylelintrc.json).
  * `stylelintConfigFile`: value which point to the file of `stylelintConfig`.
  * `stylelintNpmConfigArg`: an array that can be use directly with [`NpmTask`](https://github.com/srs/gradle-node-plugin) `args`, it contains a string, `"--stylelintConfigFile=stylelintConfigFile"`, which points to `stylelintConfigFile`.

## Using/Configuration

### Prerequisites

* None

### Gradle configuration

Apply the plugin:

```gradle
 plugins {
   id 'all.shared.gradle.base-style-config-wrapper' version '1.0.0'
 }
```

### Common checkstyle configuration

1. Configure plugin:

```gradle
  checkstyle {
    config = baseStyleConfig.common.checkstyleConfig
  }
```

2. Define a Checkstyle task to check "all" files in the project tree, e.g.: [file-lister project - build.gradle file - assessCommon task](https://github.com/gmullerb/file-lister/blob/master/build.gradle).

### Backend configuration

#### Java

##### Checkstyle/PMD plugin

```gradle
  checkstyle {
    config = baseStyleConfig.back.checkstyleConfig
    configProperties.suppressionFile = baseStyleConfig.back.checkstyleSuppressionConfigFile.path
  }

  pmd {
    ruleSets = [] // required with new PMD version
    ruleSetConfig = baseStyleConfig.back.pmdConfig
  }
```

```text
  or (short way, exactly the same):
```

```gradle
  baseStyleConfig.back.complement(checkstyle)
  baseStyleConfig.back.complement(pmd)
```

##### Checkstyle/PMD task

```gradle
  checkstyleMain {
    config = baseStyleConfig.back.checkstyleConfig
    configProperties.suppressionFile = baseStyleConfig.back.checkstyleSuppressionConfigFile.path
  }

  pmdMain {
    ruleSets = [] // required with new PMD version
    ruleSetConfig = baseStyleConfig.back.pmdConfig
  }
```

```text
  or (short way, exactly the same):
```

```gradle
  baseStyleConfig.back.complement(checkstyleMain)
  baseStyleConfig.back.complement(pmdMain)
```

> If using both, PMD should be run after Checkstyle, since Checkstyle is "lighter".  
> A complete example in [basecode project - back project](https://github.com/gmullerb/basecode/tree/master/back).

#### Groovy

##### Codenarc plugin

```gradle
  codenarc {
    config = baseStyleConfig.common.codenarcConfig
  }
```

```text
  or (short way, exactly the same):
```

```gradle
  baseStyleConfig.common.complement(codenarc)
```

##### Codenarc task

```gradle
  codenarcMain {
    config = baseStyleConfig.common.codenarcConfig
  }
```

```text
  or (short way, exactly the same):
```

```gradle
  baseStyleConfig.common.complement(codenarcMain)
```

> Gradle and Groovy have almost the same set of rules, define in the same file, see [gradle-rules.groovy](https://github.com/gmullerb/base-style-config/blob/master/config/gradle/gradle-rules.groovy).  
> A complete example in [file-lister project - build.gradle file](https://github.com/gmullerb/file-lister/blob/master/build.gradle).

### Frontend configuration

ESLint:

1. Add a config parameter to the respective ESLint script task, e.g.:
- eslint script task: `someEslintTask`.
- config parameter: `eslintConfigFile`

```json
  "scripts": {
    "someESlintTask": "eslint --config ${npm_config_eslintConfigFile} ..",
  },
```

2. Set the config parameter in the respective gradle NpmTask task:

```gradle
  task assessSomeESLint(type: NpmTask) {
    // NpmTask task settings
    args = ['someESlintTask', baseStyleConfig.front.eslintNpmConfigArg]
  }
```

Same for Stylelint:

`package.json`:

```json
  "scripts": {
    "someStylelintTask": "stylelint --config ${npm_config_stylelintConfigFile} ..",
  },
```

`build.gradle`:

```gradle
  task assessSomeStylelint(type: NpmTask) {
    // NpmTask task settings
    args = ['someStylelintTask', baseStyleConfig.front.stylelintNpmConfigArg]
  }
```

> A complete example in [basecode project - front project](https://github.com/gmullerb/basecode/tree/master/front).

### Gradle configuration

1. Configure plugin:

```gradle
  codenarc {
    config = baseStyleConfig.common.codenarcConfig
  }
```

2. Define a Codenarc task to check all gradle files in the project tree, e.g.: [file-lister project - build.gradle file - assessGradle task](https://github.com/gmullerb/file-lister/blob/master/build.gradle).


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

### Building

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
- `src/test/groovy`: Test code files[1].

> [1] Tests are done with [JUnit](http://junit.org) and [Mockito](http://javadoc.io/page/org.mockito/mockito-core/latest/org/mockito/Mockito.html).

### Convention over Configuration

All `all.shared.gradle` plugins define two classes:

* _PluginName_**Plugin**: which contains the class implements `Plugin` interface.

* _PluginName_**Extension**: which represent the extension of the plugin.

All `all.shared.gradle` plugins have two **`static`** members:

* `String EXTENSION_NAME`: This will have the name of the extension that the plugin add.
  * if the plugin does not add an extension the this field will not exist.

* `boolean complement(final Project project)`: will apply the plugin and return true if successful, false otherwise.
  * this methods is **exactly equivalent to the instance `apply` method**, but without instantiate the class if not required.

Both may be useful when applying the plugin when creating custom plugins.

## Documentation

* [`CHANGELOG.md`](CHANGELOG.md): add information of notable changes for each version here, chronologically ordered [1].

> [1] [Keep a Changelog](http://keepachangelog.com)

## License

[MIT License](/LICENSE.txt)

## Additional words

Don't forget:

* **Love what you do**.
* **Learn everyday**.
* **Share your knowledge**.
* **Learn from the past, dream on the future, live and enjoy the present to the max!**.
