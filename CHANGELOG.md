<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# vscode-theme Changelog

## Unreleased
- #55 - Syntax highlighting for shell scripts (For local variables explicitly)

## 1.7.8 - 2023-03-26
- [Experimental] Apply highlighting when indexing.
- Syncing with Template changes.

## 1.7.7
- #47 - Purple color is inconsistent - Fixed only in VSCode theme. Not affects Brighter theme.
- #49 - Markdown color inconsistencies - ordered and unordered List Text colors

## 1.7.6

### Fixed
- AppCode colors to be consistent with VSCode.

## 1.7.5

### Changed
- GoLang - Package identifier blue instead of white. Maybe slightly off.

### Fixed
- #42 - Fix Startup errors.

## 1.7.4

### Changed
- Default Annotation - Decommissioning Default Annotator due to Issue #38
- Typescript await keyword inconsistency.

## 1.7.3

### Fixed
- Go - improve syntax highlighting

### Changed
- Option to star the github repo

## 1.7.2

### Fixed
- Bug #31 - Inconsistent ANSI Console Colors

### Added
- Adding Default syntax highlighting for base control statements to match vscode
- Adding a basic version of Rust specific syntax highlighting.

## 1.7.1

### Fixed
- Compatibility issues with 2022.3 EAP
- Dart - Required & Function keyword syntax highlighting
- Dart - Skipping syntax highlighting when used within a string literal.

### Changed
- Syncing up with the template changes
- Update popup wording changes

## 1.6.2

### Added
- (Feature #16) - Add proper highlighting for react templates (jsx and tsx)

## 1.6.1

### Fixed
- (Bug #21) GoLang all keywords are purple not same as VScode

## 1.6.0

### Added
- (feature #15) Added extensive syntax highlighting for Go

## 1.5.4

### Fixed
- [Bug #11] - Scroll bar opaque - Hides warning and erros in the right-side gutter

## 1.5.3

### Fixed
- [Bug] - Kotlin - Removing incorrect highlighting for catch

## 1.5.2
- When theme is not selected. Popup will be displayed to apply theme.

## 1.5.1

### Fixed
- Notification startup issue

## 1.5.0

### Added
- (fixes #4) Added extensive syntax highlighting for C, C++, ObjectiveC
- Startup Notification to apply VSCode theme as default

### Fixed
- [Bug] Terminal - Background color to be same as VSCode #5

### Changed
- [Improvement] Java - Annotation Attribute to different color.

## 1.4.0

### Added
- Added extensive syntax highlighting for c#
- Added extensive syntax highlighting for php

### Fixed
- Javascript/Typescript - decorator and local variable color same as VSCode
- Javascript/Typescript - await keyword to be same color as VSCode

## 1.3.0

### Added
- (Experimental) - Introducing Code Editor Theme - VSCode Dark Brighter (5% More brighter than the previous theme).
  With option to switch back to older VSCode Dark theme
- Added extensive syntax highlighting for Kotlin.

### Fixed
- (Bug) - JavaScript - `from` keyword highlight only when used in import context

## 1.2.0

### Added
- Added extensive syntax highlighting for Python

### Fixed
- (Bug) - JavaScript - `try` keyword not correct color

### Changed
- Code Refactoring Removed unused code
- (Improvement) - JS/TS - Static variables and method change to italics & yellow
- (Improvement) - TS - Enum constants color to be same as VSCode.

## 1.1.0

### Added
- Added custom annotators for JavaScript and TypeScript to match VSCode Style
- > Note - This will be used by both JavaScript and TypeScript

### Fixed
- (Bug) - Fixed PSIElement font color & Keywords font color to be same as VSCode.

### Changed
- start highlight onTyping
- Documentation on tips for best viewing experience.

## 1.0.0

### Added
- Added extensive syntax highlighting for Java.
- Added extensive syntax highlighting for Dart/Flutter.

### Fixed
- WelcomeScreen color issues and Toolbar border
