package net.zetaeta.tools.java.compiler.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterDeclaration extends AnnotatedTreeNode {
    protected TypeName type;
    protected String name;
    
    
    protected Map<?, ?> getAttributes() {
        Map<Object, Object> m = new HashMap<>();
        m.putAll(super.getAttributes());
        m.put("Type", type);
        m.put("Name", name);
        return m;
    }
    
    public ParameterDeclaration(TypeName t, String n, List<Annotation> annot) {
        super(new Modifiers(), annot);
        type = t;
        name = n;
    }
}
