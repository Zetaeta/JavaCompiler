package net.zetaeta.tools.java.compiler.ast.expr;

import java.util.List;

import net.zetaeta.tools.java.compiler.parser.Token;

public class MethodAccess extends FieldOrMethodAccess {
    
    protected List<Expression> parameters;

    public MethodAccess(Token name, List<Expression> params) {
        this(name, params, null);
    }
    
    public MethodAccess(Token name, List<Expression> params, FieldOrMethodAccess next) {
        super(name, next);
        parameters = params;
    }
    
}
