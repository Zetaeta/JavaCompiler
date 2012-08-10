package net.zetaeta.tools.java.compiler.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.zetaeta.tools.java.compiler.ast.Annotation;
import net.zetaeta.tools.java.compiler.ast.AnnotationDeclaration;
import net.zetaeta.tools.java.compiler.ast.ClassDeclaration;
import net.zetaeta.tools.java.compiler.ast.ClassOrInterfaceDeclaration;
import net.zetaeta.tools.java.compiler.ast.CompilationUnit;
import net.zetaeta.tools.java.compiler.ast.EnumDeclaration;
import net.zetaeta.tools.java.compiler.ast.Expression;
import net.zetaeta.tools.java.compiler.ast.GenericObjectParameter;
import net.zetaeta.tools.java.compiler.ast.GenericParameter;
import net.zetaeta.tools.java.compiler.ast.InterfaceDeclaration;
import net.zetaeta.tools.java.compiler.ast.Modifiers;
import net.zetaeta.tools.java.compiler.ast.TypeName;
import net.zetaeta.tools.java.compiler.ast.WildcardGenericParameter;
import net.zetaeta.tools.java.compiler.parser.Token.Type;

public class Parser {
    
    private  Lexer lexer;
    private List<Annotation> annotationCache = new ArrayList<>();
    
    public Parser(Lexer lexer) {
        this.lexer = lexer;
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
        return comp;
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
        String name = parseDottedIdentifier();
//        while (lexer.nextToken().getType() == Type.IDENTIFIER) {
//            name.append(lexer.token().stringValue());
//            if (lexer.nextToken() == Token.DOT) {
//                name.append('.');
//                continue;
//            }
////            lexer.nextToken();
//            break;
//        }
        match(Type.SEMICOLON);
        lexer.nextToken();
        
        comp.setPackage(name);
    }
    
    protected void parseImports(CompilationUnit comp) throws ParsingException {
        do {
            match(Type.IMPORT);
            lexer.nextToken();
            String s = parseDottedIdentifier();
            
            comp.addImport(s);
            match(Type.SEMICOLON);
        } while (lexer.nextToken() == Token.IMPORT);
    }
    
    
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
                    if (lexer.token() == Token.LPAREN) {
                        List<Expression> expressions = parseParameterList();
                        mods.addAnnotation(new Annotation(name, expressions));
                    }
                    else {
                        mods.addAnnotation(new Annotation(name));
                    }
                }
                break;
            default:
                break;
            }
        }
        return mods;
    }
    
    protected ClassOrInterfaceDeclaration parseClassOrInterface() throws ParsingException {
        Modifiers mods = parseModifiers();
        if (mods.hasFlag(Modifiers.CLASS)) {
            return parseClass(mods);
        }
        else if (mods.hasFlag(Modifiers.INTERFACE)) {
            return parseInterface(mods);
        }
        else if (mods.hasFlag(Modifiers.ENUM)) {
            return parseEnum(mods);
        }
        else if (mods.hasFlag(Modifiers.ANNOTATION)) {
            return parseAnnotation(mods);
        }
        else {
            throw new ParsingException("Class, interface or annotation required!");
        }
    }
    
    protected String parseDottedIdentifier() {
        StringBuilder sb = new StringBuilder();
        Token tok;
        while ((tok = lexer.token()).getType() == Type.IDENTIFIER) {
            sb.append(tok.stringValue());
            lexer.nextToken();
            if (lexer.token() == Token.DOT) {
                sb.append('.');
                lexer.nextToken();
            }
            else {
                break;
            }
        }
        return sb.toString();
    }
    
    protected List<Expression> parseParameterList() throws ParsingException {
        match(Type.LPAREN);
        List<Expression> expressions = new ArrayList<>();
        while (lexer.nextToken() != Token.RPAREN) {
//            lexer.nextToken();
            expressions.add(parseExpression());
            if (lexer.token() != Token.COMMA && lexer.token() != Token.RPAREN) {
                throw new ParsingException("Invalid token in parameter list: " + lexer.token());
            }
        }
        return expressions;
    }
    
    protected Expression parseExpression() throws ParsingException {
        return new Expression();
    }
    
    protected ClassDeclaration parseClass(Modifiers mods) throws ParsingException {
        match(Type.IDENTIFIER);
        String className = lexer.token().stringValue();
        Token tok = lexer.nextToken();
        List<GenericParameter> genericParameters;
        if (tok == Token.LT) {
            genericParameters = parseGenericTypeParameters();
        }
        else {
            genericParameters = Collections.emptyList();
        }
        match(Type.LBRACE);
        while (true) {
            lexer.nextToken();
            Modifiers memberMods = parseModifiers();
            List<GenericParameter> genParams;
            if (lexer.token() == Token.LT) {
                genParams = parseGenericTypeParameters();
            }
            else {
                genParams = Collections.emptyList();
            }
            TypeName typeName = parseTypeName();
            
        }
    }
    
    protected List<GenericParameter> parseGenericTypeParameters() throws ParsingException {
        match(Type.LT);
        List<GenericParameter> params = new ArrayList<>();
        while (lexer.nextToken() != Token.GT) {
            match(Type.IDENTIFIER);
            String typeName = lexer.token().stringValue();
            List<TypeName> extending = null;
            if (lexer.nextToken() == Token.EXTENDS) {
                extending = new ArrayList<>();
                while (lexer.nextToken().getType() == Type.IDENTIFIER) {
                    extending.add(parseTypeName());
                    if (lexer.token() != Token.AMP) {
                        break;
                    }
                }
            }
            match(Type.COMMA);
        }
        return params;
    }
    
    protected TypeName parseTypeName() throws ParsingException {
        TypeName result;
        switch (lexer.token().getType()) {
        case VOID:
            result = TypeName.VOID;
            break;
        case BYTE:
            result = TypeName.BYTE;
            break;
        case SHORT:
            result = TypeName.SHORT;
            break;
        case INT:
            result = TypeName.INT;
            break;
        case LONG:
            result = TypeName.LONG;
            break;
        case FLOAT:
            result = TypeName.FLOAT;
            break;
        case DOUBLE:
            result = TypeName.DOUBLE;
            break;
        case CHAR:
            result = TypeName.CHAR;
            break;
        case BOOLEAN:
            result = TypeName.BOOLEAN;
            break;
        case IDENTIFIER:
            String name = parseDottedIdentifier();
            if (lexer.token() == Token.LT) {
                List<GenericObjectParameter> genParams = new ArrayList<>();
                lexer.nextToken();
                while (lexer.token() != Token.GT) {
                    if (lexer.token() == Token.QUERY) {
                        // A wildcard type <?>
                        lexer.nextToken();
                        if (lexer.token() == Token.EXTENDS) {
                            // <? extends Stuff>
                            List<TypeName> extending = new ArrayList<>();
                            while (lexer.nextToken() != Token.COMMA) {
                                extending.add(parseTypeName());
                                match(Type.AMP);
                            }
                            genParams.add(new WildcardGenericParameter(extending));
                        }
                        else if (lexer.token() == Token.SUPER) {
                            // <? super Stuff>
                            List<TypeName> supering = new ArrayList<>();
                            while (lexer.nextToken() != Token.COMMA) {
                                supering.add(parseTypeName());
                                match(Type.AMP);
                            }
                            genParams.add(new WildcardGenericParameter(null, supering));
                        }
                        else {
                            genParams.add(new WildcardGenericParameter());
                            lexer.nextToken();
                        }
                    }
                    else {
                        match(Type.IDENTIFIER);
                        TypeName type = parseTypeName();
                        genParams.add(new GenericObjectParameter(type));
                    }
                }
                result = new TypeName(name, genParams);
                break;
            }
            result = new TypeName(name);
            break;
        default:
            throw new ParsingException("Invalid type name!");
        }
        lexer.nextToken();
        return result;
    }
    
    protected InterfaceDeclaration parseInterface(Modifiers mods) {
        return new InterfaceDeclaration();
    }
    
    protected EnumDeclaration parseEnum(Modifiers mods) {
        return new EnumDeclaration();
    }
    
    protected AnnotationDeclaration parseAnnotation(Modifiers mods) {
        return new AnnotationDeclaration();
    }
}
