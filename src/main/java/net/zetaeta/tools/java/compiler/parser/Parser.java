package net.zetaeta.tools.java.compiler.parser;

import java.util.ArrayList;
import java.util.List;

import net.zetaeta.tools.java.compiler.ast.Annotation;
import net.zetaeta.tools.java.compiler.ast.AnnotationDeclaration;
import net.zetaeta.tools.java.compiler.ast.ClassDeclaration;
import net.zetaeta.tools.java.compiler.ast.ClassOrInterfaceDeclaration;
import net.zetaeta.tools.java.compiler.ast.CompilationUnit;
import net.zetaeta.tools.java.compiler.ast.EnumDeclaration;
import net.zetaeta.tools.java.compiler.ast.InterfaceDeclaration;
import net.zetaeta.tools.java.compiler.ast.Modifiers;
import net.zetaeta.tools.java.compiler.parser.Token.Type;

public class Parser {
    
    private Lexer lexer;
    private List<Annotation> annotationCache = new ArrayList<>();
    
    public Parser(Lexer lexer) {
        
    }
    
    public CompilationUnit parseCompilationUnit() throws ParsingException {
        CompilationUnit comp = new CompilationUnit();
        lexer.nextToken();
        if (lexer.token() == Token.PACKAGE) {
            parsePackageDeclaration(comp);
        }
        if (lexer.token() == Token.IMPORT) {
            parseImports(comp);
        }
        while (lexer.nextToken() != Token.EOF) {
            comp.addDeclaration(parseClassOrInterface());
        }
    }
    
    protected void match(Type type) throws ParsingException {
        if (lexer.token().getType() != type) {
            throw new ParsingException("Unexpected " + lexer.token() + ", expected " + type);
        }
    }
    
    protected void parseAnnotations() throws ParsingException {
        match(Type.AT);
        lexer.nextToken();
        match(Type.IDENTIFIER);
        String ident = lexer.token().stringValue();
        if (lexer.nextToken().getType() == Type.LPAREN) {
            while (lexer.nextToken() != Token.RPAREN) {
//                Literal literal = parseLiteral();
                // TODO: Parse expression
                
            }
        }
    }
    
    protected void parsePackageDeclaration(CompilationUnit comp) throws ParsingException {
        match(Type.PACKAGE);
        StringBuilder name = new StringBuilder();
        while (lexer.nextToken().getType() == Type.IDENTIFIER) {
            name.append(lexer.token().stringValue());
            if (lexer.nextToken() == Token.DOT) {
                name.append('.');
                continue;
            }
            lexer.nextToken();
            break;
        }
        match(Type.SEMICOLON);
        lexer.nextToken();
        comp.setPackage(name.toString());
    }
    
    protected void parseImports(CompilationUnit comp) throws ParsingException {
        while (lexer.token() == Token.IMPORT) {
            StringBuilder name = new StringBuilder();
            while (lexer.nextToken().getType() == Type.IDENTIFIER) {
                name.append(lexer.token().stringValue());
                if (lexer.nextToken() == Token.DOT) {
                    name.append('.');
                    lexer.nextToken();
                    continue;
                }
                match(Type.SEMICOLON);
                lexer.nextToken();
                break;
            }
            comp.addImport(name.toString());
        }
    }
    
//    protected Literal parseLiteral() throws ParsingException {
//        Token token = lexer.token();
//        switch (token.getType()) {
//        case INT_LITERAL:
//            return new IntLiteral(token.intValue());
//        case LONG_LITERAL:
//            return new LongLiteral(token.longValue());
//        case FLOAT_LITERAL:
//            return new FloatLiteral(token.floatValue());
//        case DOUBLE_LITERAL:
//            return new DoubleLiteral(token.doubleValue());
//        case CHAR_LITERAL:
//            return new CharLiteral(token.charValue());
//        case STRING_LITERAL:
//            return new StringLiteral(token.stringValue());
//        }
//    }
    
    protected Modifiers parseModifiers() throws ParsingException {
        Modifiers mods = new Modifiers();
        while (lexer.nextToken().getType() != Type.IDENTIFIER) {
            switch (lexer.token().getType()) {
            case PUBLIC:
                mods.addFlag(Modifiers.PUBLIC);
                break;
            case PROTECTED:
                mods.addFlag(Modifiers.PROTECTED);
                break;
            case PRIVATE:
                mods.addFlag(Modifiers.PRIVATE);
                break;
            case STATIC:
                mods.addFlag(Modifiers.STATIC);
                break;
            case FINAL:
                mods.addFlag(Modifiers.FINAL);
                break;
            case ABSTRACT:
                mods.addFlag(Modifiers.ABSTRACT);
                break;
            case TRANSIENT:
                mods.addFlag(Modifiers.TRANSIENT);
                break;
            case VOLATILE:
                mods.addFlag(Modifiers.VOLATILE);
                break;
            case INTERFACE:
                mods.addFlag(Modifiers.INTERFACE);
                break;
            case ENUM:
                mods.addFlag(Modifiers.ENUM);
                break;
            case AT:
                if (lexer.nextToken() == Token.INTERFACE) {
                    mods.addFlag(Modifiers.ANNOTATION);
                }
                else {
                    Token next;
                    if ((next = lexer.nextToken()).getType() != Type.IDENTIFIER) {
                        throw new ParsingException("Expected 'interface' or identifier after '@'");
                    }
                    String name = parseDottedIdentifier();
                }
            }
        }
        return mods;
    }
    
    protected ClassOrInterfaceDeclaration parseClassOrInterface() throws ParsingException {
        parseAnnotations();
        parseModifiers();
        ClassOrInterfaceDeclaration decl;
        switch (lexer.token().getType()) {
        case CLASS:
            decl = new ClassDeclaration();
            break;
        case INTERFACE:
            decl = new InterfaceDeclaration();
            break;
        case ENUM:
            decl = new EnumDeclaration();
            break;
        case AT:
            lexer.nextToken();
            match(Type.INTERFACE);
            decl = new AnnotationDeclaration();
        }
    }
    
    protected String parseDottedIdentifier() {
        StringBuilder sb = new StringBuilder();
        Token tok;
        while ((tok = lexer.token()).getType() == Type.IDENTIFIER) {
            sb.append(tok.stringValue());
            if (lexer.nextToken() == Token.DOT) {
                sb.append('.');
                lexer.nextToken();
            }
            lexer.nextToken();
            break;
        }
        return sb.toString();
    }
}
