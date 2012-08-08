package net.zetaeta.tools.java.compiler.parser;

import java.io.ObjectOutputStream.PutField;
import java.util.Arrays;

import net.zetaeta.tools.java.compiler.parser.Token.Type;

public class Lexer {
    
    /* Current character in input stream */
    private char ch;
    
    /* Index in input stream */
    private int index;
    
    /* Character stream under compilation */
    private char[] stream;
    
    /* Current token */
    private Token currentToken;
    
    /* Buffer for literals, identifier names etc. */
    private char[] buffer = new char[64];
    
    /* Current position in buffer. */
    private int bufferPos;
    
    private boolean error;
    
    private String bufferStringCache;
    private int lastHash;
    
    public Lexer(char[] source) {
        this.stream = source;
    }
    
    public Lexer(String source) {
        this(source.toCharArray());
    }
    
    protected void error(String s) {
        System.err.println(s);
        error = true;
        currentToken = Token.EOF;
    }
    
    protected char nextChar() {
        ch = stream[index++];
        if (ch == '\\') {
            convertUnicode();
        }
        return ch;
    }
    
    protected void convertUnicode() {
        char codePoint = 0;
        int i=0;
        if (stream[index++] != 'u') {
            putBack();
            return;
        }
        for (; i<=4 && isHexDigit(ch = stream[index++]); ++i) {
            codePoint |= ch << (4 - i);
        }
        if (i != 4 ) {
            error("Invalid unicode escape code");
        }
        ch = codePoint;
    }
    
    protected void putBack() {
        --index;
    }
    
    protected void addChar(char c) {
        if (bufferPos >= buffer.length) {
            buffer = Arrays.copyOf(buffer, buffer.length * 2);
        }
        buffer[bufferPos++] = c;
    }
    
    protected void clearBuffer() {
        // Actually clearing the buffer isn't necessary, is it?
        bufferPos = 0;
    }
    
    protected String bufferToString() {
        char[] bufferCopy = Arrays.copyOf(buffer, bufferPos);
        if (bufferStringCache == null || lastHash != Arrays.hashCode(bufferCopy)) {
            bufferStringCache = new String(bufferCopy);
            lastHash = Arrays.hashCode(bufferCopy);
        }
        return bufferStringCache;
    }
    
    public Token token() {
        return currentToken;
    }
    
    public Token nextToken() {
        if (error) {
            return Token.EOF;
        }
        while (true) {
            if (index == stream.length) {
                return (currentToken = Token.EOF);
            }
            nextChar();
            switch (ch) {
            case ' ': case '\t': case '\r': case '\n':
                continue;
            case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g':
            case 'h': case 'i': case 'j': case 'k': case 'l': case 'm': case 'n':
            case 'o': case 'p': case 'q': case 'r': case 's': case 't': case 'u':
            case 'v': case 'w': case 'x': case 'y': case 'z':
            case 'A': case 'B': case 'C': case 'D': case 'E': case 'F': case 'G':
            case 'H': case 'I': case 'J': case 'K': case 'L': case 'M': case 'O':
            case 'P': case 'Q': case 'R': case 'S': case 'T': case 'U': case 'V':
            case 'W': case 'X': case 'Y': case 'Z':
            case '$': case '_':
                scanIdentifier();
                break;
            case '0':
                nextChar();
                if (ch == 'x' || ch == 'X') {
                    scanHexNumber();
                }
                else {
                    putBack();
                    scanOctalNumber();
                }
                break;
            case '1': case '2': case '3': case '4': case '5': case '6': case '7':
            case '8': case '9':
                scanDecimalNumber();
                break;
            case '"':
                scanStringLiteral();
                break;
            case '\'':
                scanCharLiteral();
                break;
            case ';':
                currentToken = Token.SEMICOLON;
                break;
            case ',':
                currentToken = Token.COMMA;
                break;
            case '.':
                if (nextChar() == '.') {
                    if (nextChar() == '.') {
                        currentToken = Token.ELLIPSIS;
                        break;
                    }
                    else {
                        putBack();
                        putBack();
                    }
                }
                else {
                    putBack();
                }
                currentToken = Token.DOT;
                break;
            case '@':
                currentToken = Token.AT;
                break;
            case '+':
                switch (nextChar()) {
                case '+':
                    currentToken = Token.PLUSPLUS;
                    break;
                case '=':
                    currentToken = Token.PLUSEQ;
                    break;
                default:
                    putBack();
                    currentToken = Token.PLUS;
                    break;
                }
                break;
            case '-':
                switch (nextChar()) {
                case '-':
                    currentToken = Token.MINUSMINUS;
                    break;
                case '=':
                    currentToken = Token.MINUSEQ;
                    break;
                default:
                    putBack();
                    currentToken = Token.MINUS;
                    break;
                }
                break;
            case '%':
                if (nextChar() == '=') {
                    currentToken = Token.PERCENTEQ;
                    break;
                }
                putBack();
                currentToken = Token.PERCENT;
                break;
            case '*':
                if (nextChar() == '=') {
                    currentToken = Token.STAREQ;
                    break;
                }
                putBack();
                currentToken = Token.STAR;
                break;
            case '/':
                switch (nextChar()) {
                case '=':
                    currentToken = Token.SLASHEQ;
                    break;
                case '*':
                    skipComment();
                    break;
                default:
                    putBack();
                    currentToken = Token.SLASH;
                    break;
                }
                break;
            case '=':
                if (nextChar() == '=') {
                    currentToken = Token.EQEQ;
                    break;
                }
                putBack();
                currentToken = Token.EQ;
                break;
            case '!':
                if (nextChar() == '=') {
                    currentToken = Token.EXCLAMEQ;
                    break;
                }
                putBack();
                currentToken = Token.EXCLAM;
                break;
            case '<':
                switch(nextChar()) {
                case '<':
                    if (nextChar() == '=') {
                        currentToken = Token.LTLTEQ;
                        break;
                    }
                    putBack();
                    currentToken = Token.LTLT;
                    break;
                case '=':
                    currentToken = Token.LTEQ;
                    break;
                default:
                    putBack();
                    currentToken = Token.LT;
                    break;
                }
                break;
            case '>':
                switch(nextChar()) {
                case '>':
                    switch(nextChar()) {
                    case '>':
                        if (nextChar() == '=') {
                            currentToken = Token.GTGTGTEQ;
                            break;
                        }
                        putBack();
                        currentToken = Token.GTGTGT;
                        break;
                    case '=':
                        currentToken = Token.GTGTEQ;
                        break;
                    default:
                        putBack();
                        currentToken = Token.GTGT;
                        break;
                    }
                    break;
                case '=':
                    currentToken = Token.GTEQ;
                    break;
                default:
                    putBack();
                    currentToken = Token.GT;
                    break;
                }
                break;
            case '&':
                switch (nextChar()) {
                case '&':
                    currentToken = Token.AMPAMP;
                    break;
                case '=':
                    currentToken = Token.AMPEQ;
                    break;
                default:
                    putBack();
                    currentToken = Token.AMP;
                    break;
                }
                break;
            case '|':
                switch (nextChar()) {
                case '|':
                    currentToken = Token.PIPEPIPE;
                    break;
                case '=':
                    currentToken = Token.PIPEEQ;
                    break;
                default:
                    putBack();
                    currentToken = Token.PIPE;
                    break;
                }
                break;
            case '^':
                if (nextChar() == '=') {
                    currentToken = Token.CARETEQ;
                    break;
                }
                putBack();
                currentToken = Token.CARET;
                break;
            case '~':
                currentToken = Token.TILDE;
                break;
            case '?':
                currentToken = Token.QUERY;
                break;
            case ':':
                currentToken = Token.COLON;
                break;
            case '(':
                currentToken = Token.LPAREN;
                break;
            case ')':
                currentToken = Token.RPAREN;
                break;
            case '[':
                currentToken = Token.LBRACKET;
                break;
            case ']':
                currentToken = Token.RBRACKET;
                break;
            case '{':
                currentToken = Token.LBRACE;
                break;
            case '}':
                currentToken = Token.RBRACE;
                break;
            default:
                currentToken = Token.ERROR;
                nextChar();
            }
            return currentToken;
        }
    }
    
    protected void scanIdentifier() {
        while (isIdentifierCharacter(ch, false)) {
            addChar(ch);
            if (index >= stream.length) {
                break;
            }
            System.out.println("About to read char, index = " + index + ", stream.length = " + stream.length);
            nextChar();
        }
        putBack();
        Type type = Keywords.lookup(bufferToString());
        if (type != Type.IDENTIFIER) {
            currentToken = new Token(type);
        }
        else {
            currentToken = new Token(type, bufferToString());
        }
        clearBuffer();
    }
    
    protected boolean isIdentifierCharacter(char c, boolean firstLetter) {
        if (firstLetter) {
            switch(c) {
            case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g':
            case 'h': case 'i': case 'j': case 'k': case 'l': case 'm': case 'n':
            case 'o': case 'p': case 'q': case 'r': case 's': case 't': case 'u':
            case 'v': case 'w': case 'x': case 'y': case 'z':
            case 'A': case 'B': case 'C': case 'D': case 'E': case 'F': case 'G':
            case 'H': case 'I': case 'J': case 'K': case 'L': case 'M': case 'O':
            case 'P': case 'Q': case 'R': case 'S': case 'T': case 'U': case 'V':
            case 'W': case 'X': case 'Y': case 'Z':
            case '$': case '_':
                return true;
            default:
                return false;
            }
        }
        else {
            switch(c) {
            case 'a': case 'b': case 'c': case 'd': case 'e': case 'f': case 'g':
            case 'h': case 'i': case 'j': case 'k': case 'l': case 'm': case 'n':
            case 'o': case 'p': case 'q': case 'r': case 's': case 't': case 'u':
            case 'v': case 'w': case 'x': case 'y': case 'z':
            case 'A': case 'B': case 'C': case 'D': case 'E': case 'F': case 'G':
            case 'H': case 'I': case 'J': case 'K': case 'L': case 'M': case 'O':
            case 'P': case 'Q': case 'R': case 'S': case 'T': case 'U': case 'V':
            case 'W': case 'X': case 'Y': case 'Z':
            case '$': case '_':
            case '0': case '1': case '2': case '3': case '4': case '5': case '6':
            case '7': case '8': case '9':
                return true;
            default:
                return false;
            }
        }
    }
    
    protected void skipComment() {
        while (true) {
            if (nextChar() == '*') {
                if (nextChar() == '/') {
                    return;
                }
                putBack();
            }
        }
    }
    
    protected final boolean isHexDigit(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }
    
    
    protected boolean isNumericalLiteralModifier(char c) {
        switch(c) {
        case 'l': case 'L':
        case 'f': case 'F':
        case 'd': case 'D':
            return true;
        default:
            return false;
        }
    }
    
    protected void scanHexNumber() {
        nextChar();
        Type type = Type.INT_LITERAL;
        while (isHexDigit(ch) || ch == '_') {
            if (ch == '_') {
                nextChar();
                continue;
            }
            addChar(ch);
            if (index >= stream.length) {
                break;
            }
            nextChar();
        }
        if (index < stream.length) {
            switch (ch) {
            case 'l': case 'L':
                type = Type.LONG_LITERAL;
                break;
            default:
                putBack();
                break;
            }
        }
        switch(type) {
        case INT_LITERAL:
            currentToken = new Token(type, Integer.parseInt(bufferToString(), 16));
            break;
        case LONG_LITERAL:
            currentToken = new Token(type, Long.parseLong(bufferToString(), 16));
            break;
        default:
            throw new IllegalStateException();
        }
        clearBuffer();
    }
    
    protected boolean isOctalDigit(char c) {
        return c > '0' && c < '9';
    }
    
    protected void scanOctalNumber() {
        nextChar();
        Type type = Type.INT_LITERAL;
        boolean dotEncountered = false;
        while (isOctalDigit(ch) || ch == '_' || ch == '.') {
            if (ch == '_') {
                nextChar();
                continue;
            }
            if (ch == '.') {
                if (dotEncountered) {
                    error("Invalid floating point number!");
                    return;
                }
                type = Type.DOUBLE_LITERAL;
            }
            addChar(ch);
            if (index >= stream.length) {
                break;
            }
            nextChar();
        }
        if (index < stream.length) { // If this was at the end of file there is no character after the last digit.
            switch (ch) {
            case 'l': case 'L':
                if (type == Type.DOUBLE_LITERAL) {
                    putBack();
                    break;
                }
                type = Type.LONG_LITERAL;
                break;
            case 'f': case 'F':
                type = Type.FLOAT_LITERAL;
                break;
            case 'd': case 'D':
                type = Type.DOUBLE_LITERAL;
                break;
            default:
                putBack();
                break;
            }
        }
        switch (type) {
        case INT_LITERAL:
            currentToken = new Token(type, Integer.parseInt(bufferToString(), 8));
            break;
        case LONG_LITERAL:
            currentToken = new Token(type, Long.parseLong(bufferToString(), 8));
            break;
        case FLOAT_LITERAL:
            currentToken = new Token(type, Float.parseFloat(bufferToString()));
            break;
        case DOUBLE_LITERAL:
            currentToken = new Token(type, Double.parseDouble(bufferToString()));
            break;
        default:
            throw new IllegalStateException();
        }
        clearBuffer();
    }
    
    protected void scanDecimalNumber() {
        Type type = Type.INT_LITERAL;
        while (Character.isDigit(ch) || ch == '_' || ch == '.') {
            if (ch == '_') {
                nextChar();
                continue;
            }
            if (ch == '.') {
                if (type == Type.DOUBLE_LITERAL) {
                    error("Invalid floating point literal");
                    return;
                }
                type = Type.DOUBLE_LITERAL;
            }
            addChar(ch);
            if (index >= stream.length) {
                break;
            }
            nextChar();
        }
        if (index < stream.length) {
            switch(ch) {
            case 'l': case 'L':
                if (type == Type.DOUBLE_LITERAL) {
                    error("Invalid long literal");
                    return;
                }
                type = Type.LONG_LITERAL;
                break;
            case 'f': case 'F':
                type = Type.FLOAT_LITERAL;
                break;
            case 'd': case 'D':
                type = Type.DOUBLE_LITERAL;
                break;
            }
        }
        switch (type) {
        case INT_LITERAL:
            currentToken = new Token(type, Integer.parseInt(bufferToString()));
            break;
        case LONG_LITERAL:
            currentToken = new Token(type, Long.parseLong(bufferToString()));
            break;
        case FLOAT_LITERAL:
            currentToken = new Token(type, Long.parseLong(bufferToString()));
            break;
        case DOUBLE_LITERAL:
            currentToken = new Token(type, Long.parseLong(bufferToString()));
            break;
        default:
            throw new IllegalStateException();
        }
        clearBuffer();
    }
    
    protected char convertEscapes() {
        if (ch == '\\') {
            switch (nextChar()) {
            case 'n':       // Line feed
                return '\n';
            case 't':       // Tab
                return '\t';
            case 'f':       // Form feed
                return '\f';
            case 'r':       // Carriage return
                return '\r';
            case 'b':       // Backspace
                return '\b';
            case '\'': // '
                return '\'';
            case '\"':
                return '"';
            case '0':
                return '\0';
            default:
                error("Invalid escape sequence");
                return 0;
            }
        }
        else {
            return ch;
        }
    }
    
    protected void scanCharLiteral() {
        nextChar();
        char lit = convertEscapes();
        if (nextChar() != '\'') {
            error("Invalid char literal");
            return;
        }
        currentToken = new Token(Type.CHAR_LITERAL, lit);
    }
    
    protected void scanStringLiteral() {
        while (nextChar() != '"') {
            addChar(convertEscapes());
        }
        currentToken = new Token(Type.STRING_LITERAL, bufferToString());
        clearBuffer();
    }
}
