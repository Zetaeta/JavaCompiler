package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

public class TypeName {
    
    private String name;
    private List<GenericObjectParameter> params;
    
    public TypeName(String name) {
        this.name = name;
    }
    
    public TypeName(String name, List<GenericObjectParameter> params) {
        this.params = params;
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
}
