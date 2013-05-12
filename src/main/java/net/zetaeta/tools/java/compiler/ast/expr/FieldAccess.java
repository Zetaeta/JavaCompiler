package net.zetaeta.tools.java.compiler.ast.expr;

import net.zetaeta.tools.java.compiler.parser.Token;

public class FieldAccess extends FieldOrMethodAccess {

    public FieldAccess(Token name) {
        super(name);
    }
    
    public FieldAccess(Token name, FieldOrMethodAccess next) {
        super(name, next);
    }
    
}
