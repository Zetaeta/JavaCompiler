package net.zetaeta.tools.java.compiler.ast;

import java.util.List;

public class GenericTypeParameter extends GenericParameter {
    protected String typeName;
    protected List<TypeName> extending;
    
    public GenericTypeParameter(String typeName) {
        this.typeName = typeName;
    }
    
    public GenericTypeParameter(String typeName, List<TypeName> extending) {
        this(typeName);
        this.extending = extending;
    }
    
}
