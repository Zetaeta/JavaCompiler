package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

public class Member extends AnnotatedTreeNode {
    protected String name;
    protected TypeName type;
    protected List<TypeName> exceptions;
}
