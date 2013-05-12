package net.zetaeta.tools.java.compiler.ast;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Joiner;

public class TypeName extends TreeNode {
    
    private String name;
    private List<GenericObjectParameter> params;
    
    public TypeName(String name) {
        this.name = name;
    }
    
    public TypeName(String name, List<GenericObjectParameter> params) {
        this(name);
        this.params = params;
    }
    
    public String getName() {
        return name;
    }
    
    public static final TypeName VOID = new TypeName("void"),
                                 BYTE = new TypeName("byte"),
                                 SHORT = new TypeName("short"),
                                 INT = new TypeName("int"),
                                 LONG = new TypeName("long"),
                                 FLOAT = new TypeName("float"),
                                 DOUBLE = new TypeName("double"),
                                 CHAR = new TypeName("char"),
                                 BOOLEAN = new TypeName("boolean");

    @Override
    protected List<TreeNode> getChildList() {
        return Collections.emptyList();
    }
    
    @Override
    public String toString() {
        if (params == null || params.size() == 0) {
            return name;
        }
        System.out.println("TypeName: name = " + name);
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append('<');
        sb.append(Joiner.on(", ").join(params));
        sb.append('>');
        return sb.toString();
    }
}
