<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# vscode-theme Changelog

## Unreleased

### Changed
- Supporting 2025.x

## 1.11.0 - 2024-12-17

### Added

- Support k2 compiler

### Changed

- Removing Deprecated APIs
- Min intellj Platform SDK is now 242
- JDK version to 21
- #210 - Categorize async Python keyword as a primary keyword (Thanks @j-d-ha)

## 1.10.13 - 2024-11-23

### Added

- Init Ruby Annotator

### Fixed

- #201 - Rust mut keyword inconsistent colors
- Welcome screen - Project actions - background fixed

### Changed

- Updating library versions and syncing with template

## 1.10.12 - 2024-10-26

### Added

- CSharp - Adding yield and foreach to provide secondary color.

## 1.10.11 - 2024-07-21

### Fixed

- 194 - Fixing Yield key word for Pycharm

### Changed

- Syncing with Template Changes - Upgrading to latest version of Intellij

## 1.10.10 - 2024-02-04

### Fixed

- VScode Light Modern — fix xml foreground color purple to blue(0000ff same as VSCode) by @trofoto
- VScode Light Modern — fix the colors of tooltip in the editor, especially the background color with same color with text by @trofoto

## 1.10.9 - 2024-01-21

### Fixed:

- Removed internal APIs - So we can release v1.10.8

## 1.10.8 - 2023-12-17

### Fixed:

- VScode Light Modern — The icon color of RunWidge is too light to see; by @trofoto
- VScode Light Modern — The background color of RecentProject is too dark. by @trofoto
- Dart Secondary Color Highlighting issues fixed
- Replaced Deprecated APIs

## 1.10.7 - 2023-10-30

### Fixed

- [#159](https://github.com/dinbtechit/vscode-theme/issues/159) by @trofoto

### Changed

- @trofoto - Added colors const for following official light theme style 
- @trofoto - change several selection-about colors, including selection color in editor, global selection background color and foreground color. It's more compatible with the Intellij Light theme, also closer with VS code light theme;

## 1.10.6 - 2023-10-16

### Fixed

- #139 - Python Jupyter - Removed black background in Jupyter (Had to make a Temp fix might not be perfect.)

## 1.10.5 - 2023-10-16

### Changed

- Removed Enum usage as it is not supported in PyCharm, CLion and RubyMine 2022.x.x
- Upgrading Gradle - `8.1` to `8.3`

### Fixed

- #130 - VScode startup activity error - Specific to PyCharm, CLion and RubyMine.
- #132 - Java -Wrong colors if there is extends or super keyword inside generic of generic.
- #139 - Python Jupyter - Removed black background in Jupyter (Had to make a Temp fix might not be perfect.)
- VSCode Light Theme - Toolbar background was white and hard to see the buttons.
- #150 - Unable to select the default theme DARK_MODERN
- #151 - java.lang.NoClassDefFoundError JupyterPyDialect

## 1.10.4 - 2023-10-15

### Changed

- Removed Enum usage as it is not supported in PyCharm, CLion and RubyMine 2022.x.x
- Upgrading Gradle - `8.1` to `8.3`

### Fixed

- #130 - VScode startup activity error - Specific to PyCharm, CLion and RubyMine.
- #132 - Java -Wrong colors if there is extends or super keyword inside generic of generic.
- #139 - Python Jupyter - Removed black background in Jupyter (Had to make a Temp fix might not be perfect.)
- VSCode Light Theme - Toolbar background was white and hard to see the buttons.

## 1.10.3 - 2023-10-01

### Changed

- Keeping in Sync with IntelliJ Template changes
- Updated Rust plugin from (Deprecated)`org.rust.lang` to `com.jetbrains.rust`.
- Removing deprecated API usages - Unfortunately can no longer support 2021.1.x
- Adding more logging to triage issue #123

## 1.10.2 - 2023-08-22

### Fixed

- #103 - Fix for "Array contains no element matching the predicate"

## 1.10.1 - 2023-08-21

### Changed

- #100 - removing rainbow bracket - can be installed separately if needed.

## 1.10.0 - 2023-08-18

### Added

- Rainbow Brackets plugin will now be bundled within VSCode theme
- #84 - Functionality to report bug through IDE error reporting tool

### Fixed

- Icon colors are messed up in VSCode light theme

## 1.9.1 - 2023-08-13

### Changed

- #91 - operator and `@decorator` colors Python
- Python - Keeping vscode light theme inline with dark modern theme.

## 1.9.0 - 2023-08-13

### Added

- #66 - VSCode Light Modern theme - Initial

## 1.8.6 - 2023-08-07

### Changed

- Removing experimental update on typing
- Removing experimental Dumbaware as there are not visible improvements.

## 1.8.5 - 2023-07-06

### Fixed

- #79 - Adding separate color settings for Python imports (Part 2)

## 1.8.4 - 2023-07-06

### Fixed

- #77 - Pycharm Community edition - Inconsistent colors
- #79 - Adding separate color settings for Python imports

## 1.8.3 - 2023-06-29

### Changed

- #71 - Python - annotation docstring optimization.

## 1.8.2 - 2023-06-29

### Fixed

- #71 - Python - Inconsistent - Import and variables colors (initial)

## 1.8.1 - 2023-06-25

- #67 - Fix inactive selection color in Dark Modern theme

## 1.8.0 - 2023-05-17

- #59 - VSCode Dark Modern Theme Initial
- [Chores] - Updating links
- [Chores] - syncing with Intellij Templates

## 1.7.9 - 2023-05-07

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
