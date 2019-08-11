# Base Style Config Wrapper Change Log

## 3.0.0 - August 2019

* Breaking changes:
  * Removes `stylelintConfig`.
  * Renames `BaseStyleConfigWrapperExtension` `back` field to `java`, it gives more accurate context.
  * Renames `BaseStyleConfigWrapperExtension` `front` field to `js`, it gives more accurate context.
  * Renames `BackCodeStyleConfig` class to `JavaCodeStyleConfig`.
    * Extracts `codenarcConfig` from `JavaCodeStyleConfig` to `GroovyCodeStyleConfig`.
      * Adds `groovy` to `BaseStyleConfigWrapperExtension` class.
  * Renames `FrontCodeStyleConfig` class to `JsCodeStyleConfig`.
* Substitutes getting `base-style-config` from using Maven Artifact to direct download to avoid resolving `configuration` prematurely.
* Adds `tsEslintConfig`.
* Upgrades versions.
* Updates VERSIONS_COMPATIBILITY table.
* Updates README file.

## 2.0.0 - April 2019

* Breaking changes:
  * Moves groovy configuration from `CommonCodeStyleConfig` to `JavaCodeStyleConfig`.
  * Automatically sets pmd, codenarc, checkstyle version, when properties `CHECKSTYLE_VERSION`, `PMD_VERSION` & `CODENARC_VERSION` are present, respectively.
  * Automatically complements pmd, codenarc, checkstyle when `autoComplement` is set to true (which is the default).
* Upgrades versions.
* Updates README file.

## 1.0.2 - March 2019

* Updates main code to work with base-style-config:2.0.0.
* Updates test code to use [spy-project-factory](https://github.com/gmullerb/spy-project-factory).
* Removes redundant getter code.
* Adds Gitlab CI.
* Adds Versions Compatibility Table.
* Upgrades Gradle to version 5.3.
* Updates README file.

## 1.0.1 - October 2018

* Adds two new static fields to the `BaseStyleConfigWrapperPlugin`.
* Renames `CodeStyleConfig` to `BaseStyleConfigWrapperExtension`.
* Fixes some log information
* Updates README file.
