package net.zetaeta.tools.java.compiler.parser;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.zetaeta.tools.java.compiler.ast.Annotation;
import net.zetaeta.tools.java.compiler.ast.AnnotationDeclaration;
import net.zetaeta.tools.java.compiler.ast.Block;
import net.zetaeta.tools.java.compiler.ast.ClassDeclaration;
import net.zetaeta.tools.java.compiler.ast.ClassOrInterfaceDeclaration;
import net.zetaeta.tools.java.compiler.ast.CompilationUnit;
import net.zetaeta.tools.java.compiler.ast.Constructor;
import net.zetaeta.tools.java.compiler.ast.EnumDeclaration;
import net.zetaeta.tools.java.compiler.ast.Field;
import net.zetaeta.tools.java.compiler.ast.GenericObjectParameter;
import net.zetaeta.tools.java.compiler.ast.GenericTypeParameter;
import net.zetaeta.tools.java.compiler.ast.InterfaceDeclaration;
import net.zetaeta.tools.java.compiler.ast.Member;
import net.zetaeta.tools.java.compiler.ast.Method;
import net.zetaeta.tools.java.compiler.ast.Modifiers;
import net.zetaeta.tools.java.compiler.ast.ParameterDeclaration;
import net.zetaeta.tools.java.compiler.ast.SolidMethod;
import net.zetaeta.tools.java.compiler.ast.TypeName;
import net.zetaeta.tools.java.compiler.ast.WildcardGenericParameter;
import net.zetaeta.tools.java.compiler.ast.expr.AssignmentExpression;
import net.zetaeta.tools.java.compiler.ast.expr.Expression;
import net.zetaeta.tools.java.compiler.ast.expr.FieldAccess;
import net.zetaeta.tools.java.compiler.ast.expr.FieldOrMethodAccess;
import net.zetaeta.tools.java.compiler.ast.expr.MethodAccess;
import net.zetaeta.tools.java.compiler.ast.expr.NullExpression;
import net.zetaeta.tools.java.compiler.ast.stm.ExpressionStatement;
import net.zetaeta.tools.java.compiler.ast.stm.ReturnStatement;
import net.zetaeta.tools.java.compiler.ast.stm.Statement;
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
    
    protected List<Annotation> parseAnnotations() throws ParsingException {
        return null;
    }
    
    protected Annotation parseAnnotation() throws ParsingException {
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
        return null;
    }
    
    protected void parsePackageDeclaration(CompilationUnit comp) throws ParsingException {
        match(Type.PACKAGE);
        lexer.nextToken();
        String name = parseDottedIdentifier();
        System.out.println(name);
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
        while (lexer.token().getType() != Type.IDENTIFIER && lexer.token() != Token.EOF) {
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
            case CLASS:
                mods.addFlag(Modifiers.CLASS);
                break;
            case AT:
                Token next = lexer.nextToken();
                if (next == Token.INTERFACE) {
                    mods.addFlag(Modifiers.ANNOTATION);
                }
                else {
                    if (next.getType() != Type.IDENTIFIER) {
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
            lexer.nextToken();
        }
        return mods;
    }
    
    protected ClassOrInterfaceDeclaration parseClassOrInterface() throws ParsingException {
        List<Annotation> annots = parseAnnotations();
        Modifiers mods = parseModifiers();
        System.out.println(mods.getModifiers());
        if (mods.hasFlag(Modifiers.CLASS)) {
            return parseClass(mods, annots);
        }
        else if (mods.hasFlag(Modifiers.INTERFACE)) {
            return parseInterface(mods, annots);
        }
        else if (mods.hasFlag(Modifiers.ENUM)) {
            return parseEnum(mods, annots);
        }
        else if (mods.hasFlag(Modifiers.ANNOTATION)) {
            return parseAnnotationDeclaration(mods, annots);
        }
        else {
            throw new ParsingException("Class, interface or annotation required!");
        }
    }
    
    protected String parseDottedIdentifier() {
        StringBuilder sb = new StringBuilder();
        Token tok;
        while ((tok = lexer.token()).getType() == Type.IDENTIFIER || tok == Token.THIS) {
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
    
    protected List<ParameterDeclaration> parseParameterListDeclaration() throws ParsingException {
        match(Type.LPAREN);
        List<ParameterDeclaration> params = new ArrayList<>();
        lexer.nextToken();
        while (lexer.token() != Token.RPAREN) {
//            lexer.nextToken();
            params.add(parseParameterDeclaration());
            if (lexer.token() != Token.COMMA && lexer.token() != Token.RPAREN) {
                throw new ParsingException("Invalid token in parameter list: " + lexer.token());
            }
        }
        lexer.nextToken();
        return params;
    }
    
    protected List<Expression> parseParameterList() throws ParsingException {
        match(Type.LPAREN);
        List<Expression> expressions = new ArrayList<>();
        lexer.nextToken();
        while (lexer.token() != Token.RPAREN) {
            expressions.add(parseExpression());
            if (lexer.token() != Token.COMMA && lexer.token() != Token.RPAREN) {
                throw new ParsingException("Invalid token in parameter list: " + lexer.token());
            }
        }
        return expressions;
    }
    
    protected ParameterDeclaration parseParameterDeclaration() throws ParsingException {
        TypeName typeName = parseTypeName();
        
        match(Type.IDENTIFIER);
        String name = lexer.token().stringValue();
        lexer.nextToken();
        System.out.println("parse param decl: next token = " + lexer.token());
        return new ParameterDeclaration(typeName, name, null);
    }
    
    protected Expression parseExpression() throws ParsingException {
        System.out.println("parseExpression: token = " + lexer.token());
        List<Expression> subExpressions = new ArrayList<>();
        while (lexer.token() != Token.SEMICOLON && lexer.token() != Token.COMMA &&
                lexer.token() != Token.RPAREN && lexer.token() != Token.RBRACKET) { // possible expression enders.
            Expression sub = parseSubExpression();
            switch (lexer.token().getType()) {
            case EQ:
                lexer.nextToken();
                Expression equals = parseExpression();
                return new AssignmentExpression(sub, equals);
            default:
                System.out.println("default: token = " + lexer.token());
            }
        }
        return new Expression();
    }
    
    protected Expression parseSubExpression() throws ParsingException {
        System.out.println("parseSub: token = " + lexer.token());
        switch (lexer.token().getType()) {
        case IDENTIFIER:
        case THIS:
            FieldOrMethodAccess access = parseMethodCallOrVarAccess();
            return access;
        case NULL:
            lexer.nextToken();
            return new NullExpression();
        }
        return null;
        
    }
    
    protected ClassDeclaration parseClass(Modifiers mods, List<Annotation> annots) throws ParsingException {
        match(Type.IDENTIFIER);
        String className = lexer.token().stringValue();
        Token tok = lexer.nextToken();
        List<GenericTypeParameter> genericParameters;
        if (tok == Token.LT) {
            genericParameters = parseGenericTypeParameters();
        }
        else {
            genericParameters = Collections.emptyList();
        }
        TypeName superClass;
        List<TypeName> interfaces;
        if (tok == Token.EXTENDS) {
            lexer.nextToken();
            superClass = parseTypeName();
        }
        else {
            superClass = new TypeName("java.lang.Object");
        }
        tok = lexer.token();
        if (tok == Token.IMPLEMENTS) {
            interfaces = parseInterfaces();
        }
        else {
            interfaces = Collections.emptyList();
        }
        match(Type.LBRACE);
        lexer.nextToken();
        List<Member> members = new ArrayList<>();
        while (lexer.token() != Token.RBRACE) {
            Member member = parseMember();
            members.add(member);
        }
        ClassDeclaration cls = new ClassDeclaration(className, mods, annots, genericParameters, superClass, interfaces, members);
        System.out.println(cls);
        return cls;
    }
    
    protected Member parseMember() throws ParsingException {
        System.out.println("parseMember");
        List<Annotation> annot = parseAnnotations();
        Modifiers memberMods = parseModifiers();
        List<GenericTypeParameter> genParams;
        if (lexer.token() == Token.LT) {
            genParams = parseGenericTypeParameters();
        }
        else {
//o            genParams = Collections.emptyList();
            genParams = null;
        }
        TypeName type = parseTypeName();
        if (lexer.token() == Token.LPAREN) {
            return parseConstructor(type, memberMods, annot, genParams);
        }
        match(Type.IDENTIFIER);
        String name = lexer.token().stringValue();
        System.out.println("name = " + name); if (lexer.nextToken() == Token.LPAREN) {
            return parseMethod(memberMods, annot, genParams, type, name);
        }
        System.out.println("parseMember: token = " + lexer.token());
        if (genParams != null) {
            throw new ParsingException("A field cannot have generic type parameters!");
        }
        return parseField(name, type, memberMods, annot);
    }
    
    public Constructor parseConstructor(TypeName type, Modifiers mods, List<Annotation> annot, List<GenericTypeParameter> genParams) throws ParsingException {
        match(Type.LPAREN);
        List<ParameterDeclaration> params = parseParameterListDeclaration();
        List<TypeName> exceptions;
        if (lexer.token() == Token.THROWS) {
            exceptions = parseMethodThrows();
        }
        else {
            exceptions = Collections.emptyList();
        }
        Block body = parseBlock();
        return new Constructor(type, mods, annot, genParams, params, exceptions, body);
    }
    
    protected Method parseMethod(Modifiers mod, List<Annotation> annot, List<GenericTypeParameter> genParams, TypeName returns, String name) throws ParsingException {
        System.out.println("parseMethod");
        match(Type.LPAREN);
        List<ParameterDeclaration> params = parseParameterListDeclaration();
        List<TypeName> exceptions;
        if (lexer.token() == Token.THROWS) {
            exceptions = parseMethodThrows();
        }
        else {
            exceptions = Collections.emptyList();
        }
        Block body = parseBlock();
        return new SolidMethod(name, returns, mod, annot, genParams, params, exceptions, body);
    }
    
    protected List<TypeName> parseMethodThrows() throws ParsingException {
        List<TypeName> exceptions = new ArrayList<>();
        match(Type.THROWS);
        while (lexer.token() != Token.LBRACE) {
            exceptions.add(parseTypeName());
            if (lexer.token() == Token.COMMA) {
                lexer.nextToken();
            }
            else {
                match(Type.LBRACE);
            }
        }
        return exceptions;
    }
    
    protected Field parseField(String name, TypeName type, Modifiers mods, List<Annotation> annot) throws ParsingException {
        System.out.println("parseField");
        if (lexer.token() == Token.SEMICOLON) {
            return new Field(name, type, mods, annot);
        }
        Expression initializer = parseExpression();
        return new Field(name, type, mods, annot, initializer);
    }
    
    protected Block parseBlock() throws ParsingException {
        System.out.println("parseBlock");
        match(Type.LBRACE);
        List<Statement> statements = new ArrayList<>();
        lexer.nextToken();
        Token tok;
        while ((tok = lexer.token()) != Token.RBRACE) {
            System.out.println("parseBlock: tok = " + tok);
            switch (tok.getType()) {
            case IDENTIFIER:
            case THIS:
                statements.add(parseNonCtrlFlowStatement());
                break;
            case RETURN:
                statements.add(parseReturnStatement());
                break;
            case EOF:
                throw new ParsingException(new EOFException());
            default:
                System.out.println("parseBlock: default: tok = " + tok);
            }
        }
        lexer.nextToken();
        System.out.println("finished parsing block!");
        return new Block(statements);
    }
    
    protected Statement parseNonCtrlFlowStatement() throws ParsingException {
        Token first = lexer.token();
        if (!possibleFieldName(first)) {
            throw new ParsingException("Unexpected " + first.getType() + ", expected field or method name!");
        }
        Token second = lexer.nextToken();
        if (lexer.token().getType() == Type.IDENTIFIER || lexer.token() == Token.LT) { // var declaration
//            return parseVariableDeclaration(first);
            return null;
        }
        else { // field or method access
            System.out.println("parseNonCtrlFlowStatement: first = " + first + ", second = " + second);
            lexer.putBack(first);
            ExpressionStatement st = new ExpressionStatement(parseExpression());
            match(Type.SEMICOLON);
            lexer.nextToken();
            return st;
        }
    }
    
    protected ReturnStatement parseReturnStatement() throws ParsingException {
        System.out.println("parseReturnStatement");
        match(Type.RETURN);
        lexer.nextToken();
        Expression returns = parseExpression();
        match(Type.SEMICOLON);
        lexer.nextToken();
        System.out.println("finished return");
        return new ReturnStatement(returns);
    }
    
    public boolean possibleFieldName(Token tok) {
        if (tok == Token.THIS || tok == Token.CLASS) {
            return true;
        }
        if (tok.getType() == Type.IDENTIFIER) {
            return true;
        }
        return false;
    }
    
/*    protected Statement parseMethodCallOrVarAccessOrVarDeclaration() throws ParsingException {
        Token first = lexer.token();
        if (!possibleFieldName(first)) {
            throw new ParsingException("Unexpected " + first.getType() + ", expected field or method name!");
        }
        lexer.nextToken();
        if (lexer.token().getType() == Type.IDENTIFIER) { // var declaration
            
        }
        else { // field or method access
            parseMethodCallOrVarAccess(first);
        }
    }*/
    
    protected FieldOrMethodAccess parseMethodCallOrVarAccess() throws ParsingException {
        Token first = lexer.token();
        System.out.println("pMCOVA: first = " + first);
        FieldOrMethodAccess access;
        if (lexer.token() == Token.LPAREN) {
            List<Expression> parameters = parseParameterList();
            access = new MethodAccess(first, parameters);
        }
        else {
            access = new FieldAccess(first);
            lexer.nextToken();
        }
        if (lexer.token() == Token.DOT) {
            access.setNext(parseMethodCallOrVarAccess());
        }
        System.out.println("pMCOVA: token = " + lexer.token());
        return access;
    }
    
    protected List<TypeName> parseInterfaces() throws ParsingException {
        match(Type.IMPLEMENTS);
        lexer.nextToken();
        List<TypeName> interfaces = new ArrayList<>();
        while (lexer.token().getType() == Type.IDENTIFIER) {
            interfaces.add(parseTypeName());
            if (lexer.token() == Token.COMMA) {
                lexer.nextToken();
            }
        }
        return interfaces;
    }
    
    protected List<GenericTypeParameter> parseGenericTypeParameters() throws ParsingException {
        match(Type.LT);
        List<GenericTypeParameter> params = new ArrayList<>();
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
                lexer.nextToken();
                System.out.println("parseTypeName: name = " + name + ", genParams = " + genParams);
                result = new TypeName(name, genParams);
                break;
            }
            result = new TypeName(name);
            break;
        default:
            System.out.println(lexer.token());
            throw new ParsingException("Invalid type name!");
        }
        System.out.println(lexer.token());
        return result;
    }
    
    protected InterfaceDeclaration parseInterface(Modifiers mods, List<Annotation> annots) {
        return null;
    }
    
    protected EnumDeclaration parseEnum(Modifiers mods, List<Annotation> annots) {
        return null;
    }
    
    protected AnnotationDeclaration parseAnnotationDeclaration(Modifiers mods, List<Annotation> annots) {
        return null;
    }
}
