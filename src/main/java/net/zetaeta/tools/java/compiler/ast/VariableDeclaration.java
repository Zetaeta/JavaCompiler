package net.zetaeta.tools.java.compiler.ast;

public class VariableDeclaration extends AnnotatedTreeNode {
    protected TypeName type;
    protected String name;
    protected Expression value;
}
