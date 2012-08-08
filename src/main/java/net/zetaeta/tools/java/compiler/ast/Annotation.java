package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

public class Annotation extends TreeNode {
    private String name;
    private List<Literal> parameters;
}
