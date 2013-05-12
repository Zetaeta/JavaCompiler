package net.zetaeta.tools.java.compiler.ast.expr;

import java.util.ArrayList;
import java.util.List;

import net.zetaeta.tools.java.compiler.ast.TreeNode;

public class Expression extends TreeNode {

    @Override
    protected List<TreeNode> getChildList() {
        return new ArrayList<>();
    }
}
