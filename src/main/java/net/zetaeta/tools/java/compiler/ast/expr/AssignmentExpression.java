package net.zetaeta.tools.java.compiler.ast.expr;


public class AssignmentExpression extends Expression {
    protected Expression assigned;
    protected Expression to;
    
    public AssignmentExpression(Expression ass, Expression to) {
        assigned = ass;
        this.to = to;
    }
}
