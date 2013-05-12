package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.List;

import net.zetaeta.tools.java.compiler.ast.expr.Expression;

public class Field extends Member {
    protected Expression initializer;

    public Field(String name, TypeName type, Modifiers mods,
            List<Annotation> annot) {
        super(name, type, mods, annot);
    }
    
    public Field(String name, TypeName type, Modifiers mods,
            List<Annotation> annot, Expression init) {
        this(name, type, mods, annot);
        initializer = init;
    }
    
    @Override
    protected List<TreeNode> getChildList() {
        List<TreeNode> list = new ArrayList<>();
        list.addAll(super.getChildList());
        if (initializer != null) {
            list.add(initializer);
        }
        return list;
    }

}
