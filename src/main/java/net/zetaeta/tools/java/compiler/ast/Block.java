package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.List;

import net.zetaeta.tools.java.compiler.ast.stm.Statement;

public class Block extends TreeNode {

    protected List<Statement> statements;
    
    public Block(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    protected List<TreeNode> getChildList() {
        return new ArrayList<TreeNode>(statements);
    }
}
