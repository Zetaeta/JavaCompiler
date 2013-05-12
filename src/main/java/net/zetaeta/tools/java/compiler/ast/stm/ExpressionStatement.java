package net.zetaeta.tools.java.compiler.ast.stm;

import java.util.List;

import net.zetaeta.tools.java.compiler.ast.TreeNode;
import net.zetaeta.tools.java.compiler.ast.expr.Expression;

import com.google.common.collect.Lists;

public class ExpressionStatement extends Statement {
    protected Expression expression;
    
    public ExpressionStatement(Expression exp) {
        expression = exp;
    }

    @Override
    protected List<TreeNode> getChildList() {
        return Lists.newArrayList(expression);
    }
}
