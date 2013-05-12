package net.zetaeta.tools.java.compiler.ast.expr;

import net.zetaeta.tools.java.compiler.parser.Token;

/**
 * An individual access to a field or method. Can be chained, for example:
 * {@code
 * Class.field.value.method1("meow").polygon.doStuff(4, -2);
 * }
 * would be stored as:
 * FieldAccess(name="Class", next=FieldAccess(name="field", next=FieldAccess(name="value", 
 * next=MethodAccess(name="method1", parameters=(...), next=FieldAccess(name="polygon, next=MethodAccess(name="doStuff", ...))))))
 * @author daniel
 *
 */
public abstract class FieldOrMethodAccess extends Expression {
    Token name;
    FieldOrMethodAccess next;
    
    public FieldOrMethodAccess(Token name) {
        this(name, null);
    }
    
    public FieldOrMethodAccess(Token name, FieldOrMethodAccess next) {
        this.name = name;
        this.next = next;
    }
    
    public void setNext(FieldOrMethodAccess n) {
        next = n;
    }
}
