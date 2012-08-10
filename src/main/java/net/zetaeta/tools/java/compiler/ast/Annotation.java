package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class Annotation extends TreeNode {
    private String name;
    private List<Expression> parameters;
    
    public Annotation(String name) {
        this.name = name;
    }
    
    public Annotation(String name, List<Expression> parameters) {
        this(name);
        this.parameters = parameters;
    }
    
    @Override
    protected List<TreeNode> getChildList() {
        return new ArrayList<TreeNode>(parameters);
    }
    
}
