package net.zetaeta.tools.java.compiler.ast;

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
}
