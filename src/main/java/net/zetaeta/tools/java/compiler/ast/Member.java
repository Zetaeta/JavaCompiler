package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Member extends AnnotatedTreeNode {
    protected String name;
    protected TypeName type; // field type or return type.
    
    protected Member(String name, TypeName type, Modifiers mods, List<Annotation> annot) {
        super(mods, annot);
        this.name = name;
        this.type = type;
    }
    
    @Override
    protected Map<?, ?> getAttributes() {
        Map<Object, Object> m = new HashMap<>();
        m.putAll(super.getAttributes());
        m.put("Name", name);
        m.put("Type", type);
        return m;
    }
    
    @Override
    protected List<TreeNode> getChildList() {
        List<TreeNode> list = new ArrayList<>();
        list.addAll(super.getChildList());
        return list;
    }
}
