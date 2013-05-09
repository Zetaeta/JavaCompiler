package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

public class TreeNodeWithModifiers extends TreeNode {
    protected int modifiers;
    
    public void addModifier(int mod) {
        modifiers |= mod;
    }
    
    public void removeModifier(int mod) {
        modifiers &= ~mod;
    }
    
    public boolean hasModifier(int mod) {
        return (modifiers & mod) > 0;
    }
    
    @Override
    protected List<TreeNode> getChildList() {
        // TODO Auto-generated method stub
        return null;
    }
}
