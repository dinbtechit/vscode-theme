<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# vscode-theme Changelog

## [Unreleased]
### Fixed
- [Bug #11] - Scroll bar opaque - Hides warning and erros in the right-side gutter 

## [1.5.3]
### Fixed
- [Bug] - Kotlin - Removing incorrect highlighting for catch

## [1.5.2]
- When theme is not selected. Popup will be displayed to apply theme.

## [1.5.1]
### Fixed
- Notification startup issue

## [1.5.0]
### Added
- (fixes #4) Added extensive syntax highlighting for C, C++, ObjectiveC
- Startup Notification to apply VSCode theme as default

### Fixed
- [Bug] Terminal - Background color to be same as VSCode #5

### Changed
- [Improvement] Java - Annotation Attribute to different color.

## [1.4.0]
### Added
- Added extensive syntax highlighting for c#
- Added extensive syntax highlighting for php

### Fixed
- Javascript/Typescript - decorator and local variable color same as VSCode
- Javascript/Typescript - await keyword to be same color as VSCode

## [1.3.0]
### Added
- (Experimental) - Introducing Code Editor Theme - VSCode Dark Brighter (5% More brighter than the previous theme).
  With option to switch back to older VSCode Dark theme
- Added extensive syntax highlighting for Kotlin.

### Fixed
- (Bug) - JavaScript - `from` keyword highlight only when used in import context

## [1.2.0]
### Added
- Added extensive syntax highlighting for Python

### Fixed
- (Bug) - JavaScript - `try` keyword not correct color

### Changed
- Code Refactoring Removed unused code
- (Improvement) - JS/TS - Static variables and method change to italics & yellow 
- (Improvement) - TS - Enum constants color to be same as VSCode.

## [1.1.0]
### Added
- Added custom annotators for JavaScript and TypeScript to match VSCode Style
- Added `Settings` -> `Editor` -> `Color Scheme` - > `JavaScript (Extra)`
  > Note - This will be used by both JavaScript and TypeScript

### Fixed
- (Bug) - Fixed PSIElement font color & Keywords font color to be same as VSCode.

### Changed
- start highlight onTyping
- Documentation on tips for best viewing experience.

## [1.0.0]
### Added
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)

### Added
- Added extensive syntax highlighting for Java.
- Added extensive syntax highlighting for Dart/Flutter.

### Fixed
- WelcomeScreen color issues and Toolbar border
