# LiteCode

This Android code editor now includes comprehensive syntax highlighting for Java, Kotlin,C, C++ and Python programming languages.

## Features

### Syntax Highlighting
- **Java (.java files)**: Full syntax highlighting with keywords, types, modifiers, comments, and strings
- **Kotlin (.kt files)**: Complete syntax highlighting with Kotlin-specific keywords and features
- **Python (.py files)**: Enhanced syntax highlighting (existing functionality improved)
- **C (.c files) and C++(.cpp files)**: Accurate syntax highlighting for procedural and object-oriented constructs 
- **Automatic detection**: File type detection based on file extension

### Supported Language Elements

#### Java
- **Keywords**: `public`, `private`, `protected`, `static`, `final`, `abstract`, `class`, `interface`, `extends`, `implements`, etc.
- **Types**: `String`, `Integer`, `Double`, `List`, `ArrayList`, `Map`, `HashMap`, etc.
- **Modifiers**: `public`, `private`, `protected`, `static`, `final`, `abstract`, etc.
- **Comments**: Single-line (`//`) and multi-line (`/* */`)
- **Strings**: Double quotes (`"`) and single quotes (`'`)

#### Kotlin
- **Keywords**: `fun`, `val`, `var`, `class`, `data`, `object`, `companion`, `suspend`, `inline`, etc.
- **Types**: `String`, `Int`, `Double`, `List`, `MutableList`, `Map`, `MutableMap`, etc.
- **Modifiers**: `public`, `private`, `internal`, `open`, `final`, `sealed`, `data`, etc.
- **Comments**: Single-line (`//`) and multi-line (`/* */`)
- **Strings**: Double quotes (`"`), single quotes (`'`), and triple quotes (`"""`)

#### Python
- **Keywords**: `def`, `class`, `if`, `else`, `for`, `while`, `try`, `except`, etc.
- **Types**: `str`, `int`, `float`, `bool`, `list`, `tuple`, `dict`, `set`, etc.
- **Modifiers**: `async`, `await`, `staticmethod`, `classmethod`, etc.
- **Comments**: Hash symbol (`#`)
- **Strings**: Single quotes (`'`), double quotes (`"`), and triple quotes (`"""`)

#### C
- **Keywords**: 'int', 'char', 'float', 'double', 'if', 'else', 'for', 'while', 'return', 'void', 'struct', 'typedef', etc.
- **Types**: int, char, float, double, long, short, unsigned, etc.
- **Modifiers**: const, volatile, static, extern, register
- **Comments**: Single-line (//) and multi-line (/* */)
- **Strings**: Double quotes (") and character literals (')

### C++
- **Keywords**: All C keywords plus class, namespace, template, public, private, protected, virtual, new, delete, try, catch, throw, etc.
- **Types**: int, float, double, bool, string, vector, map, unordered_map, etc.
- **Modifiers** : const, static, mutable, inline, virtual, explicit, etc.
- **Comments**: Same as C
- **Strings**: Same as C, plus support for std::string and raw string literals (R"(text)")

## How It Works

### File Type Detection
The editor automatically detects file types based on file extensions:
- `.java` → Java syntax highlighting
- `.kt` → Kotlin syntax highlighting  
- `.py` → Python syntax highlighting
-  '.c' → C syntax highlighting
-  '.cpp' → C++ syntax highlighting
- Default fallback → Python syntax highlighting

### Syntax Highlighting Engine
The `SyntaxHighlighter` class processes text and applies appropriate colors:
- **Comments**: Green (dark mode: lighter green)
- **Strings**: Red (dark mode: orange)
- **Keywords**: Blue and bold (dark mode: blue)
- **Types**: Teal and bold (dark mode: lighter teal)
- **Modifiers**: Navy and bold (dark mode: yellow)

### Color Schemes
- **Light Mode**: Traditional IDE colors
- **Dark Mode**: VS Code-inspired dark theme colors

## Usage

1. **Create a new file** with `.java`, `.kt`, or `.py` extension
2. **Open an existing file** - syntax highlighting will be automatically applied
3. **Switch between light and dark modes** using the theme toggle in the drawer

## Technical Implementation

### Files Modified/Created
- `SyntaxRules.kt` - Enhanced data class with types and modifiers
- `SyntaxHighlighter.kt` - New syntax highlighting engine
- `MainActivity.kt` - Updated to use file type detection
- `java.json` - Java syntax rules
- `kotlin.json` - Kotlin syntax rules
- `python.json` - Enhanced Python syntax rules
- 'c.json' – C syntax rules
- 'cpp.json' – C++ syntax rules

### Key Components
- **SyntaxRules**: Data class containing language-specific rules
- **SyntaxHighlighter**: Core highlighting logic with regex-based pattern matching
- **File Type Detection**: Automatic syntax rule loading based on file extension
- **Real-time Highlighting**: Syntax highlighting updates as you type

## Sample Files
Sample code files are included in the `assets` folder:
- `sample.java` - Java example with various syntax elements
- `sample.kt` - Kotlin example with modern language features
- `sample.py` - Python example with comprehensive language features
- sample.c – Demonstrates C syntax: functions, structs, loops
sample.cpp – Demonstrates C++ syntax: classes, templates, exception handling

## Notes
- Syntax highlighting is applied in real-time as you type
- The editor maintains all existing functionality (file management, compilation, etc.)
- Only syntax highlighting has been enhanced - no other features were modified
- Performance optimized with efficient regex patterns and caching
