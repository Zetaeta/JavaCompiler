package net.zetaeta.tools.java.compiler.parser;

public class Token {
    
    public enum Type {
        
        /*
         * Specials
         */
        EOF,
        ERROR,
        IDENTIFIER,
        
        /*
         * Reserved words
         */
        ABSTRACT("abstract"),
        ASSERT("assert"),
        BOOLEAN("boolean"),
        BREAK("break"),
        BYTE("byte"),
        CASE("case"),
        CATCH("catch"),
        CHAR("char"),
        CLASS("class"),
        CONST("const"),
        DO("do"),
        DOUBLE("double"),
        ENUM("enum"),
        EXTENDS("extends"),
        FALSE("false"),
        FINAL("final"),
        FLOAT("float"),
        FOR("for"),
        GOTO("goto"),
        IF("if"),
        INT("int"),
        INTERFACE("interface"),
        IMPLEMENTS("implements"),
        IMPORT("import"),
        INSTANCEOF("instanceof"),
        LONG("long"),
        NATIVE("native"),
        NEW("new"),
        NULL("null"),
        PACKAGE("package"),
        PRIVATE("private"),
        PROTECTED("protected"),
        PUBLIC("public"),
        RETURN("return"),
        SHORT("short"),
        STATIC("static"),
        STRICTFP("strictfp"),
        SUPER("super"),
        SWITCH("switch"),
        SYNCHRONIZED("synchronized"),
        THIS("this"),
        THROW("throw"),
        THROWS("throws"),
        TRANSIENT("transient"),
        TRUE("true"),
        TRY("try"),
        VOID("void"),
        VOLATILE("volatile"),
        WHILE("while"),
        
        /*
         * Literals
         */
        INT_LITERAL,
        LONG_LITERAL,
        FLOAT_LITERAL,
        DOUBLE_LITERAL,
        CHAR_LITERAL,
        STRING_LITERAL,
        
        /*
         * Other symbols
         */
        LPAREN("("),
        RPAREN(")"),
        LBRACE("{"),
        RBRACE("}"),
        LBRACKET("["),
        RBRACKET("]"),
        SEMICOLON(";"),
        COLON(":"),
        COMMA(","),
        DOT("."),
        ELLIPSIS("..."),
        EQ("="),
        EQEQ("=="),
        EXCLAM("!"),
        EXCLAMEQ("!="),
        GT(">"),
        LT("<"),
        GTEQ(">="),
        LTEQ("<="),
        PLUS("+"),
        MINUS("-"),
        STAR("*"),
        SLASH("/"),
        PLUSPLUS("++"),
        MINUSMINUS("--"),
        PLUSEQ("+="),
        MINUSEQ("-="),
        STAREQ("*="),
        SLASHEQ("/="),
        LTLT("<<"),
        GTGT(">>"),
        GTGTGT(">>>"),
        LTLTEQ("<<="),
        GTGTEQ(">>="),
        GTGTGTEQ(">>>="),
        PIPE("|"),
        AMP("&"),
        PIPEPIPE("||"),
        AMPAMP("&&"),
        CARET("^"),
        PIPEEQ("|="),
        AMPEQ("&="),
        TILDE("~"),
        PERCENT("%"),
        CARETEQ("^="),
        PERCENTEQ("%="),
        AT("@"),
        QUERY("?")
        ;
        
        private String name;
        
        private Type() {
           this(null); 
        }
        
        private Type(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        
        public String toString() {
            return name != null ? name : "";
        }
    }
    
    private Type type;
    private String stringValue;
    private int intValue;
    private long longValue;
    private float floatValue;
    private double doubleValue;
    private boolean booleanValue;
    private char charValue;
    
    public Token(Type type) {
        this.type = type;
    }
    
    public Token(Type type, String value) {
        this(type);
        this.stringValue = value;
    }
    
    public Token(Type type, int value) {
        this(type);
        intValue = value;
    }
    
    public Token(Type type, long value) {
        this(type);
        longValue = value;
    }
    
    public Token(Type type, float value) {
        this(type);
        floatValue = value;
    }
    
    public Token(Type type, double value) {
        this(type);
        doubleValue = value;
    }
    
    public Token(Type type, char value) {
        this(type);
        charValue = value;
    }
    
    public Type getType() {
        return type;
    }
    
    public int intValue() {
        return intValue;
    }
    
    public long longValue() {
        return longValue;
    }
    
    public float floatValue() {
        return floatValue;
    }
    
    public double doubleValue() {
        return doubleValue;
    }
    
    public String stringValue() {
        return stringValue;
    }
    
    public char charValue() {
        return charValue;
    }
    
    @Override
    public String toString() {
        switch (type) {
        case IDENTIFIER:
            return type.name() + '(' + stringValue + ')';
        case INT_LITERAL:
            return type.name() + '(' + intValue + ')';
        case LONG_LITERAL:
            return type.name() + '(' + longValue + ')';
        case FLOAT_LITERAL:
            return type.name() + '(' + floatValue + ')';
        case DOUBLE_LITERAL:
            return type.name() + '(' + doubleValue + ')';
        case CHAR_LITERAL:
            return type.name() + "('" + charValue + "')";
        case STRING_LITERAL:
            return type.name() + "(\"" + stringValue + "\")";
        default:
            return type.name();
        }
    }
    
    public static final Token EOF = new Token(Type.EOF),
                              ERROR = new Token(Type.ERROR),
                              IDENTIFIER = new Token(Type.IDENTIFIER),
                              ABSTRACT = new Token(Type.ABSTRACT),
                              ASSERT = new Token(Type.ASSERT),
                              BOOLEAN = new Token(Type.BOOLEAN),
                              BREAK = new Token(Type.BREAK),
                              BYTE = new Token(Type.BYTE),
                              CASE = new Token(Type.CASE),
                              CATCH = new Token(Type.CATCH),
                              CHAR = new Token(Type.CHAR),
                              CLASS = new Token(Type.CLASS),
                              CONST = new Token(Type.CONST),
                              DO = new Token(Type.DO),
                              DOUBLE = new Token(Type.DOUBLE),
                              ENUM = new Token(Type.ENUM),
                              EXTENDS = new Token(Type.EXTENDS),
                              FALSE = new Token(Type.FALSE),
                              FINAL = new Token(Type.FINAL),
                              FLOAT = new Token(Type.FLOAT),
                              FOR = new Token(Type.FOR),
                              GOTO = new Token(Type.GOTO),
                              IF = new Token(Type.IF),
                              INT = new Token(Type.INT),
                              INTERFACE = new Token(Type.INTERFACE),
                              IMPLEMENTS = new Token(Type.IMPLEMENTS),
                              IMPORT = new Token(Type.IMPORT),
                              INSTANCEOF = new Token(Type.INSTANCEOF),
                              LONG = new Token(Type.LONG),
                              NATIVE = new Token(Type.NATIVE),
                              NEW = new Token(Type.NEW),
                              NULL = new Token(Type.NULL),
                              PACKAGE = new Token(Type.PACKAGE),
                              PRIVATE = new Token(Type.PRIVATE),
                              PROTECTED = new Token(Type.PROTECTED),
                              PUBLIC = new Token(Type.PUBLIC),
                              RETURN = new Token(Type.RETURN),
                              SHORT = new Token(Type.SHORT),
                              STATIC = new Token(Type.STATIC),
                              STRICTFP = new Token(Type.STRICTFP),
                              SUPER = new Token(Type.SUPER),
                              SWITCH = new Token(Type.SWITCH),
                              SYNCHRONIZED = new Token(Type.SYNCHRONIZED),
                              THIS = new Token(Type.THIS),
                              THROW = new Token(Type.THROW),
                              THROWS = new Token(Type.THROWS),
                              TRANSIENT = new Token(Type.TRANSIENT),
                              TRUE = new Token(Type.TRUE),
                              TRY = new Token(Type.TRY),
                              VOID = new Token(Type.VOID),
                              VOLATILE = new Token(Type.VOLATILE),
                              WHILE = new Token(Type.WHILE),
                              INT_LITERAL = new Token(Type.INT_LITERAL),
                              LONG_LITERAL = new Token(Type.LONG_LITERAL),
                              FLOAT_LITERAL = new Token(Type.FLOAT_LITERAL),
                              DOUBLE_LITERAL = new Token(Type.DOUBLE_LITERAL),
                              CHAR_LITERAL = new Token(Type.CHAR_LITERAL),
                              STRING_LITERAL = new Token(Type.STRING_LITERAL),
                              LPAREN = new Token(Type.LPAREN),
                              RPAREN = new Token(Type.RPAREN),
                              LBRACE = new Token(Type.LBRACE),
                              RBRACE = new Token(Type.RBRACE),
                              LBRACKET = new Token(Type.LBRACKET),
                              RBRACKET = new Token(Type.RBRACKET),
                              SEMICOLON = new Token(Type.SEMICOLON),
                              COLON = new Token(Type.COLON),
                              COMMA = new Token(Type.COMMA),
                              DOT = new Token(Type.DOT),
                              ELLIPSIS = new Token(Type.ELLIPSIS),
                              EQ = new Token(Type.EQ),
                              EQEQ = new Token(Type.EQEQ),
                              EXCLAM = new Token(Type.EXCLAM),
                              EXCLAMEQ = new Token(Type.EXCLAMEQ),
                              GT = new Token(Type.GT),
                              LT = new Token(Type.LT),
                              GTEQ = new Token(Type.GTEQ),
                              LTEQ = new Token(Type.LTEQ),
                              PLUS = new Token(Type.PLUS),
                              MINUS = new Token(Type.MINUS),
                              STAR = new Token(Type.STAR),
                              SLASH = new Token(Type.SLASH),
                              PLUSPLUS = new Token(Type.PLUSPLUS),
                              MINUSMINUS = new Token(Type.MINUSMINUS),
                              PLUSEQ = new Token(Type.PLUSEQ),
                              MINUSEQ = new Token(Type.MINUSEQ),
                              STAREQ = new Token(Type.STAREQ),
                              SLASHEQ = new Token(Type.SLASHEQ),
                              LTLT = new Token(Type.LTLT),
                              GTGT = new Token(Type.GTGT),
                              GTGTGT = new Token(Type.GTGTGT),
                              LTLTEQ = new Token(Type.LTLTEQ),
                              GTGTEQ = new Token(Type.GTGTEQ),
                              GTGTGTEQ = new Token(Type.GTGTGTEQ),
                              PIPE = new Token(Type.PIPE),
                              AMP = new Token(Type.AMP),
                              PIPEPIPE = new Token(Type.PIPEPIPE),
                              AMPAMP = new Token(Type.AMPAMP),
                              CARET = new Token(Type.CARET),
                              PIPEEQ = new Token(Type.PIPEEQ),
                              AMPEQ = new Token(Type.AMPEQ),
                              TILDE = new Token(Type.TILDE),
                              PERCENT = new Token(Type.PERCENT),
                              CARETEQ = new Token(Type.CARETEQ),
                              PERCENTEQ = new Token(Type.PERCENTEQ),
                              AT = new Token(Type.AT),
                              QUERY = new Token(Type.QUERY);

}
