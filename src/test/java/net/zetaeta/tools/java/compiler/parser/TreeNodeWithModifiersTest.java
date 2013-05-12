package net.zetaeta.tools.java.compiler.parser;

import java.util.Collections;
import java.util.List;

import net.zetaeta.tools.java.compiler.ast.Modifiers;
import net.zetaeta.tools.java.compiler.ast.TreeNode;

public class TreeNodeWithModifiersTest extends TreeNode {
    protected Modifiers mods;
    
    public TreeNodeWithModifiersTest(Modifiers mods) {
        this.mods = mods;
    }
    
    public Modifiers getModifiers() {
        return mods;
    }
    
    @Override
    protected List<TreeNode> getChildList() {
        // TODO Auto-generated method stub
        return null;
    }
}
