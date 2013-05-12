package net.zetaeta.tools.java.compiler.ast.stm;

import java.util.List;

import com.google.common.collect.Lists;

import net.zetaeta.tools.java.compiler.ast.TreeNode;
import net.zetaeta.tools.java.compiler.ast.expr.Expression;

public class ReturnStatement extends Statement {
    protected Expression expr;
    
    public ReturnStatement(Expression expr) {
        this.expr = expr;
    }

    @Override
    protected List<TreeNode> getChildList() {
        return Lists.newArrayList(expr);
    }

}
