package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class Expression extends TreeNode {

    @Override
    protected List<TreeNode> getChildList() {
        return new ArrayList<>();
    }
}
