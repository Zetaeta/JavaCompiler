package net.zetaeta.tools.java.compiler.ast;

import java.util.Collections;
import java.util.List;

public class IntLiteral extends Literal {
    
    @Override
    protected List<TreeNode> getChildList() {
        return Collections.emptyList();
    }
}
