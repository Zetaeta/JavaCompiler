package net.zetaeta.tools.java.compiler.ast;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNodeWithModifiers extends TreeNode {
    protected Modifiers mods;
    
    public TreeNodeWithModifiers(Modifiers mods) {
        this.mods = mods;
    }
    
    public Modifiers getModifiers() {
        return mods;
    }
    
    @Override
    protected Map<?, ?> getAttributes() {
        Map<Object, Object> m = new HashMap<>();
        m.putAll(super.getAttributes());
        m.put("Modifiers", mods.toString());
        return m;
    }
    
    @Override
    protected List<TreeNode> getChildList() {
        // TODO Auto-generated method stub
        return Collections.emptyList();
    }
}
