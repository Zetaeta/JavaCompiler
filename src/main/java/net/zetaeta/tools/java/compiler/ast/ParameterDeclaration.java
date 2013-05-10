package net.zetaeta.tools.java.compiler.ast;

public class ParameterDeclaration extends AnnotatedTreeNode {
    protected TypeName type;
    protected String name;
    
    public ParameterDeclaration(TypeName t, String n) {
        type = t;
        name = n;
    }
}
