package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

import net.zetaeta.tools.java.compiler.ast.expr.Expression;

public class VariableDeclaration extends AnnotatedTreeNode {
    protected TypeName type;
    protected String name;
    protected Expression value;
    protected VariableDeclaration(Modifiers mods, List<Annotation> annot) {
        super(mods, annot);
        // TODO Auto-generated constructor stub
    }
}
